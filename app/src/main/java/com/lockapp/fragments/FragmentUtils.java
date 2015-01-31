package com.lockapp.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lockapp.MainActivity;
import com.lockapp.R;

/**
 * Created by Andrei on 30/01/2015.
 */
public abstract class FragmentUtils {
    public static final String SHARED_ELEMENT_NAME = "button";
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mLockDeviceComponent;
    private LocalBroadcastManager mLocalBroadcastManager;
    private Handler mHandler = new Handler();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void putFragment (Activity activity, Fragment fragment) {
        activity.getFragmentManager().beginTransaction()
                                     .replace(R.id.content, fragment)
                                     .commit();
    }

    public static void putFragment (final FragmentActivity activity, android.support.v4.app.Fragment fragment){
        activity.getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.content, fragment)
                                            .commit();
    }

    public void init(Activity activity) {
        mDevicePolicyManager = (DevicePolicyManager) activity.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mLockDeviceComponent = new ComponentName(activity, MainActivity.LockAppDeviceAdmin.class);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(activity);
    }

    public DevicePolicyManager getPolicyManager() {
        return mDevicePolicyManager;
    }

    public ComponentName getLockName() {
        return mLockDeviceComponent;
    }

    public void swapFragmentDelayed() {
        mHandler.postDelayed(new Runnable(){

            public void run() {
                swapFragment();
            }
        },555);
    }

    public LocalBroadcastManager getBroadcastManager() {
        return mLocalBroadcastManager;
    }

    public abstract View onCreateView(Activity activity, LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    public abstract void onResume();
    public abstract void onPause();
    public abstract void swapFragment();
    public abstract View getSharedElement();
    public abstract void checkStatus();
}
