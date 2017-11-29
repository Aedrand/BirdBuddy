package com.riggsandrew.ariggs.birdlisttest;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private RecyclerView recyclerView;
    private BirdAdapter birdAdapter;
    private ThumbnailDownloader<BirdHolder> thumbnailDownloader;

    private List<Bird> birdList;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        birdList = SiteScrape.getBirdList();

        /**birdList = new ArrayList<>();
        birdList.add(new Bird("Northern Cardinal"));
        birdList.add(new Bird("Black-capped Chickadee"));
        birdList.add(new Bird("Red-tailed Hawk"));
        birdList.add(new Bird("Osprey"));
        birdList.add(new Bird("Peregrine Falcon"));
        birdList.add(new Bird("American Robin"));
        birdList.add(new Bird("Black Phoebe"));
        birdList.add(new Bird("Blue Jay"));
        birdList.add(new Bird("Mourning Dove"));
        birdList.add(new Bird("Northern Mockingbird"));**/

        setRetainInstance(true);
        Handler responseHandler = new Handler();
        thumbnailDownloader = new ThumbnailDownloader<>(responseHandler, getActivity());
        thumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<BirdHolder>() {
                    @Override
                    public void onThumbnailDownloaded(BirdHolder target, BitmapDrawable thumbnail) {
                        BitmapDrawable drawable = thumbnail;
                        target.bindDrawable(drawable);
                    }
        }, getActivity());
        thumbnailDownloader.start();
        thumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started.");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.main_frag_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();;
        thumbnailDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed.");
    }

    public void updateUI() {

        birdAdapter = new BirdAdapter(birdList);
        recyclerView.setAdapter(birdAdapter);

    }

    private class BirdHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Bird bird;
        private TextView birdName;
        private ImageView birdImg;

        public BirdHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.single_line, container, false));
            itemView.setOnClickListener(this);

            birdName = (TextView) itemView.findViewById(R.id.single_line_bird_name);
            birdImg = (ImageView) itemView.findViewById(R.id.single_line_bird_image);
        }

        public void bindDrawable(BitmapDrawable drawable) {
            birdImg.setImageDrawable(drawable);
        }

        public void bind(Bird targetBird) {
            bird = targetBird;
            birdName.setText(bird.getName());
        }

        @Override
        public void onClick(View view) {
            Intent intent = SingleBirdActivity.newIntent(getActivity(), bird.getName());
            startActivity(intent);
        }
    }

    private class BirdAdapter extends RecyclerView.Adapter<BirdHolder> {

        private List<Bird> birdList;

        public BirdAdapter(List<Bird> birds) {
            birdList = birds;
        }

        @Override
        public BirdHolder onCreateViewHolder(ViewGroup container, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BirdHolder(layoutInflater, container);
        }

        @Override
        public void onBindViewHolder(BirdHolder holder, int position) {
            Bird bird = birdList.get(position);
            String birdURL = "https://www.allaboutbirds.org/guide/" + bird.getUri_name() + "/id";
            thumbnailDownloader.queueThumbnail(holder, birdURL);
            holder.bind(bird);
        }

        @Override
        public int getItemCount() {
            return birdList.size();
        }
    }

}
