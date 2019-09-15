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
import android.provider.SearchIndexableResource;

import androidx.preference.Preference;
import com.android.settings.DisplaySettings;
import androidx.preference.SwitchPreference;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.search.SearchIndexable;

import java.util.Arrays;
import java.util.List;

@SearchIndexable
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

    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(
                        Context context, boolean enabled) {
                    final SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.cutout;
                    return Arrays.asList(sir);
                }
            };
}
