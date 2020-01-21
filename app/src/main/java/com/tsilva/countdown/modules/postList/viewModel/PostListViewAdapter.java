package com.tsilva.countdown.modules.postList.viewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tsilva.countdown.databinding.PostItemBinding;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModel;

import java.util.List;

/**
 * Created by Telmo Silva on 20.01.2020.
 */

public final class PostListViewAdapter extends BaseAdapter
{
    private Context context = null;
    private List<PostItemViewModel> postItemViewModelList = null;

    private PostListViewAdapter() {}

    public PostListViewAdapter(Context context, List<PostItemViewModel> postItemViewModelList)
    {
        this.context = context;
        this.postItemViewModelList = postItemViewModelList;
    }

    @Override
    public int getCount()
    {
        if(postItemViewModelList != null && !postItemViewModelList.isEmpty())
        {
            return postItemViewModelList.size();
        }

        return 0;
    }

    @Override
    public PostItemViewModel getItem(int position)
    {
        if(postItemViewModelList != null && !postItemViewModelList.isEmpty())
        {
            return postItemViewModelList.get(position);
        }

        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return Integer.valueOf(position).longValue();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
//        if(convertView != null)
//        {
//            return convertView;
//        }

        return getPostItemFragmentView(position);
    }

    private View getPostItemFragmentView(int position)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // R.layout.post_item
        PostItemBinding postItemFragmentBinding = PostItemBinding.inflate(
                layoutInflater, null, false);
        PostItemViewModel postItemViewModel = postItemViewModelList.get(position);
        postItemViewModel.setPosition(position);
        postItemFragmentBinding.setViewModel(postItemViewModel);

        return postItemFragmentBinding.getRoot();
    }
}