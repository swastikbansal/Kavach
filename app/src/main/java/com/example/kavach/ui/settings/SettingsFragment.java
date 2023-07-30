package com.example.kavach.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kavach.R;
import com.example.kavach.databinding.FragmentHomeBinding;
import com.example.kavach.ui.home.HomeViewModel;

public class SettingsFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings,container,false);
    }

}

