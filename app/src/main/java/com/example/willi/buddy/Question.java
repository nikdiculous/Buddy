package com.example.willi.buddy;

public class Question {
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int answerNr;
    private String resource;
    private int quizIdentifier;

    public Question(){}

    public Question(String question, String option1, String option2, String option3, int answerNr, String resource, int identifier) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNr = answerNr;
        this.resource = resource;
        this.quizIdentifier = identifier;
    }

    public int getQuizIdentifier() {
        return quizIdentifier;
    }

    public void setQuizIdentifier(int quizIdentifier) {
        this.quizIdentifier = quizIdentifier;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }
}