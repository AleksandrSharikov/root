package com.hstat.tgb.questions;

import com.hstat.common.dtoModels.DTO;
import com.hstat.tgb.dialogInterface.MessageMapHandler;
import com.hstat.tgb.dialogInterface.ResultCollector;
import com.hstat.tgb.dialogInterface.ResultProcessor;
import com.hstat.tgb.models.DialogQuestions;
import com.hstat.tgb.outcomeProcessor.OutcomeProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 *  The class of runnable to create the thread for dialog
 */
@Slf4j
public class Dialog<T extends DTO> implements Runnable {

    private final Object lock;      // object for sync
    private final long chatId;
    private final DialogQuestions questions;
    private final MessageMapHandler messageMap;      // Map of answers like queue
    private final ResultCollector<T> result;
    private final ResultProcessor<T> resultProcessor;
    private int questionNumber;
    private final int qq;
    private final OutcomeProcessor outcomeProcessor;
    volatile boolean exit = false;


    public Dialog(long chatId,
                  DialogQuestions questions,
                  MessageMapHandler messageMap,
                  ResultProcessor<T> resultProcessor,
                  ResultCollector<T> result,
                  OutcomeProcessor outcomeProcessor)
    {
        this.chatId = chatId;
        this.questions = questions;
        this.messageMap = messageMap;
        this.result = result;
        this.resultProcessor = resultProcessor;
        this.questionNumber = -1;
        this.qq = questions.getQuantity();
        this.outcomeProcessor = outcomeProcessor;
        this.lock = new Object();
    }

    @Override
    public void run() {

        log.info("Dialog for id:" + chatId + " started");
        while(!exit){
            try {
            log.info("Question number: " + questionNumber);
        // process all records inn queue. Probably redundant. Can be changed to if()
            if(messageMap.getMessage(chatId) != null){
                result.setRes(questionNumber++,messageMap.getMessage(chatId));
                if(questionNumber < qq) {
                    outcomeProcessor.sendMessage(chatId, questions.getQuestion(questionNumber));
                } else {
                    outcomeProcessor.sendMessage(chatId, "Thank you, that's all");
                    messageMap.closeId(chatId);
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
                messageMap.closeId(chatId);
                outcomeProcessor.sendMessage(chatId, "Entrance error. Data reset");
            }
        }
        resultProcessor.process(result.toDto());
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
