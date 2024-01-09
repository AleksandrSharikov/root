package com.hstat.tgb.messagesProcessing;

import com.hstat.tgb.dto.StatSend;
import com.hstat.tgb.models.DialogResult;

public class Dialog implements Runnable {
    private long chatId;
    private DialogResult result;

    public Dialog(long chatId) {
        this.chatId = chatId;
        this.result = new DialogResult();
    }

    @Override
    public void run() {
        boolean exit = false;
        while(!exit){
            if()
        }
    }
}
