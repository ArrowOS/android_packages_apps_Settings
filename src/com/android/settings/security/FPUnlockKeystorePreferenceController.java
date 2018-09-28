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
import android.support.v7.preference.Preference;
import com.android.settings.DisplaySettings;
import android.support.v14.preference.SwitchPreference;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.search.DatabaseIndexingUtils;
import com.android.settings.search.InlineSwitchPayload;
import com.android.settings.search.ResultPayload;
import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import android.hardware.fingerprint.FingerprintManager;

import static android.provider.Settings.System.FP_UNLOCK_KEYSTORE;

public class FPUnlockKeystorePreferenceController extends AbstractPreferenceController implements
        PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String PREF_FP_UNLOCK_KEYSTORE = "fp_unlock_keystore";

    private FingerprintManager mFingerprintManager;

    public FPUnlockKeystorePreferenceController(Context context) {
	super(context);
    }

    @Override
    public String getPreferenceKey() {
	return PREF_FP_UNLOCK_KEYSTORE;
    }

    @Override
    public void updateState(Preference preference) {
	int FPKeystoreValue = Settings.System.getInt(mContext.getContentResolver(),
                FP_UNLOCK_KEYSTORE, 1);
	((SwitchPreference) preference).setChecked(FPKeystoreValue != 0);
    }

   @Override
    public boolean isAvailable() {
	/*mFingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
	if (mFingerprintManager != null){
                return true;
            }*/
	return true;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean FPKeystoreValue = (Boolean) newValue;
        Settings.System.putInt(mContext.getContentResolver(), FP_UNLOCK_KEYSTORE, FPKeystoreValue ? 1 : 0);
        return true;
    }

    @Override
    public ResultPayload getResultPayload() {
        final Intent intent = DatabaseIndexingUtils.buildSearchResultPageIntent(mContext,
                LockscreenDashboardFragment.class.getName(), PREF_FP_UNLOCK_KEYSTORE,
                mContext.getString(R.string.lockscreen_settings_title));

        return new InlineSwitchPayload(FP_UNLOCK_KEYSTORE,
                ResultPayload.SettingsSource.SYSTEM, 1, intent,
                isAvailable(), 1);
    }
}
