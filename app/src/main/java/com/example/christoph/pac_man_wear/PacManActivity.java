package com.example.christoph.pac_man_wear;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;

import com.example.christoph.pac_man_wear.views.GameView;

public class PacManActivity extends Activity {
    private GameView gameView;
    private int updateTimeout = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pac_man);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {showMainMenu();    }
        });

        // indicating update interval
        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {
            private long time = 0;

            @Override
            public void run()
            {
                time += updateTimeout;

                update(time);
                h.postDelayed(this, updateTimeout);
            }
        }, updateTimeout);
    }

    /**
     * Show the main view.
     */
    private void showMainMenu() {
        Button playGameBtn = (Button) findViewById(R.id.play_game_btn);

        // clicked on play game button
        playGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNewGame();
            }
        });
    }

    /**
     * Game Loop.
     * @param time
     */
    private void update(long time) {
        if (gameView == null) return;

        gameView.update(time);
    }

    /**
     * Load a new game instance.
     */
    private void loadNewGame() {
        gameView = new GameView(this);
        setContentView(gameView);
    }
}
