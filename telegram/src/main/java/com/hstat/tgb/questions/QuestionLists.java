package com.hstat.tgb.questions;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionLists {
    private final List<String> dialog = List.of(
            "What meds did you have?",
            "How  did you feel"
    );
    private final List<String> reg = List.of(
           "When is your birthday? (DD.MM.YYYY)",
            "Enter some password"
    );

    public List<String> getDialog(){
        return new ArrayList<>(dialog);
    }

}
