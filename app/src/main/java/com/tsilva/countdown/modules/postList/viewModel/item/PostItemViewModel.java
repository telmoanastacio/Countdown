package com.tsilva.countdown.modules.postList.viewModel.item;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.tsilva.countdown.R;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.status.StatusManager;

import java.util.Date;

/**
 * Created by Telmo Silva on 22.01.2020.
 */

public final class PostItemViewModel
{
    private Context context = null;
    private PersistenceService persistenceService = null;
    private StorageService storageService = null;
    private int position = -1;
    private String postId = null;
    private CountdownEventDto countdownEventDto = null;

    public PostItemObservables postItemObservables = null;

    private PostItemViewModel() {}

    PostItemViewModel(Context context,
                      PersistenceService persistenceService,
                      StorageService storageService,
                      String postId,
                      CountdownEventDto countdownEventDto)
    {
        this.context = context;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
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

        setupDrawables();
    }

    private void setupDrawables()
    {
        Drawable edit = context.getResources().getDrawable(R.drawable.edit);
        Drawable delete = context.getResources().getDrawable(R.drawable.delete);

        edit.mutate().setColorFilter(
                ContextCompat.getColor(context, android.R.color.black), PorterDuff.Mode.MULTIPLY);
        delete.mutate().setColorFilter(
                ContextCompat.getColor(context, android.R.color.black), PorterDuff.Mode.MULTIPLY);

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

    //TODO: send to the edit view
    public void onEditClicked(View view)
    {
        System.out.println("=== CLICKED EDIT ITEM: " + position);
    }

    //TODO: show a confirmation dialog
    public void onDeleteClicked(View view)
    {
        System.out.println("=== CLICKED DELETE ITEM: " + position);
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