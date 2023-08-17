package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatFragment extends Fragment {



    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Toast.makeText(getActivity(),"Hello",Toast.LENGTH_LONG).show();
        //new CommonMethod(getActivity(),"Hello");
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        //TextView name = view.findViewById();
        return view;
        //return inflater.inflate(R.layout.fragment_chat, container, false);
    }
}