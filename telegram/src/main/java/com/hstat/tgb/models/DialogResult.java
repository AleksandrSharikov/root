package com.hstat.tgb.models;

import java.util.Date;

/**
 * Set of answers date and id from dialog
 */
public class DialogResult {

    private final long chatId;
    private Date created;
    private String med;
    private int feel;

    public DialogResult(long chatId){
        this.chatId = chatId;
    }

    public void setRes(int i, String res){
        switch (i) {
            case -1 -> this.created = new Date();
            case 0 -> this.med = res;
            case 1 -> this.feel = Integer.parseInt(res);
        }
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
