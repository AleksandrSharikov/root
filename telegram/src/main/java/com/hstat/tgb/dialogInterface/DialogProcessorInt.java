package com.hstat.tgb.dialogInterface;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface DialogProcessorInt {
    void process(Update update);
    boolean isInProcess(long id);
    void stop(long id);
}
