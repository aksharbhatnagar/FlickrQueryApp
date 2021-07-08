package com.akshar.flickrqueryapp;

public class Utils {
    static String createUrl(PhotoResult photo) {
        return "https://farm" + photo.getFarm() + ".staticflickr.com/"
                + photo.getServer() + "/"
                + photo.getId()
                + "_"
                + photo.getSecret()
                + "_m.jpg";
    }

}
