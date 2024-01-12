package com.hstat.tgb.models;

import java.util.List;

public class DialogQuestions {
    private final List<String> questions = List.of(
            "What meds did you have?",
            "How  did you feel"
    );

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
