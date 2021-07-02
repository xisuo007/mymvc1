package com.king.lianxi;

/**
 * Created by ljq on 2020-9-18 10:33
 */
public class maidian {
    public static void main(String[] args){
    /**
        amd | 客户端  | 否  | imei,idfa
        apv | 客户端  | 是  | 应用版本 app version  app 必须传递
        clt | 客户端  | 是  | 终端类型 [app web h5]
        ct | 客户端  | 是  | 客户端时间戳 ms
        data | 服务端  | 是  | 业务字段，服务端传递给客户段要埋点的数据
        did | 客户端  | 是  | 设备id ，app 端必须传，web h5 为空，服务端会将did写入cookie
        dst | 客户端  | 是  | 设备系统类型 1 android 2 ios app 必须传递
        dsv | 客户端  | 是  | 设备系统版本 app 必须传递
        dsv | 客户端  | 是  | 设备系统版本 app 必须传递
        dt | 客户端  | 是  | 设备类型 app 必须传递
        eid | 客户端  | 是  |  事件id
        fr | 客户端  | 是  | app 下载来源
        ip | 服务端  | 是  | client ip
        lag | 客户端  | 是  | 客户端语言 app 必须传递
        lat | 客户端  | 否  | 纬度 app 必须传递
        lc | 客户端  | 否  | 登陆方式
        lon | 客户端  | 否  | 经度 app 必须传递
        net | 客户端  | 是  | 网络类型 2－4  : 2-4g ，5:wifi ，1：其他  app 必须传递
        pf | 客户端  | 是  |  平台类型，哪个平台在调用数据接口
        pid | 客户端  | 是  | 页面id
        pno | 客户端  | 是  | 页面访问深度
        ppid | 客户端  | 是  | 父页面id
        refer | 客户端  | 是  | 请求的refer url web h5 必须传递
        req | 服务端  | 是  | 请求到达时间
        scr | 客户端  | 是  | 屏幕分辨率 app 必须传递
        sid | 客户端  | 否  | 会话 session id
        sig | 客户端  | 是  | 签名
        sn | 客户端  | 是  | 请求域名 web h5 必须传递
        tpc | 客户端  | 是  | 具体在给哪个业务或者产品在埋点，也是发给kafka的topic
        typ | 客户端  | 是  | 事件类型 p: 进入页面事件，e: 页面内事件（点击曝光等）
        ua | 服务端  | 是  | user agent
        uid | 客户端  | 否  | 用户id
        url | 客户端  | 是  | 请求的url web h5 必须传递
        ver | 客户端  | 是  | 日志版本
    */
    }
}
