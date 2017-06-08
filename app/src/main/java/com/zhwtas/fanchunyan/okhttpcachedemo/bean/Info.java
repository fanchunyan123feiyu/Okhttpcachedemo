package com.zhwtas.fanchunyan.okhttpcachedemo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FacChunYan on 2017/5/27.
 */

public class Info {
    public int error_code;
    public String reason;
    public List<String> result;

    @Override
    public String toString() {
        return "Info{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
