package delta.common.framework.objects.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.w3c.dom.Element;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.framework.objects.data.Identifiable;

/**
 * Default implementation for a XML reader/writer.
 * @param <E> Type of managed objects.
 * @author DAM
 */
public class DefaultXMLIO<E extends Identifiable<Long>> implements ObjectXMLWriter<E>,ObjectXMLReader<E>
{
  @Override
  public E readObject(Element tag, Long id)
  {
    return null;
  }

  @Override
  public void writeMainAttributes(TransformerHandler hd, AttributesImpl objectAttrs, E object)
  {
    // Nothing!
  }

  @Override
  public void writeChildTags(TransformerHandler hd, E object)
  {
    // Nothing!
  }
}
