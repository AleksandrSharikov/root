package com.hstat.dtoModels;

import javax.swing.*;
import java.time.LocalDate;

public record UserCard(long userId,
                       long tdId,
                       String name,
                       Spring password,
                       LocalDate birthDay)
implements DTO{}
