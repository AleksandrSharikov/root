package com.hstat.tgb.messagesProcessing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class IncomeProcessor {

    private final AnswerMap answerMap;
    private final ApplicationContext applicationContext;
    @Lookup
    private Dialog getDialog(long id){
        return applicationContext.getBean(Dialog.class, id);
    }

    @Autowired
    public IncomeProcessor(AnswerMap answerMap, ApplicationContext applicationContext) {
        this.answerMap = answerMap;
        this.applicationContext = applicationContext;
    }

    public void process(Update update) {
        log.info("Update is in the processor");
        if (answerMap.mergeUpdate(update)) {
            log.info("New thread shall start");
            Dialog dialog = getDialog(update.getMessage().getChatId());
            Thread thread = new Thread(dialog,
                    update.getMessage().getChatId().toString());
            thread.start();
        }
    }
}
