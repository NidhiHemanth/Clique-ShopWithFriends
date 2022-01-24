package com.bmsce.clique_shopwithfriends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    static SharedPreferences mSharedPreferences;
    static SharedPreferences.Editor mEditor;
    static EditText token, session;
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
        token = view.findViewById(R.id.TOKEN);
        session = view.findViewById(R.id.SESSION_ID);

        actv(false);

        RadioGroup rg = view.findViewById(R.id.userType);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.host:
                    RoomCodeScreen.isHost = true;
                    actv(true);
                    break;
                case R.id.audience:
                    actv(false);
                    break;
            }
        });

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        checkSharedPreferences();

        Button room = view.findViewById(R.id.Enter);
        room.setOnClickListener(v -> {
            if(RoomCodeScreen.isHost)
                RoomCodeScreen.website = edit.getText().toString();

            OpenTokConfig.TOKEN = token.getText().toString();
            OpenTokConfig.SESSION_ID = session.getText().toString();

            mEditor.putString(getString(R.string.session_token), token.getText().toString());
            mEditor.commit();
            mEditor.putString(getString(R.string.session_id), session.getText().toString());
            mEditor.commit();

            Intent intent = new Intent(getActivity().getBaseContext(), RoomCodeScreen.class);
            startActivity(intent);

            Log.d("me->", "intent");

//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//                getActivity().startService(new Intent(getActivity(), FloatingViewService.class));
//                getActivity().finish();
//            } else if (Settings.canDrawOverlays(getActivity())) {
//                getActivity().startService(new Intent(getActivity(), FloatingViewService.class));
//                getActivity().finish();
//            } else {
//                askPermission();
//                Toast.makeText(getActivity(), "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
//            }
        });

        return view;
    }

    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    private void actv(final boolean active) {
        edit.setEnabled(active);
        if (active) {
            edit.requestFocus();
        }
    }

    private void checkSharedPreferences() {
        String s_token = mSharedPreferences.getString(getString(R.string.session_token), "");
        String s_id = mSharedPreferences.getString(getString(R.string.session_id), "");

        token.setText(s_token);
        session.setText(s_id);
    }


}
