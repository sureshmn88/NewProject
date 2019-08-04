package com.sisar.youtubeapi.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaList implements Parcelable {

    String path,imageid,filetype;

    protected MediaList(Parcel in) {
        path = in.readString();
        imageid = in.readString();
        filetype = in.readString();
    }

    public MediaList(){

    }
    public static final Creator <MediaList> CREATOR = new Creator <MediaList>() {
        @Override
        public MediaList createFromParcel(Parcel in) {
            return new MediaList(in);
        }

        @Override
        public MediaList[] newArray(int size) {
            return new MediaList[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(imageid);
        dest.writeString(filetype);
    }
}
