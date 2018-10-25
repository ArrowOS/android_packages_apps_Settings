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

package com.android.settings.deviceinfo.firmwareversion;

import android.os.Build;
import android.os.SystemProperties;
import android.support.annotation.VisibleForTesting;

import com.android.settings.R;

public class BuildDateDialogController {

    @VisibleForTesting
    static final int BUILD_DATE_VALUE_ID = R.id.build_date_value;
    private final static String ARROW_BUILD_DATE_PROP = "ro.build.date";

    private final FirmwareVersionDialogFragment mDialog;

    public BuildDateDialogController(FirmwareVersionDialogFragment dialog) {
        mDialog = dialog;
    }

    /**
     * Updates the build date to the dialog.
     */
    public void initialize() {
	String build_date_prop = SystemProperties.get(ARROW_BUILD_DATE_PROP);
	String build_date = SystemProperties.get(ARROW_BUILD_DATE_PROP).substring(0,10).trim();
	String build_year = SystemProperties.get(ARROW_BUILD_DATE_PROP).substring(build_date_prop.length() - 4).trim();
	if (!build_date.isEmpty())
           mDialog.setText(BUILD_DATE_VALUE_ID, build_date + ", " + build_year);
    }
}
