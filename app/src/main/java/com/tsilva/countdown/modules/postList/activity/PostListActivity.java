package com.tsilva.countdown.modules.postList.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.R;
import com.tsilva.countdown.databinding.PostListActivityBinding;
import com.tsilva.countdown.modules.optionsMenu.viewModel.OptionsMenuViewModelFactory;
import com.tsilva.countdown.modules.postList.viewModel.PostListViewModelFactory;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelFactory;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;

import javax.inject.Inject;

/**
 * Created by Telmo Silva on 20.01.2020.
 */

public final class PostListActivity extends CurrentActivity
{
    public static boolean isAlive = false;

    @Inject
    Context context;

    @Inject
    StorageService storageService;

    @Inject
    PostListViewModelFactory postListViewModelFactory;

    @Inject
    PostItemViewModelFactory postItemViewModelFactory;

    private PostListActivityBinding postListActivityBinding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        isAlive = true;

        CountdownApp.applicationComponent.inject(this);
        setCurrentActivity();

        postListActivityBinding = DataBindingUtil
                .setContentView(this, R.layout.post_list_activity);
        postListActivityBinding.setViewModel(
                postListViewModelFactory.create(postListActivityBinding.getRoot(),
                                                postItemViewModelFactory));

        postListActivityBinding.executePendingBindings();
    }

    @Override
    protected void onDestroy()
    {
        isAlive = false;
        super.onDestroy();
    }

    @Override
    public CurrentActivity getCurrentActivity()
    {
        return storageService.getActivityManager().getCurrentActivity();
    }

    @Override
    public void setCurrentActivity()
    {
        try
        {
            storageService.getActivityManager().setCurrentActivity(this);
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}