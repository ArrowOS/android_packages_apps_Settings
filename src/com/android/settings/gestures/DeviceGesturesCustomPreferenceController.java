/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.android.settings.gestures;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settings.overlay.FeatureFactory;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import androidx.preference.Preference;

import static com.android.internal.logging.nano.MetricsProto.MetricsEvent.SETTINGS_GESTURES;

import java.util.List;

public class DeviceGesturesCustomPreferenceController extends AbstractPreferenceController implements PreferenceControllerMixin {

    static final String KEY_GESTURES_CUSTOM = "custom_device_gestures_preference";

    private final MetricsFeatureProvider mMetricsFeatureProvider;

    public DeviceGesturesCustomPreferenceController(Context context) {
        super(context);
        mMetricsFeatureProvider = FeatureFactory.getFactory(context).getMetricsFeatureProvider();
    }

    @Override
    public boolean isAvailable() {
        return !mContext.getResources().getString(R.string.config_customGesturesPackage).equals("");
    }

    @Override
    public String getPreferenceKey() {
        return KEY_GESTURES_CUSTOM;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (KEY_GESTURES_CUSTOM.equals(preference.getKey())) {
            mMetricsFeatureProvider.action(mContext, SETTINGS_GESTURES);
            try {
                String[] customGesturesPackage = mContext.getResources().getString(R.string.config_customGesturesPackage).split("/");
                String activityName = customGesturesPackage[0];
                String className = customGesturesPackage[1];
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(activityName, className));
                mContext.startActivity(intent);
            } catch (Exception e){
            }
        }
        return false;
    }

    @Override
    public void updateNonIndexableKeys(List<String> keys) {
        keys.add(getPreferenceKey());
    }

}
