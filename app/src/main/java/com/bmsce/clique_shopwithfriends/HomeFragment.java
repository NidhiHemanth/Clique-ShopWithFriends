package com.bmsce.clique_shopwithfriends;

import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    View view;
    private EditText edit;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d("me->", "home fragment");

        edit = view.findViewById(R.id.WEBSITE);
        actv(false);

        RadioGroup rg = view.findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButton:
                    actv(true);
                    break;
                case R.id.radioButton2:
                    actv(false);
                    break;
            }
        });

        Button room = view.findViewById(R.id.Enter);
        room.setOnClickListener(v -> {

            EditText token = view.findViewById(R.id.TOKEN);
            EditText session = view.findViewById(R.id.SESSION_ID);

            RoomCodeScreen.website = edit.getText().toString();

            OpenTokConfig.TOKEN = token.getText().toString();
            OpenTokConfig.SESSION_ID = session.getText().toString();

            Intent intent = new Intent(getActivity().getBaseContext(), RoomCodeScreen.class);
            startActivity(intent);

            Log.d("me->", "intent");

        });
        return view;
    }

    private void actv(final boolean active) {
        edit.setEnabled(active);
        if (active) {
            edit.requestFocus();
        }
    }
}
