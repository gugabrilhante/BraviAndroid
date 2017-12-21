package gustavo.brilhante.braviandroid.rest;

import android.content.Intent;

import java.util.List;

import gustavo.brilhante.braviandroid.model.RssFeedItem;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Gustavo on 15/12/17.
 */

public class RestCliente {

    private Retrofit retrofit;
    private final static int UPDATE_INTERVAL_MIN = 10;
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    private final static String TAG = "RestClient";
    List<RssFeedItem> cachedList = null;
    Intent mIntent;




    public RestCliente(String RSS_LINK) {

        retrofit = new Retrofit.Builder()
                .baseUrl(RSS_LINK)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

    }


    public <T> T createRestClient(Class<T> clazz){
        return this.retrofit.create(clazz);
    }
}
