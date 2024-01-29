package com.hstat.tgb.config;

import com.hstat.common.dtoModels.UserCard;
import com.hstat.tgb.DialogPostProcessors.RegPostProcessor;
import com.hstat.tgb.mailKafka.DialogSender;
import com.hstat.tgb.questions.Dialog;
import com.hstat.tgb.registration.RegCollector;
import com.hstat.tgb.registration.RegMap;
import com.hstat.tgb.survey.AnswerMap;
import com.hstat.tgb.survey.Survey;
import com.hstat.tgb.models.DialogQuestions;
import com.hstat.tgb.sendToTg.TgSender;
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
    private final TgSender tgSender;
    private final DialogSender dialogSender;
    private final QuestionLists questionLists;
    private final RegMap regMap;
    private final RegPostProcessor regPostProcessor;
   // private final RegCollector regCollector;


    @Autowired
    public Beans(AnswerMap answerMap, TgSender tgSender, DialogSender dialogSender, QuestionLists questionLists, RegMap regMap, RegPostProcessor regPostProcessor){//, RegCollector regCollector) {
        this.dialogSender = dialogSender;
        this.questionLists = questionLists;
        this.answerMap = answerMap;
        this.tgSender = tgSender;
        this.regMap = regMap;
        this.regPostProcessor = regPostProcessor;
      //  this.regCollector = regCollector;
    }


// Creation of new dialog bean. Autowire all necessary services
    @Bean
    @Scope("prototype")
    public Survey getSurvey(long chatId) {
        log.info("New Dialog have been required");
        return new Survey(chatId, new DialogQuestions(questionLists.getDialog()), answerMap, dialogSender, tgSender);
    }

    @Bean
    @Scope("prototype")
    public Dialog<UserCard> getReg(long chatId){
        return new Dialog<UserCard>(chatId, new DialogQuestions(questionLists.getReg()), regMap, regPostProcessor, new RegCollector(chatId), tgSender);
    }

}
