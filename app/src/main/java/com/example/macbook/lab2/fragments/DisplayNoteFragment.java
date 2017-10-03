package com.example.macbook.lab2.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbook.lab2.R;

public class DisplayNoteFragment extends Fragment {

    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notes_display, container, false);

        return view;
    }
}