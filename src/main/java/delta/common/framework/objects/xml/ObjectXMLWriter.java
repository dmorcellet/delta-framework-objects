package delta.common.framework.objects.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.framework.objects.data.Identifiable;

/**
 * Interface of an object writer to XML.
 * @param <E> Type of managed objects.
 * @author DAM
 */
public interface ObjectXMLWriter<E extends Identifiable<Long>>
{
  /**
   * Write object attributes in the main tag for the object.
   * @param hd Output stream.
   * @param objectAttrs Storage for attributes to write.
   * @param object Object to write.
   */
  void writeMainAttributes(TransformerHandler hd, AttributesImpl objectAttrs, E object);

  /**
   * Write child tags for the object, if any.
   * @param hd Output stream.
   * @param object Object to write.
   * @throws Exception if an error occurs.
   */
  void writeChildTags(TransformerHandler hd, E object) throws Exception;
}
