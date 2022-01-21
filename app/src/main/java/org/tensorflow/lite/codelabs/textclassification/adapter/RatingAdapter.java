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
 package org.tensorflow.lite.codelabs.textclassification.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.codelabs.textclassification.R;
import org.tensorflow.lite.codelabs.textclassification.model.Rating;
import org.tensorflow.lite.codelabs.textclassification.util.NLPUtil;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static java.security.AccessController.getContext;

/**
 * RecyclerView adapter for a bunch of Ratings.
 */
public class RatingAdapter extends FirestoreAdapter<RatingAdapter.ViewHolder> {

    public RatingAdapter(Query query) {
        super(query);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rating, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DocumentSnapshot snap = getSnapshot(position);
        holder.bind(getSnapshot(position).toObject(Rating.class));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameView;
        MaterialRatingBar ratingBar;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.rating_item_name);
            ratingBar = itemView.findViewById(R.id.rating_item_rating);
            textView = itemView.findViewById(R.id.rating_item_text);
        }

        public void bind(Rating rating) {
            nameView.setText(rating.getUserName());
            textView.setText(rating.getText());
            Float scorePositive = rating.getPositiveRate();
            Float scoreNegative = rating.getNegativeRate();
            if(scorePositive != null && scoreNegative !=null){
                if(scoreNegative > scorePositive){
                    int color = Color.parseColor("#f44336");
                    ratingBar.setSupportProgressTintList(ColorStateList.valueOf(color));
                    float rating_stars = (5*rating.getNegativeRate())/1;
                    ratingBar.setRating(rating_stars);

                }else{
                    int color = Color.parseColor("#2196f3");
                    ratingBar.setSupportProgressTintList(ColorStateList.valueOf(color));
                    float rating_stars = (5*rating.getPositiveRate())/1;
                    ratingBar.setRating(rating_stars);
                }
            }


        }
    }

}
