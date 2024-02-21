package com.hstat.tgb.userService;


import com.hstat.tgb.dialogInterface.ProcessorsList;
import org.springframework.stereotype.Service;

/**
 * Handler of users activities
 */
@Service
public class UserActivities {

    private final ProcessorsList processorsList;

    public UserActivities(ProcessorsList processorsList) {
        this.processorsList = processorsList;
    }

    public void deactiveId(long chatId){
        processorsList.getAllMaps().forEach(s -> s.closeId(chatId));
        processorsList.getAllProcessors().forEach(s -> s.stop(chatId));
    }

}
