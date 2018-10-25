package com.example.willi.buddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;

public class quiz_start extends AppCompatActivity {

    private TextView ques;
    private Button OptA, OptB, OptC, OptD;
    private ImageButton btn_end;
    private List<Question> questionList;
    private int questionCounter, questioncountTotal;
    private Question currentQuestion;
    private ProgressBar mProgressbar = null;
    QuizDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);

        ques = findViewById(R.id.TxtQuestions);
        OptA = findViewById(R.id.btnOptA);
        OptB = findViewById(R.id.btnOptB);
        OptC = findViewById(R.id.btnOptC);
        OptD = findViewById(R.id.btnOptD);
        btn_end = findViewById(R.id.btn_quizend);
        mProgressbar = findViewById(R.id.progressBarCounter);

        //getting Intent from Quiz_Home
        Intent intent = getIntent();
        final int questcat = intent.getIntExtra("quizCat", 0);

        //Creating DBhelper object and get questions based on quizCat and shuffle questions
        dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions(questcat);
        questioncountTotal = questionList.size();
        Collections.shuffle(questionList);

        //getting progress bar for quiz counter
        mProgressbar.setMax(questioncountTotal);
        mProgressbar.setProgress(0);

        //display question
        showNextQuestion();

        //button onclick listener and call check answer
        OptA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 1;
                checkanswer(answer);

            }
        });

        OptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 2;
                checkanswer(answer);

            }
        });

        OptC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 3;
                checkanswer(answer);

            }
        });

        OptD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int answer = 4;
                checkanswer(answer);

            }
        });
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showNextQuestion() {
        if (questionCounter < questioncountTotal) {

            currentQuestion = questionList.get(questionCounter);

            ques.setText(currentQuestion.getQuestion());
            OptA.setText(currentQuestion.getOption1());
            OptB.setText(currentQuestion.getOption2());
            OptC.setText(currentQuestion.getOption3());
            OptD.setText(currentQuestion.getOption4());

            mProgressbar.setProgress(questionCounter);
        }
    }

    private void checkanswer(int answer) {

        questionCounter++;
        //display Toast msg if answer is correct
        if(answer == currentQuestion.getAnswerNr())
        {
            Toast toast = Toast.makeText(getApplicationContext(),"CORRECT!",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();

            //End quiz if all question is being answered
            if(questionCounter == questioncountTotal){
                endquiz();
            }
            else{
                showNextQuestion();
            }
        }
        else //display dialog box and insert link into TO-DO table
        {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
            mBuilder.setMessage("Your answer is wrong, please check your TO-DO list to learn more").setCancelable(false).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    if(questionCounter == questioncountTotal){
                        endquiz();
                    }
                    else{
                        showNextQuestion();
                    }
                }
            });
            AlertDialog alert = mBuilder.create();
            alert.show();
            //check for existing row, if false, insert new row into To-DO table
            if(dbHelper.checkRowexist(currentQuestion.getTitle()) == false)
            {
                dbHelper.addNewURL(currentQuestion.getTitle(),currentQuestion.getResource());
            }

        }
    }

    private void endquiz() {
        mProgressbar.setProgress(questioncountTotal);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setMessage("End of Quiz, your TO-DO list has been updated.").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(quiz_start.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog alert = mBuilder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setMessage("Are you sure you want to end the quiz?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(quiz_start.this, MainActivity.class));
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = mBuilder.create();
        alert.show();

    }
}
