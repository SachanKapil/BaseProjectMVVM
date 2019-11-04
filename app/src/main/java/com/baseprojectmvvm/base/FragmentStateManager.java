package com.baseprojectmvvm.base;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public abstract class FragmentStateManager {

    private final FragmentManager mFragmentManager;
    private BaseFragment activeFragment;
    private ViewGroup container;

    public FragmentStateManager(ViewGroup container, FragmentManager fm) {
        mFragmentManager = fm;
        this.container = container;
    }

    public abstract BaseFragment getItem(int position);

    public BaseFragment changeFragment(int position, String tag) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        BaseFragment fragment = (BaseFragment) mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = getItem(position);
            fragmentTransaction.add(container.getId(), fragment, tag);

        } else {
            fragmentTransaction.show(fragment);
        }
        if (activeFragment != null && activeFragment != fragment) {
            fragmentTransaction.hide(activeFragment);
        }

        activeFragment = fragment;

        // Set fragment as primary navigator for child manager back stack to be handled by system
        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();


        return fragment;
    }

    /**
     * Removes Fragment from Fragment Manager and clears all saved states. Call to changeFragment()
     * will restart fragment from fresh state.
     *
     * @param position
     */
    public void removeFragment(int position, String tag) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.remove(mFragmentManager.findFragmentByTag(tag));
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    public Fragment getCurrentVisibleFragment() {
        Fragment currentVisibleFragment = null;
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                currentVisibleFragment = fragment;
            }
        }
        return currentVisibleFragment;
    }
}