package com.hstat.user.services;

import com.hstat.user.model.User;
import com.hstat.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
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
        boolean answer = userRepository.findByTgId(tgId).isPresent();
        log.info(String.format("Looking for %d. Found = %b", tgId, answer));
        return answer;
    }

}
