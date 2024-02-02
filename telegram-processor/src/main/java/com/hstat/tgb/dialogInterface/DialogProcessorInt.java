package com.hstat.tgb.dialogInterface;

import com.hstat.common.dtoModels.TgMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface DialogProcessorInt {
    void process(TgMessage message);
    boolean isInProcess(long id);
    void stop(long id);
}
