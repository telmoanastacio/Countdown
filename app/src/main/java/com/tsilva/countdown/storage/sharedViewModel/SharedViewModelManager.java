package com.tsilva.countdown.storage.sharedViewModel;

import com.tsilva.countdown.modules.postList.viewModel.PostListViewModel;

/**
 * Created by Telmo Silva on 27.01.2020.
 */

public final class SharedViewModelManager
{
    private static SharedViewModelManager sharedViewModelManagerInstance = null;

    private PostListViewModel postListViewModel = null;

    private SharedViewModelManager() {}

    public static SharedViewModelManager sharedViewModelManagerInstance()
    {
        if(sharedViewModelManagerInstance == null)
        {
            sharedViewModelManagerInstance = new SharedViewModelManager();
        }
        return sharedViewModelManagerInstance;
    }

    public PostListViewModel getPostListViewModel() throws Throwable
    {
        if(postListViewModel == null)
        {
            throw new Throwable("PostListViewModel is null");
        }

        return postListViewModel;
    }

    public void setPostListViewModel(PostListViewModel postListViewModel)
    {
        this.postListViewModel = postListViewModel;
    }
}