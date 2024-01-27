package com.hstat.common.dtoModels;

import java.time.LocalDateTime;

public record StatSend(long catId,
                       LocalDateTime created,
                       String med,
                       int feel
) implements DTO {
}
