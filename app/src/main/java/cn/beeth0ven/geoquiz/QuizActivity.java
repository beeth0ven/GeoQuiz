package cn.beeth0ven.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mTextView;

    private int mQuestionIndex = 0;

    private Question[] mQuestions = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        mTextView = (TextView) findViewById(R.id.question_text_view);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousQuestion();
            }
        });

        updateQuestion();
    }

    private void increaseQuestionIndex() {
        mQuestionIndex = (mQuestionIndex + 1) % mQuestions.length;
    }


    private void decreaseQuestionIndex() {
        mQuestionIndex = (mQuestionIndex - 1) % mQuestions.length;
        if (mQuestionIndex < 0) {
            mQuestionIndex += mQuestions.length;
        }
    }

    private void updateQuestion() {
        mTextView.setText(mQuestions[mQuestionIndex].getTextResId());
    }

    private void nextQuestion() {
        increaseQuestionIndex();
        updateQuestion();
    }

    private void previousQuestion() {
        decreaseQuestionIndex();
        updateQuestion();
    }

    private void checkAnswer(boolean isTrue) {
        int stringId = mQuestions[mQuestionIndex].isAnswerTrue() == isTrue ? R.string.correct_toast : R.string.incorrect_toast;
        Toast.makeText(
                QuizActivity.this,
                stringId,
                Toast.LENGTH_SHORT
        ).show();
    }

}
