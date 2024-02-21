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


    private final List<MessageMapHandler> allMaps;
    private final List<DialogProcessorInt> allProcessors;


    public ProcessorsList(List<MessageMapHandler> allMaps, List<DialogProcessorInt> allProcessors) {
        this.allMaps = allMaps;
        this.allProcessors = allProcessors;
    }

    public List<MessageMapHandler> getAllMaps() {
        return allMaps;
    }

    public List<DialogProcessorInt> getAllProcessors() {
        return allProcessors;
    }
}
