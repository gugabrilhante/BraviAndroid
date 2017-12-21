package gustavo.brilhante.braviandroid.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.io.Serializable;

/**
 * Created by Gustavo on 17/12/17.
 */

@Root(name = "description")
@Element
public class RssChannelDescription implements Serializable {

    @Text(required=false)
    String descriptionText;

    public String getText()
    {
        return descriptionText;
    }

}
