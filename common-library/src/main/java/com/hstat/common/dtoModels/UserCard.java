package com.hstat.common.dtoModels;

import java.time.LocalDate;

public record UserCard(long userId,
                       long tgId,
                       String name,
                       String password,
                       LocalDate birthDay)
implements DTO{}
