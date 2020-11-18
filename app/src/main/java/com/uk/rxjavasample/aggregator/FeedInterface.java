package com.uk.rxjavasample.aggregator;



import com.uk.rxjavasample.aggregator.models.Feed;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;


public interface FeedInterface {

    @GET("https://news.google.com/atom?hl=en-US&gl=US&ceid=US:en")
    Observable<Feed> getFeedGoogle();

    @GET("https://www.theregister.com/software/headlines.atom")
    Observable<Feed> getFeedRegister();


}
