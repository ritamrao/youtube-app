package com.example.youtube_app;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webView);

        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/JL9cVKNjB-4?si=emafuUsR5U_YhH9L\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";
        webView.loadData(video, "text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String youtubeUrl = getIntent().getStringExtra("url");
        String videoId = extractVideoId(youtubeUrl);

        String iframeHtml = "<!DOCTYPE html><html><body>" +
                "<div id=\"player\"></div>" +
                "<script>" +
                "var tag = document.createElement('script');" +
                "tag.src = \"https://www.youtube.com/iframe_api\";" +
                "var firstScriptTag = document.getElementsByTagName('script')[0];" +
                "firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);" +
                "var player;" +
                "function onYouTubeIframeAPIReady() {" +
                "player = new YT.Player('player', {" +
                "height: '390'," +
                "width: '640'," +
                "videoId: '" + videoId + "'," +
                "playerVars: {" +
                "'playsinline': 1" +
                "}," +
                "events: {" +
                "'onReady': onPlayerReady," +
                "'onStateChange': onPlayerStateChange" +
                "}" +
                "});" +
                "}" +
                "function onPlayerReady(event) {" +
                "event.target.playVideo();" +
                "}" +
                "function onPlayerStateChange(event) {" +
                "if (event.data == YT.PlayerState.PLAYING && !done) {" +
                "setTimeout(stopVideo, 6000);" +
                "done = true;" +
                "}" +
                "}" +
                "function stopVideo() {" +
                "player.stopVideo();" +
                "}" +
                "</script>" +
                "</body></html>";
        Log.d("HTML", "HTML String: " + iframeHtml);

        webView.loadData(iframeHtml, "text/html", "UTF-8");
    }


    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private String extractVideoId(String youtubeUrl) {
        String videoId = "";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 && youtubeUrl.contains("youtube.com")) {
            String[] urlParts = youtubeUrl.split("v=");
            if (urlParts.length > 1) {
                videoId = urlParts[1];
                int ampersandIndex = videoId.indexOf('&');
                if (ampersandIndex != -1) {
                    videoId = videoId.substring(0, ampersandIndex);
                }
            }
        }else if (youtubeUrl.contains("youtu.be")) {
            String[] urlParts = youtubeUrl.split("/");
            if (urlParts.length > 1) {
                videoId = urlParts[1];
                int questionMarkIndex = videoId.indexOf('?');
                if (questionMarkIndex != -1) {
                    videoId = videoId.substring(0, questionMarkIndex);
                }
            }
        }
        Log.d("VideoID", "Video ID: " + videoId);
        return videoId;
    }
}
