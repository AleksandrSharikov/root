package com.hstat.tgb.messagesProcessing;

import com.hstat.tgb.models.DialogQuestions;
import com.hstat.tgb.models.DialogResult;
import com.hstat.tgb.outcomeProcessor.OutcomeProcessor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Dialog implements Runnable {
    private final long chatId;
    private final DialogQuestions questions;
    private final AnswerMap answerMap;
    private final DialogResult result;
    private int questionNumber;
    private final int qq;
    private final OutcomeProcessor outcomeProcessor;
    volatile boolean exit = false;


    public Dialog(long chatId, DialogQuestions questions, AnswerMap answerMap, OutcomeProcessor outcomeProcessor) {
        this.chatId = chatId;
        this.questions = questions;
        this.answerMap = answerMap;
        this.result = new DialogResult(chatId);
        this.questionNumber = -1;
        this.qq = questions.getQuantity();
        this.outcomeProcessor = outcomeProcessor;
    }

    @Override
    public void run() {

        log.info("Dialog for id:" + chatId + " started");
        while(!exit){
            try {
            log.info("Question number: " + questionNumber);
            while(!answerMap.getList(chatId).isEmpty()){
                result.setRes(questionNumber++,answerMap.getList(chatId).poll());
                if(questionNumber < qq) {
                    outcomeProcessor.sendMessage(chatId, questions.getQuestion(questionNumber));
                } else {
                    outcomeProcessor.sendMessage(chatId, "Thank you, that's all");
                    answerMap.closeId(chatId);
                    log.info("Prepared result: " + result);
                    exit = true;
                    break;
                }
            }
            if(!exit){
                synchronized (this){
                    wait(5_000);
                }
            }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                answerMap.closeId(chatId);
                outcomeProcessor.sendMessage(chatId, "Entrance error. Data reset");
            }
        }
        log.info("Dialog for id:" + chatId + " finished");
    }


}
