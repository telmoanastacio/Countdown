package com.tsilva.countdown.modules.confirmScreen.viewModel;

import android.view.View;

/**
 * Created by Telmo Silva on 31.01.2020.
 */

public final class ConfirmDialogViewModel
{
    private View.OnClickListener positiveOnClickListener = null;
    private View.OnClickListener negativeOnClickListener = null;

    private ConfirmDialogViewModel()
    {}

    public ConfirmDialogViewModel(
            View.OnClickListener positiveOnClickListener,
            View.OnClickListener negativeOnClickListener)
    {
        this.positiveOnClickListener = positiveOnClickListener;
        this.negativeOnClickListener = negativeOnClickListener;
    }

    public View.OnClickListener getPositiveOnClickListener()
    {
        return positiveOnClickListener;
    }

    public View.OnClickListener getNegativeOnClickListener()
    {
        return negativeOnClickListener;
    }
}