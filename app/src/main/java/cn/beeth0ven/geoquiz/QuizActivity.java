package cn.beeth0ven.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mTextView;

    private int mQuestionIndex = 0;

    private Question[] mQuestions = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private boolean[] mIsCheaters = new boolean[] {
            false,
            false,
            false,
            false
    };

    private boolean getCurrentIsCheater() {
        return mIsCheaters[mQuestionIndex];
    }

    private void setCurrentIsCheater(boolean isCheater) {
        mIsCheaters[mQuestionIndex] = isCheater;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuestionIndex();
                updateQuestion();
            }
        });

        mTextView = (TextView) findViewById(R.id.question_text_view);

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show cheat
                boolean answerIsTrue = mQuestions[mQuestionIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        if (savedInstanceState != null) {
            mQuestionIndex = savedInstanceState.getInt("mQuestionIndex", 0);
            mIsCheaters = savedInstanceState.getBooleanArray("mIsCheaters");
        }

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) { return; }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) { return; }
            setCurrentIsCheater(CheatActivity.wasAnswerShown(data));
        }
    }

    private void increaseQuestionIndex() {
        mQuestionIndex = (mQuestionIndex + 1) % mQuestions.length;
    }

    private void updateQuestion() {
        mTextView.setText(mQuestions[mQuestionIndex].getTextResId());
    }

    private void checkAnswer(boolean isTrue) {

        int stringId = getCurrentIsCheater() ?
                R.string.judgment_toast :
                mQuestions[mQuestionIndex].isAnswerTrue() == isTrue ?
                        R.string.correct_toast :
                        R.string.incorrect_toast;

        Toast.makeText(
                QuizActivity.this,
                stringId,
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt("mQuestionIndex", mQuestionIndex);
        outState.putBooleanArray("mIsCheaters", mIsCheaters);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
