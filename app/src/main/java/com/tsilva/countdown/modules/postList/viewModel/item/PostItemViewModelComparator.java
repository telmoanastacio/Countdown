package com.tsilva.countdown.modules.postList.viewModel.item;

import java.util.Comparator;

/**
 * Created by Telmo Silva on 23.01.2020.
 */

public final class PostItemViewModelComparator implements Comparator<PostItemViewModel>
{
    private PostItemViewModelComparatorModeEnum mode = PostItemViewModelComparatorModeEnum.NO_SORTING;

    private PostItemViewModelComparator() {}

    public PostItemViewModelComparator(PostItemViewModelComparatorModeEnum mode)
    {
        this.mode = mode;
    }

    @Override
    public int compare(PostItemViewModel o1, PostItemViewModel o2)
    {
        if(mode == PostItemViewModelComparatorModeEnum.ASC_TSI)
        {
            return o1.getCountdownEventDto().tsi.compareTo(o2.getCountdownEventDto().tsi);
        }
        else if(mode == PostItemViewModelComparatorModeEnum.DESC_TSI)
        {
            return o2.getCountdownEventDto().tsi.compareTo(o1.getCountdownEventDto().tsi);
        }

        return 0;
    }
}