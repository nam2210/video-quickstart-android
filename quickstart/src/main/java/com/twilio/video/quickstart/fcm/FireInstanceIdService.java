package com.twilio.video.quickstart.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by nampham on 10/10/18.
 */
public class FireInstanceIdService extends FirebaseInstanceIdService{
    private static final String TAG = FireInstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

    }
}
