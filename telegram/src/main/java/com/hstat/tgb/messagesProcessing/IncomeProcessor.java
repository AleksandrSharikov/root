package com.hstat.tgb.messagesProcessing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class IncomeProcessor {
    private final DialogProcessor dialogProcessor;
    private final CommandProcessor commandProcessor;

    public IncomeProcessor(DialogProcessor dialogProcessor, CommandProcessor commandProcessor) {
        this.dialogProcessor = dialogProcessor;
        this.commandProcessor = commandProcessor;
    }

    public void process(Update update){
        if (update.getMessage().getText().startsWith("/")){
            commandProcessor.process(update);
        } else if(dialogProcessor.getThreadMap().containsKey(update.getMessage().getChatId())) {
            dialogProcessor.process(update);
        } else {
            log.info("Unsorted update: " + update.getMessage().getText());
        }
    }

}
