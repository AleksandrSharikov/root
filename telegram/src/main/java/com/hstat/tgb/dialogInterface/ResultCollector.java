package com.hstat.tgb.dialogInterface;

import com.hstat.dtoModels.DTO;

public interface ResultCollector {
    void setRes(int i, String res);
    public DTO toDto();
}
