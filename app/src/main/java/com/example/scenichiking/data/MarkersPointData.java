package com.example.scenichiking.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MarkersPointData implements Parcelable {
    private List<MarkerPoint> markerPoints;

    protected MarkersPointData(Parcel in) {
        markerPoints = in.createTypedArrayList(MarkerPoint.CREATOR);
    }

    public static final Creator<MarkersPointData> CREATOR = new Creator<MarkersPointData>() {
        @Override
        public MarkersPointData createFromParcel(Parcel in) {
            return new MarkersPointData(in);
        }

        @Override
        public MarkersPointData[] newArray(int size) {
            return new MarkersPointData[size];
        }
    };

    public MarkersPointData() {

    }

    public List<MarkerPoint> getMarkerPoints() {
        return markerPoints;
    }

    public void setMarkerPoints(List<MarkerPoint> markerPoints) {
        this.markerPoints = markerPoints;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(markerPoints);
    }
}
