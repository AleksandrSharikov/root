package com.hstat.tgb.registration;

import com.hstat.tgb.dialogInterface.DialogProcessorInt;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RegProcessor implements DialogProcessorInt {
    @Override
    public void process(Update update) {

    }

    @Override
    public boolean isInProcess(long id) {
        return false;
    }

    @Override
    public void stop(long id) {

    }
}
