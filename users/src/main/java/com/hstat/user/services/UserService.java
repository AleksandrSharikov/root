package com.hstat.user.services;

import com.hstat.user.model.User;

public interface UserService {

    long saveUser(User user);

    boolean tgPresent(Long thId);
}
