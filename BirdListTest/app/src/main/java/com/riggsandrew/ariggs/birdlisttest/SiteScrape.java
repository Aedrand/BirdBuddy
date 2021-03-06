package com.riggsandrew.ariggs.birdlisttest;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Andrew Riggs on 11/14/2017.
 */


public class SiteScrape {

    public static List<Bird> getBirdList() {
        List<Bird> birdList = new ArrayList<>();
        try {
            BirdListRetriever retriever = new BirdListRetriever();
            birdList = retriever.execute("https://www.allaboutbirds.org/guide/browse.aspx?name=a").get();
        } catch (InterruptedException inte) {
            inte.printStackTrace();
        } catch (ExecutionException exe) {
            exe.printStackTrace();
        }
        return birdList;
    }

    public static String getBirdDescription(String inUrl) {
        String result = "";
        try {
            DescriptionRetriever retriever = new DescriptionRetriever();
            result = retriever.execute(inUrl).get();
        } catch (InterruptedException inte) {
            inte.printStackTrace();
        } catch (ExecutionException exe) {
            exe.printStackTrace();
        }
        return result;
    }

    public static BitmapDrawable getBitmapDrawable(Context context, String inUrl) {
        BitmapDrawable drawable = null;
        try {
            ImageRetriever retriever = new ImageRetriever();
            Bitmap bitmap = retriever.execute(inUrl).get();
            drawable = new BitmapDrawable(context.getResources(), bitmap);
        } catch (InterruptedException inte) {
            inte.printStackTrace();
        } catch (ExecutionException exe) {
            exe.printStackTrace();
        }

        System.out.println(drawable);

        return drawable;
    }

    private static class DescriptionRetriever extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                Document document = Jsoup.connect(params[0]).get();
                Elements children = document.select("div#spp_name").get(0).children();
                String p = children.get(4).child(1).text();
                result = p;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return result;
        }
    }

    private static class BirdListRetriever extends AsyncTask<String, Void, List<Bird>> {
        @Override
        protected List<Bird> doInBackground(String... params) {
            List<Bird> birdList = new ArrayList<>();
            try {
                Document document = Jsoup.connect(params[0]).get();
                Elements resultList = document.select("div#species_results").get(0).children();
                System.out.println("HERE " + resultList.get(2).child(1).child(0).text());
                System.out.println("HERE " + resultList.size());
                for(Element element : resultList) {
                    birdList.add(new Bird(element.child(1).child(0).text()));
                }
                System.out.println("HERE " + birdList.get(0).getUri_name());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return birdList;
        }
    }

    private static class ImageRetriever extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap result = null;
            try {
                Document document = Jsoup.connect(params[0]).get();
                Elements pictureDiv = document.select("div#id_glamor");
                Elements children = pictureDiv.get(0).children();
                System.out.println(children.get(0).absUrl("src"));
                String imageUrl = children.get(0).absUrl("src");
                URL url = new URL(imageUrl);
                System.out.println(url);
                result = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            return result;
        }
    }
}
