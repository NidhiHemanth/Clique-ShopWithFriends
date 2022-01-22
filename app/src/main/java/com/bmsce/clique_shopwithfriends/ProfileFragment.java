package com.bmsce.clique_shopwithfriends;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    ImageView editProfile;
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

        editProfile = view.findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getBaseContext(), EditProfile.class);
            startActivity(intent);
            getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        TextView username = (TextView) view.findViewById(R.id.username);
        TextView email = (TextView) view.findViewById(R.id.email);
        Uri photo = user.getPhotoUrl();
        username.setText(user != null ? user.getDisplayName() : "Not Specified");
        email.setText(user != null ? user.getEmail() : "Not Specified");
        ImageView pfp = (ImageView) view.findViewById(R.id.profilepicture);
        pfp.setImageURI(android.net.Uri.parse(photo.toString()));
        Button sign_out = view.findViewById(R.id.sign_out);
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