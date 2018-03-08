package com.example.android.dota2;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.dota2.Adapter.FavAdapter;
import com.example.android.dota2.Adapter.HeroAdapter;
import com.example.android.dota2.Adapter.Heroes;
import com.example.android.dota2.Utils.NetworkUtils;
import com.facebook.stetho.Stetho;

import java.net.URL;
import java.util.List;

import static com.example.android.dota2.Data.Contract.TaskContract.CONTENT_URI;
import static com.example.android.dota2.Data.Contract.TaskContract.HERO_ID;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DOTA_URL = "dotaurl";
    private static final int HERO_LOADER_ID = 0;

    private FavAdapter mFavoriteAdapter;
    private HeroAdapter mHeroAdapter;
    private RecyclerView mRecycleView;

    private TextView mLoadingError;
    private ProgressBar mLoadingPb;


    private final static String MENU_SELECTED = "selected";
    private int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);


        mRecycleView = findViewById(R.id.rv_numbers);
        mLoadingError = findViewById(R.id.loading_error_message);
        mLoadingPb = findViewById(R.id.pb_loading);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecycleView.setLayoutManager(layoutManager);

        mRecycleView.setHasFixedSize(true);


//        URL url = NetworkUtils.builtURL();
//        new HeroAsyncTask().execute(url);
//
//        showHeroDataView();

        if (savedInstanceState == null) {
            build();

            //            build("heroes");
//            URL url = NetworkUtils.builtURL();
//            new HeroAsyncTask().execute(url);
        } else {
            if (savedInstanceState != null) {
                selected = savedInstanceState.getInt(MENU_SELECTED);

                if (selected == -1) {
                    build();

                } else if (selected == R.id.fav) {
                    getActionBar().setTitle("Favorite Heroes");
                    getLoaderManager().restartLoader(HERO_LOADER_ID, null, this);
                    mFavoriteAdapter = new FavAdapter(new HeroAdapter.ListItemClickListener() {
                        @Override
                        public void onListItemClick(Heroes heroes) {
                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            intent.putExtra("data", heroes);
                            startActivity(intent);
                        }
                    }, this);
                    mRecycleView.setAdapter(mFavoriteAdapter);
                }
            }
        }

    }

    private void showHeroDataView() {

        mLoadingError.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {

        mRecycleView.setVisibility(View.INVISIBLE);

        mLoadingError.setVisibility(View.VISIBLE);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mHeroData = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (mHeroData != null) {
                    deliverResult(mHeroData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {

                    return getContentResolver().query(CONTENT_URI,
                            null,
                            null,
                            null,
                            HERO_ID);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mHeroData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFavoriteAdapter.swapCursor(data);

//        String heroName = data.getString(Integer.parseInt(Contract.TaskContract.HERO_NAME));
//
//        TextView textView = findViewById(R.id.detail_heroName);
//        textView.setText(heroName);
          //  mRecycleView.setAdapter(mFavoriteAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavoriteAdapter.swapCursor(null);
    }


    @SuppressLint("StaticFieldLeak")
    public class HeroAsyncTask extends AsyncTask<URL, Void, List<Heroes>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingPb.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Heroes> doInBackground(URL... urls) {


            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Heroes> result = NetworkUtils.fetchDotaHeroesData(urls[0]);
            Log.i("xyz1", String.valueOf(result));
            return result;

        }

        @Override
        protected void onPostExecute(List<Heroes> heroes) {

            mLoadingPb.setVisibility(View.INVISIBLE);

            if (heroes != null) {
                mHeroAdapter = new HeroAdapter(MainActivity.this, heroes, new HeroAdapter.ListItemClickListener() {
                    @Override
                    public void onListItemClick(Heroes heroes) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("data", heroes);
                        startActivity(intent);
                    }
                });
                mRecycleView.setAdapter(mHeroAdapter);
                mHeroAdapter.notifyDataSetChanged();

            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENU_SELECTED, selected);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        switch (id) {
            case R.id.all:
                //  getActionBar().setTitle("ALL");
                //build("heroes");
                selected = id;
                break;
            case R.id.fav:
                selected = id;
                //getActionBar().setTitle("Favorite Heroes");
                getLoaderManager().restartLoader(HERO_LOADER_ID, null, this);
                mFavoriteAdapter = new FavAdapter(new HeroAdapter.ListItemClickListener() {
                    @Override
                    public void onListItemClick(Heroes heroes) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("data", heroes);
                        startActivity(intent);
                    }
                }, this);
                mRecycleView.setAdapter(mFavoriteAdapter);
        }
        return super.onOptionsItemSelected(item);
    }

    private URL


    build() {
        URL final_Url = NetworkUtils.builtURL();
        new HeroAsyncTask().execute(final_Url);
        return final_Url;
    }
}