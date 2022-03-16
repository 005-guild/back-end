package com.fzj.pms.exception;

import com.fzj.pms.entity.enums.RestCode;

import java.util.Optional;

public class SystemErrorException extends BaseException {

    public SystemErrorException(String message) {
        super(Optional.ofNullable(message).orElse(RestCode.SYS_ERROR_EXCEPTION.getMessage()));
        this.code = RestCode.SYS_ERROR_EXCEPTION.getCode();
    }
}
