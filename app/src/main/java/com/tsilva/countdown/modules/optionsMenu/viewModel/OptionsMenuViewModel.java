package com.tsilva.countdown.modules.optionsMenu.viewModel;

import android.content.Context;
import android.view.View;

import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountRequestBodyDto;
import com.tsilva.countdown.api.contract.firebaseAuthApiClient.deleteAccount.DeleteAccountResponseBodyDto;
import com.tsilva.countdown.api.requests.post.PostFirebaseAuthApiClientDeleteAccount;
import com.tsilva.countdown.api.restClient.ResponseCallback;
import com.tsilva.countdown.modules.confirmScreen.fragment.ConfirmDialog;
import com.tsilva.countdown.persistence.UserLoginCredentials;
import com.tsilva.countdown.services.PersistenceService;
import com.tsilva.countdown.services.StorageService;
import com.tsilva.countdown.storage.dialog.DialogManager;

import java.util.LinkedList;
import java.util.List;

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

                        String idToken = userLoginCredentials.getIdToken();
                        if(idToken != null)
                        {
                            DeleteAccountRequestBodyDto deleteAccountRequestBodyDto =
                                    new DeleteAccountRequestBodyDto(idToken);
                            fetchDeleteAccount(deleteAccountRequestBodyDto);
                        }

                        userLoginCredentials.clearCredencials();

                        storageService.getActivityManager().backToLoginScreen();
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