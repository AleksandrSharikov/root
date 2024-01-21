package com.hstat.tgb.dialog;

import com.hstat.tgb.dialogInterface.DialogProcessorInt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.HashMap;
import java.util.Map;


/**
 * The class to start new threads for Dialogs and maintain them
 */
@Slf4j
@Service
public class DialogProcessor implements DialogProcessorInt {

    private final AnswerMap answerMap;
    private final ApplicationContext applicationContext;

    //  map with runnables for the threads
    private final Map<Long, Dialog> threadMap = new HashMap<>();

    //Creating a bean of Dialog
    @Lookup
    private Dialog getDialog(long id){
        return applicationContext.getBean(Dialog.class, id);
    }

    @Autowired
    public DialogProcessor(AnswerMap answerMap, ApplicationContext applicationContext) {
        this.answerMap = answerMap;
        this.applicationContext = applicationContext;
    }

    /**
     * If chatId is new, create a new thread for it, else notify existing one, if it is last answer, remove
     * the thread  from the map
     * @param update
     */
    public void process(Update update) {
        log.info("Update is in the processor");
        if (answerMap.mergeUpdate(update)) {
            log.info("New thread shall start");
            Dialog dialog = getDialog(update.getMessage().getChatId());
            Thread thread = new Thread(dialog,
                    update.getMessage().getChatId().toString());
            threadMap.put(update.getMessage().getChatId(), dialog);
            thread.start();
        } else {
            if(threadMap.get(update.getMessage().getChatId()).notifyThread()){
                threadMap.remove(update.getMessage().getChatId());
            }
        }
    }


    // Check if there is thread with certain id im the map
    public boolean isInProcess(long id){
        return threadMap.containsKey(id);
    }

    @Override
    public void stop(long id) {
        threadMap.remove(id);
    }

}
