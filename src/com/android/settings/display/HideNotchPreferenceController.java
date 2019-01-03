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

package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.preference.Preference;
import com.android.settings.DisplaySettings;
import androidx.preference.SwitchPreference;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.search.DatabaseIndexingUtils;
import com.android.settings.search.InlineSwitchPayload;
import com.android.settings.search.ResultPayload;
import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

public class HideNotchPreferenceController extends AbstractPreferenceController implements
        PreferenceControllerMixin {

    private static final String KEY_HIDE_NOTCH = "cutout_settings";

    public HideNotchPreferenceController(Context context) {
	super(context);
    }

    @Override
    public String getPreferenceKey() {
	return KEY_HIDE_NOTCH;
    }

   @Override
    public boolean isAvailable() {
	return !mContext.getResources().getString(
                com.android.internal.R.string.config_mainBuiltInDisplayCutout).isEmpty();
    }

    @Override
    public ResultPayload getResultPayload() {
        final Intent intent = DatabaseIndexingUtils.buildSearchResultPageIntent(mContext,
                DisplaySettings.class.getName(), KEY_HIDE_NOTCH,
                mContext.getString(R.string.display_settings));

        return new InlineSwitchPayload(KEY_HIDE_NOTCH,
                ResultPayload.SettingsSource.SYSTEM, 1, intent,
                isAvailable(), 1);
    }
}
