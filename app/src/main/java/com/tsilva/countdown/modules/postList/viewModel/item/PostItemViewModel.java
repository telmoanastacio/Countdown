package com.tsilva.countdown.modules.postList.viewModel.item;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.tsilva.countdown.R;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.modules.ModulesConfiguration;
import com.tsilva.countdown.modules.confirmScreen.fragment.ConfirmDialog;
import com.tsilva.countdown.modules.editPost.activity.EditPostActivity;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;
import com.tsilva.countdown.storage.dialog.DialogManager;
import com.tsilva.countdown.storage.status.StatusManager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Telmo Silva on 22.01.2020.
 */

public final class PostItemViewModel
{
    public PostItemObservables postItemObservables = null;
    private Context context = null;
    private PersistenceService persistenceService = null;
    private StorageService storageService = null;
    private UserLoginCredentials userLoginCredentials = null;
    private int position = -1;
    private String postId = null;
    private CountdownEventDto countdownEventDto = null;
    private boolean isOwner = false;

    private PostItemViewModel() {}

    PostItemViewModel(Context context,
                      PersistenceService persistenceService,
                      StorageService storageService,
                      UserLoginCredentials userLoginCredentials,
                      String postId,
                      CountdownEventDto countdownEventDto)
    {
        this.context = context;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
        this.userLoginCredentials = userLoginCredentials;
        this.postId = postId;
        this.countdownEventDto = countdownEventDto;

        setup();
    }

    CountdownEventDto getCountdownEventDto()
    {
        return countdownEventDto;
    }

    private void setup()
    {
        postItemObservables = new PostItemObservables();

        postItemObservables.title.set(countdownEventDto.title);

        if(countdownEventDto.email != null
                && userLoginCredentials.getEmail().equals(countdownEventDto.email))
        {
            isOwner = true;
        }

        setupDrawables();
    }

    private void setupDrawables()
    {
        int color = isOwner ? R.color.enabled : R.color.disabled;
        Drawable edit = context.getResources().getDrawable(R.drawable.edit);
        Drawable delete = context.getResources().getDrawable(R.drawable.delete);

        edit.mutate().setColorFilter(
                ContextCompat.getColor(context, color), PorterDuff.Mode.MULTIPLY);
        delete.mutate().setColorFilter(
                ContextCompat.getColor(context, color), PorterDuff.Mode.MULTIPLY);

        postItemObservables.editDrawable.set(edit);
        postItemObservables.deleteDrawable.set(delete);
    }

    public void setPosition(int position)
    {
        if(this.position == -1)
        {
            this.position = position;
        }
    }

    //TODO: send to the details view
    public void onItemClicked(View view)
    {
        System.out.println("=== CLICKED ITEM: " + position);
    }

    public void onEditClicked(View view)
    {
        if(isOwner)
        {
            CurrentActivity currentActivity =
                    storageService.getActivityManager().getCurrentActivity();
            Intent editPost =
                    new Intent(currentActivity, EditPostActivity.class);
            editPost.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            editPost.putExtra(ModulesConfiguration.POST_ID, postId);
            editPost.putExtra(ModulesConfiguration.COUNTDOWN_EVENT_DTO, countdownEventDto);

            currentActivity.startActivity(editPost);
        }
    }

    public void onDeleteClicked(View view)
    {
        final DialogManager dialogManager = storageService.getDialogManager();
        storageService.getUtilsManager().showConfirmationDialog(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        List<Class> dialogsToClearList = new LinkedList<>();
                        dialogsToClearList.add(ConfirmDialog.class);
                        dialogManager.clearSpecificDialogs(dialogsToClearList);

                        try
                        {
                            storageService.getSharedViewModelManager()
                                    .getPostListViewModel()
                                    .fetchDeleteCountdownEvent(postId);
                        }
                        catch(Throwable throwable)
                        {
                            throwable.printStackTrace();
                        }
                    }
                },
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        List<Class> dialogsToClearList = new LinkedList<>();
                        dialogsToClearList.add(ConfirmDialog.class);
                        dialogManager.clearSpecificDialogs(dialogsToClearList);
                    }
                });
    }

    public final class PostItemObservables
    {
        public ObservableInt progress = new ObservableInt();

        public ObservableInt progressColor = new ObservableInt();

        public ObservableField<String> title = new ObservableField<>();

        public ObservableField<Drawable> progressDrawable = new ObservableField<>();
        public ObservableField<Drawable> editDrawable = new ObservableField<>();
        public ObservableField<Drawable> deleteDrawable = new ObservableField<>();

        private PostItemObservables()
        {
            StatusManager statusManager = storageService.getStatusManager();
            Date now = new Date();

            title.set(countdownEventDto.title == null ? "" : countdownEventDto.title);
            progress.set(
                    Double.valueOf(statusManager.getProgress(countdownEventDto, now) * 100.0)
                            .intValue());
            progressColor.set(statusManager.getProgressColor(countdownEventDto, now));
            progressDrawable.set(statusManager.getDrawable(countdownEventDto, now));
            editDrawable.set(null);
            deleteDrawable.set(null);
        }
    }
}