package com.zhwtas.fanchunyan.okhttpcachedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.zhwtas.fanchunyan.okhttpcachedemo.apiservice.IApi;
import com.zhwtas.fanchunyan.okhttpcachedemo.bean.Info;
import com.zhwtas.fanchunyan.okhttpcachedemo.bean.TokenResult;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

public class MainActivity extends AppCompatActivity {

    private Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? BODY : NONE);

        cache=new Cache(getCacheDir(), 10240*1024);
        OkHttpClient okHttpClient=new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                //.addInterceptor(new AddHeaderInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody requestBody=new FormBody.Builder().add("key","949ea374b5538b4d1631a01d7c743562").build();
        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(10, TimeUnit.MILLISECONDS);
        CacheControl cacheControl = builder.build();
        Request request=new Request.Builder()
              //  .cacheControl(cacheControl)
                .url("http://japi.juhe.cn/comic/category?key=949ea374b5538b4d1631a01d7c743562")
                .get()
                .build();
  /*      okhttp3.Call call=okHttpClient.newCall(request);//缓存只能是get请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    if(response.isSuccessful()){
                        Log.e("TAG",response.body().string()+"----");
                    }else{
                        failedCallBack("服务器错误");
                    }

                }
            });*/

        xUtilsDemo();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetApi.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
      /*  retrofit.create(IApi.class)
               // .getToken("client_credentials")
                .getBookType("949ea374b5538b4d1631a01d7c743562")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
              *//*  .subscribe(new Observer<Info>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("TAG","onError");
                    }
                    @Override
                    public void onNext(Info tokenResult) {
                        Log.e("TAG","onNext");
                        Log.e("TAG",tokenResult+"------");
                    }
                });*//*
              .subscribe(new SelfDefineSubscriber<Info>(){
                  @Override
                  public void onNext(Info info) {
                      super.onNext(info);
                      Log.e("TAG",info.toString());
                  }
              });*/
       /* IApi    service= retrofit.create(IApi.class);
        Call<Info> call= service.getBookType2("949ea374b5538b4d1631a01d7c743562");
        try {
           retrofit2.Response<Info> response=call.execute();
           Log.e("TAG--",response.body().toString()) ;
        } catch (IOException e) {
            e.printStackTrace();

        }*/
    }

    private void failedCallBack(String 服务器错误) {
       /* if (!NetworkUtil.isConnected(context)) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }*/
    }

    private void xUtilsDemo() {
        Log.e("TAG","xUtilsDemo");
        RequestParams requestParams=new RequestParams("http://japi.juhe.cn/comic/category");
        requestParams.addBodyParameter("key","949ea374b5538b4d1631a01d7c743562");
        requestParams.setCacheMaxAge(3600 * 24 * 30);
        requestParams.setCacheSize(10240*1024);
        requestParams.setCacheDirName(getCacheDir().getAbsolutePath());
        x.http().post(requestParams, new org.xutils.common.Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","post  success---"+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
               /* if(Net){
                    Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                }*/
                Log.e("TAG","onError");
            }
            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
                Log.e("TAG","onCancelled");
            }
            @Override
            public void onFinished() {
                Log.e("TAG","onFinished");
            }

            @Override
            public boolean onCache(String result) {
                Log.e("TAG","post  onCache---"+result);

                return false;
            }
        });
    }

    class SelfDefineSubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
           e.printStackTrace();
        }

        @Override
        public void onNext(T t) {

        }
    }
    class AddHeaderInterceptor  implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            //客户端名称
            String client_name = "android_driver";
            //客户端密钥
            String client_secret = "android_driver_password";

            byte[] str=(client_name+":"+client_secret).getBytes();
            String auth= Base64.encodeToString(str,0).trim();
            Request requst = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("authorization", "Basic " + auth)
                    .build();
            return chain.proceed(requst);
        }
    }
    class CacheInterceptor implements  Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Response response1 = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    //cache for 30 days
                    .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
                    .build();
            return response1;
        }
    }
}
