package com.fzj.pms.entity.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fzj.pms.entity.enums.RestCode;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    private long timestamp = System.currentTimeMillis();

    public static Result success(){
        Result result=new Result();
        result.setRestCode(RestCode.SUCCESS);
        return result;
    }

    public static<T> Result success(T data){
        Result<T> result=new Result<>();
        result.setRestCode(RestCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static Result failure(RestCode code){
        Result result=new Result();
        result.setRestCode(code);
        return result;
    }


    public static Result failure(String message){
        Result result=new Result();
        result.setCode(RestCode.SYS_ERROR_EXCEPTION.getCode());
        result.setMessage(message);
        return result;
    }


    public static Result failure(Integer code, String message){
        Result result=new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static<T> Result failure(Integer code,T data){
        Result<T> result=new Result<>();
        result.setCode(code);
        result.setData(data);
        return result;
    }

    private void setRestCode(RestCode restCode){
        this.code=restCode.getCode();
        this.message=restCode.getMessage();
    }

}
