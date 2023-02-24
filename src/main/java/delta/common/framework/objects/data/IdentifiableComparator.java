package delta.common.framework.objects.data;

import java.util.Comparator;

/**
 * Comparator for Identifiables, using their primary key.
 * @param <E> Type of managed identifiables.
 * @author DAM
 */
public class IdentifiableComparator<E extends Identifiable<Long>> implements Comparator<E>
{

  @Override
  public int compare(E o1, E o2)
  {
    Long p1=o1.getPrimaryKey();
    Long p2=o2.getPrimaryKey();
    return Long.compare(p1.longValue(),p2.longValue());
  }
}
