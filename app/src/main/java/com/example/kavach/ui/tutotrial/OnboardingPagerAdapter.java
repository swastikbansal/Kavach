package com.example.kavach.ui.tutotrial;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class OnboardingPagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 7; // Number of onboarding screens

    public OnboardingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OnboardingFragment1();
            case 1:
                return new OnboardingFragment2();
            case 2:
                return new OnboardingFragment3();
            case 3:
                return new OnboardingFragment4();
            case 4:
                return new OnboardingFragment5();
            case 5:
                return new OnboardingFragment6();
            case 6:
                return new OnboardingFragment7();


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
