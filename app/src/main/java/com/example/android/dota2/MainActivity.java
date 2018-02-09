package com.example.android.dota2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.dota2.Data.HeroAdapter;
import com.example.android.dota2.Data.Heroes;
import com.example.android.dota2.Utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    HeroAdapter mHeroAdapter;
    RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecycleView = findViewById(R.id.rv_numbers);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecycleView.setLayoutManager(layoutManager);

        mRecycleView.setHasFixedSize(true);

        URL url = NetworkUtils.builtURL();
        new HeroAsyncTask().execute(url);


    }


    @SuppressLint("StaticFieldLeak")
    public class HeroAsyncTask extends AsyncTask<URL, Void, List<Heroes>> {

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

            mHeroAdapter = new HeroAdapter(MainActivity.this,heroes, new HeroAdapter.ListItemClickListener() {
                @Override
                public void onListItemClick(Heroes heroes) {
                    Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                    intent.putExtra("data",heroes);
                    startActivity(intent);
                }
            });

            mRecycleView.setAdapter(mHeroAdapter);

        }
    }

}