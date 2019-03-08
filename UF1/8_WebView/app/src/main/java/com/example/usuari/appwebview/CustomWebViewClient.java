package com.example.usuari.appwebview;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Log.d("SHIT HAPPENS", ">"+request.toString());
        if(!request.toString().contains("google.com")) {
            view.loadData("<html><body>ERROR, URL PROHIBIDA</body></html>", "text/html", "UTF-8");
        } else {
            view.loadUrl(request.toString());
        }
        return false;
    }
}
