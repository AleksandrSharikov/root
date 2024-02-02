package com.hstat.tgb.dialogInterface;

import com.hstat.common.dtoModels.TgMessage;

public interface DialogProcessorInt {
    void process(TgMessage message);
    boolean isInProcess(long id);
    void stop(long id);
}
