package delta.common.framework.objects.data;

/**
 * Proxy for a data object.
 * @author DAM
 * @param <E> Type of the data object to manage.
 */
public class DataProxy<E extends Identifiable<Long>>
{
  private Long _primaryKey;
  private E _target;
  private ObjectSource<E> _source;

  /**
   * Full constructor.
   * @param primaryKey Object identifier.
   * @param source Parent objects source.
   */
  public DataProxy(Long primaryKey, ObjectSource<E> source)
  {
    _primaryKey=primaryKey;
    _source=source;
    if (_source==null)
    {
      throw new AssertionError("source is null when building a "+getClass().getName()+" proxy !");
    }
  }

  /**
   * Get the data object managed by this proxy (load it if necessary).
   * @return a data object or <code>null</code>.
   */
  public E getDataObject()
  {
    if ((_target==null) && (_primaryKey!=null))
    {
      _target=_source.load(_primaryKey);
    }
    return _target;
  }

  /**
   * Get the objects source of this proxy.
   * @return the objects source of this proxy.
   */
  public ObjectSource<E> getSource()
  {
  	return _source;
  }

  /**
   * Get the primary key of the managed object.
   * @return the primary key of the managed object.
   */
  public Long getPrimaryKey()
  {
    return _primaryKey;
  }
}
