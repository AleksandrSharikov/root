package com.hstat.tgb.survey;

import com.hstat.tgb.mailKafka.DialogSender;
import com.hstat.tgb.models.DialogQuestions;
import com.hstat.tgb.sendToTg.TgSender;
import lombok.extern.slf4j.Slf4j;

/**
 *  The class of runnable to create the thread for dialog
 */
@Slf4j
public class Survey implements Runnable {

    private final Object lock;      // object for sync
    private final long chatId;
    private final DialogQuestions questions;
    private final AnswerMap answerMap;      // Map of answers like queue
    private final SurveyResult result;
    private final DialogSender dialogSender;
    private int questionNumber;
    private final int qq;
    private final TgSender tgSender;
    volatile boolean exit = false;


    public Survey(long chatId,
                  DialogQuestions questions,
                  AnswerMap answerMap,
                  DialogSender dialogSender,
                  TgSender tgSender)
    {
        this.chatId = chatId;
        this.questions = questions;
        this.answerMap = answerMap;
        this.result = new SurveyResult(chatId);
        this.dialogSender = dialogSender;
        this.questionNumber = -1;
        this.qq = questions.getQuantity();
        this.tgSender = tgSender;
        this.lock = new Object();
    }

    @Override
    public void run() {

        log.info("Dialog for id:" + chatId + " started");
        while(!exit){
            try {
            log.info("Question number: " + questionNumber);
        // process all records inn queue. Probably redundant. Can be changed to if()
            while(!answerMap.getList(chatId).isEmpty()){
                result.setRes(questionNumber++,answerMap.getList(chatId).poll());
                if(questionNumber < qq) {
                    tgSender.sendMessage(chatId, questions.getQuestion(questionNumber));
                } else {
                    tgSender.sendMessage(chatId, "Thank you, that's all");
                    answerMap.closeId(chatId);
                    log.info("Prepared result: " + result);
                    exit = true;
                    break;
                }
            }
            if(!exit){
                synchronized (lock){
                    lock.wait();
                }
            }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                answerMap.closeId(chatId);
                tgSender.sendMessage(chatId, "Entrance error. Data reset");
            }
        }
        dialogSender.send(result.toDto());
        log.info("Dialog for id:" + chatId + " finished");
    }


    /**
     * notify thread, when new message comes
     * @return return if the next question is last, to delete the thread from map inUse before closing
     */
    public boolean notifyThread() {
        synchronized (lock) {
            lock.notify();
            return questionNumber == qq - 1;
        }
    }
}
