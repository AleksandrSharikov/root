package com.hstat.tgb.dialogInterface;

import com.hstat.common.dtoModels.TgMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageMapHandler {
    void closeId(long userId);
    String pollMessage(long id);
    boolean mergeUpdate(TgMessage message);
}

