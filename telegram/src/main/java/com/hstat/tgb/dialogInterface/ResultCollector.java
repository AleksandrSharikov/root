package com.hstat.tgb.dialogInterface;

import com.hstat.dtoModels.DTO;

public interface ResultCollector<T extends DTO> {
    void setRes(int i, String res);
    public T toDto();
}
