package com.popn.PopModels;

public class BroadcastDetailsModel {
    String Id,keywords;

    public BroadcastDetailsModel(String id, String keywords) {
        Id = id;
        this.keywords = keywords;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
