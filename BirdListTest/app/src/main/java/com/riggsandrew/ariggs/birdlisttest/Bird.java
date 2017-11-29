package com.riggsandrew.ariggs.birdlisttest;

import java.net.URI;
import java.net.URL;

/**
 * Created by Andrew Riggs on 11/14/2017.
 */

public class Bird {

    private String name;
    private String url_name;

    public Bird(String name) {
        this.name = name;

        String[] split = name.split(" ");
        for (int i = 0; i < split.length; i++) {
            split[i] = Character.toUpperCase(split[i].charAt(0)) + split[i].substring(1);
            split[i] = split[i].replace("\'", "");
        }

        this.url_name = split[0];
        if (split.length > 1) {
            for (int i = 1; i < split.length; i++) {
                url_name += "_" + split[i];
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri_name() {
        return url_name;
    }
}
