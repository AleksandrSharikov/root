package com.hstat.tgb.survey;

import com.hstat.common.dtoModels.TgMessage;
import com.hstat.tgb.dialogInterface.DialogProcessorInt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;


/**
 * The class to start new threads for Dialogs and maintain them
 */
@Slf4j
@Service
public class SurveyProcessor implements DialogProcessorInt {

    private final AnswerMap answerMap;
    private final ApplicationContext applicationContext;

    //  map with runnables for the threads
    private final Map<Long, Survey> threadMap = new HashMap<>();

    //Creating a bean of Dialog
    @Lookup
    private Survey getSurvey(long id){
        return applicationContext.getBean(Survey.class, id);
    }

    @Autowired
    public SurveyProcessor(AnswerMap answerMap, ApplicationContext applicationContext) {
        this.answerMap = answerMap;
        this.applicationContext = applicationContext;
    }

    /**
     * If chatId is new, create a new thread for it, else notify existing one, if it is last answer, remove
     * the thread  from the map
     * @param message
     */
    @Override
    public void process(TgMessage message) {
        log.info("Update is in the processor");
        if (answerMap.mergeUpdate(message)) {
            log.info("New thread shall start");
            Survey survey = getSurvey(message.chatId());
            Thread thread = new Thread(survey,
                    String.valueOf(message.chatId()));
            threadMap.put(message.chatId(), survey);
            thread.start();
        } else {
            if(threadMap.get(message.chatId()).notifyThread()){
                threadMap.remove(message.chatId());
            }
        }
    }


    // Check if there is thread with certain id im the map
    @Override
    public boolean isInProcess(long id){
        return threadMap.containsKey(id);
    }

    @Override
    public void stop(long id) {
        threadMap.remove(id);
    }

}
