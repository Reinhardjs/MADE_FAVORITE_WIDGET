package com.example.made_final.databinding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.made_final.data.Resource;
import com.example.made_final.ui.base.BaseAdapter;

import java.util.ArrayList;

final class ListBindingAdapter {

    private ListBindingAdapter() {
        // Private Constructor to hide the implicit one
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = "resource")
    public static void setResource(RecyclerView recyclerView, Resource resource) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null)
            return;

        if (resource == null || resource.getData() == null)
            return;

        if (adapter instanceof BaseAdapter) {
            ((BaseAdapter) adapter).setData((ArrayList) resource.getData());
        }
    }

}