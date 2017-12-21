package gustavo.brilhante.braviandroid.model;
/**
 * Created by subodhnijsure on 5/4/16.
 */

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;

@Root(name = "item", strict = false)
public class RssFeedItem implements Serializable {


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return descripcion;
    }

    public String getPublicationDate() {
        return publicationDate;
    }



    public RssContent getContent() {
        return content;
    }

    @Element(name = "title", required = false)
    private String title;

    @Element(name = "content", required = false)
    @Namespace(prefix = "media")
    private RssContent content;

/*    @Element(name="description")
    @Path("description")
    @Namespace(prefix = "")
    private RssDescription descripcion;*/

    @Path("description")
    @Text(required = false)
    String descripcion;

    @Element(name = "pubDate", required = false)
    private String publicationDate;


    public RssFeedItem() {}


}
