package com.stayingalive.stayingaliveapp.services;

/**
 * Created by mauriciolara on 10/29/14.
 */
public enum StayingAliveSound {

    CLICK("sounds/click.wav");

    private final String mFileName;

    private StayingAliveSound(String fileName){
        mFileName = fileName;
    }

    public String getFileName(){
        return mFileName;
    }

}
