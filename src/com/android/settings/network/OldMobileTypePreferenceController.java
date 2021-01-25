/*
 * Copyright (C) 2018 The LineageOS Project
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

package com.android.settings.network;

import android.content.Context;
import android.os.UserManager;

import static android.os.UserHandle.myUserId;
import static android.os.UserManager.DISALLOW_CONFIG_MOBILE_NETWORKS;

import static com.android.settingslib.RestrictedLockUtilsInternal.hasBaseUserRestriction;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;

public class OldMobileTypePreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {

    private static final String KEY_USE_OLD_MOBILETYPE = "use_old_mobiletype";

    private final UserManager mUserManager;
    private final boolean mIsSecondaryUser;

    public OldMobileTypePreferenceController(Context context) {
        super(context);
        mUserManager = (UserManager) context.getSystemService(Context.USER_SERVICE);
        mIsSecondaryUser = !mUserManager.isAdminUser();
    }

    @Override
    public String getPreferenceKey() {
        return KEY_USE_OLD_MOBILETYPE;
    }

    @Override
    public boolean isAvailable() {
        final boolean isPrefAllowedOnDevice = mContext.getResources().getBoolean(
                com.android.settings.R.bool.config_show_mobile_plan);
        final boolean isPrefAllowedForUser = !mIsSecondaryUser
                && !Utils.isWifiOnly(mContext)
                && !hasBaseUserRestriction(mContext, DISALLOW_CONFIG_MOBILE_NETWORKS, myUserId());
        return isPrefAllowedForUser && isPrefAllowedOnDevice;
    }
}
