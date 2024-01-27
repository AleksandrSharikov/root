package com.hstat.tgb.registration;

import com.hstat.common.dtoModels.UserCard;
import com.hstat.tgb.questions.Dialog;
import com.hstat.tgb.dialogInterface.DialogProcessorInt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RegProcessor implements DialogProcessorInt {

    private final Map<Long, Dialog<UserCard>> threadMap;
    //private final RegMap regMap;
  //  private final OutcomeProcessor outcomeProcessor;
   // private final ApplicationContext applicationContext;
    private final String regGrid =
            "You are not registered yet.\nPlease rake a few minutes for the registration.\n\n What is your name?";



    public RegProcessor(){;//(OutcomeProcessor outcomeProcessor) {
     //   this.regMap = regMap;
     //   this.outcomeProcessor = outcomeProcessor;
       // this.applicationContext = applicationContext;
        this.threadMap = new HashMap<>();
    }

    @Lookup
    Dialog<UserCard> getRegDialog(long id){
        return null;
    }


    @Override
    public void process(Update update) {
        log.info(String.format("Update \"%s\" is in reg processor", update.getMessage().getText()));
        if (!isInProcess(update.getMessage().getChatId())){
         //   outcomeProcessor.sendMessage(update.getMessage().getChatId(), regGrid);
            Dialog<UserCard> dialog = getRegDialog(update.getMessage().getChatId());
            Thread thread = new Thread(dialog,
                    update.getMessage().getChatId().toString());
            threadMap.put(update.getMessage().getChatId(), dialog);
            thread.start();
        } else {
            if (threadMap.get(update.getMessage().getChatId()).notifyThread()){
                threadMap.remove(update.getMessage().getChatId());
            }
        }
    }

    @Override
    public boolean isInProcess(long id) {
        return threadMap.containsKey(id);
    }

    @Override
    public void stop(long id) {
        threadMap.remove(id);
    }
}
