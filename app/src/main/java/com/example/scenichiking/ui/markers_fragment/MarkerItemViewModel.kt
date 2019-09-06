package com.example.scenichiking.ui.markers_fragment

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.example.scenichiking.data.MarkerPoint

class MarkerItemViewModel(var markerPoint: MarkerPoint, var markerItemListener: MarkerItemViewModelListener) {
    val lat: ObservableField<String>
    val lng: ObservableField<String>
    var isFavorite: ObservableBoolean

    init {

        lat = ObservableField<String>(markerPoint.point.latitude.toString())
        lng = ObservableField<String>(markerPoint.point.longitude.toString())
        isFavorite = ObservableBoolean(markerPoint.isFavorite)

    }

    fun onFavoriteClick() {
        if (isFavorite.get()) isFavorite.set(false) else isFavorite.set(true)
        markerPoint.isFavorite=isFavorite.get()
        markerItemListener.onFavoriteClick(markerPoint)

    }

    interface MarkerItemViewModelListener {

        fun onFavoriteClick(markerPoint: MarkerPoint)

    }
}