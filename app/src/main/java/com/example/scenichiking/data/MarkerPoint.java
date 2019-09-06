package com.example.scenichiking.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class MarkerPoint implements Parcelable {
    private LatLng point;
    private boolean isFavorite;

    protected MarkerPoint(Parcel in) {
        point = in.readParcelable(LatLng.class.getClassLoader());
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<MarkerPoint> CREATOR = new Creator<MarkerPoint>() {
        @Override
        public MarkerPoint createFromParcel(Parcel in) {
            return new MarkerPoint(in);
        }

        @Override
        public MarkerPoint[] newArray(int size) {
            return new MarkerPoint[size];
        }
    };

    public MarkerPoint() {

    }

    public LatLng getPoint() {
        return point;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(point, flags);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
