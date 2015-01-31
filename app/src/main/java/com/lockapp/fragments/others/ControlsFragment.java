package com.lockapp.fragments.others;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lockapp.R;
import com.lockapp.fragments.FragmentUtils;
import com.lockapp.fragments.lollipop.ControlsUtils;

public class ControlsFragment extends Fragment {

    FragmentUtils utils = new ControlsUtils() {

        @Override
        public void swapFragment() {
            if (getActivity() != null) {
                Fragment promptFragment = new PromptFragment();
                getFragmentManager().beginTransaction().replace(R.id.content, promptFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .commit();
            }
        }
    };

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstance){
       return utils.onCreateView(getActivity(), inflater, parent, savedInstance);
    }

    @Override
    public void onResume() {
        super.onResume();
        utils.onResume();
    }

    @Override
    public void onPause() {
        utils.onPause();
        super.onPause();
    }
}