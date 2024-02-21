package com.hstat.tgb.registration;

import com.hstat.common.dtoModels.TgMessage;
import com.hstat.common.dtoModels.UserCard;
import com.hstat.tgb.dev.MemoryMonitorService;
import com.hstat.tgb.sendToTg.TgSender;
import com.hstat.tgb.questions.Dialog;
import com.hstat.tgb.dialogInterface.DialogProcessorInt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

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
    public void process(TgMessage message) {

        log.info(String.format("Update \"%s\" is in reg processor", message.message()));
        if (!regMap.mergeUpdate(message)){
            log.info(String.format("Starting new thread for id %d", message.chatId()));
            tgSender.sendMessage(message.chatId(), regGrid);
           // memoryMonitorService.monitor();
            Dialog<UserCard> dialog = getRegDialog(message.chatId());
            Thread thread = new Thread(dialog,
                    String.valueOf(message.chatId()));
         //   memoryMonitorService.monitor();
            threadMap.put(message.chatId(), dialog);
            thread.start();
        } else {
            log.info(String.format("Proceeding with thread %d", message.chatId()));
            if (threadMap.get(message.chatId()).notifyThread()){
                log.info(String.format("Thread %d notified", message.chatId()));
                threadMap.remove(message.chatId());
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
