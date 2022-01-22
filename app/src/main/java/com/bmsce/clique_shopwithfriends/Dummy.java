package com.bmsce.clique_shopwithfriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Dummy extends AppCompatActivity {

    ImageView iv2, iv3, iv4, iv5;
    ArrayList<String> p1, p2, p3, p4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        iv2 = findViewById(R.id.imageView2);
        iv3 = findViewById(R.id.imageView3);
        iv4 = findViewById(R.id.imageView4);
        iv5 = findViewById(R.id.imageView5);

        p1 = new ArrayList<>();
        p2 = new ArrayList<>();
        p3 = new ArrayList<>();
        p4 = new ArrayList<>();

        iv2.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String temp = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            p1.add(temp);

            Toast.makeText(this, p1.toString(), Toast.LENGTH_LONG).show();
        });

        iv3.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String temp = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            p2.add(temp);

            Toast.makeText(this, p2.toString(), Toast.LENGTH_LONG).show();
        });

        iv4.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String temp = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            p3.add(temp);

            Toast.makeText(this, p3.toString(), Toast.LENGTH_LONG).show();
        });

        iv5.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String temp = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            p4.add(temp);

            Toast.makeText(this, p4.toString(), Toast.LENGTH_LONG).show();
        });

        Button submit = findViewById(R.id.button2);
        submit.setOnClickListener(v -> {
            String message = "Person 1 : " + p1.toString() + "\nPerson 2 : " + p2.toString() + "\nPerson 3 : " + p3.toString() + "\nPerson 4 : " + p4.toString();
            sendMessage(message);
        });
    }

    private void sendMessage(String message)
    {
        // Creating new intent
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");

        // Give your message here
        intent.putExtra(Intent.EXTRA_TEXT, message);

        // Checking whether Whatsapp
        // is installed or not
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this,"Please install whatsapp first.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Starting Whatsapp
        startActivity(intent);
    }

}