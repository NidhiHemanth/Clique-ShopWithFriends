package com.bmsce.clique_shopwithfriends;

import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d("me->", "home fragment");

        EditText token = view.findViewById(R.id.TOKEN);
        EditText session = view.findViewById(R.id.SESSION_ID);
        OpenTokConfig.TOKEN = token.getText().toString();
        OpenTokConfig.SESSION_ID = session.getText().toString();
        Button room = view.findViewById(R.id.Enter);
        room.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getBaseContext(), RoomCodeScreen.class);
            startActivity(intent);
            Log.d("me->", "intent");
        });
        return view;
    }
}
