package com.hstat.tgb.config;

import com.hstat.tgb.mailKafka.DialogSender;
import com.hstat.tgb.dialog.AnswerMap;
import com.hstat.tgb.dialog.Dialog;
import com.hstat.tgb.models.DialogQuestions;
import com.hstat.tgb.outcomeProcessor.OutcomeProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Creating of  necessary beans
 */
@Slf4j
@Configuration
public class Beans {

    private final DialogQuestions questions;
    private final AnswerMap answerMap;
    private final OutcomeProcessor outcomeProcessor;
    private final DialogSender dialogSender;

    @Autowired
    public Beans(AnswerMap answerMap, OutcomeProcessor outcomeProcessor, DialogSender dialogSender) {
        this.dialogSender = dialogSender;
        this.questions = new DialogQuestions();
        this.answerMap = answerMap;
        this.outcomeProcessor = outcomeProcessor;
    }


// Creation of new dialog bean. Autowire all necessary services
    @Bean
    @Scope("prototype")
    public Dialog getDialog(long chatId) {
        log.info("New Dialog have been required");
        return new Dialog(chatId, questions, answerMap, dialogSender, outcomeProcessor);
    }

}
