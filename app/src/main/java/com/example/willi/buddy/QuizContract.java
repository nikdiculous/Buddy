package com.example.willi.buddy;

import android.provider.BaseColumns;

public final class QuizContract {
    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "questions";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_RESOURCE = "resource";
        public static final String COLUMN_IDENTIFIER = "quiz_identifier";
        public static final String COLUMN_TITLE = "quiz_title";

    }

    public static class UserTable{
        public static final String TABLE_UNAME = "user";
        public static final String TITLE_ID = "id";
        public static final String USER_TITLE = "title";
        public static final String USER_URL = "hyperlink";
    }
}
