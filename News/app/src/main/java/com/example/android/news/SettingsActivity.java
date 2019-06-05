package com.example.android.news;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_settings);


    }
    public static class NewsPreferenceFragment extends PreferenceFragment
    implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference subject = findPreference(getString(R.string.settings_subject_key));
            bindPreference(subject);

            Preference date = findPreference(getString(R.string.settings_date_key));
            bindPreference(date);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringVal = newValue.toString();
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = ((ListPreference) preference).findIndexOfValue(stringVal);
                if (prefIndex>=0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }

            return true;
        }

        private void bindPreference(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String prefString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference,prefString);
        }
    }

}
