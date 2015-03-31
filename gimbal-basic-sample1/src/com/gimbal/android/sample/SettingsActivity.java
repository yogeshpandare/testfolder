/**
 * Copyright (C) 2014 Gimbal, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of Gimbal, Inc.
 *
 * The following sample code illustrates various aspects of the Gimbal SDK.
 *
 * The sample code herein is provided for your convenience, and has not been
 * tested or designed to work on any particular system configuration. It is
 * provided AS IS and your use of this sample code, whether as provided or
 * with any modification, is at your own risk. Neither Gimbal, Inc.
 * nor any affiliate takes any liability nor responsibility with respect
 * to the sample code, and disclaims all warranties, express and
 * implied, including without limitation warranties on merchantability,
 * fitness for a specified purpose, and against infringement.
 */
package com.gimbal.android.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceManager;

// NOTE: Using deprecated apis to support api levels down to 8 and keep
// code relatively straight forward
@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference placeMonitoringPreference = findPreference(GimbalDAO.PLACE_MONITORING_PREFERENCE);
        placeMonitoringPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue == Boolean.TRUE) {
                    CommunicationManager.getInstance().startReceivingCommunications();
                    PlaceManager.getInstance().startMonitoring();
                }
                else {
                    PlaceManager.getInstance().stopMonitoring();
                    CommunicationManager.getInstance().stopReceivingCommunications();
                }
                return true;
            }
        });

        Preference resetAppInstanceIdPreference = findPreference("pref_reset_app_instance_id");
        resetAppInstanceIdPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Gimbal.resetApplicationInstanceIdentifier();
                Toast.makeText(SettingsActivity.this, "App Instance ID reset", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();

        // The value is actually managed by Gimbal - update the value that the UI will see
        Preference placeMonitoringPreference = findPreference(GimbalDAO.PLACE_MONITORING_PREFERENCE);
        if (placeMonitoringPreference instanceof CheckBoxPreference) {
            CheckBoxPreference pref = (CheckBoxPreference) placeMonitoringPreference;
            pref.setChecked(PlaceManager.getInstance().isMonitoring());
        }
        else {
            SwitchPreference pref = (SwitchPreference) placeMonitoringPreference;
            pref.setChecked(PlaceManager.getInstance().isMonitoring());
        }
    }
}
