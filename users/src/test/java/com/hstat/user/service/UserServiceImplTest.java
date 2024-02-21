package com.hstat.user.service;

import com.hstat.common.dtoModels.UserCard;
import com.hstat.user.model.User;
import com.hstat.user.repository.UserRepository;
import com.hstat.user.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void tgPresent_UserExists_ReturnsTrue() {
        long tgId = 2L;

        when(userRepository.findByTgId(tgId)).thenReturn(Optional.of(new User( new UserCard(1,2,"TestName","SecretPassword" ,LocalDate.now()))));
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        boolean result = userService.tgPresent(tgId);

        assertTrue(result);
    }

    @Test
    void tgPresent_UserDoesNotExist_ReturnsFalse() {
        long tgId = 3L;

        when(userRepository.findByTgId(tgId)).thenReturn(Optional.empty());
        UserServiceImpl userService = new UserServiceImpl(userRepository);

        boolean result = userService.tgPresent(tgId);

        assertFalse(result);
   
    }
}
