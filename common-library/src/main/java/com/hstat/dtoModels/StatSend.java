package com.hstat.dtoModels;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StatSend(long catId, LocalDateTime created, String med, int feel) {
}
