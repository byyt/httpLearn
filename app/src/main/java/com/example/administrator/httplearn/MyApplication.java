package com.example.administrator.httplearn;

import android.app.Application;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cookie.CookieManger;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EasyHttp.init(this);//默认初始化,必须调用


        //这里涉及到安全我把url去掉了，demo都是调试通的
        String Url = "http://192.168.0.80:8585";


        //设置请求头
//        HttpHeaders headers = new HttpHeaders();
////        headers.put("User-Agent", SystemInfoUtils.getUserAgent(this, AppConstant.APPID));
//
//        CookieManger cookieManger = new CookieManger(this);
//        List<Cookie> list = cookieManger.getCookieStore().getCookies();
//        StringBuilder s = new StringBuilder();
//        if (list != null && list.size() > 0) {
//            for (Cookie cookie : list) {
//                s.append(cookie.name()).append("=").append(cookie.value()).append(";");
//            }
//            headers.put("Cookie", s.toString());
//        }


//        //设置请求参数
//        HttpParams params = new HttpParams();
//        params.put("appId", AppConstant.APPID);


        EasyHttp.getInstance()
                .debug("RxEasyHttp", true)
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 1000)
                .setConnectTimeout(60 * 1000)
                .setRetryCount(3)//默认网络不好自动重试3次
                .setRetryDelay(500)//每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setBaseUrl(Url)
                //下面是我加的，必须使用cookie，登陆成功后从服务端保存cookie，之后每次请求都带上cookit
                .setCookieStore(new CookieManger(this))
//                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
//                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
//                .setCacheVersion(1)//缓存版本为1
//                .setHostnameVerifier(new UnSafeHostnameVerifier(Url))//全局访问规则
//                .setCertificates()//信任所有证书
//                //.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
//                .addCommonHeaders(headers)//设置全局公共头
//                .addCommonParams(params)//设置全局公共参数
//                .addInterceptor(new CustomSignInterceptor());//添加参数签名拦截器
                .addInterceptor(new CookieInterceptor());//处理自己业务的拦截器

    }
}
