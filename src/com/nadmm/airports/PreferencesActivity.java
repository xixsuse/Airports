/*
 * FlightIntel for Pilots
 *
 * Copyright 2011 Nadeem Hasan <nhasan@nadmm.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */

package com.nadmm.airports;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity 
            implements OnSharedPreferenceChangeListener {

    public static final String KEY_STARTUP_CHECK_EXPIRED_DATA = "startup_check_expired_data";
    public static final String KEY_LOCATION_USE_GPS = "location_use_gps";
    public static final String KEY_LOCATION_NEARBY_RADIUS = "location_nearby_radius";
    public static final String KEY_SHOW_EXTRA_RUNWAY_DATA = "extra_runway_data";
    public static final String KEY_SHOW_GPS_NOTAMS = "show_gps_notams";
    public static final String KEY_AUTO_DOWNLOAD_ON_3G = "auto_download_on_3G";
    public static final String KEY_DISCLAIMER_AGREED = "disclaimer_agreed";
    public static final String KEY_PHONE_TAP_ACTION = "phone_tap_action";
    public static final String KEY_WX_FAV_MIGRATED = "wx_fav_migrated";
    public static final String SHOW_LOCAL_TIME = "show_local_time";

    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource( R.xml.preferences );
        mSharedPrefs = getPreferenceScreen().getSharedPreferences();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Initialize the preference screen
        onSharedPreferenceChanged( mSharedPrefs, KEY_LOCATION_NEARBY_RADIUS );
        onSharedPreferenceChanged( mSharedPrefs, KEY_PHONE_TAP_ACTION );

        // Set up a listener whenever a key changes
        mSharedPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        mSharedPrefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged( SharedPreferences sharedPreferences, String key ) {
        Preference pref = findPreference( key );
       if ( key.equals( KEY_LOCATION_NEARBY_RADIUS ) ) {
            String radius = mSharedPrefs.getString( key, "20" );
            pref.setSummary( "Show airports within a radius of "+radius+ " NM" );
        } else if ( key.equals( KEY_PHONE_TAP_ACTION ) ) {
            String value = mSharedPrefs.getString( KEY_PHONE_TAP_ACTION, "dial" );
            if ( value.equals( "ignore" ) ) {
                pref.setSummary( "Do nothing" );
            } else if ( value.equals( "dial" ) ) {
                pref.setSummary( "Dial the number" );
            } else if ( value.equals( "call" ) ) {
                pref.setSummary( "Call the number" );
            }
        }
    }

}
