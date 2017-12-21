package gustavo.brilhante.braviandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import gustavo.brilhante.braviandroid.R;
import gustavo.brilhante.braviandroid.base.BaseFragment;
import gustavo.brilhante.braviandroid.model.RssFeedItem;

public class NoticiaFragment extends BaseFragment {
    private static final String RSS_FEED_ITEM = "RssFeedItem";

    @BindView(R.id.tituloTextView)    TextView tituloTextView;
    @BindView(R.id.dataTextView)      TextView dataTextView;
    @BindView(R.id.descricaoTextView) TextView descricaoTextView;

    @BindView(R.id.noticiaIcone) ImageView noticiaIcone;

    private RssFeedItem rssFeedItem;

    private Unbinder unbinder;

    View rootView;

    public NoticiaFragment() {
    }

    public static NoticiaFragment newInstance(RssFeedItem item) {
        NoticiaFragment fragment = new NoticiaFragment();
        Bundle args = new Bundle();
        args.putSerializable(RSS_FEED_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rssFeedItem = (RssFeedItem) getArguments().getSerializable(RSS_FEED_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_noticia, container, false);
        this.unbinder = ButterKnife.bind(NoticiaFragment.this, rootView);

        tituloTextView.setText(rssFeedItem.getTitle());
        dataTextView.setText(rssFeedItem.getPublicationDate());
        if(rssFeedItem.getDescription()!=null)descricaoTextView.setText(rssFeedItem.getDescription());
        if(rssFeedItem.getContent()!=null) {
            noticiaIcone.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(rssFeedItem.getContent().getUrl()).into(noticiaIcone);
        }else{
            noticiaIcone.setVisibility(View.GONE);
        }

        return rootView;
    }

}
