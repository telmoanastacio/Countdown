package com.tsilva.countdown.modules.postList.viewModel;

import android.widget.ListView;

import androidx.databinding.BindingAdapter;

import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModel;
import com.tsilva.countdown.services.StorageService;

import java.util.List;

/**
 * Created by Telmo Silva on 20.01.2020.
 */

public final class PostListViewBindings
{
    @BindingAdapter({"bind:items"})
    public static void setItems(ListView listView, List<PostItemViewModel> items)
    {
        //write your code to set ListView adapter.
        if(items != null && !items.isEmpty())
        {
            PostListViewAdapter postListViewAdapter = new PostListViewAdapter(
                    StorageService.storageServiceInstance().getContext(), items);
            StorageService.storageServiceInstance()
                    .getAdapterManager().addAdapter(postListViewAdapter);
            listView.setAdapter(postListViewAdapter);
        }
    }
}