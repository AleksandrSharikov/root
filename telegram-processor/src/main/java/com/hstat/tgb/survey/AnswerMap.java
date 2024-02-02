package com.hstat.tgb.survey;

import com.hstat.common.dtoModels.TgMessage;
import com.hstat.tgb.dialogInterface.MessageMapHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Map of dequeues to different dialogs (probably redundant. Can be substituted by Map<..., String>)
 */
@Slf4j
@Service
public class AnswerMap implements MessageMapHandler {
    private final Map<Long, Deque<String>> inUse = new ConcurrentHashMap<>();

    // Get messages for the certain chatId
    public Deque<String> getList(long id){
        return inUse.get(id);
    }

    @Override
    public String pollMessage(long id){ return inUse.get(id).poll();}
    // Close id and remove it from the map
    @Override
    public void closeId(long id){
        inUse.remove(id);
    }

    // Merge new update to the map. Returns true if it is new id in the map, and false if id already existed
    @Override
    public boolean mergeUpdate(TgMessage  message){
        if(inUse.containsKey(message.chatId())){
            inUse.get(message.chatId()).push(message.message());
            log.info("Map record updated");
            return false;
        } else {
            log.info("Added the new record to map");
            inUse.put(message.chatId(), new LinkedList<>(List.of(message.message())));
            return true;
        }
    }

}

