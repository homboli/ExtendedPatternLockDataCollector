package me.gergelytusko.extendedpatternlockdatacollector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PatternLockSessionActivity extends Activity implements LockPatternView.OnPatternListener {
    private LockPatternView mPatternView;
    private StorageManager storageManager;
    private TextView mProgressTextView;
    private int attemptCount;
    private String userId;
    private String comment;
    private String finger;
    private String position;
    private ImageView mImageView;
    private int TOTAL_ATTEMPT_COUNT;
    private PatternForDataCollection patternForDataCollection;
    private int scenarioIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        mPatternView = (LockPatternView)findViewById(R.id.pl_pattern);
        patternForDataCollection = new PatternForDataCollection();
        storageManager = new StorageManager();
        mPatternView.setOnPatternListener(this);
        mProgressTextView = (TextView) findViewById(R.id.progress);
        attemptCount = 1;
        Intent intent = getIntent();
        TOTAL_ATTEMPT_COUNT = 15;
        userId = intent.getStringExtra("USER_ID");
        comment = intent.getStringExtra("COMMENT");
        position = intent.getStringExtra("POSITION");
        finger = intent.getStringExtra("FINGER");
        scenarioIndex = intent.getIntExtra("SCENARIO_INDEX", -1);
        mPatternView.setUserId(userId);
        mPatternView.setComment(comment);
        mPatternView.setPosition(position);
        mPatternView.setFinger(finger);
        mPatternView.setAttemptCount(attemptCount);
        mProgressTextView.setText(String.format("%s/%s", Integer.toString(attemptCount), Integer.toString(TOTAL_ATTEMPT_COUNT)));
        mImageView = (ImageView) findViewById(R.id.patternToDrawImageView);
        mImageView.setImageResource(R.drawable.pattern1);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPatternStart() {}

    @Override
    public void onPatternCleared() {}

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {}

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        mPatternView.patternLockSession.setAttempt(attemptCount);
        mPatternView.patternLockSessionContinuous.setAttempt(attemptCount);
        if(pattern.equals(patternForDataCollection.getPattern())){ //Only submit pattern if it matches to the expected one
            storageManager.addPatternLockSession(mPatternView.patternLockSession);
            storageManager.addPatternLockSessionContinuous(mPatternView.patternLockSessionContinuous);
        }
        mPatternView.clearPattern();
        attemptCount += 1;
        if(attemptCount == TOTAL_ATTEMPT_COUNT + 1){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("SCENARIO_INDEX", scenarioIndex);
            //Log.e("asd","scnenarioindex in patterndetected:" + scenarioIndex);
            setResult(RESULT_OK, resultIntent);
            mPatternView.patternLockSensorManager.unregisterListeners();
            this.finish();
        }
        mPatternView.setAttemptCount(attemptCount);
        mProgressTextView.setText(Integer.toString(attemptCount) + "/" + Integer.toString(TOTAL_ATTEMPT_COUNT));
    }
}