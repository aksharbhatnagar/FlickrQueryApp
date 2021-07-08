package com.akshar.flickrqueryapp;

import java.util.ArrayList;

public class ImageResult {
    private int page;
    private int pages;
    private String perpage;
    private int total;
    private ArrayList<PhotoResult> photo;
    public String getPerPage() {
        return perpage;
    }
    public ArrayList<PhotoResult> getPhotoArray() {
        return photo;
    }

    private String stat;
}

    class PhotoResult {
        private String id;
        private String owner;
        private String secret;
        private int server;
        private int farm;
        private String title;
        private int ispublic;
        private int isfriend;
        private int isfamily;

        public String getId() {
            return id;
        }

        public String getSecret() {
            return secret;
        }

        public int getServer() {
            return server;
        }

        public int getFarm() {
            return farm;
        }
    }
