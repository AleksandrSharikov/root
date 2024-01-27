package com.hstat.tgb.DialogPostProcessors;

import com.hstat.common.dtoModels.UserCard;
import com.hstat.tgb.dialogInterface.ResultProcessor;
import org.springframework.stereotype.Service;

@Service
public class RegPostProcessor implements ResultProcessor<UserCard> {
    @Override
    public void process(UserCard data) {

    }
}
