package com.zhwtas.fanchunyan.okhttpcachedemo.bean;

/**
 * Created by FacChunYan on 2017/5/27.
 */

public class TokenResult {
    public String access_token;
    public int expires_in;
    public String token_type;
    public Object scope;

    @Override
    public String toString() {
        return "TokenResult{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", token_type='" + token_type + '\'' +
                ", scope=" + scope +
                '}';
    }
}
