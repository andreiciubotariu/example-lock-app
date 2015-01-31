package com.lockapp;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lockapp.fragments.NotificationStatus;
import com.lockapp.fragments.PromptUtils;
import com.lockapp.fragments.lollipop.ControlsUtils;

/**
 * The Activity shown to the user, a.k.a the Main Activity. Allows the user to lock the device or disable the device administrator.
 * @author Andrei Ciubotariu
 */
public class MainActivity extends ActionBarActivity {

    public static class LockAppDeviceAdmin extends DeviceAdminReceiver {
        @Override
        public void onDisabled(Context context, Intent intent) {
            NotificationStatus.cancelNotification(context);
            LocalBroadcastManager.getInstance(context)
                                .sendBroadcast(new Intent(ControlsUtils.BROADCAST_ADMIN_DISABLED));
        }

        @Override
        public void onEnabled(Context context, Intent intent) {
            LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(new Intent (PromptUtils.BROADCAST_ADMIN_ENABLED));
        }

    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName lockDeviceComponent = new ComponentName(this, LockAppDeviceAdmin.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (devicePolicyManager.isAdminActive(lockDeviceComponent)) {
                getFragmentManager().beginTransaction().replace(R.id.content, new com.lockapp.fragments.lollipop.ControlsFragment())
                        .commit();
            }
            else {
                getFragmentManager().beginTransaction().replace(R.id.content, new com.lockapp.fragments.lollipop.PromptFragment())
                        .commit();
            }
        }
        else {
            if (devicePolicyManager.isAdminActive(lockDeviceComponent)) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new com.lockapp.fragments.others.ControlsFragment())
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new com.lockapp.fragments.others.PromptFragment())
                        .commit();
            }
        }

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_lock, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.menu_about:
			Intent intent = new Intent (this, AboutActivity.class);
			startActivity (intent);
		}
		return super.onOptionsItemSelected(item);

	}

}