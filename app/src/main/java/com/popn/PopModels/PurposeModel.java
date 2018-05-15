package com.popn.PopModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Android-Dev2 on 4/20/2018.
 */

public class PurposeModel implements Parcelable{
       String id, name;

    public PurposeModel(){

    }

    public PurposeModel(String id, String name){
        this.id = id;
        this.name = name;
    }

    protected PurposeModel(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<PurposeModel> CREATOR = new Creator<PurposeModel>() {
        @Override
        public PurposeModel createFromParcel(Parcel in) {
            return new PurposeModel(in);
        }

        @Override
        public PurposeModel[] newArray(int size) {
            return new PurposeModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
