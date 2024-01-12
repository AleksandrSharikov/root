package com.hstat.tgb.messagesProcessing;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class CommandProcessor {

    private final DialogProcessor dialogProcessor;

    public CommandProcessor(DialogProcessor dialogProcessor) {
        this.dialogProcessor = dialogProcessor;
    }

    public void process(Update update){
        if (update.getMessage().getText().trim().equalsIgnoreCase("/start test")){
            dialogProcessor.process(update);
        }
    }
}
