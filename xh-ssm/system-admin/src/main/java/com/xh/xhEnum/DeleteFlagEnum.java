package com.xh.xhEnum;

import lombok.Data;

/**
 * @description: 好好学Java，早日找到好工作
 * @author: XiaoHua
 * @create: 2024-10-11 13:10
 **/

public enum DeleteFlagEnum {
    //yes代表逻辑上已经删除，no代表逻辑上没有删除
    YES("1"),NO("0");

    private String value;
    DeleteFlagEnum(String value){
        this.value = value;
    }
    public String getValue(){
        return value;

    }
}
