package com.hstat.tgb.survey;


import com.hstat.dtoModels.StatSend;
import com.hstat.tgb.dialogInterface.ResultCollector;

import java.time.LocalDateTime;

/**
 * Set of answers date and id from dialog
 */
public class SurveyResult implements ResultCollector {

    private final long chatId;
    private LocalDateTime created;
    private String med;
    private int feel;

    public SurveyResult(long chatId){
        this.chatId = chatId;
    }

    public void setRes(int i, String res){
        switch (i) {
            case -1 -> this.created = LocalDateTime.now();
            case 0 -> this.med = res;
            case 1 -> this.feel = Integer.parseInt(res);
        }
    }

    public StatSend toDto(){
        return new StatSend(
                chatId,
                created,
                med,
                feel
        );
    }



    @Override
    public String toString() {
        return "DialogResult{" +
                "chatId=" + chatId +
                ", created=" + created +
                ", med='" + med + '\'' +
                ", feel=" + feel +
                '}';
    }
}
