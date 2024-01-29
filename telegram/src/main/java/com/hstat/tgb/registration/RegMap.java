package com.hstat.tgb.registration;

import com.hstat.tgb.dialogInterface.MessageMapHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class RegMap implements MessageMapHandler {

    private final Map<Long, String> inReg = new ConcurrentHashMap<>();
    @Override
    public String pollMessage(long id){return  inReg.remove(id);}
    @Override
    public void closeId(long id) {
    inReg.remove(id);
    }
    @Override
    public boolean mergeUpdate(Update update){
        boolean was = inReg.containsKey(update.getMessage().getChatId());

        if (inReg.put(
                update.getMessage().getChatId(),
                update.getMessage().getText()) != null)
        {
            log.warn(String.format("Record for id %d overwritten before processing", update.getMessage().getChatId()));
        }

        return was;
    }
}

