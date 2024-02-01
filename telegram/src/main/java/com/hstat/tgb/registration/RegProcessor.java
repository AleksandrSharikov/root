package com.hstat.tgb.registration;

import com.hstat.common.dtoModels.UserCard;
import com.hstat.tgb.dev.MemoryMonitorService;
import com.hstat.tgb.sendToTg.TgSender;
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
    private final RegMap regMap;
    private final TgSender tgSender;
    private final MemoryMonitorService memoryMonitorService;
    private final String regGrid =
            "You are not registered yet.\nPlease take a few minutes for the registration.";



    public RegProcessor(TgSender tgSender, RegMap regMap, MemoryMonitorService memoryMonitorService) {
        this.regMap = regMap;
        this.tgSender = tgSender;
        this.memoryMonitorService = memoryMonitorService;
        this.threadMap = new HashMap<>();
    }

    @Lookup
    Dialog<UserCard> getRegDialog(long id){
        return null;
    }


    @Override
    public void process(Update update) {

        log.info(String.format("Update \"%s\" is in reg processor", update.getMessage().getText()));
        if (!regMap.mergeUpdate(update)){
            log.info(String.format("Starting new thread for id %d", update.getMessage().getChatId()));
            tgSender.sendMessage(update.getMessage().getChatId(), regGrid);
           // memoryMonitorService.monitor();
            Dialog<UserCard> dialog = getRegDialog(update.getMessage().getChatId());
            Thread thread = new Thread(dialog,
                    update.getMessage().getChatId().toString());
         //   memoryMonitorService.monitor();
            threadMap.put(update.getMessage().getChatId(), dialog);
            thread.start();
        } else {
            log.info(String.format("Proceeding with thread %d", update.getMessage().getChatId()));
            if (threadMap.get(update.getMessage().getChatId()).notifyThread()){
                log.info(String.format("Thread %d notified", update.getMessage().getChatId()));
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
