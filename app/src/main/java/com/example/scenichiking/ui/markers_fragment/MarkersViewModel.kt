package com.example.scenichiking.ui.markers_fragment

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.example.scenichiking.ui.base.BaseViewModel
import com.example.scenichiking.data.MarkerPoint

class MarkersViewModel : BaseViewModel<MarkersNavigator>() {
    var markerPoints: ObservableList<MarkerPoint> = ObservableArrayList<MarkerPoint>()
    fun setMarkerPoints(points: List<MarkerPoint>) {
        markerPoints.addAll(points)
    }


}