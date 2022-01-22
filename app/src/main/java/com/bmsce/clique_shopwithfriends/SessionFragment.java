package com.bmsce.clique_shopwithfriends;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SessionFragment extends Fragment {

    View view;
    ListView simpleList;
    String sessionList[] = {"Session 1 : 25th November, 2021",
            "Session 2 : 30th November, 2021",
            "Session 3 : 31th November, 2021",
            "Session 4 : 3rd December, 2021",
            "Session 5 : 25th December, 2021",
            "Session 6 : 31st December, 2021"};

    public SessionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_session, container, false);

        // Toast.makeText(getActivity(), "Session reached", Toast.LENGTH_SHORT).show();

        simpleList = (ListView) view.findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getActivity(),
                        R.layout.listview_layout,
                        R.id.textView,
                        sessionList);

        simpleList.setAdapter(arrayAdapter);

        return view;
    }
}