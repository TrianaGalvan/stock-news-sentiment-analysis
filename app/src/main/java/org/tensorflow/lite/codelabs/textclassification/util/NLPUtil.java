package org.tensorflow.lite.codelabs.textclassification.util;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;

import org.tensorflow.lite.codelabs.textclassification.MainActivity;
import org.tensorflow.lite.codelabs.textclassification.MainClassifier;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NLPUtil {
    private static final String TAG = "MainActivity";

    // NLP
    private static ExecutorService executorService;
    // NLClassifie variable
    private static NLClassifier textClassifier;
    public static final String TAG_POSITIVE = "1";
    public static final String TAG_NEGATIVE = "0";

    /** Download model from Firebase ML. */
    public synchronized void downloadModel() {
        executorService = Executors.newSingleThreadExecutor();

        final FirebaseCustomRemoteModel remoteModel =
                new FirebaseCustomRemoteModel
                        .Builder("sentiment_analysis")
                        .build();

        FirebaseModelDownloadConditions conditions =
                new FirebaseModelDownloadConditions.Builder()
                        .requireWifi()
                        .build();

        final FirebaseModelManager firebaseModelManager = FirebaseModelManager.getInstance();

        firebaseModelManager
                .download(remoteModel, conditions)
                .continueWithTask(task ->
                        firebaseModelManager.getLatestModelFile(remoteModel)
                )
                .continueWith(executorService, (Continuation<File, Void>) task -> {
                    // Initialize a text classifier instance with the model
                    File modelFile = task.getResult();

                    textClassifier = NLClassifier.createFromFile(modelFile);

                    return null;
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to download and initialize the model. ", e);
                });

        firebaseModelManager.isModelDownloaded(remoteModel);
    }


    /** Send input text to TextClassificationClient and get the classify messages. */
    public static Hashtable<String, Float> classify(final String text) {
        // creating a My HashTable Dictionary
        Hashtable<String, Float> resultsClassifier = new Hashtable<String, Float>();
        List<Category> results = textClassifier.classify(text);

        for (int i = 0; i < results.size(); i++) {
            Category result = results.get(i);
            if(result.getLabel().equals(TAG_POSITIVE)){
                resultsClassifier.put(TAG_POSITIVE, result.getScore());
            }else{
                resultsClassifier.put(TAG_NEGATIVE, result.getScore());
            }
        }

        //awaitTerminationAfterShutdown(executorService);
        return resultsClassifier;
    }

    public static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }




}
