package com.fzj.pms.entity.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JwtToken {

    @ApiModelProperty("请求时携带的token")
    private String accessToken;

    @ApiModelProperty("刷新token")
    private String refreshToken;

}
