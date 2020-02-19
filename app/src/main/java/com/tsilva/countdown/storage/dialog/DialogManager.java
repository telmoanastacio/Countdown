package com.tsilva.countdown.storage.dialog;

import android.view.View;

import androidx.fragment.app.DialogFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Telmo Silva on 31.01.2020.
 */

public final class DialogManager
{
    private static volatile DialogManager dialogManagerInstance = null;

    private List<DialogFragment> dialogFragmentList = null;

    private View.OnClickListener confirmDialogPositiveOnClickListener = null;
    private View.OnClickListener confirmDialogNegativeOnClickListener = null;

    private DialogManager() {}

    public static DialogManager dialogManagerInstance()
    {
        if(dialogManagerInstance == null)
        {
            synchronized(DialogManager.class)
            {
                if(dialogManagerInstance == null)
                {
                    dialogManagerInstance = new DialogManager();
                }
            }
        }
        return dialogManagerInstance;
    }

    public DialogFragment getCurrentDialogFragment()
    {
        if(dialogFragmentList != null && !dialogFragmentList.isEmpty())
        {
            return dialogFragmentList.get(dialogFragmentList.size() - 1);
        }

        return null;
    }

    public void setCurrentDialogFragment(DialogFragment dialogFragment) throws Throwable
    {
        if(dialogFragment == null)
        {
            throw new Throwable("No valid dialog provided");
        }

        if(this.dialogFragmentList == null)
        {
            this.dialogFragmentList = new LinkedList<>();
        }

        this.dialogFragmentList.add(dialogFragment);
    }

    public View.OnClickListener getConfirmDialogPositiveOnClickListener()
    {
        return confirmDialogPositiveOnClickListener;
    }

    public void setConfirmDialogPositiveOnClickListener(
            View.OnClickListener confirmDialogPositiveOnClickListener)
    {
        this.confirmDialogPositiveOnClickListener = confirmDialogPositiveOnClickListener;
    }

    public View.OnClickListener getConfirmDialogNegativeOnClickListener()
    {
        return confirmDialogNegativeOnClickListener;
    }

    public void setConfirmDialogNegativeOnClickListener(
            View.OnClickListener confirmDialogNegativeOnClickListener)
    {
        this.confirmDialogNegativeOnClickListener = confirmDialogNegativeOnClickListener;
    }

    public void clearSpecificDialogs(List<Class> clearDialogs)
    {
        // DialogFragment
        if(dialogFragmentList != null && !dialogFragmentList.isEmpty())
        {
            for(int i = dialogFragmentList.size() - 1; i >= 0; i--)
            {
                DialogFragment dialogFragment = dialogFragmentList.get(i);
                if(clearDialogs.contains(dialogFragment.getClass()))
                {
                    dialogFragment.dismissAllowingStateLoss();
                    dialogFragment = null;
                    dialogFragmentList.remove(i);
                }
            }
        }
    }

    public void clearDialogByObject(Object dialog)
    {
        if(dialog == null)
        {
            return;
        }

        // DialogFragment
        if(DialogFragment.class.isAssignableFrom(dialog.getClass()))
        {
            if(dialogFragmentList != null && !dialogFragmentList.isEmpty())
            {
                DialogFragment dialogFragment = (DialogFragment) dialog;
                dialogFragment.dismissAllowingStateLoss();
                dialogFragmentList.remove(dialogFragment);
                dialog = null;
            }
        }
    }
}