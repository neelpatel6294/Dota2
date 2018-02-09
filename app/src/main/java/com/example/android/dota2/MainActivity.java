package com.example.android.dota2;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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
            super.onPostExecute(heroes);
            mHeroAdapter = new HeroAdapter(heroes);
            mRecycleView.setAdapter(mHeroAdapter);

        }
    }

}