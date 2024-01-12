package com.hstat.tgb.Config;

import com.hstat.tgb.messagesProcessing.AnswerMap;
import com.hstat.tgb.messagesProcessing.Dialog;
import com.hstat.tgb.models.DialogQuestions;
import com.hstat.tgb.outcomeProcessor.OutcomeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Slf4j
@Configuration
public class Beans {

    private final DialogQuestions questions;
    private final AnswerMap answerMap;
    private final OutcomeProcessor outcomeProcessor;

    @Autowired
    public Beans(AnswerMap answerMap, OutcomeProcessor outcomeProcessor) {
        this.questions = new DialogQuestions();
        this.answerMap = answerMap;
        this.outcomeProcessor = outcomeProcessor;
    }



    @Bean
    @Scope("prototype")
    public Dialog getDialog(long chatId) {
        log.info("New Dialog have been required");
        return new Dialog(chatId, questions, answerMap, outcomeProcessor);
    }

}
