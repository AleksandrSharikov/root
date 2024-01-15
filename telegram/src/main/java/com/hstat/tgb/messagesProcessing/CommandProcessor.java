package com.hstat.tgb.messagesProcessing;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * works if update recognized as command
 */
@Service
public class CommandProcessor {

    private final DialogProcessor dialogProcessor;

    public CommandProcessor(DialogProcessor dialogProcessor) {
        this.dialogProcessor = dialogProcessor;
    }

    /**
     * perform acts required by different commands
     * @param update
     */
    public void process(Update update){
        if (update.getMessage().getText().trim().equalsIgnoreCase("/start_test")){
            dialogProcessor.process(update);
        }
    }
}
