package com.bmsce.clique_shopwithfriends;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomCodeScreen extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    static class SubscriberContainer {
        public LinearLayout container;
        public ToggleButton toggleAudio;
        public Subscriber subscriber;
        public Uri uri;
        public ImageView displayImage;
        ArrayList<String> items;

        public SubscriberContainer(LinearLayout container, ToggleButton toggleAudio, ImageView imageView, Subscriber subscriber, Uri uri) {
            this.toggleAudio = toggleAudio;
            this.subscriber = subscriber;
            this.container = container;
            this.uri = uri;
            this.displayImage = imageView;
            this.items = new ArrayList<>();
        }
    }

    AlertDialog.Builder builder;
    public static Uri uri;
    public static boolean isHost = false;
    public static String website = "https://www.amazon.in/";
    private static final String TAG = "tag->";
    private static final int PERMISSIONS_REQUEST_CODE = 124;
    private Session session;
    WebView webViewContainer;
    private final int MAX_NUM_SUBSCRIBERS = 4;
    private Publisher publisher;
    List<SubscriberContainer> subscribers;
    private boolean sessionConnected = false;

    private PublisherKit.PublisherListener publisherListener = new PublisherKit.PublisherListener() {
        @Override
        public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
            Log.d(TAG, "onStreamCreated: Own stream " + stream.getStreamId() + " created");
        }

        @Override
        public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
            Log.d(TAG, "onStreamDestroyed: Own stream " + stream.getStreamId() + " destroyed");
        }

        @Override
        public void onError(PublisherKit publisherKit, OpentokError opentokError) {
            finishWithMessage("PublisherKit error: " + opentokError.getMessage());
        }
    };


    private Session.SessionListener sessionListener = new Session.SessionListener() {
        //called once session is connected to
        @Override
        public void onConnected(Session session) {
            Log.d(TAG, "onConnected: Connected to session " + session.getSessionId());
            sessionConnected = true;

            //Publishing a stream means that the audio and video is published to the session
            //Once you have connected to a session, you can publish a stream that other clients
            // connected to the session can view.
            //For audio -> both host and audience must publish the stream
            //for screenshare, only host must publish and audience must only subscribe

            Log.d(TAG, "onConnected: test 5");
            ScreenSharingCapturer screenSharingCapturer = new ScreenSharingCapturer(RoomCodeScreen.this, webViewContainer);
            Log.d(TAG, "onConnected: test 6");
            publisher = new Publisher.Builder(RoomCodeScreen.this)
                    .capturer(screenSharingCapturer)
                    .build();
            Log.d(TAG, "onConnected: test 7");
            publisher.setPublisherListener(publisherListener);
            Log.d(TAG, "onConnected: test 8");
            publisher.setPublisherVideoType(PublisherKit.PublisherKitVideoType.PublisherKitVideoTypeScreen);
            Log.d(TAG, "onConnected: test 9");
            publisher.setAudioFallbackEnabled(false);
            Log.d(TAG, "onConnected: test 10");

               webViewContainer.setWebViewClient(new WebViewClient());
                Log.d(TAG, "onConnected: test 1");
                WebSettings webSettings = webViewContainer.getSettings();
                Log.d(TAG, "onConnected: test 2");
                webViewContainer.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                Log.d(TAG, "onConnected: test 3");
                webViewContainer.loadUrl(website);
                Log.d(TAG, "onConnected: test 4");
                webSettings.setJavaScriptEnabled(true);
                Log.d(TAG, "onConnected: test 12");
            publisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            Log.d(TAG, "onConnected: test 13");
            session.publish(publisher);

        }

        @Override
        public void onDisconnected(Session session) {
            Log.d(TAG, "onDisconnected: disconnected from session " + session.getSessionId());
            sessionConnected = false;
            RoomCodeScreen.this.session = null;
        }

        @Override
        public void onError(Session session, OpentokError opentokError) {
            finishWithMessage("Session error: " + opentokError.getMessage());
        }

        @Override
        public void onStreamReceived(Session session, Stream stream) {
            Log.d(TAG, "onStreamReceived: New stream " + stream.getStreamId() + " in session " + session.getSessionId());

            Log.d(TAG, "onStreamReceived: test 15");

            //subscribes to the streams already present in the sessions which it connects to
            final Subscriber subscriber = new Subscriber.Builder(RoomCodeScreen.this, stream).build();
            subscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            subscriber.setSubscriberListener(subscriberListener);
            session.subscribe(subscriber);
            webViewContainer.addView(subscriber.getView());
            addSubscriber(subscriber);
        }

        @Override
        public void onStreamDropped(Session session, Stream stream) {
            Log.d(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " + session.getSessionId());

            removeSubscriberWithStream(stream);
        }
    };

    SubscriberKit.SubscriberListener subscriberListener = new SubscriberKit.SubscriberListener() {
        @Override
        public void onConnected(SubscriberKit subscriberKit) {
            Log.d(TAG, "onConnected: Subscriber connected. Stream: " + subscriberKit.getStream().getStreamId());
        }

        @Override
        public void onDisconnected(SubscriberKit subscriberKit) {
            Log.d(TAG, "onDisconnected: Subscriber disconnected. Stream: " + subscriberKit.getStream().getStreamId());
        }

        @Override
        public void onError(SubscriberKit subscriberKit, OpentokError opentokError) {
            finishWithMessage("SubscriberKit onError: " + opentokError.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_code_screen);

        builder = new AlertDialog.Builder(this);

        //goes to openTokConfig.java and checks if there is a sessionID and token provided or not
        if(!OpenTokConfig.isValid()) {
            finishWithMessage("Invalid OpenTokConfig. " + OpenTokConfig.getDescription());
            return;
        }

        final ToggleButton toggleAudio = findViewById(R.id.toggleAudio);
        toggleAudio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (publisher == null) {
                return;
            }

            if (isChecked) {
                publisher.setPublishAudio(true);
            } else {
                publisher.setPublishAudio(false);
            }
        });

        subscribers = new ArrayList<>();
        for (int i = 0; i < MAX_NUM_SUBSCRIBERS; i++) {
            int containerId = getResources().getIdentifier("subscriberview" + (new Integer(i)).toString(),
                    "id", this.getPackageName());
            int toggleAudioId = getResources().getIdentifier("toggleAudioSubscriber" + (new Integer(i)).toString(),
                    "id", this.getPackageName());
            int imageViewId = getResources().getIdentifier("imageView" + (new Integer(i)).toString(),
                    "id", this.getPackageName());
            subscribers.add(new SubscriberContainer(
                    findViewById(containerId),
                    findViewById(toggleAudioId),
                    findViewById(imageViewId),
                    null, uri));

        }
        webViewContainer = findViewById(R.id.webview);
        requestPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (session == null) {
            return;
        }

        session.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (session == null) {
            return;
        }

        session.onPause();

        if (isFinishing()) {
            disconnectSession();
        }
    }

    @Override
    protected void onDestroy() {
        disconnectSession();

        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ": " + perms);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        finishWithMessage("onPermissionsDenied: " + requestCode + ": " + perms);
    }

    @AfterPermissionGranted(PERMISSIONS_REQUEST_CODE)
    private void requestPermissions() {
        String[] perms = {Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

        if (EasyPermissions.hasPermissions(this, perms)) {
            //Joining session if permissions are given
            session = new Session.Builder(this, OpenTokConfig.API_KEY, OpenTokConfig.SESSION_ID).build();
            session.setSessionListener(sessionListener);
            session.connect(OpenTokConfig.TOKEN);
        } else {
            //request permissions if not given
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_video_app), PERMISSIONS_REQUEST_CODE, perms);
        }
    }

    private SubscriberContainer findFirstEmptyContainer(Subscriber subscriber) {
        for (SubscriberContainer subscriberContainer : subscribers) {
            if (subscriberContainer.subscriber == null) {
                return subscriberContainer;
            }
        }
        return null;
    }

    private SubscriberContainer findContainerForStream(Stream stream) {
        for (SubscriberContainer subscriberContainer : subscribers) {
            if (subscriberContainer.subscriber.getStream().getStreamId().equals(stream.getStreamId())) {
                return subscriberContainer;
            }
        }
        return null;
    }

    private void addSubscriber(Subscriber subscriber) {
        SubscriberContainer container = findFirstEmptyContainer(subscriber);
        if (container == null) {
            Toast.makeText(this, "New subscriber ignored. MAX_NUM_SUBSCRIBERS limit reached", Toast.LENGTH_LONG).show();
            return;
        }

        container.subscriber = subscriber;
        subscriber.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);

        container.toggleAudio.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                subscriber.setSubscribeToAudio(true);
            } else {
                subscriber.setSubscribeToAudio(false);
            }
        });

        container.container.setVisibility(View.VISIBLE);
        container.toggleAudio.setVisibility(View.VISIBLE);
        container.displayImage.setVisibility(View.VISIBLE);

        container.displayImage.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String temp = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            container.items.add(temp);

            Toast.makeText(getApplicationContext(), container.items.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    private void removeSubscriberWithStream(Stream stream) {
        SubscriberContainer container = findContainerForStream(stream);

        if (container == null) {
            return;
        }
        container.container.setVisibility(View.INVISIBLE);
        container.toggleAudio.setOnCheckedChangeListener(null);
        container.toggleAudio.setVisibility(View.INVISIBLE);
        container.displayImage.setOnClickListener(null);
        container.displayImage.setVisibility(View.INVISIBLE);
        container.subscriber = null;
    }

    private void disconnectSession() {
        builder.setMessage("Do you want to exit this meet?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    if (session == null || !sessionConnected) {
                        return;
                    }

                    sessionConnected = false;

                    String message = "";
                    for(int i = 0; i < subscribers.size(); i++) {
                        message += "Person " + i + " : " + subscribers.get(i).items.toString() + "\n";
                    }

                    if (subscribers.size() > 0) {
                        for (SubscriberContainer subscriberContainer : subscribers) {
                            if (subscriberContainer.subscriber != null) {
                                session.unsubscribe(subscriberContainer.subscriber);
                            }
                        }
                    }

                    if (publisher != null) {
                        session.unpublish(publisher);
                        publisher = null;
                    }

                    sendMessage(message);

                    session.disconnect();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Attention User!");
        alert.show();
    }

    @Override
    public void onBackPressed() {
        disconnectSession();
    }

    private void finishWithMessage(String message) {
        Log.e(TAG, message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        this.finish();
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