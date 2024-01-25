package com.hstat.tgb.config;

import com.hstat.tgb.mailKafka.DialogSender;
import com.hstat.tgb.survey.AnswerMap;
import com.hstat.tgb.survey.Survey;
import com.hstat.tgb.models.DialogQuestions;
import com.hstat.tgb.outcomeProcessor.OutcomeProcessor;
import com.hstat.tgb.questions.QuestionLists;
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

    private final AnswerMap answerMap;
    private final OutcomeProcessor outcomeProcessor;
    private final DialogSender dialogSender;
    private final QuestionLists questionLists;


    @Autowired
    public Beans(AnswerMap answerMap, OutcomeProcessor outcomeProcessor, DialogSender dialogSender, QuestionLists questionLists) {
        this.dialogSender = dialogSender;
        this.questionLists = questionLists;
        this.answerMap = answerMap;
        this.outcomeProcessor = outcomeProcessor;
    }


// Creation of new dialog bean. Autowire all necessary services
    @Bean
    @Scope("prototype")
    public Survey getDialog(long chatId) {
        log.info("New Dialog have been required");
        return new Survey(chatId, new DialogQuestions(questionLists.getDialog()), answerMap, dialogSender, outcomeProcessor);
    }

}
