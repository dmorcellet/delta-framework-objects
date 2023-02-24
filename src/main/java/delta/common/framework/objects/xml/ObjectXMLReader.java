package delta.common.framework.objects.xml;

import org.w3c.dom.Element;

import delta.common.framework.objects.data.Identifiable;

/**
 * Interface of an object reader from XML.
 * @param <E> Type of managed objects.
 * @author DAM
 */
public interface ObjectXMLReader<E extends Identifiable<Long>>
{
  /**
   * Read an object from the given tag (DOM mode).
   * @param tag Input tag.
   * @param id Object identifier.
   * @return the loaded object (<code>null</code> values will cause a warn).
   */
  E readObject(Element tag, Long id);
}
