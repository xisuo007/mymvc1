package com.king.lianxi;

public enum BusiCodeEnum {
    code1001("1001", "征信进件接口"),
    code1002("1002", "分期信息推送接口"),
    code1003("1003", "开卡信息推送接口"),
    code1004("1004", "通知相关接口"),
    code1005("1005", "补充分期材料接口"),
    code1006("1006", "抵押材料补录接口"),
    code1008("1008", "信息确认接口"),
    code1009("1009", "电子签约合同与证据链下载接口"),
    code1011("1011", "订单状态查询接口"),
    code1012("1012", "订单申请接口"),
    code1013("1013", "订单最新通知记录查询接口"),
    ;
    private final String key;
    private final String des;

    BusiCodeEnum(String key, String des) {
        this.des = des;
        this.key = key;
    }
    
    public static String getDesc(String key){
        for (BusiCodeEnum value : BusiCodeEnum.values()) {
            if (value.getKey().equals(key)) {
                return value.getDes();
            }
        }
        return "";
    }

    public String getKey() {
        return key;
    }

    public String getDes() {
        return des;
    }
}