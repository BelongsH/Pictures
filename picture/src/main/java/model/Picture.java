package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuhuiliang on 16/6/6.
 */
public class Picture implements Parcelable {
    //图片ID
    private int pictureID;
    //图片路径
    private String picturePath;
    //图片缩略图
    private String thumbPath;


    public Picture(int pictureID, String picturePath, String thumbPath) {
        this.pictureID = pictureID;
        this.picturePath = picturePath;
        this.thumbPath = thumbPath;
    }

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pictureID);
        dest.writeString(this.picturePath);
        dest.writeString(this.thumbPath);
    }

    protected Picture(Parcel in) {
        this.pictureID = in.readInt();
        this.picturePath = in.readString();
        this.thumbPath = in.readString();
    }

    public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
