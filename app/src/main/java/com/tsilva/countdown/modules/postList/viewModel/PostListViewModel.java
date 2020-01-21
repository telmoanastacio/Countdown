package com.tsilva.countdown.modules.postList.viewModel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;

import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.getCountdownEvent.CountdownEventsDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseRealtimeDBApiClient.postCountdownEvent.PostCountdownEventResponseBodyDto;
import com.tsilva.countdown.api.requests.get.GetFirebaseRealtimeDBApiClientGetCountdownEvents;
import com.tsilva.countdown.api.requests.post.PostFirebaseRealtimeDBApiClientPostCountdownEvent;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.modules.postList.activity.PostListActivity;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModel;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelComparator;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelComparatorModeEnum;
import com.tsilva.countdown.modules.postList.viewModel.item.PostItemViewModelFactory;
import com.tsilva.countdown.persistence.contract.PostsIdToEventMapDto;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
    private GetFirebaseRealtimeDBApiClientGetCountdownEvents
            getFirebaseRealtimeDBApiClientGetCountdownEvents = null;
    private PostFirebaseRealtimeDBApiClientPostCountdownEvent
            postFirebaseRealtimeDBApiClientPostCountdownEvent = null;

    private PostsIdToEventMapDto postsIdToEventMapDto = null;
    @Bindable
    public ObservableArrayList<PostItemViewModel> postItemList = new ObservableArrayList<>();

    private PostListViewModel() {}

    public PostListViewModel(
            View rootView,
            PostItemViewModelFactory postItemViewModelFactory,
            Context context,
            PersistenceService persistenceService,
            StorageService storageService,
            GetFirebaseRealtimeDBApiClientGetCountdownEvents
                    getFirebaseRealtimeDBApiClientGetCountdownEvents,
            PostFirebaseRealtimeDBApiClientPostCountdownEvent
                    postFirebaseRealtimeDBApiClientPostCountdownEvent)
    {
        this.rootView = rootView;
        this.postItemViewModelFactory = postItemViewModelFactory;
        this.context = context;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
        this.getFirebaseRealtimeDBApiClientGetCountdownEvents =
                getFirebaseRealtimeDBApiClientGetCountdownEvents;
        this.postFirebaseRealtimeDBApiClientPostCountdownEvent =
                postFirebaseRealtimeDBApiClientPostCountdownEvent;

        setup();
    }

    //TODO: present edit view and save if confirmed
    public void onNewEventClick(View view)
    {
        PostCountdownEventRequestBodyDto postCountdownEventRequestBodyDto =
                new PostCountdownEventRequestBodyDto(
                        "tberlinera11@hotmail.com",
                        "Test title 6",
                        "Test details 6",
                        "",
                        new ArrayList<String>(0),
                        1579859709868L,
                        1580459709868L);
        fetchPostCountdownEvent(postCountdownEventRequestBodyDto);
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
                                    new PostsIdToEventMapDto(countdownEventsDto.postsIdToEventMap);

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

    private void fetchPostCountdownEvent(
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
//                            postItemList.add(postItemViewModelFactory.create(
//                                    postCountdownEventResponseBodyDto.name, countdownEventDto));
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

            if(list.isEmpty())
            {
                storageService.getActivityManager().backToLoginScreen();
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