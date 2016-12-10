package com.shop.oasaustre.shoppinglist.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.shop.oasaustre.shoppinglist.R;

/**
 * Created by oasaustre on 10/12/16.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
