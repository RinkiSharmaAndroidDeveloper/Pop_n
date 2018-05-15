package com.popn.PopModels;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonalBroadcastModel implements Parcelable {
    String id,userName,latititude,logitude,purpose1,keyword1,keyword2,keyword3,interest1;

    public PersonalBroadcastModel(String id, String userName, String latititude, String logitude, String purpose1, String keyword1, String keyword2, String keyword3, String interest1) {
        this.id = id;
        this.userName = userName;
        this.latititude = latititude;
        this.logitude = logitude;
        this.purpose1 = purpose1;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.interest1 = interest1;
    }

    protected PersonalBroadcastModel(Parcel in) {
        id = in.readString();
        userName = in.readString();
        latititude = in.readString();
        logitude = in.readString();
        purpose1 = in.readString();
        keyword1 = in.readString();
        keyword2 = in.readString();
        keyword3 = in.readString();
        interest1 = in.readString();
    }

    public static final Creator<PersonalBroadcastModel> CREATOR = new Creator<PersonalBroadcastModel>() {
        @Override
        public PersonalBroadcastModel createFromParcel(Parcel in) {
            return new PersonalBroadcastModel(in);
        }

        @Override
        public PersonalBroadcastModel[] newArray(int size) {
            return new PersonalBroadcastModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLatititude() {
        return latititude;
    }

    public void setLatititude(String latititude) {
        this.latititude = latititude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public String getPurpose1() {
        return purpose1;
    }

    public void setPurpose1(String purpose1) {
        this.purpose1 = purpose1;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public String getInterest1() {
        return interest1;
    }

    public void setInterest1(String interest1) {
        this.interest1 = interest1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(latititude);
        dest.writeString(logitude);
        dest.writeString(purpose1);
        dest.writeString(keyword1);
        dest.writeString(keyword2);
        dest.writeString(keyword3);
        dest.writeString(interest1);
    }
}
