package com.tsilva.countdown.modules.confirmScreen.viewModel;

import android.view.View;

/**
 * Created by Telmo Silva on 31.01.2020.
 */

public final class ConfirmDialogViewModelFactory
{
    public ConfirmDialogViewModelFactory()
    {}

    public ConfirmDialogViewModel create(
            View.OnClickListener positiveOnClickListener,
            View.OnClickListener negativeOnClickListener)
    {
        return new ConfirmDialogViewModel(positiveOnClickListener, negativeOnClickListener);
    }
}