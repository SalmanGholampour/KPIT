package com.example.scenichiking.ui.main

import com.example.scenichiking.data.MarkerPoint
import com.example.scenichiking.ui.base.BaseViewModel

class MainViewModel : BaseViewModel<MainNavigator>() {
    var markerPoints = mutableListOf<MarkerPoint>()
    var hasRoute: Boolean = false

    fun addMarkerPoint(point: MarkerPoint) {
        markerPoints.add(point)


    }
    fun updateMarker(point: MarkerPoint) {
        markerPoints.set(markerPoints.indexOf(point), point)
        navigator.updateAllMarkers(markerPoints)
    }

    fun onOverViewClick() {
        navigator.onOverViewClick()
    }

    fun onGetRouteClick() {
        navigator.onGetRouteClick()
    }

    fun onRouteClick() {
        navigator.onRouteClick()
    }

    fun onGotoListClick() {
        navigator.onGotoListClick()
    }

}