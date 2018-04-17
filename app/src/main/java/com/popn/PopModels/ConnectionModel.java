package com.popn.PopModels;

/**
 * Created by Vs1 on 2/13/2018.
 */

public class ConnectionModel {
    private String title;
    private String  imageUrl;
    String SocialID,identityId;

    public ConnectionModel() {
    }

    public ConnectionModel(String title, String imageUrl, String socialID, String identityId) {
        this.title = title;
        this.imageUrl = imageUrl;
        SocialID = socialID;
        this.identityId = identityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSocialID() {
        return SocialID;
    }

    public void setSocialID(String socialID) {
        SocialID = socialID;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }
}
