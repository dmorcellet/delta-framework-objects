package delta.common.framework.objects.data;

/**
 * Base class for data objects.
 * @author DAM
 * @param <E> Type of the data object.
 */
public abstract class DataObject<E extends Identifiable<Long>> implements Identifiable<Long>
{
  private Long _primaryKey;

  /**
   * Default constructor.
   */
  public DataObject()
  {
    // Nothing to do !
  }

  /**
   * Get the primary key of this object.
   * @return the primary key of this object.
   */
  public Long getPrimaryKey()
  {
    return _primaryKey;
  }

  /**
   * Set the primary key of this object.
   * @param primaryKey Primary key to set.
   */
  public void setPrimaryKey(Long primaryKey)
  {
    _primaryKey=primaryKey;
  }

  /**
   * Get a readable label for this object.
   * @return a readable string.
   */
  public String getLabel()
  {
    return "";
  }

  /**
   * Get the class name for this object.
   * @return the class name for this object.
   */
  public String getClassName()
  {
    return null;
  }

  /**
   * Indicates if the given key is not null.
   * @param key Key to test.
   * @return <code>true</code> if it is not null, <code>false</code> otherwise.
   */
  public static boolean isNotNull(Long key)
  {
    return ((key!=null) && (key.longValue()!=0)); 
  }

  /**
   * Indicates if the given keys are equal or not.
   * @param key1 One key.
   * @param key2 Another key.
   * @return <code>true</code> if they are, <code>false</code> otherwise.
   */
  public static boolean keysAreEqual(Long key1, Long key2)
  {
    if (key1!=null)
    {
      if (key2!=null)
      {
        return key1.longValue()==key2.longValue();
      }
      return false;
    }
    return key2==null;
  }
}
