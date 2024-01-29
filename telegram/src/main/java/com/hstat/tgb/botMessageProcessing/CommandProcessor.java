package com.hstat.tgb.botMessageProcessing;

import com.hstat.tgb.survey.SurveyProcessor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * works if update recognized as command
 */
@Service
public class CommandProcessor {

    private final SurveyProcessor surveyProcessor;

    public CommandProcessor(SurveyProcessor surveyProcessor) {
        this.surveyProcessor = surveyProcessor;
    }

    /**
     * perform acts required by different commands
     * @param update
     */
    public void process(Update update){
        if (update.getMessage().getText().trim().equalsIgnoreCase("/start_test")){
            surveyProcessor.process(update);
        }
    }
}
