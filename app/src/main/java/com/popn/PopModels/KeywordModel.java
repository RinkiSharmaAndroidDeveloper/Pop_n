package com.popn.PopModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Android-Dev2 on 4/20/2018.
 */

public class KeywordModel implements Parcelable {
    String keywordId, keywordName, keywordColor;

    public KeywordModel(){

    }

    protected KeywordModel(Parcel in) {
        keywordId = in.readString();
        keywordName = in.readString();
        keywordColor = in.readString();
    }

    public static final Creator<KeywordModel> CREATOR = new Creator<KeywordModel>() {
        @Override
        public KeywordModel createFromParcel(Parcel in) {
            return new KeywordModel(in);
        }

        @Override
        public KeywordModel[] newArray(int size) {
            return new KeywordModel[size];
        }
    };

    public String getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(String keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public String getKeywordColor() {
        return keywordColor;
    }

    public void setKeywordColor(String keywordColor) {
        this.keywordColor = keywordColor;
    }

    public KeywordModel(String keywordId, String keywordName, String keywordColor) {

        this.keywordId = keywordId;
        this.keywordName = keywordName;
        this.keywordColor = keywordColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keywordId);
        dest.writeString(keywordName);
        dest.writeString(keywordColor);
    }
}
