package com.lockapp.fragments;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lockapp.R;

/**
 * Created by Andrei on 30/01/2015.
 */
public abstract class PromptUtils extends FragmentUtils {
    public static final String BROADCAST_ADMIN_ENABLED = "BROADCAST_ADMIN_ENABLED";

    private Button mModifyAdminButton;

    private BroadcastReceiver mAdminEnabledReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            swapFragment();
        }
    };

    @Override
    final public View onCreateView(final Activity activity, LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        init(activity);
        View root = inflater.inflate(R.layout.fragment_prompt, parent, false);
        mModifyAdminButton = (Button) root.findViewById(R.id.modify_admin);
        mModifyAdminButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addAdminIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                addAdminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, getLockName());
                addAdminIntent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "Used for locking the device");
                activity.startActivity(addAdminIntent);
            }
        });
        getSharedElement().setEnabled(true);
        return root;
    }


    @Override
    final public void checkStatus() {
        if (getLockName() != null && getPolicyManager() != null) {
            boolean adminEnabled = getPolicyManager().isAdminActive(getLockName());

            if (adminEnabled) {
                getSharedElement().setEnabled(false);
                swapFragmentDelayed();
            }
        }
    }
    @Override
    final public View getSharedElement() {
        return mModifyAdminButton;
    }

    @Override
    final public void onResume() {
        getBroadcastManager().registerReceiver(mAdminEnabledReceiver, new IntentFilter(BROADCAST_ADMIN_ENABLED));
        checkStatus();
    }

    @Override
    final public void onPause() {
        getBroadcastManager().unregisterReceiver(mAdminEnabledReceiver);
    }

}
