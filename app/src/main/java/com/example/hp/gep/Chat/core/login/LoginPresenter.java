package com.example.hp.gep.Chat.core.login;

import android.app.Activity;
import android.util.Log;


public class LoginPresenter implements LoginContract.Presenter, LoginContract.OnLoginListener {
    private LoginInteractor mLoginInteractor;

    public LoginPresenter() {
        mLoginInteractor = new LoginInteractor(this);
    }

    @Override
    public void login(Activity activity, String email, String password) {
        mLoginInteractor.performFirebaseLogin(activity, email, password);
    }

    @Override
    public void onSuccess(String message) {
        Log.d("Success", message);
    }

    @Override
    public void onFailure(String message) {
        Log.d("fail", message);
    }
}
