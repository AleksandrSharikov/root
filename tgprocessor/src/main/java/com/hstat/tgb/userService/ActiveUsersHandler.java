package com.hstat.tgb.userService;

public interface ActiveUsersHandler {
    void add(long chatId);

    boolean isActive(long chatId);

    boolean askUser(long userId);

    void remove(long chatId);
}
