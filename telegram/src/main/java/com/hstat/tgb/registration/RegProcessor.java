package com.hstat.tgb.registration;

import com.hstat.tgb.survey.Survey;
import com.hstat.tgb.dialogInterface.DialogProcessorInt;
import com.hstat.tgb.outcomeProcessor.OutcomeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RegProcessor implements DialogProcessorInt {

    private final RegMap regMap;
    private final OutcomeProcessor outcomeProcessor;
    private final ApplicationContext applicationContext;
    private final String regGrid =
            "You are not registered yet.\nPlease rake a few minutes for the registration.\n\n What is your name?";

    private final Map<Long, Survey> threadMap;

    public RegProcessor(RegMap regMap, OutcomeProcessor outcomeProcessor, ApplicationContext applicationContext) {
        this.regMap = regMap;
        this.outcomeProcessor = outcomeProcessor;
        this.applicationContext = applicationContext;
        this.threadMap = new HashMap<>();
    }

    @Lookup
    private RegDialog getRegDialog(long id){
        return applicationContext.getBean(RegDialog.class, id);
    }


    @Override
    public void process(Update update) {
        log.info(String.format("Update \"%s\" is in reg processor", update.getMessage().getText()));
        if (!isInProcess(update.getMessage().getChatId())){
            outcomeProcessor.sendMessage(update.getMessage().getChatId(), regGrid);

        }
    }

    @Override
    public boolean isInProcess(long id) {
        return threadMap.containsKey(id);
    }

    @Override
    public void stop(long id) {
        threadMap.remove(id);
    }
}
