package com.zhwtas.fanchunyan.okhttpcachedemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by FacChunYan on 2017/5/31.
 */

public class AndroidH5Activity extends AppCompatActivity{

    private WebView webview;
    private TextView msgView;
    private Button button;
    private Button button1;
    private Button button2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webview);
        msgView = (TextView) findViewById(R.id.msg);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        // 启用javascript
        webview.getSettings().setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
       // webview.loadUrl("file:///android_asset/wst.html");
       webview.loadUrl("http://192.168.1.112:8080/myasset/wst.html");
        //webview.addJavascriptInterface(new JsInteration(), "control");
        webview.addJavascriptInterface(this, "control");
        webview.setWebChromeClient(new WebChromeClient() {
        });
		/*
		 * webview.setWebViewClient(new WebViewClient() {
		 *
		 * @Override public void onPageFinished(WebView view, String url) {
		 * super.onPageFinished(view, url); testMethod(webview); }
		 *
		 * });
		 */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl("javascript:sayHello()");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                webview.loadUrl("javascript:alertMessage(\"" + "content" + "\")");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl("javascript:toastMessage(\"" + "content" + "\")");
            }
        });
    }

    private void testMethod(WebView webView) {
        String call = "javascript:sayHello()";

		/*
		 * call = "javascript:alertMessage(\"" + "content" + "\")";
		 *
		 * call = "javascript:toastMessage(\"" + "content" + "\")";
		 *
		 * call = "javascript:sumToJava(1,2)";
		 */
        webView.loadUrl(call);

    }
    @JavascriptInterface
    public void toastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
    public class JsInteration {

		/*@JavascriptInterface
		public void toastMessage(String message) {
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
		}*/

        @JavascriptInterface
        public void onSumResult(int result) {
            Log.i("TAG", "onSumResult result=" + result);
        }
    }

}
