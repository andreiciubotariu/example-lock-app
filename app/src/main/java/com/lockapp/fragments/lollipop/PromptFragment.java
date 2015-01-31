package com.lockapp.fragments.lollipop;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lockapp.R;
import com.lockapp.fragments.FragmentUtils;
import com.lockapp.fragments.PromptUtils;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class PromptFragment extends Fragment {


    FragmentUtils utils = new PromptUtils() {

        @Override
        public void swapFragment() {
            if (getActivity() != null) {
                Fragment controlsFragment = new ControlsFragment();
                controlsFragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.change_transform));
                controlsFragment.setEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.explode));
                getFragmentManager().beginTransaction().replace(R.id.content, controlsFragment)
                        .addSharedElement(getSharedElement(), SHARED_ELEMENT_NAME)
                        .commit();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedState) {
        return utils.onCreateView(getActivity(), inflater, parent, savedState);
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