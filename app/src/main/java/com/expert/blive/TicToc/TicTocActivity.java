package com.expert.blive.TicToc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.expert.blive.HomeMainActivity;
import com.expert.blive.R;


public class TicTocActivity extends AppCompatActivity {
    private boolean Vibration;
    private static final String PREFS_NAME = "vibration";
    private static final String PREF_VIBRATION = "TicVib";
   // private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictoc);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        CardView Ai = findViewById(R.id.ai_mode);
        CardView FM = findViewById(R.id.friends_mode);

        Ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicTocActivity.this, NameActivity.class);
                startActivity(intent);
            }
        });

        FM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicTocActivity.this,TwoNameActivity.class);
                startActivity(intent);
            }
        });

        CardView settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(TicTocActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
       Intent intent = new Intent(TicTocActivity.this, HomeMainActivity.class);
       startActivity(intent);
    }

}
