package com.hstat.tgb.dialogInterface;

import com.hstat.tgb.registration.RegMap;
import com.hstat.tgb.registration.RegProcessor;
import com.hstat.tgb.dialog.AnswerMap;
import com.hstat.tgb.dialog.DialogProcessor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Combine classes with the same interfaces to lists
 */

@Service
public class ProcessorsList {
    private final AnswerMap answerMap;
    private final RegMap regMap;
    private final DialogProcessor dialogProcessor;
    private final RegProcessor regProcessor;

    private final List<ActiveMapHandler> allMaps;
    private final List<DialogProcessorInt> allProcessors;

    public ProcessorsList(AnswerMap answerMap, RegMap regMap, DialogProcessor dialogProcessor, RegProcessor regProcessor) {
        this.answerMap = answerMap;
        this.regMap = regMap;
        this.dialogProcessor = dialogProcessor;
        this.regProcessor = regProcessor;
        this.allMaps = List.of(answerMap, regMap);
        this.allProcessors = List.of(dialogProcessor, regProcessor);
    }

    public List<ActiveMapHandler> getAllMaps() {
        return allMaps;
    }

    public List<DialogProcessorInt> getAllProcessors() {
        return allProcessors;
    }
}
