package com.hstat.tgb.models;

public class DialogResult {

    private final long chatId;
    private String med;
    private int feel;

    public DialogResult(long chatId){
        this.chatId = chatId;
    }

    public void setRes(int i, String res){
        switch (i) {
            case 0 -> this.med = res;
            case 1 -> this.feel = Integer.parseInt(res);
        }
    }

    @Override
    public String toString() {
        return "DialogResult{" +
                "chatId=" + chatId +
                ", med='" + med + '\'' +
                ", feel=" + feel +
                '}';
    }
}
