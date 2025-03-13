package com.heli.production.domain.dto;

import com.heli.production.domain.entity.SpecialCasesEntity;
import lombok.Data;

import java.util.List;

@Data
public class SpecialHandleDTO extends SpecialCasesEntity {

    private List<Long> notifyUserList;

}
