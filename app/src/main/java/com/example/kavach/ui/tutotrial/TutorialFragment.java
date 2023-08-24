package com.example.kavach.ui.tutotrial;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.kavach.R;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

import android.app.Application;
import com.airbnb.lottie.Lottie;

public class TutorialFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);

        PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnBoarding());

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, paperOnboardingFragment)
                .commit();

        return rootView;
    }

    private ArrayList<PaperOnboardingPage> getDataForOnBoarding() {

        PaperOnboardingPage src1 = new PaperOnboardingPage(
                "KAVACH",
                "Your Saftey Companion.",
                Color.parseColor("#282c34") ,
                R.raw.animation_micanimsr,
                R.drawable.baseline_keyboard_double_arrow_right_24);

        PaperOnboardingPage src2 = new PaperOnboardingPage(
                "KAVACH",
                "Your Saftey Companion.",
                Color.parseColor("#282c34") ,
                R.raw.animation_sosbutton,
                R.drawable.baseline_keyboard_double_arrow_right_24);

        PaperOnboardingPage src3 = new PaperOnboardingPage(
                "KAVACH",
                "Your Saftey Companion.",
                Color.parseColor("#282c34") ,
                R.raw.animation_micanimsr,
                R.drawable.baseline_keyboard_double_arrow_right_24);



        ArrayList<PaperOnboardingPage>elements =new ArrayList<>();
        elements.add(src1);
        elements.add(src2);
        elements.add(src3);

        return elements;

    }
}
