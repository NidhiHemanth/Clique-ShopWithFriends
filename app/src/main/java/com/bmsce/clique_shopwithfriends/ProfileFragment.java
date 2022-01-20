package com.bmsce.clique_shopwithfriends;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    View view;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private FirebaseUser user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Toast.makeText(getActivity(), "Profile reached", Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity().getApplicationContext(), gso);

        TextView username = (TextView) view.findViewById(R.id.username);
        TextView email = (TextView) view.findViewById(R.id.email);

        username.setText(user != null ? user.getDisplayName() : "Not Specified");
        email.setText(user != null ? user.getEmail() : "Not Specified");

        Button sign_out = (Button) view.findViewById(R.id.sign_out);
        sign_out.setOnClickListener(v -> {
            mAuth.signOut();
            googleSignInClient.signOut();

            Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });


        return view;
    }
}