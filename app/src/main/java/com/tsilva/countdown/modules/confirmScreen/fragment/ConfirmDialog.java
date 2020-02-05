package com.tsilva.countdown.modules.confirmScreen.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tsilva.countdown.CountdownApp;
import com.tsilva.countdown.databinding.ConfirmationDialogBinding;
import com.tsilva.countdown.modules.confirmScreen.viewModel.ConfirmDialogViewModelFactory;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.dialog.CurrentDialog;
import com.tsilva.countdown.storage.dialog.DialogManager;

import javax.inject.Inject;

/**
 * Created by Telmo Silva on 31.01.2020.
 */

public final class ConfirmDialog extends DialogFragment implements CurrentDialog
{
    public static boolean isAlive = false;

    @Inject
    StorageService storageService;

    @Inject
    ConfirmDialogViewModelFactory confirmDialogViewModelFactory;

    private View.OnClickListener positiveOnClickListener = null;
    private View.OnClickListener negativeOnClickListener = null;
    private ConfirmationDialogBinding confirmationDialogBinding = null;

    public static ConfirmDialog newInstance()
    {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        Bundle arguments = new Bundle();
        confirmDialog.setArguments(arguments);
        return confirmDialog;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        isAlive = true;

        CountdownApp.applicationComponent.inject(this);
        setCurrentDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if(arguments != null)
        {}

        DialogManager dialogManager = storageService.getDialogManager();
        positiveOnClickListener = dialogManager.getConfirmDialogPositiveOnClickListener();
        negativeOnClickListener = dialogManager.getConfirmDialogNegativeOnClickListener();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        setCancelable(false);
        confirmationDialogBinding =
                ConfirmationDialogBinding.inflate(inflater, container, false);
        confirmationDialogBinding.setViewModel(confirmDialogViewModelFactory.create(
                positiveOnClickListener, negativeOnClickListener));

        confirmationDialogBinding.executePendingBindings();

        return confirmationDialogBinding.getRoot();
    }

    @Override
    public void onDetach()
    {
        isAlive = false;
        storageService.getDialogManager().clearDialogByObject(this);
        super.onDetach();
    }

    @Override
    public void setCurrentDialog()
    {
        try
        {
            storageService.getDialogManager().setCurrentDialogFragment(this);
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}