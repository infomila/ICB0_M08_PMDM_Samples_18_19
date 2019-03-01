package com.example.usuari.appwebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient( new WebViewClient());

        //webView.loadUrl("http://www.google.com");

        //Activem el Javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        // Connectar Javascript amb Java
        webView.addJavascriptInterface( new InterficieJavascript(), "Android");


        //String html = "<html><body><h1>TITOL</h1> <p onClick=\"alert('Hello world');\">Lorem Ipsum </p></body></html>";
        //webView.loadData(html, "text/html", "UTF-8");

        webView.loadUrl("file:///android_asset/pagina.html");

    }
}
