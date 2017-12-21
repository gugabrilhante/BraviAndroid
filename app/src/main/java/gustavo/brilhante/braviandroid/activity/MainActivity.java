package gustavo.brilhante.braviandroid.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.anupcowkur.reservoir.Reservoir;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gustavo.brilhante.braviandroid.R;
import gustavo.brilhante.braviandroid.adapter.NoticiaAdapter;
import gustavo.brilhante.braviandroid.adapter.NoticiaAdapterListener;
import gustavo.brilhante.braviandroid.base.NavigationActivity;
import gustavo.brilhante.braviandroid.cache.InternalStorage;
import gustavo.brilhante.braviandroid.component.GustavoActionBar;
import gustavo.brilhante.braviandroid.component.GustavoSearchFloatingBar;
import gustavo.brilhante.braviandroid.fragment.NoticiaFragment;
import gustavo.brilhante.braviandroid.model.RssFeed;
import gustavo.brilhante.braviandroid.model.RssFeedItem;
import gustavo.brilhante.braviandroid.rest.RssRestCliente;
import gustavo.brilhante.braviandroid.util.AnimUtils;
import gustavo.brilhante.braviandroid.util.ContextUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends NavigationActivity implements NoticiaAdapterListener{

    @BindView(R.id.contentFragmentLayout)
    FrameLayout contentFrameLayout;

    @BindView(R.id.leftRecyclerView)
    RecyclerView leftRecyclerView;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.actionBar)
    GustavoActionBar actionBar;

    @BindView(R.id.searchBar)
    GustavoSearchFloatingBar searchBar;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

//    public String url = "http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml";
    public String url = "http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml";
    public String baseUrl = "", arg = "";

        public String CACHE_ITEM_KEY = "CACHE_ITEM_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        try {
            Reservoir.init(this, 2048); //in bytes
        } catch (IOException e) {
            //failure
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        List<RssFeedItem> list = getCachedList();
        if(list==null) {
            carregarNoticias();
        }else{
            putDataToViews(list);
        }
        actionBar.setBackButtonListener(new GustavoActionBar.BackButtonListener() {
            @Override
            public void onBackButtonClick(ImageButton button) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    hideLeftMenu();
                } else {
                    showLeftMenu();
                }
            }
        });
        actionBar.setRightClickListener(new GustavoActionBar.RightButtonListener() {
            @Override
            public void onRightButtonClick(ImageButton button) {
                if(searchBar.getVisibility()==View.GONE){
//                    searchBar.setVisibility(View.INVISIBLE);
                    AnimUtils.fadeInViewAndVisiBle(searchBar);
                }else{
//                    searchBar.setVisibility(View.VISIBLE);
                    AnimUtils.fadeOutViewAndGone(searchBar);
                }
            }
        });
        searchBar.setText(url);
        searchBar.setSearchButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = searchBar.getText();
                carregarNoticias();
            }
        });

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarNoticias();
            }
        });
    }

    public void showOnlyNew(){
        hideSearchBar();
        hideLeftMenu();
    }

    void showLeftMenu(){
        if (!drawerLayout.isDrawerOpen(Gravity.LEFT))drawerLayout.openDrawer(Gravity.LEFT);
    }

    void hideLeftMenu(){
        if (drawerLayout.isDrawerOpen(Gravity.LEFT))drawerLayout.closeDrawer(Gravity.LEFT);
    }

    void hideSearchBar(){
        if(searchBar.getVisibility()==View.VISIBLE)AnimUtils.fadeOutViewAndGone(searchBar);
    }

    //metodo abstrado dentro de NavigationActivity
    @Override
    public void errorMessageClicked() {
        carregarNoticias();
    }

    void carregarNoticias(){

        if(URLUtil.isHttpUrl(url) || URLUtil.isHttpsUrl(url)) {
            if (url.charAt(url.length() - 1) == '/') url = url.substring(0, url.length() - 1);

            baseUrl = url.substring(0, url.lastIndexOf("/") + 1);
            arg = url.substring(url.lastIndexOf("/") + 1);

            RssRestCliente.build(baseUrl).getFeedItens(arg)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RssFeed>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            setLoading(true, false);
                        }

                        @Override
                        public void onNext(RssFeed rssFeed) {
                            putDataToViews(rssFeed.getChannel().getItemList());

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("erro", "e");
                            ContextUtils.showErrorDialog(getContext(), getStringFromResource(R.string.loading_error));
                            setLoading(false, true);
                            stopRefreshing();
                        }

                        @Override
                        public void onComplete() {
                            setLoading(false, false);
                            hideSearchBar();
                            showLeftMenu();
                            stopRefreshing();
                        }
                    });
        }else{
            ContextUtils.showWarningDialog(this, "a URL informada não é válida");
        }

    }

    void putDataToViews(List<RssFeedItem> list){
        setLeftRecyclerView(list);
        if (list.size() > 0) {
            NoticiaFragment fragment = NoticiaFragment.newInstance(list.get(0));
            goToFragment(fragment, false);
            saveListToCache(list);
        }
    }

    boolean saveListToCache(List<RssFeedItem> itens){
        try {
//            Reservoir.put(CACHE_ITEM_KEY, itens);
            InternalStorage.writeObject(this, CACHE_ITEM_KEY, itens);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    List<RssFeedItem> getCachedList(){
//        Type resultType = new TypeToken<List<RssFeedItem>>() {}.getType();

//            return Reservoir.get(CACHE_ITEM_KEY, resultType);
        try {
            return (List<RssFeedItem>) InternalStorage.readObject(this, CACHE_ITEM_KEY);
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }


    }

    void stopRefreshing(){
        if(swiperefresh.isRefreshing())swiperefresh.setRefreshing(false);
    }


    public void setLeftRecyclerView(List<RssFeedItem> feed){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        NoticiaAdapter adapter = new NoticiaAdapter(feed);
        adapter.setListener(this);
        leftRecyclerView.setLayoutManager(layoutManager);
        leftRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onNoticiaClicked(RssFeedItem feedItem) {
        showOnlyNew();
        NoticiaFragment fragment = NoticiaFragment.newInstance(feedItem);
        goToFragment(fragment, false);
    }
}
