package me.gergelytusko.extendedpatternlockdatacollector;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class StorageManager {
    private FirebaseFirestore db;
    public StorageManager(){
        db = FirebaseFirestore.getInstance();
    }

    public void addPatternLockSession(PatternLockSession patternLockSession) {
        db.collection("PatternLockSessions1").add(patternLockSession)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "PatternLockSession was successfully uploaded:" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding PatternLockSession", e);
                    }
                });
    }

    public void addPatternLockSessionContinuous(PatternLockSession patternLockSessionContinuous) {
        db.collection("PatternLockSessionsContinuous1").add(patternLockSessionContinuous)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "PatternLockSession was successfully uploaded:" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding PatternLockSession", e);
                    }
                });
    }
}
