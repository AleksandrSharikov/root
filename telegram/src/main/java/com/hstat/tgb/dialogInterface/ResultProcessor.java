package com.hstat.tgb.dialogInterface;

import com.hstat.dtoModels.DTO;

public interface ResultProcessor<T extends DTO> {
    void  process(T data);
}
