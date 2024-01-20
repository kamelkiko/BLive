package com.expert.blive.TicToc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.expert.blive.R;


public class TwoNameActivity extends AppCompatActivity {

    public CharSequence player1 = "Player 1";
    public CharSequence player2 = "Player 2";
    public boolean selectedsingleplayer = false;
    private EditText first;
    private EditText second;
    private String GAME_ID = "4386503";
    private String BANNER_ID = "Banner_Android";
    private String INS_ID = "Interstitial_Android";
    private boolean test = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_two_name);
        final TextView button = findViewById(R.id.button2);

        SoftKeyboardStateHelper.SoftKeyboardStateListener softKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                button.setVisibility(View.GONE);

            }

            @Override
            public void onSoftKeyboardClosed() {
                button.setVisibility(View.VISIBLE);
            }
        };

        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(getApplicationContext(), findViewById(R.id.parent));
        softKeyboardStateHelper.addSoftKeyboardStateListener(softKeyboardStateListener);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        first = findViewById(R.id.first_player);
        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player1 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        second = findViewById(R.id.second_player);
        second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                player2 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        TextView ds = findViewById(R.id.button2);
        ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TwoNameActivity.this, ChooseActivity.class);
                i.putExtra("selectedsingleplayer", selectedsingleplayer);
                CharSequence[] players = {player1, player2};
                i.putExtra("playersnames", players);

                startActivity(i);
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TwoNameActivity.this, TicTocActivity.class);
        startActivity(intent);
    }


}
