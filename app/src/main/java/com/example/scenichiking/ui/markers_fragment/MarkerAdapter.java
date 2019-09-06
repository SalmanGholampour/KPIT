package com.example.scenichiking.ui.markers_fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.scenichiking.data.MarkerPoint;
import com.example.scenichiking.databinding.ItemMarkerEmptyViewBinding;
import com.example.scenichiking.databinding.ItemMarkerViewBinding;
import com.example.scenichiking.ui.base.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MarkerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private MarkerAdapterListener mListener;

    private List<MarkerPoint> data;

    public void setListener(MarkerAdapterListener listener) {
        this.mListener = listener;
    }

    public MarkerAdapter(List<MarkerPoint> markerPoints) {
        this.data = markerPoints;
    }


    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        } else {
            return 1;
        }
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemMarkerViewBinding itemMarkerViewBinding = ItemMarkerViewBinding
                        .inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new MarkerItemViewHolder(itemMarkerViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemMarkerEmptyViewBinding emptyViewBinding = ItemMarkerEmptyViewBinding
                        .inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<MarkerPoint> markerPoints) {
        this.data.addAll(markerPoints);
        notifyDataSetChanged();
    }

    public void clearItems() {
        this.data.clear();
    }


    @Override
    public int getItemViewType(int position) {
        if (data != null && !data.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public class MarkerItemViewHolder extends BaseViewHolder implements
            MarkerItemViewModel.MarkerItemViewModelListener {

        private ItemMarkerViewBinding mBinding;


        public MarkerItemViewHolder(ItemMarkerViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

            MarkerPoint markerPoint = data.get(position);
            MarkerItemViewModel viewModel = new MarkerItemViewModel(markerPoint, this);
            mBinding.setViewModel(viewModel);

            mBinding.executePendingBindings();
        }


        @Override
        public void onFavoriteClick(@NotNull MarkerPoint markerPoint) {
            if (mListener != null) mListener.onFavoriteClick(markerPoint);
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        private ItemMarkerEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemMarkerEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }


        @Override
        public void onBind(int position) {
            MarkerEmptyItemViewModel emptyItemViewModel = new MarkerEmptyItemViewModel();
            mBinding.setViewModel(emptyItemViewModel);
        }
    }

    public interface MarkerAdapterListener {
        void onFavoriteClick(MarkerPoint markerPoint);


    }
}
