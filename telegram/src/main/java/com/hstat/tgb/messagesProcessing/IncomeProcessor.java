package com.hstat.tgb.messagesProcessing;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IncomeProcessor {
    private final Map<Long, List<String>> inUse;

    public IncomeProcessor() {
        this.inUse = new ConcurrentHashMap<>();
    }

    public void process(Update update){
        if(inUse.containsKey(update.getMessage().getChatId())){
            inUse.get(update.getMessage().getChatId()).add(update.getMessage().getText());
        } else {
            inUse.put(update.getMessage().getChatId(), new ArrayList<>(List.of(update.getMessage().getText())));
        }

    }

    public List<String> getList(long id){
        return inUse.get(id);
    }

    public void closeId(long id){
        inUse.remove(id);
    }

}
