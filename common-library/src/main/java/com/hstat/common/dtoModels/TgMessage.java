package com.hstat.common.dtoModels;

public record TgMessage(long chatId,
                        String message
) implements DTO {}
