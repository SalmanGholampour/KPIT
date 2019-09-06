package com.example.scenichiking.ui.main

import com.example.scenichiking.data.MarkerPoint

interface MainNavigator {
    fun updateAllMarkers(markerPoints: MutableList<MarkerPoint>)
    fun onGotoListClick()
    fun onRouteClick()
    fun onGetRouteClick()
    fun onOverViewClick()

}