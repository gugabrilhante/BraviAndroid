package gustavo.brilhante.braviandroid.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import gustavo.brilhante.braviandroid.R;
import gustavo.brilhante.braviandroid.model.RssFeedItem;

/**
 * Created by Gustavo on 15/12/17.
 */

public class NoticiaHolder extends RecyclerView.ViewHolder {

    TextView tituloTextView, dataTextView, descricaoTextView;

    ImageView listIcon;

    Context context;

    public NoticiaHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        tituloTextView    = (TextView) itemView.findViewById(R.id.tituloTextView);
        dataTextView      = (TextView) itemView.findViewById(R.id.dataTextView);
        descricaoTextView = (TextView) itemView.findViewById(R.id.descricaoTextView);

        listIcon          = (ImageView) itemView.findViewById(R.id.listIcon);
    }

    public void bind(RssFeedItem rssFeedItem){
        tituloTextView.setText(rssFeedItem.getTitle());
        dataTextView.setText(rssFeedItem.getPublicationDate());
        if(rssFeedItem.getDescription()!=null)descricaoTextView.setText(rssFeedItem.getDescription());
        if(rssFeedItem.getContent()!=null) {
            listIcon.setVisibility(View.VISIBLE);
            Picasso.with(context).load(rssFeedItem.getContent().getUrl()).into(listIcon);
        }else{
            listIcon.setVisibility(View.GONE);
        }
    }


    public static NoticiaHolder build(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_menu, parent, false);
        NoticiaHolder holder = new NoticiaHolder(view, parent.getContext());
        return holder;
    }
}
