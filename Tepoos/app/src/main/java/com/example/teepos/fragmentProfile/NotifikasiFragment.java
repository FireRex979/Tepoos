package com.example.teepos.fragmentProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teepos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifikasiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifikasiFragment extends Fragment {
    public NotifikasiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifikasi, container, false);
    }
}