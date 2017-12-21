package gustavo.brilhante.braviandroid.rest;


import gustavo.brilhante.braviandroid.model.RssFeed;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Gustavo on 15/12/17.
 */

public class RssRestCliente {

    private RssRestClienteApi restClientApi;

    public String arg = "HomePage.xml";

    public RssRestCliente(String url){
        this.restClientApi = new RestCliente(url).createRestClient(RssRestClienteApi.class);
    }


    public static RssRestCliente build(String url){
        return new RssRestCliente(url);
    }

    public Observable<RssFeed> getFeedItens(String arg){
        return this.restClientApi.getItens(arg);
    }

    public interface RssRestClienteApi{

        @GET
        Observable<RssFeed> getItens(@Url String url);


    }
}
