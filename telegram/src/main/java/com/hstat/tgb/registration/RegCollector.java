package com.hstat.tgb.registration;

import com.hstat.dtoModels.UserCard;
import com.hstat.tgb.dialogInterface.ResultCollector;

import java.time.LocalDate;

public class RegCollector implements ResultCollector<UserCard> {
    private final long tgId;
    private String name;
    private String password;
    private LocalDate birthDay;

    public RegCollector(long tgId) {
        this.tgId = tgId;
    }

    @Override
    public void setRes(int i, String res) {
        switch (i){
            case 1 -> name = res;
            case 2 -> birthDay = LocalDate.parse(res);
            case 3 -> password = res;
        }
    }



    @Override
    public UserCard toDto() {
        return new UserCard(
                -1L,
                tgId,
                name,
                password,
                birthDay
        );
    }
}
