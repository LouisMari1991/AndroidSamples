package com.googlesamples.unsplash.ui.grid;

import android.support.v7.widget.RecyclerView;
import com.googlesamples.unsplash.databinding.PhotoItemBinding;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private final PhotoItemBinding binding;

    public PhotoViewHolder(PhotoItemBinding itemBinding) {
        super(itemBinding.getRoot());
        binding = itemBinding;
    }

    public PhotoItemBinding getBinding() {
        return binding;
    }
}
