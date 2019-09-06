package com.example.scenichiking.ui.markers_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.scenichiking.BR;
import com.example.scenichiking.ui.base.BaseFragment;
import com.example.scenichiking.ui.main.MainActivity;
import com.example.scenichiking.data.MarkerPoint;
import com.example.scenichiking.data.MarkersPointData;
import com.example.scenichiking.R;
import com.example.scenichiking.databinding.FragmentMarkersBinding;

public class MarkersFragment extends BaseFragment<FragmentMarkersBinding, MarkersViewModel>
        implements MarkersNavigator {
    public static final String TAG = MarkersFragment.class.getSimpleName();
    public static final String MARKERS_KEY = "Markers_Key";
    FragmentMarkersBinding binding;
    MarkersViewModel viewModel;
    LinearLayoutManager linearLayoutManager;
    MarkerAdapter adapter;

    public static MarkersFragment newInstance(MarkersPointData data) {
        Bundle args = new Bundle();
        args.putParcelable(MARKERS_KEY, data);
        MarkersFragment fragment = new MarkersFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_markers;
    }

    @Override
    public MarkersViewModel getViewModel() {
        viewModel = new MarkersViewModel();
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);

        setUp();

    }

    private void setUp() {
        adapter = new MarkerAdapter(((MarkersPointData) getArguments().getParcelable(MARKERS_KEY))
                .getMarkerPoints());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.markersRecyclerView.setLayoutManager(linearLayoutManager);
        binding.markersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.markersRecyclerView.setAdapter(adapter);
        adapter.setListener(markerPoint -> ((MainActivity) getActivity()).updateMarkers(markerPoint));
    }

}
