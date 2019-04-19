package thenvengers.com.trouve.ui.splashScreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import thenvengers.com.trouve.R;
import thenvengers.com.trouve.ui.search.SearchActivity;

public class SplashActivity extends AppCompatActivity {

@BindView(R.id.imageView3)
    public ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Animation animSlideUp = AnimationUtils.loadAnimation(this, R.anim.enter_slide_up);
        mImageView.clearAnimation();
        mImageView.startAnimation(animSlideUp);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, SearchActivity.class);
                startActivity(i);
            }
        },5000);
    }
}
