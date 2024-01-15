package com.hstat.tgb.messagesProcessing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


/**
 * Processor for anny income messages
 */
@Slf4j
@Service
public class IncomeProcessor {
    private final DialogProcessor dialogProcessor;
    private final CommandProcessor commandProcessor;

    public IncomeProcessor(DialogProcessor dialogProcessor, CommandProcessor commandProcessor) {
        this.dialogProcessor = dialogProcessor;
        this.commandProcessor = commandProcessor;
    }

    /**
     * process the message
     * @param update
     */
    public void process(Update update){
        if (update.getMessage().getText().startsWith("/")){ // is it a command
            commandProcessor.process(update);
        } else if(dialogProcessor.isInThreadMap(update.getMessage().getChatId())) {  // is there a dialog opened
            dialogProcessor.process(update);
        } else {
            log.info("Unsorted update: " + update.getMessage().getText());
        }
    }

}
