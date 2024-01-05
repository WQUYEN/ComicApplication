package quyenvvph20946.fpl.supercomicapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import pl.droidsonroids.gif.GifImageView;
import quyenvvph20946.fpl.supercomicapplication.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 3000; // Thời gian hiển thị màn hình chào

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GifImageView gifImageView = findViewById(R.id.gifImageViewSplash);
        gifImageView.setImageResource(R.drawable.welcome_android);
        // Sử dụng Handler để chuyển đến màn hình chính sau khi hiển thị màn hình chào
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển đến màn hình chính (hoặc màn hình khác)
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}