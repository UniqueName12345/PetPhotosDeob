package com.google.appinventor.components.runtime.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.SynchronousQueue;

/* loaded from: classes.dex */
public class AccountChooser {
    private static final String ACCOUNT_PREFERENCE = "account";
    private static final String ACCOUNT_TYPE = "com.google";
    private static final String LOG_TAG = "AccountChooser";
    private static final String NO_ACCOUNT = "";
    private AccountManager accountManager;
    private Activity activity;
    private String chooseAccountPrompt;
    private String preferencesKey;
    private String service;

    public AccountChooser(Activity activity, String service, String title, String key) {
        this.activity = activity;
        this.service = service;
        this.chooseAccountPrompt = title;
        this.preferencesKey = key;
        this.accountManager = AccountManager.get(activity);
    }

    public Account findAccount() {
        Account account;
        Account[] accounts = this.accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length == 1) {
            persistAccountName(accounts[0].name);
            return accounts[0];
        } else if (accounts.length == 0) {
            String accountName = createAccount();
            if (accountName != null) {
                persistAccountName(accountName);
                return this.accountManager.getAccountsByType(ACCOUNT_TYPE)[0];
            }
            Log.i(LOG_TAG, "User failed to create a valid account");
            return null;
        } else {
            String accountName2 = getPersistedAccountName();
            if (accountName2 == null || (account = chooseAccount(accountName2, accounts)) == null) {
                String accountName3 = selectAccount(accounts);
                if (accountName3 != null) {
                    persistAccountName(accountName3);
                    return chooseAccount(accountName3, accounts);
                }
                Log.i(LOG_TAG, "User failed to choose an account");
                return null;
            }
            return account;
        }
    }

    private Account chooseAccount(String accountName, Account[] accounts) {
        for (Account account : accounts) {
            if (account.name.equals(accountName)) {
                Log.i(LOG_TAG, "chose account: " + accountName);
                return account;
            }
        }
        return null;
    }

    private String createAccount() {
        Log.i(LOG_TAG, "Adding auth token account ...");
        AccountManagerFuture<Bundle> future = this.accountManager.addAccount(ACCOUNT_TYPE, this.service, null, null, this.activity, null, null);
        try {
            Bundle result = future.getResult();
            String accountName = result.getString("authAccount");
            Log.i(LOG_TAG, "created: " + accountName);
            return accountName;
        } catch (AuthenticatorException e) {
            e.printStackTrace();
            return null;
        } catch (OperationCanceledException e2) {
            e2.printStackTrace();
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private String selectAccount(Account[] accounts) {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        SelectAccount select = new SelectAccount(accounts, queue);
        select.start();
        Log.i(LOG_TAG, "Select: waiting for user...");
        String account = null;
        try {
            account = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Selected: " + account);
        if ("".equals(account)) {
            return null;
        }
        return account;
    }

    private SharedPreferences getPreferences() {
        return this.activity.getSharedPreferences(this.preferencesKey, 0);
    }

    private String getPersistedAccountName() {
        return getPreferences().getString(ACCOUNT_PREFERENCE, null);
    }

    private void persistAccountName(String accountName) {
        Log.i(LOG_TAG, "persisting account: " + accountName);
        getPreferences().edit().putString(ACCOUNT_PREFERENCE, accountName).commit();
    }

    public void forgetAccountName() {
        getPreferences().edit().remove(ACCOUNT_PREFERENCE).commit();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class SelectAccount extends Thread implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
        private String[] accountNames;
        private SynchronousQueue<String> queue;

        SelectAccount(Account[] accounts, SynchronousQueue<String> queue) {
            this.queue = queue;
            this.accountNames = new String[accounts.length];
            for (int i = 0; i < accounts.length; i++) {
                this.accountNames[i] = accounts[i].name;
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            AccountChooser.this.activity.runOnUiThread(new Runnable() { // from class: com.google.appinventor.components.runtime.util.AccountChooser.SelectAccount.1
                @Override // java.lang.Runnable
                public void run() {
                    AlertDialog.Builder ab = new AlertDialog.Builder(AccountChooser.this.activity).setTitle(Html.fromHtml(AccountChooser.this.chooseAccountPrompt)).setOnCancelListener(SelectAccount.this).setSingleChoiceItems(SelectAccount.this.accountNames, -1, SelectAccount.this);
                    ab.show();
                    Log.i(AccountChooser.LOG_TAG, "Dialog showing!");
                }
            });
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int button) {
            try {
                if (button >= 0) {
                    String account = this.accountNames[button];
                    Log.i(AccountChooser.LOG_TAG, "Chose: " + account);
                    this.queue.put(account);
                } else {
                    this.queue.put("");
                }
            } catch (InterruptedException e) {
            }
            dialog.dismiss();
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialog) {
            Log.i(AccountChooser.LOG_TAG, "Chose: canceled");
            onClick(dialog, -1);
        }
    }
}
