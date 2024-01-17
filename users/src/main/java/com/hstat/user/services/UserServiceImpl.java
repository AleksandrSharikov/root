package com.hstat.user.services;

import com.hstat.user.model.User;
import com.hstat.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public long saveUser(User user){
        return userRepository.save(user).getUserId();
    }

    @Override
    public boolean tgPresent(Long tgId) {
        return userRepository.findByTgId(tgId).isPresent();
    }

}
