package thenvengers.com.trouve.ui.imageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URI;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;
import thenvengers.com.trouve.R;

public class ImageHolder extends AppCompatActivity {

    private static final String TAG = "ImageHolder";
    @BindView
   (R.id.mImageView) public ImageView mImgView;
    @BindView
    (R.id.image_toolbar) public Toolbar mToolbar;
    public String mImgUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        getIncomingIntent();
    }
    private void getIncomingIntent() {
        Log.d(TAG, "In Incoming Intent");
        Intent i = getIntent();
        if (i.hasExtra("m_url")){
            Log.d(TAG, "getIncomingIntent: found image url");
            mImgUrl = i.getStringExtra("m_url");
            Log.i(TAG, "getIncomingIntent: m_url = " + mImgUrl);
            Glide.with(this).load(mImgUrl).into(mImgView);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bookmark) {
            Toast.makeText(ImageHolder.this, "Bookmark clicked", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Trouve");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mImgUrl);
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
