package com.sisar.youtubeapi.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NewsFeed implements Parcelable {
    String userimage,username,title,description;
    ArrayList<MediaList> mediaList;

    public NewsFeed(){

    }
    protected NewsFeed(Parcel in) {
        userimage = in.readString();
        username = in.readString();
        title = in.readString();
        description = in.readString();
        mediaList = in.createTypedArrayList(MediaList.CREATOR);
    }

    public static final Creator <NewsFeed> CREATOR = new Creator <NewsFeed>() {
        @Override
        public NewsFeed createFromParcel(Parcel in) {
            return new NewsFeed(in);
        }

        @Override
        public NewsFeed[] newArray(int size) {
            return new NewsFeed[size];
        }
    };

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList <MediaList> getMediaList() {
        return mediaList;
    }

    public void setMediaList(ArrayList <MediaList> mediaList) {
        this.mediaList = mediaList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userimage);
        dest.writeString(username);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeTypedList(mediaList);
    }
}
