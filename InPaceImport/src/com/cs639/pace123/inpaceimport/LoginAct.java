package com.cs639.pace123.inpaceimport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.cs639.pace123.inpaceimport.R;
import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

import java.util.Arrays;
import java.util.List;

public class LoginAct extends Activity {

    private LoginButton loginButton;
    private Button postingImageButton;
    private Button updatingStatus;
    private TextView userName;
    private UiLifecycleHelper uiHelper;
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private static String message = "Sample status posted from android app";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        userName = (TextView) findViewById(R.id.user_name);
        loginButton = (LoginButton) findViewById(R.id.authButton);
        loginButton.setUserInfoChangedCallback(new UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    userName.setText("Hello, " + user.getName());


                } else {
                    userName.setText("You are not logged in");
                }

            }


        });




/*
        postingImageButton = (Button) findViewById(R.id.post_image);
        postingImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                postImage();
            }
        });

        updatingStatus = (Button) findViewById(R.id.update_status);
        updatingStatus.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });*/

        // buttonsEnabled(false);
    }

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                //  buttonsEnabled(true);

                Log.d("Facebook Login", "Facebook session opened");
                if (Session.getActiveSession() != null || Session.getActiveSession().isOpened()){
                    Intent i = new Intent(LoginAct.this,GameOptions.class);
                    startActivity(i);
                }

            } else if (state.isClosed()) {
                // buttonsEnabled(false);
                Log.d("Facebook Login", "Facebook session closed");
            }
        }
    };

/*    public void buttonsEnabled(boolean isEnabled) {
        postingImageButton.setEnabled(isEnabled);
        updatingStatus.setEnabled(isEnabled);
    }

    public void postImage() {
        if (checkPermissions()) {
            Bitmap img = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            Request uploadRequest = Request.newUploadPhotoRequest(
                    Session.getActiveSession(), img, new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            Toast.makeText(LoginAct.this,
                                    "Photo uploaded successfully",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            uploadRequest.executeAsync();
        } else {
            requestPermissions();
        }
    }

    public void postStatusMessage() {
        if (checkPermissions()) {
            Request request = Request.newStatusUpdateRequest(
                    Session.getActiveSession(), message,
                    new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            if (response.getError() == null)
                                Toast.makeText(LoginAct.this,
                                        "Status updated successfully",
                                        Toast.LENGTH_LONG).show();
                        }
                    });
            request.executeAsync();
        } else {
            requestPermissions();
        }
    }*/

    public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
            return s.getPermissions().contains("publish_actions");
        } else
            return false;
    }

    public void requestPermissions() {
        Session s = Session.getActiveSession();
        if (s != null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                    this, PERMISSIONS));
    }



    @Override
    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        uiHelper.onResume();
        //  buttonsEnabled(Session.getActiveSession().isOpened());

    }

    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }

}