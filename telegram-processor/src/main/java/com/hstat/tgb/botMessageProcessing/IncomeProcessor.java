package com.hstat.tgb.botMessageProcessing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hstat.common.dtoModels.TgMessage;
import com.hstat.tgb.dialogInterface.DialogProcessorInt;
import com.hstat.tgb.dialogInterface.ProcessorsList;
import com.hstat.tgb.userService.ActiveUsersHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


/**
 * Processor for anny income messages
 */
@Slf4j
@Service
public class IncomeProcessor {
    private final String tgIncome = "tg.income";
    private final ObjectMapper objectMapper;
    private final CommandProcessor commandProcessor;
    private final ProcessorsList processorsList;
    private final ActiveUsersHandler activeUsersHandler;

    public IncomeProcessor(ObjectMapper objectMapper, CommandProcessor commandProcessor, ProcessorsList processorsList, ActiveUsersHandler activeUsersHandler) {
        this.objectMapper = objectMapper;
        this.processorsList = processorsList;
        this.commandProcessor = commandProcessor;
        this.activeUsersHandler = activeUsersHandler;
    }

    @KafkaListener(id = "Processor", topics = tgIncome)
    private void receive(String message) throws JsonProcessingException {
        TgMessage tgMessage = objectMapper.readValue(message, TgMessage.class);
        log.info(String.format("Received message %s from tg Id %d", tgMessage.message(), tgMessage.chatId()));
        process(tgMessage);
    }

    /**
     * process the message
     * @param message
     */
    private void process(TgMessage message){
        // look if user is active or stored in the db
        if(!activeUsersHandler.isActive(message.chatId())){
            if(activeUsersHandler.askUser(message.chatId())) {
                activeUsersHandler.add(message.chatId());
                sort(message);
            } else {
                processorsList.getAllProcessors().get(1).process(message);   // reg Processor
                activeUsersHandler.add(message.chatId());
            }
        } else {
            sort(message);
        }
    }

    private void sort(TgMessage message) {
        boolean found = false;
        if (message.message().startsWith("/")){ // is it a command
            commandProcessor.process(message);
            found = true;
        } else {
            for(DialogProcessorInt processor : processorsList.getAllProcessors()){
                if (processor.isInProcess(message.chatId())){
                    if(found){
                        log.warn("ID in more then one maps simultaneously");
                    } else {
                        processor.process(message);
                        found = true;
                    }
                }
            }
            }
        if(!found){
            log.info(String.format("Unsorted update from id = %d \n text = %s",
                    message.chatId(), message.message()));
        }
    }

}
