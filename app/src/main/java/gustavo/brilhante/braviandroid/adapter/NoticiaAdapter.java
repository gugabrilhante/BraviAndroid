package gustavo.brilhante.braviandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import gustavo.brilhante.braviandroid.adapter.holder.NoticiaHolder;
import gustavo.brilhante.braviandroid.model.RssFeedItem;

/**
 * Created by Gustavo on 15/12/17.
 */

public class NoticiaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    NoticiaAdapterListener listener;

    private List<RssFeedItem> rssFeedItems;

    public NoticiaAdapter(List<RssFeedItem> rssFeedItems) {
        this.rssFeedItems = rssFeedItems;
    }

    public void setListener(NoticiaAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NoticiaHolder.build(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            NoticiaHolder noticiaHolder = (NoticiaHolder) holder;
            noticiaHolder.bind(rssFeedItems.get(position));
            noticiaHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener!=null){
                            listener.onNoticiaClicked(rssFeedItems.get(position));
                        }
                    }
            });

    }

    @Override
    public int getItemCount() {
        return rssFeedItems.size();
    }
}
