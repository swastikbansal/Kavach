package com.example.kavach.ui.tutotrial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.kavach.R;

public class TutorialFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);

        // Find the arrowImageView by its ID in the rootView
        ImageView arrowImageView = rootView.findViewById(R.id.arrowImageView);

        // Initialize your ViewPager and adapter here
        ViewPager viewPager = rootView.findViewById(R.id.viewPager);
        OnboardingPagerAdapter onboardingPagerAdapter = new OnboardingPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(onboardingPagerAdapter);

        // Add the OnPageChangeListener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Calculate translation based on positionOffset to move the arrow
                float translationX = arrowImageView.getWidth() * (position + positionOffset);
                arrowImageView.setTranslationX(translationX);
            }

            @Override
            public void onPageSelected(int position) {
                // This method is called when a new page is selected
                // You can perform any additional actions here
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // This method is called when the scroll state changes
            }
        });

        return rootView;
    }
}

