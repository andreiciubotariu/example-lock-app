package com.lockapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends ActionBarActivity {
    private static String GH_URL = "http://www.github.com/";
    private String mVersionName = "";
    private CharSequence mAppName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView appNameTextView = (TextView) findViewById (R.id.app_name);
        TextView appVersionTextView = (TextView) findViewById (R.id.app_version);
        try {
            mVersionName = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            mAppName = getPackageManager().getApplicationLabel(getApplicationInfo())+ " ";
        } catch (NameNotFoundException e) {
            //should not happen
        }

        appNameTextView.setText (mAppName);
        appVersionTextView.setText (mVersionName);
	}

    public void viewSite (View v){
        try{
            startActivity (new Intent(Intent.ACTION_VIEW, Uri.parse(GH_URL + "andreiciubotariu")));
        } catch (ActivityNotFoundException e){
            //usually won't happen.
            e.printStackTrace();
        }
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default: return super.onOptionsItemSelected(item);	
		}
	}

}
