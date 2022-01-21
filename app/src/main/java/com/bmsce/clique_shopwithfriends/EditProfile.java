package com.bmsce.clique_shopwithfriends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    TextInputLayout pname, pemail, pphone, pcity, pbemail, pbphone;
    String sname, semail, sphone, scity, sbemail, sbphone;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private FirebaseUser user;

    FirebaseDatabase rootNode;
    DatabaseReference DBreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            finish();
            return true;
        });

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        pname = findViewById(R.id.prof_username);
        pemail = findViewById(R.id.prof_email);
        pphone = findViewById(R.id.prof_phone);
        pcity = findViewById(R.id.prof_city);
        pbemail = findViewById(R.id.prof_backup_email);
        pbphone = findViewById(R.id.prof_backup_phone);

        rootNode = FirebaseDatabase.getInstance();
        DBreference = rootNode.getReference("users");

        Query checkUser = DBreference.orderByChild("name").equalTo(user.getDisplayName());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    sname = user.getDisplayName();
                    pname.setPlaceholderText(sname);
                    semail = snapshot.child(user.getDisplayName()).child("email").getValue(String.class);
                    pemail.setPlaceholderText(semail);
                    sphone = snapshot.child(user.getDisplayName()).child("phoneNo").getValue(String.class);
                    pphone.setPlaceholderText(sphone);
                    scity = snapshot.child(user.getDisplayName()).child("city").getValue(String.class);
                    pcity.setPlaceholderText(scity);
                    sbemail = snapshot.child(user.getDisplayName()).child("backupEmail").getValue(String.class);
                    pbemail.setPlaceholderText(sbemail);
                    sbphone = snapshot.child(user.getDisplayName()).child("backupPhoneNo").getValue(String.class);
                    pbphone.setPlaceholderText(sbphone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button save_profile = findViewById(R.id.save_edit_profile);
        save_profile.setOnClickListener(v -> {
            UserDefinition u = new UserDefinition(user.getDisplayName(), user.getEmail());

            u.setCity(pcity.getEditText().getText().toString() != null? pcity.getEditText().getText().toString() : "");
            u.setPhoneNo(pphone.getEditText().getText().toString() != null? pphone.getEditText().getText().toString() : "");
            u.setBackupEmail(pbemail.getEditText().getText().toString() != null? pbemail.getEditText().getText().toString() : "");
            u.setBackupPhoneNo(pbphone.getEditText().getText().toString() != null? pbphone.getEditText().getText().toString() : "");

            DBreference.child(u.getName()).setValue(u);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}