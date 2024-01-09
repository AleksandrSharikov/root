package com.hstat.tgb.messagesProcessing;

import com.hstat.tgb.models.DialogResult;

public class Dialog implements Runnable {
    private long chatId;
    private IncomeProcessor incomeProcessor;
    private DialogResult result;

    public Dialog(long chatId, IncomeProcessor incomeProcessor) {
        this.chatId = chatId;
        this.incomeProcessor = incomeProcessor;
        this.result = new DialogResult();
    }

    @Override
    public void run() {
        boolean exit = false;
        while(!exit){
            incomeProcessor.getList(chatId);
        }
    }
}
