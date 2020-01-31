package com.tsilva.countdown.modules.postList.viewModel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventsDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventResponseBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.updateCountdownEvent.UpdateCountdownEventRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.updateCountdownEvent.UpdateCountdownEventResponseBodyDto;
import com.tsilva.countdown.api.requests.get.GetFirebaseRealtimeDBApiClientGetCountdownEvents;
import com.tsilva.countdown.api.requests.patch.PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent;
import com.tsilva.countdown.api.requests.post.PostFirebaseRealtimeDBApiClientPostCountdownEvent;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.modules.editPost.activity.EditPostActivity;
import com.tsilva.countdown.modules.postList.activity.PostListActivity;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModel;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelComparator;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelComparatorModeEnum;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelFactory;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.persistence.contract.PostsIdToEventMapDto;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Telmo Silva on 20.01.2020.
 */

public final class PostListViewModel extends BaseObservable
{
    private View rootView = null;
    private PostItemViewModelFactory postItemViewModelFactory = null;
    private Context context = null;
    private PersistenceService persistenceService = null;
    private StorageService storageService = null;
    private UserLoginCredentials userLoginCredentials = null;
    private GetFirebaseRealtimeDBApiClientGetCountdownEvents
            getFirebaseRealtimeDBApiClientGetCountdownEvents = null;
    private PostFirebaseRealtimeDBApiClientPostCountdownEvent
            postFirebaseRealtimeDBApiClientPostCountdownEvent = null;
    private PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent
            patchFirebaseRealtimeDBApiClientUpdateCountdownEvent = null;

    private PostsIdToEventMapDto postsIdToEventMapDto = null;
    @Bindable
    public ObservableArrayList<PostItemViewModel> postItemList = new ObservableArrayList<>();

    private PostListViewModel() {}

    PostListViewModel(
            View rootView,
            PostItemViewModelFactory postItemViewModelFactory,
            Context context,
            PersistenceService persistenceService,
            StorageService storageService,
            UserLoginCredentials userLoginCredentials,
            GetFirebaseRealtimeDBApiClientGetCountdownEvents
                    getFirebaseRealtimeDBApiClientGetCountdownEvents,
            PostFirebaseRealtimeDBApiClientPostCountdownEvent
                    postFirebaseRealtimeDBApiClientPostCountdownEvent,
            PatchFirebaseRealtimeDBApiClientUpdateCountdownEvent
                    patchFirebaseRealtimeDBApiClientUpdateCountdownEvent)
    {
        this.rootView = rootView;
        this.postItemViewModelFactory = postItemViewModelFactory;
        this.context = context;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
        this.userLoginCredentials = userLoginCredentials;
        this.getFirebaseRealtimeDBApiClientGetCountdownEvents =
                getFirebaseRealtimeDBApiClientGetCountdownEvents;
        this.postFirebaseRealtimeDBApiClientPostCountdownEvent =
                postFirebaseRealtimeDBApiClientPostCountdownEvent;
        this.patchFirebaseRealtimeDBApiClientUpdateCountdownEvent =
                patchFirebaseRealtimeDBApiClientUpdateCountdownEvent;

        this.storageService.getSharedViewModelManager().setPostListViewModel(this);

        setup();
    }

    public void onDestroy()
    {
        this.storageService.getSharedViewModelManager().setPostListViewModel(null);
    }

    public void onNewEventClick(View view)
    {
        CurrentActivity currentActivity =
                storageService.getActivityManager().getCurrentActivity();
        Intent editPost =
                new Intent(currentActivity, EditPostActivity.class);
        editPost.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        currentActivity.startActivity(editPost);
    }

    private void setup()
    {
        fetchCountdownEvents();
    }

    private void fetchCountdownEvents()
    {
        getFirebaseRealtimeDBApiClientGetCountdownEvents
                .execute(new ResponseCallback<ResponseBody>()
                {
                    @Override
                    public void success(ResponseBody responseBody)
                    {
                        try
                        {
                            CountdownEventsDto countdownEventsDto =
                                    new CountdownEventsDto(new JSONObject(responseBody.string()));

                            postsIdToEventMapDto =
                                    new PostsIdToEventMapDto(filteredMap(countdownEventsDto));

                            storageService.savePostsIdToEventMapDto(postsIdToEventMapDto);

                            populatePostItemListFromMemory();
                        }
                        catch(JSONException | IOException e)
                        {
                            e.printStackTrace();

                            populatePostItemListFromMemory();
                        }
                    }

                    @Override
                    public void failure(Throwable t)
                    {
                        t.printStackTrace();
                        System.out.println("Couldn't retrieve posts");

                        populatePostItemListFromMemory();
                    }
                });
    }

    public void fetchPostCountdownEvent(
            final PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto)
    {
        if(postCountdownEventRequestBodyDto != null)
        {
            postFirebaseRealtimeDBApiClientPostCountdownEvent.execute(
                    postCountdownEventRequestBodyDto,
                    new ResponseCallback<PostCountdownEventResponseBodyDto>()
                    {
                        @Override
                        public void success(PostCountdownEventResponseBodyDto
                                                    postCountdownEventResponseBodyDto)
                        {
                            CountdownEventDto countdownEventDto =
                                    new CountdownEventDto(postCountdownEventRequestBodyDto);
                            postsIdToEventMapDto.postsIdToEventMap.put(
                                    postCountdownEventResponseBodyDto.name, countdownEventDto);

                            storageService.savePostsIdToEventMapDto(postsIdToEventMapDto);

                            populatePostItemListFromMemory();

                            storageService.getAdapterManager().notifyAdapters(null);
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            System.out.println("Couldn't post a new event");
                        }
                    });
        }
    }

    public void fetchPatchCountdownEvent(
            final String postId,
            final UpdateCountdownEventRequestBodyDto updateCountdownEventRequestBodyDto)
    {
        if(postId != null && updateCountdownEventRequestBodyDto != null)
        {
            patchFirebaseRealtimeDBApiClientUpdateCountdownEvent.execute(
                    postId,
                    updateCountdownEventRequestBodyDto,
                    new ResponseCallback<UpdateCountdownEventResponseBodyDto>()
                    {
                        @Override
                        public void success(UpdateCountdownEventResponseBodyDto
                                                    updateCountdownEventResponseBodyDto)
                        {
                            CountdownEventDto countdownEventDto =
                                    new CountdownEventDto(updateCountdownEventRequestBodyDto);
                            postsIdToEventMapDto.postsIdToEventMap.put(
                                    postId, countdownEventDto);

                            storageService.savePostsIdToEventMapDto(postsIdToEventMapDto);

                            populatePostItemListFromMemory();

                            storageService.getAdapterManager().notifyAdapters(null);
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            System.out.println("Couldn't patch post");
                        }
                    });
        }
    }

    private Map<String, CountdownEventDto> filteredMap(CountdownEventsDto countdownEventsDto)
    {
        if(countdownEventsDto != null && countdownEventsDto.postsIdToEventMap != null)
        {
            Map<String, CountdownEventDto> filteredMap = new HashMap<>();

            String currentUserEmail = userLoginCredentials.getEmail();

            for(String key : countdownEventsDto.postsIdToEventMap.keySet())
            {
                CountdownEventDto event = countdownEventsDto.postsIdToEventMap.get(key);

                if(event != null)
                {
                    if(currentUserEmail.equals(event.email))
                    {
                        filteredMap.put(key, event);
                    }

                    if(event.shareWith.contains(currentUserEmail))
                    {
                        filteredMap.put(key, event);
                    }
                }
            }

            return filteredMap;
        }

        return null;
    }

    private void populatePostItemList(PostsIdToEventMapDto postsIdToEventMapDto)
    {
        if(PostListActivity.isAlive)
        {
            List<PostItemViewModel> list = new LinkedList<>();

            for(String key : postsIdToEventMapDto.postsIdToEventMap.keySet())
            {
                list.add(postItemViewModelFactory
                                 .create(key, postsIdToEventMapDto.postsIdToEventMap.get(key)));
            }

            if(list.size() >= 2)
            {
                sortItems(list);
            }

            postItemList.clear();
            postItemList.addAll(list);
        }
    }

    private void sortItems(List<PostItemViewModel> list)
    {
        Collections.sort(
                list,
                new PostItemViewModelComparator(PostItemViewModelComparatorModeEnum.ASC_TSI));
    }

    private void populatePostItemListFromMemory()
    {
        PostsIdToEventMapDto postsIdToEventMapDto = storageService.loadPostsIdToEventMapDto();

        if(postsIdToEventMapDto != null)
        {
            populatePostItemList(postsIdToEventMapDto);
        }
    }
}