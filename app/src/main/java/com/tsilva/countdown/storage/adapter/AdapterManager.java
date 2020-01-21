package com.tsilva.countdown.storage.adapter;

import android.widget.BaseAdapter;

import com.tsilva.countdown.modules.postList.activity.PostListActivity;
import com.tsilva.countdown.modules.postList.viewModel.PostListViewAdapter;

import javax.annotation.Nullable;

/**
 * Created by Telmo Silva on 23.01.2020.
 */

public final class AdapterManager
{
    private static AdapterManager adapterManagerInstance = null;

    private PostListViewAdapter postListViewAdapter = null;

    private AdapterManager() {}

    public static AdapterManager adapterManagerInstance()
    {
        if(adapterManagerInstance == null)
        {
            adapterManagerInstance = new AdapterManager();
        }
        return adapterManagerInstance;
    }

    public void addAdapter(BaseAdapter adapter)
    {
        if(adapter.getClass().equals(PostListViewAdapter.class))
        {
            if(PostListActivity.isAlive)
            {
                postListViewAdapter = (PostListViewAdapter) adapter;
            }
            else
            {
                postListViewAdapter = null;
            }
        }
    }

    /**
     *
     * @param adapter if null all adapters will be notified
     */
    public synchronized  <T extends BaseAdapter> void notifyAdapters(@Nullable T adapter)
    {
        // PostListViewAdapter
        if(postListViewAdapter != null && PostListActivity.isAlive)
        {
            if(adapter == null)
            {
                postListViewAdapter.notifyDataSetChanged();
            }
            else
            {
                if(adapter.getClass().equals(PostListViewAdapter.class))
                {
                    postListViewAdapter.notifyDataSetChanged();
                }
            }
        }
        else
        {
            postListViewAdapter = null;
        }

        //
    }
}