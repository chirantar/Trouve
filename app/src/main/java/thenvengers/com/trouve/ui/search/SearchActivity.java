package thenvengers.com.trouve.ui.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import thenvengers.com.trouve.FlickrApi;
import thenvengers.com.trouve.R;
import thenvengers.com.trouve.model.Photos;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "SearchActivity";

    @BindView(R.id.item_recycler_view) RecyclerView mImageItemView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView ;
    @BindView(R.id.search_view) SearchView mSearchView;
    SearchViewModel mSearchViewModel;


    ImageItemViewAdapter mImageViewAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //mSearchView = (SearchView)findViewById(R.id.search_view);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        mImageItemView.setLayoutManager(layoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.setActivated(true);
                mSearchView.requestFocus();
            }
        });
        mSearchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        final Observer<Photos> photosObserver = new Observer<Photos>() {
            @Override
            public void onChanged(@Nullable Photos photos) {
                if(photos != null) {
                    initializeRecyclerView(photos);
                }
            }
        };
        String url = FlickrApi.getFinalUrl("food");
        mSearchViewModel.getImages(url).observe(this, photosObserver);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onClick: ON searchview click");
                mSearchView.clearFocus();

                if( query.isEmpty() == false ){
                    Log.d(TAG, "onClick: Inside If condition");
                    String url = FlickrApi.getFinalUrl(query);
                    mSearchViewModel.getNewImages(url).observe(SearchActivity.this, photosObserver);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        setActionBarDrawerToggle();

        setNavigationView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mSearchView.clearFocus();
    }

    private void setNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setActionBarDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initializeRecyclerView(Photos photos) {
        mImageViewAdapter = new ImageItemViewAdapter(this, photos);
        mImageItemView.setAdapter(mImageViewAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
