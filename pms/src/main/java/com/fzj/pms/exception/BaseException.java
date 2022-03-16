package com.fzj.pms.exception;

import com.fzj.pms.entity.enums.RestCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{
    protected Integer code;

    BaseException(String message) {
      super(message);
    }

    BaseException(RestCode restCode){
        super(restCode.getMessage());
        this.code = restCode.getCode();
    }

}
