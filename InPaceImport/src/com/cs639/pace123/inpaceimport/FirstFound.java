package com.cs639.pace123.inpaceimport;

//import com.cs639.pace.inpace.R;
import com.cs639.pace123.inpaceimport.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.CommunicationManager;
import com.gimbal.android.CommunicationListener;
import com.gimbal.android.Communication;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.Visit;
import com.gimbal.android.Gimbal;
import com.gimbal.android.Push;

import android.util.Log;
/**
 * Created by meghanjordan on 12/11/14.
 */
public class FirstFound extends Activity {

	//proximity code
	private PlaceEventListener placeEventListener;
	private CommunicationListener communicationListener;
	private BeaconEventListener beaconSightingListener;
    private BeaconManager beaconManager;
	//proximity code
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstfound);
        
        //try new proximity code below 

Gimbal.setApiKey(this.getApplication(), "a5415326-d587-44e6-81ce-4102e3a666eb");
Gimbal.registerForPush("391267132077");
        
        placeEventListener = new PlaceEventListener() {
            @Override
            public void onVisitStart(Visit visit) {
                // This will be invoked when a place is entered. Example below shows a simple log upon enter
                Log.i("Info:", "Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()));
            }

            @Override
            public void onVisitEnd(Visit visit) {
                // This will be invoked when a place is exited. Example below shows a simple log upon exit
                Log.i("Info:", "Exit: " + visit.getPlace().getName() + ", at: " + new Date(visit.getDepartureTimeInMillis()));
            }
        };
        PlaceManager.getInstance().addListener(placeEventListener);
        
        
        communicationListener = new CommunicationListener() {
            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Visit visit) {
                 for (Communication comm : communications) {
                    Log.i("INFO", "Place Communication: " + visit.getPlace().getName() + ", message: " + comm.getTitle());
                }
                //allow Gimbal to show the notification for all communications
                return communications;
            }
            
            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Push push) {
                 for (Communication comm : communications) {
                    Log.i("INFO", "Received a Push Communication with message: " + comm.getTitle());
                }
                //allow Gimbal to show the notification for all communications
                return communications;
            }

            @Override
            public void onNotificationClicked(List communications) {
                Log.i("INFO", "Notification was clicked on");
            }
        };
        CommunicationManager.getInstance().addListener(communicationListener);      
        
        beaconSightingListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {
                Log.i("INFO", sighting.toString());
            }
        };
        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconSightingListener);
        
        PlaceManager.getInstance().startMonitoring();
        beaconManager.startListening();
                                          
        
        Button b = (Button) findViewById(R.id.hiButton);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Send email
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                       "mailto", "chandrashekar.yv@gmail.com", null));
                //Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                       // "mailto", "pooja11mahesh@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "InPace: I am trying to email you through our InPace code!");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "iT Works !! :)");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

    }
}