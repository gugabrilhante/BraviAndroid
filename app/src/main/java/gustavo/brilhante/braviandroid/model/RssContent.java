package gustavo.brilhante.braviandroid.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Gustavo on 15/12/17.
 */

@Root(name = "content", strict = false)
public class RssContent implements Serializable {

    public RssContent(String url, String medium, String height, String width) {
        this.url = url;
        this.medium = medium;
        this.height = height;
        this.width = width;
    }

    public RssContent() {
    }

    public String getMedium() {
        return medium;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getUrl() {
        return url;
    }

    @Attribute(name = "url",required = false)
    private String url;

    @Attribute(name = "medium",required = false)
    private String medium;

    @Attribute(name = "height",required = false)
    private String height;

    @Attribute(name = "width",required = false)
    private String width;



}
