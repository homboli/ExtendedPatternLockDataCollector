package me.gergelytusko.extendedpatternlockdatacollector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    private String finger;
    private String position;
    private List<Pair<String, String>> scenarios;
    private int scenarioIndex;
    private EditText userIdEditText;
    private EditText commentEditText;
    private TextView fingerInstructionTextView;
    private TextView positionInstructionTextView;
    static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        scenarioIndex = 0;
        scenarios = new ArrayList<>(Arrays.asList(new Pair<>("standing","thumb"), new Pair<>("standing","indexFinger"), new Pair<>("sitting","thumb"), new Pair<>("sitting","indexFinger")));
        position = scenarios.get(scenarioIndex).first;
        finger = scenarios.get(scenarioIndex).second;
        Button buttonOne = findViewById(R.id.start_button);
        this.userIdEditText = (EditText) findViewById(R.id.user_id_text);
        this.commentEditText = (EditText) findViewById(R.id.comment_text);
        this.fingerInstructionTextView = (TextView) findViewById(R.id.finger_instruction);
        this.positionInstructionTextView = (TextView) findViewById(R.id.position_instruction);
        fingerInstructionTextView.setText(getString(this.getResources().getIdentifier(finger, "string", this.getPackageName())));
        positionInstructionTextView.setText(getString(this.getResources().getIdentifier(position, "string", this.getPackageName())));
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userId = userIdEditText.getText().toString();
                String comment = commentEditText.getText().toString();
                if(TextUtils.isEmpty(userId)){
                    Snackbar snackbar = Snackbar
                            .make(findViewById(android.R.id.content), R.string.NoUserIdErrorMessage, Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    commentEditText.setEnabled(false);
                    userIdEditText.setEnabled(false);
                    Intent intent = new Intent(getBaseContext(), PatternLockSessionActivity.class);
                    intent.putExtra("USER_ID", userId);
                    intent.putExtra("COMMENT", comment);
                    intent.putExtra("FINGER", finger);
                    intent.putExtra("POSITION", position);
                    intent.putExtra("SCENARIO_INDEX", scenarioIndex);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                scenarioIndex = data.getIntExtra("SCENARIO_INDEX", -10) + 1;

               if(scenarioIndex == 4){
                   scenarioIndex = 0;
                   commentEditText.setEnabled(true);
                   userIdEditText.setEnabled(true);
                   commentEditText.setText("");
                   userIdEditText.setText("");
               }
                position = scenarios.get(scenarioIndex).first;
                finger = scenarios.get(scenarioIndex).second;
                fingerInstructionTextView.setText(getString(this.getResources().getIdentifier(finger, "string", this.getPackageName())));
                positionInstructionTextView.setText(getString(this.getResources().getIdentifier(position, "string", this.getPackageName())));
            }
        }
    }

}