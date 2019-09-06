package com.example.scenichiking.ui.main;

import com.mapbox.mapboxsdk.plugins.annotation.Options;

public abstract class AbstractFactory {
    abstract Options getOption(String optionType);
}
