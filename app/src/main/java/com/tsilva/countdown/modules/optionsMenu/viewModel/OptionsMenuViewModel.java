package com.tsilva.countdown.modules.optionsMenu.viewModel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountResponseBodyDto;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientDeleteAccount;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.modules.loginScreen.activity.LoginScreenActivity;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.activity.CurrentActivity;

/**
 * Created by Telmo Silva on 10.01.2020.
 */

public final class OptionsMenuViewModel
{
    private Context context = null;
    private PersistenceService persistenceService = null;
    private StorageService storageService = null;
    private UserLoginCredentials userLoginCredentials = null;
    private PostFirebaseAuthApiClientDeleteAccount postFirebaseAuthApiClientDeleteAccount = null;

    private OptionsMenuViewModel() {}

    OptionsMenuViewModel(Context context,
                         PersistenceService persistenceService,
                         StorageService storageService,
                         UserLoginCredentials userLoginCredentials,
                         PostFirebaseAuthApiClientDeleteAccount
                                 postFirebaseAuthApiClientDeleteAccount)
    {
        this.context = context;
        this.persistenceService = persistenceService;
        this.storageService = storageService;
        this.userLoginCredentials = userLoginCredentials;
        this.postFirebaseAuthApiClientDeleteAccount = postFirebaseAuthApiClientDeleteAccount;
    }

    public void onDeleteAccountClick(View view)
    {
        String idToken = userLoginCredentials.getIdToken();
        if(idToken != null)
        {
            DeleteAccountRequestBodyDto deleteAccountRequestBodyDto =
                    new DeleteAccountRequestBodyDto(idToken);
            fetchDeleteAccount(deleteAccountRequestBodyDto);
        }

        userLoginCredentials.clearCredencials();

        CurrentActivity currentActivity = storageService.getActivityManager().getCurrentActivity();
        Intent loginScreen = new Intent(currentActivity, LoginScreenActivity.class);
        loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        currentActivity.startActivity(loginScreen);

        storageService.getActivityManager().clearCurrentActivityStack();
    }

    private void fetchDeleteAccount(DeleteAccountRequestBodyDto deleteAccountRequestBodyDto)
    {
        if(deleteAccountRequestBodyDto != null)
        {
            postFirebaseAuthApiClientDeleteAccount.execute(
                    deleteAccountRequestBodyDto,
                    new ResponseCallback<DeleteAccountResponseBodyDto>()
                    {
                        @Override
                        public void success(DeleteAccountResponseBodyDto
                                                    deleteAccountResponseBodyDto)
                        {
                            //TODO: present confirmation window
                            System.out.println();
                        }

                        @Override
                        public void failure(Throwable t)
                        {
                            t.printStackTrace();
                            System.out.println("Couldn't delete account");
                        }
                    });
        }
    }
}