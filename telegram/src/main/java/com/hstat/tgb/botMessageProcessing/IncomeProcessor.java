package com.hstat.tgb.botMessageProcessing;

import com.hstat.tgb.dialogInterface.DialogProcessorInt;
import com.hstat.tgb.dialogInterface.ProcessorsList;
import com.hstat.tgb.userService.ActiveUsersHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


/**
 * Processor for anny income messages
 */
@Slf4j
@Service
public class IncomeProcessor {
    private final CommandProcessor commandProcessor;
    private final ProcessorsList processorsList;
    private final ActiveUsersHandler activeUsersHandler;

    public IncomeProcessor(CommandProcessor commandProcessor, ProcessorsList processorsList, ActiveUsersHandler activeUsersHandler) {
        this.processorsList = processorsList;
        this.commandProcessor = commandProcessor;
        this.activeUsersHandler = activeUsersHandler;
    }

    /**
     * process the message
     * @param update
     */
    public void process(Update update){
        // look if user is active or stored in the db
        if(!activeUsersHandler.isActive(update.getMessage().getChatId())){
            if(activeUsersHandler.askUser(update.getMessage().getChatId())) {
                activeUsersHandler.add(update.getMessage().getChatId());
                sort(update);
            } else {
                processorsList.getAllProcessors().get(1).process(update);   // reg Processor
                activeUsersHandler.add(update.getMessage().getChatId());
            }
        } else {
            sort(update);
        }
    }

    private void sort(Update update) {
        boolean found = false;
        if (update.getMessage().getText().startsWith("/")){ // is it a command
            commandProcessor.process(update);
            found = true;
        } else {
            for(DialogProcessorInt processor : processorsList.getAllProcessors()){
                if (processor.isInProcess(update.getMessage().getChatId())){
                    if(found){
                        log.warn("ID in more then one maps simultaneously");
                    } else {
                        processor.process(update);
                        found = true;
                    }
                }
            }
            }
        if(!found){
            log.info(String.format("Unsorted update from id = %d \n text = %s",
                    update.getMessage().getChatId(), update.getMessage().getText()));
        }
    }

}
