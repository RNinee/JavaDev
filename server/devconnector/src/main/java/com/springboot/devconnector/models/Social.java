package com.springboot.devconnector.models;

public class Social {

    private String youtube;
    private String twitter;
    private String facebook;
    private String linkedin;
    private String instagram;

    // No-args constructor
    public Social() {
    }

    // All-args constructor
    public Social(String youtube, String twitter, String facebook, String linkedin, String instagram) {
        this.youtube = youtube;
        this.twitter = twitter;
        this.facebook = facebook;
        this.linkedin = linkedin;
        this.instagram = instagram;
    }

    // Getters and Setters
    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
