package com.lockapp.fragments.lollipop;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lockapp.LockDeviceReceiver;
import com.lockapp.R;
import com.lockapp.fragments.FragmentUtils;
import com.lockapp.fragments.NotificationStatus;

/**
 * Created by Andrei on 30/01/2015.
 */
public abstract class ControlsUtils extends FragmentUtils {
    public static final String BROADCAST_ADMIN_DISABLED = "BROADCAST_ADMIN_DISABLED";
    private Button mLockNowButton;
    private Button mDisableAdminButton;

    private BroadcastReceiver mAdminDisabledReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationStatus.cancelNotification(context);
            swapFragment();
        }
    };

    @Override
    final public View onCreateView(final Activity activity, LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        init(activity);
        View root = inflater.inflate (R.layout.fragment_controls, parent, false);
        final Intent lockIntent = new Intent (activity, LockDeviceReceiver.class);
        mLockNowButton = (Button) root.findViewById(R.id.lock_now_button);
        mLockNowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getPolicyManager().isAdminActive(getLockName())) {
                    activity.sendBroadcast(lockIntent);
                } else {
                    Intent addAdminIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    addAdminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, getLockName());
                    addAdminIntent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                            "Used for locking the device");
                    activity.startActivity(addAdminIntent);
                }
            }
        });

        mDisableAdminButton = (Button) root.findViewById(R.id.modify_admin);
        mDisableAdminButton.setEnabled(true);
        mDisableAdminButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getPolicyManager().removeActiveAdmin(getLockName());
            }
        });


        CheckBox checkbox = (CheckBox) root.findViewById(R.id.status_bar_check);
        checkbox.setChecked(NotificationStatus.hasNotification());
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NotificationStatus.createNotification(activity);
                } else {
                    NotificationStatus.cancelNotification(activity);

                }
            }
        });
        getSharedElement().setEnabled(true);
        return root;
    }

    @Override
    final public View getSharedElement() {
        return mDisableAdminButton;
    }

    @Override
    final public void onResume() {
        getBroadcastManager().registerReceiver(mAdminDisabledReceiver, new IntentFilter(BROADCAST_ADMIN_DISABLED));
        checkStatus();
    }

    @Override
    final public void onPause() {
        getBroadcastManager().unregisterReceiver(mAdminDisabledReceiver);
    }

    @Override
    final public void checkStatus() {
        if (getLockName() != null && getPolicyManager() != null) {
            boolean adminEnabled = getPolicyManager().isAdminActive(getLockName());
            if (!adminEnabled) {
                getSharedElement().setEnabled(false);
                swapFragmentDelayed();
            }
        }
    }
}
