package com.popn.PopModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Android-Dev2 on 4/20/2018.
 */

public class InterestModel implements Parcelable {
        String interestId, interestName;

    public InterestModel(){

    }

    protected InterestModel(Parcel in) {
        interestId = in.readString();
        interestName = in.readString();
    }

    public static final Creator<InterestModel> CREATOR = new Creator<InterestModel>() {
        @Override
        public InterestModel createFromParcel(Parcel in) {
            return new InterestModel(in);
        }

        @Override
        public InterestModel[] newArray(int size) {
            return new InterestModel[size];
        }
    };

    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public InterestModel(String interestId, String interestName) {

        this.interestId = interestId;
        this.interestName = interestName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(interestId);
        dest.writeString(interestName);
    }
}
