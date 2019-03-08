package com.example.usuari.appwebview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class CustomWebChromeClient extends WebChromeClient {


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
    }
}
