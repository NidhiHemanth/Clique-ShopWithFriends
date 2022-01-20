package com.bmsce.clique_shopwithfriends;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SessionFragment extends Fragment {

    View view;

    public SessionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_session, container, false);

        Toast.makeText(getActivity(), "Session reached", Toast.LENGTH_SHORT).show();

        return view;
    }
}