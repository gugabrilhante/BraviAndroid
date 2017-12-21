package gustavo.brilhante.braviandroid.model;
/**
 * Created by subodhnijsure on 5/4/16.
 */

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "channel", strict = false)
public class RssChannel implements Serializable {
    @ElementList(name = "item", type = RssFeedItem.class ,required = false, inline = true, entry = "item")
    private List<RssFeedItem> itemList;


    public List<RssFeedItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<RssFeedItem> itemList) {
        this.itemList = itemList;
    }

    public RssChannel(List<RssFeedItem> mFeedItems) {
        this.itemList = mFeedItems;
    }

    public RssChannel() {
    }
}
