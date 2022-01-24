package com.bmsce.clique_shopwithfriends;

import android.Manifest;
import android.content.ClipboardManager;
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
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomCodeScreen extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    static class SubscriberContainer {

        public ToggleButton toggleAudio;
        public Subscriber subscriber;
        public Uri uri;
        public ImageView displayImage;
        ArrayList<String> items;

        public SubscriberContainer(ToggleButton toggleAudio, ImageView imageView, Subscriber subscriber, Uri uri) {
            this.toggleAudio = toggleAudio;
            this.subscriber = subscriber;

            this.uri = uri;
            this.displayImage = imageView;
            this.items = new ArrayList<>();
        }
    }

    public static Uri uri;
    public static boolean isHost = false;
    public static String website = "https://www.amazon.in/";
    private static final String TAG = "tag->";
    private static final int PERMISSIONS_REQUEST_CODE = 124;
    private Session session;
    private RelativeLayout publisherViewContainer;
    private WebView webViewContainer;
    private final int MAX_NUM_SUBSCRIBERS = 4;
    private Publisher publisher;
    private List<SubscriberContainer> subscribers;
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

            if(isHost)
            {
                Log.d(TAG, "onConnected: hmm 1");
                ScreenSharingCapturer screenSharingCapturer = new ScreenSharingCapturer(RoomCodeScreen.this, webViewContainer);
                Log.d(TAG, "onConnected: hmm 2");
                publisher = new Publisher.Builder(RoomCodeScreen.this)
                       // .capturer(screenSharingCapturer)
                        .build();
                Log.d(TAG, "onConnected: hmm 3");

                publisher.setPublisherListener(publisherListener);
                Log.d(TAG, "onConnected: hmm 4");
                publisher.setPublisherVideoType(PublisherKit.PublisherKitVideoType.PublisherKitVideoTypeScreen);
                Log.d(TAG, "onConnected: hmm 5");
                publisher.setAudioFallbackEnabled(false);
                Log.d(TAG, "onConnected: hmm 6");
                /*
                webViewContainer.setWebViewClient(new WebViewClient());
                Log.d(TAG, "onConnected: hmm 7");
                WebSettings webSettings = webViewContainer.getSettings();
                Log.d(TAG, "onConnected: hmm 8");
                webSettings.setJavaScriptEnabled(true);
                Log.d(TAG, "onConnected: hmm 9");
                webViewContainer.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                Log.d(TAG, "onConnected: hmm 10");
                webViewContainer.loadUrl(website);
                Log.d(TAG, "onConnected: hmm 11");
                */

                publisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
                Log.d(TAG, "onConnected: hmm 12");
                publisherViewContainer.addView(publisher.getView());
                Log.d(TAG, "onConnected: hmm 13");
                session.publish(publisher);

            }
            else{
                publisher = new Publisher.Builder(RoomCodeScreen.this).build();
                publisher.setPublisherListener(publisherListener);
                publisher.setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);

                publisherViewContainer.addView(publisher.getView());

                session.publish(publisher);
            }
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

            Log.d(TAG, "onStreamReceived: hmm 15");
            //subscribes to the streams already present in the sessions which it connects to
            final Subscriber subscriber = new Subscriber.Builder(RoomCodeScreen.this, stream).build();
            session.subscribe(subscriber);
            addSubscriber(subscriber);
        }

        @Override
        public void onStreamDropped(Session session, Stream stream) {
            Log.d(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " + session.getSessionId());

            removeSubscriberWithStream(stream);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_code_screen);

        //goes to openTokConfig.java and checks if there is a sessionID and token provided or not
        if(!OpenTokConfig.isValid()) {
            finishWithMessage("Invalid OpenTokConfig. " + OpenTokConfig.getDescription());
            return;
        }

        publisherViewContainer = findViewById(R.id.publisherview);


        final ToggleButton toggleAudio = findViewById(R.id.toggleAudio);
        toggleAudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (publisher == null) {
                    return;
                }

                if (isChecked) {
                    publisher.setPublishAudio(true);
                } else {
                    publisher.setPublishAudio(false);
                }
            }
        });



        subscribers = new ArrayList<>();
        for (int i = 0; i < MAX_NUM_SUBSCRIBERS; i++) {
            int toggleAudioId = getResources().getIdentifier("toggleAudioSubscriber" + (new Integer(i)).toString(),
                    "id", this.getPackageName());
            int imageViewId = getResources().getIdentifier("imageView" + (new Integer(i)).toString(),
                    "id", this.getPackageName());
            //Glide.with(RoomCodeScreen.this).load(Objects.requireNonNull(subscribers.get(i).uri).toString())
              //    .into(subscribers.get(i).displayImage);
            subscribers.add(new SubscriberContainer(
                    findViewById(toggleAudioId),
                    findViewById(imageViewId),
                    null, uri));

        }

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
        container.toggleAudio.setVisibility(View.VISIBLE);
    }

    private void removeSubscriberWithStream(Stream stream) {
        SubscriberContainer container = findContainerForStream(stream);

        if (container == null) {
            return;
        }

;
    }

    private void disconnectSession() {
        if (session == null || !sessionConnected) {
            return;
        }

        sessionConnected = false;

        if (subscribers.size() > 0) {
            for (SubscriberContainer subscriberContainer : subscribers) {
                if (subscriberContainer.subscriber != null) {
                    session.unsubscribe(subscriberContainer.subscriber);
                }
            }
        }

        if (publisher != null) {
            publisherViewContainer.removeView(publisher.getView());
            session.unpublish(publisher);
            publisher = null;
        }

        session.disconnect();
    }

    private void finishWithMessage(String message) {
        Log.e(TAG, message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        this.finish();
    }
}