package com.example.scenichiking.ui.main;

import android.graphics.Color;

import com.mapbox.mapboxsdk.plugins.annotation.CircleOptions;
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions;
import com.mapbox.mapboxsdk.plugins.annotation.Options;
import com.mapbox.mapboxsdk.utils.ColorUtils;

public class OptionFactory extends AbstractFactory {

    @Override
    Options getOption(String type) {
        if (type.equalsIgnoreCase("circle")) {
            CircleOptions circleOptions = new CircleOptions().withCircleRadius(10f).withCircleColor(ColorUtils.colorToRgbaString(Color.RED));
            return circleOptions;
        } else if (type.equalsIgnoreCase("line")) {
            LineOptions lineOptions = new LineOptions().withLineColor(ColorUtils.colorToRgbaString(Color.BLUE)).withLineWidth(3f);
            return lineOptions;
        } else return null;
    }
}
