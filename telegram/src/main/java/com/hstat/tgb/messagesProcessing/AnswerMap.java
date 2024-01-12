package com.hstat.tgb.messagesProcessing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class AnswerMap {
    private final Map<Long, Deque<String>> inUse = new ConcurrentHashMap<>();
    public Deque<String> getList(long id){
        return inUse.get(id);
    }

    public void closeId(long id){
        inUse.remove(id);
    }

    public boolean mergeUpdate(Update update){
        if(inUse.containsKey(update.getMessage().getChatId())){
            inUse.get(update.getMessage().getChatId()).push(update.getMessage().getText());
            log.info("Map record updated");
            return false;
        } else {
            log.info("Added the new record to map");
            inUse.put(update.getMessage().getChatId(), new LinkedList<>(List.of(update.getMessage().getText())));
            return true;
        }
    }

}

