package com.hstat.tgb.dialogInterface;

import com.hstat.tgb.registration.RegMap;
import com.hstat.tgb.registration.RegProcessor;
import com.hstat.tgb.survey.AnswerMap;
import com.hstat.tgb.survey.SurveyProcessor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Combine classes with the same interfaces to lists
 */

@Service
public class ProcessorsList {
    private final AnswerMap answerMap;
    private final RegMap regMap;
    private final SurveyProcessor surveyProcessor;
    private final RegProcessor regProcessor;

    private final List<MessageMapHandler> allMaps;
    private final List<DialogProcessorInt> allProcessors;

    public ProcessorsList(AnswerMap answerMap, RegMap regMap, SurveyProcessor surveyProcessor, RegProcessor regProcessor) {
        this.answerMap = answerMap;
        this.regMap = regMap;
        this.surveyProcessor = surveyProcessor;
        this.regProcessor = regProcessor;
        this.allMaps = List.of(answerMap, regMap);
        this.allProcessors = List.of(surveyProcessor, regProcessor);
    }

    public List<MessageMapHandler> getAllMaps() {
        return allMaps;
    }

    public List<DialogProcessorInt> getAllProcessors() {
        return allProcessors;
    }
}
