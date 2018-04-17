package com.popn.PopModels;

/**
 * Created by Android-Dev2 on 4/6/2018.
 */

public class BroadcastLocationModel  {

        public String name,message;

    public BroadcastLocationModel(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BroadcastLocationModel(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public BroadcastLocationModel(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
