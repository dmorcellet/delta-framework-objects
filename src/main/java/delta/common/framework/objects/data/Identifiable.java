package delta.common.framework.objects.data;

import java.io.Serializable;

/**
 * Interface of an object that has a technical identifier.
 * @author DAM
 * @param <T> Type of identifier.
 */
public interface Identifiable<T extends Serializable>
{
  /**
   * Get the identifier.
   * @return an identifier.
   */
  T getPrimaryKey();
}
