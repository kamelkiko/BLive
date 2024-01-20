package com.expert.blive.HomeActivity.HomeMainCategory.Popular.category;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.expert.blive.R;

import im.delight.android.webview.AdvancedWebView;

public class GameScreenActivity extends AppCompatActivity implements AdvancedWebView.Listener {

    private AdvancedWebView mWebView;
    private Dialog loading_box;
    public static String gameLink = "";

    public GameScreenActivity() {
        // require empty constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        mWebView = findViewById(R.id.webview);
        mWebView.setListener(GameScreenActivity.this, this);
        mWebView.setMixedContentAllowed(false);

        if (!gameLink.equals("")) {

            mWebView.loadUrl(gameLink);

            openLoadingBox();

        } else {

            Toast.makeText(this, "Something went wrong , please try again later", Toast.LENGTH_SHORT).show();

            finish();

        }

    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

        loading_box.dismiss();

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }


    private void openLoadingBox() {
        loading_box = new Dialog(this);
        loading_box.setContentView(R.layout.loading_box);
        loading_box.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading_box.setCanceledOnTouchOutside(false);
        loading_box.setCancelable(false);
        Window window = loading_box.getWindow();
        window.setGravity(Gravity.CENTER);
        loading_box.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
}