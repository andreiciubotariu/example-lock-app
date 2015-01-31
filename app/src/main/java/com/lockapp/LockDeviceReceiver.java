package com.lockapp;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LockDeviceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		DevicePolicyManager devicePolicyManager = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		ComponentName lockDeviceComponent = new ComponentName(context, MainActivity.LockAppDeviceAdmin.class);
		if (devicePolicyManager.isAdminActive(lockDeviceComponent))
		{
			try {
			    devicePolicyManager.lockNow();
			} catch (SecurityException se) {
				Toast.makeText(context, "Could not lock", Toast.LENGTH_SHORT).show();
			}
		}
        else {
            Toast.makeText(context, "Admin privilege required", Toast.LENGTH_SHORT).show();
        }
	}
}
