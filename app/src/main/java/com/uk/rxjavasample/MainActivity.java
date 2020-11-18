package com.uk.rxjavasample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding4.view.RxView;
import com.jakewharton.rxbinding4.widget.RxCompoundButton;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.uk.rxjavasample.adapters.CustomAdapter;
import com.uk.rxjavasample.aggregator.FeedInterface;
import com.uk.rxjavasample.aggregator.RetrofitClient;
import com.uk.rxjavasample.aggregator.models.Entry;
import com.uk.rxjavasample.aggregator.models.Feed;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private String TAG = "AAA";

    private RecyclerView recyclerView;

    private void drawList(List<Entry> entriesList) {


        recyclerView.setAdapter(new CustomAdapter(entriesList));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        FeedInterface feedInterface = RetrofitClient.get().create(FeedInterface.class);

        Observable<Feed> feedGoogle = feedInterface.getFeedGoogle();
        Observable<Feed> feedRegister = feedInterface.getFeedRegister();


        Observable<List<Entry>> combinedObservable = Observable.combineLatest(feedGoogle, feedRegister, (feed1, feed2) -> {
            List<Entry> entries = new ArrayList<>();
            for(Entry entry: feed1.getEntry())
                entries.add(entry);

            for(Entry entry: feed2.getEntry())
                entries.add(entry);

            return entries;
        });

        combinedObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::drawList);

//
//        Call<Feed> call = feedInterface.getFeed();
//        call.enqueue(new Callback<Feed>() {
//            @Override
//            public void onResponse(Call<Feed> call, Response<Feed> response) {
//                if(response.isSuccessful()) {
//                    Feed feed = response.body();
//
//                    Log.d("AAA","Feed is OK" + feed.getTitle() + " " + feed.getEntry().size());
//                    for(Entry entry : feed.getEntry()) {
//                        Log.d("AAA", entry.getTitle());
//                    }
//                } else {
//                    Log.d("AAA", "Failed");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Feed> call, Throwable t) {
//                Log.d("AAA", t.getMessage());
//            }
//        });

    }


}