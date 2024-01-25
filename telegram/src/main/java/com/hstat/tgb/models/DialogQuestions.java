package com.hstat.tgb.models;

import java.util.List;

/**
 * Set of questions for dialog
 */
public class DialogQuestions {
    private final List<String> questions;

    public DialogQuestions(List<String> questions) {
        this.questions = questions;
    }


    public String getQuestion(int i){
        if(i < 0){
            return "before beginning";
        }
        if(i >= questions.size()) {
            return "END";
        }
        return questions.get(i);
    }

    public int getQuantity() {
        return questions.size();
    }

}
