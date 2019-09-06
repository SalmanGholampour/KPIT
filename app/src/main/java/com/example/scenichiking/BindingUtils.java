package com.example.scenichiking;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.scenichiking.data.MarkerPoint;
import com.example.scenichiking.ui.markers_fragment.MarkerAdapter;

import java.util.List;

public final class BindingUtils {
    @BindingAdapter({"adapter"})
    public static void addMarkerItems(RecyclerView recyclerView, List<MarkerPoint> markerPoints) {
        MarkerAdapter adapter = (MarkerAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(markerPoints);
        }
    }
}
