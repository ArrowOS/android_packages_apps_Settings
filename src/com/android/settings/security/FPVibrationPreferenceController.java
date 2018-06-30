/*
 * Copyright (C) 2018 ArrowOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.search.DatabaseIndexingUtils;
import com.android.settings.search.InlineSwitchPayload;
import com.android.settings.search.ResultPayload;
import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import static android.provider.Settings.System.FINGERPRINT_SUCCESS_VIB;

public class FPVibrationPreferenceController extends AbstractPreferenceController implements
        PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String PREF_FINGERPRINT_VIBE = "fingerprint_success_vib";

    public FPVibrationPreferenceController(Context context) {
	super(context);
    }

    @Override
    public String getPreferenceKey() {
	return PREF_FINGERPRINT_VIBE;
    }

    @Override
    public void updateState(Preference preference) {
	int FPVibrationValue = Settings.System.getInt(mContext.getContentResolver(),
                FINGERPRINT_SUCCESS_VIB, 1);
	((SwitchPreference) preference).setChecked(FPVibrationValue != 0);
    }

   @Override
    public boolean isAvailable() {
	return mContext.getResources().getBoolean(
               com.android.internal.R.bool.config_hasFingerprintSensor);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean FPVibrationValue = (Boolean) newValue;
        Settings.System.putInt(mContext.getContentResolver(), FINGERPRINT_SUCCESS_VIB, FPVibrationValue ? 1 : 0);
        return true;
    }

    @Override
    public ResultPayload getResultPayload() {
        final Intent intent = DatabaseIndexingUtils.buildSearchResultPageIntent(mContext,
                LockscreenDashboardFragment.class.getName(), PREF_FINGERPRINT_VIBE,
                mContext.getString(R.string.lockscreen_settings_title));

        return new InlineSwitchPayload(FINGERPRINT_SUCCESS_VIB,
                ResultPayload.SettingsSource.SYSTEM, 1, intent,
                isAvailable(), 1);
    }
}
