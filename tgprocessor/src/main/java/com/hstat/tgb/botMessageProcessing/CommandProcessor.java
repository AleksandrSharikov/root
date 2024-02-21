package com.hstat.tgb.botMessageProcessing;

import com.hstat.common.dtoModels.TgMessage;
import com.hstat.tgb.dev.SelfTest;
import com.hstat.tgb.survey.SurveyProcessor;
import org.springframework.stereotype.Service;

/**
 * works if update recognized as command
 */
@Service
public class CommandProcessor {

    private final SurveyProcessor surveyProcessor;
    private final SelfTest selfTest;

    public CommandProcessor(SurveyProcessor surveyProcessor, SelfTest selfTest) {
        this.surveyProcessor = surveyProcessor;
        this.selfTest = selfTest;
    }

    /**
     * perform acts required by different commands
     * @param message
     */
    public void process(TgMessage message){
        if (message.message().trim().equalsIgnoreCase("/start_test")){
            surveyProcessor.process(message);
        }
        if (message.message().equals("/self_test")){
            selfTest.test(message.chatId());
        }
    }
}
