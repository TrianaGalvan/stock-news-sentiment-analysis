/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package org.tensorflow.lite.codelabs.textclassification.model;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Model POJO for a rating.
 */
public class Rating {

    private String userId;
    private String userName;
    private String text;
    private Float positiveRate;
    private Float negativeRate;
    private @ServerTimestamp Date timestamp;

    public Rating() {}

    public Rating(FirebaseUser user,  String text, Float scorePositive, Float scoreNegative) {
        this.userId = user.getUid();
        this.userName = user.getDisplayName();
        if (TextUtils.isEmpty(this.userName)) {
            this.userName = user.getEmail();
        }
        this.text = text;
        this.positiveRate = scorePositive;
        this.negativeRate = scoreNegative;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Float getPositiveRate() {
        return positiveRate;
    }

    public Float getNegativeRate() {
        return negativeRate;
    }

    public void setNegativeRate(Float negativeRate) {
        this.negativeRate = negativeRate;
    }

    public void setPositiveRate(Float positiveRate) {
        this.positiveRate = positiveRate;
    }
}
