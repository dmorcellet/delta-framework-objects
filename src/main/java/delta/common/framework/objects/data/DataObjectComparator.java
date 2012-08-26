package delta.common.framework.objects.data;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A comparator for <tt>DataObject</tt>s.
 * @author DAM
 * @param <E> Type of the data objects to compare.
 */
public class DataObjectComparator<E extends DataObject<E>> implements Comparator<E>,Serializable
{
  private static final long serialVersionUID=1L;

  /**
   * Compare two data objects, using their label.
   * @param do1 First object.
   * @param do2 Other object.
   * @return see {@link Comparator#compare(Object, Object)} 
   */
  public int compare(E do1, E do2)
  {
    String label1=do1.getLabel();
    String label2=do2.getLabel();
    int ret=label1.compareTo(label2);
    return ret;
  }
}
