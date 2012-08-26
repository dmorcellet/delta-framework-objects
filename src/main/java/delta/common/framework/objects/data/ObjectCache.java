package delta.common.framework.objects.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Cache for data objects.
 * @author DAM
 * @param <E> Type of the data objects to manage.
 */
public class ObjectCache<E extends Identifiable<Long>>
{
  private HashMap<Long,E> _cache;

  /**
   * Default constructor.
   */
  public ObjectCache()
  {
    _cache=new HashMap<Long,E>();
  }

  /**
   * Find an object in this cache.
   * @param primaryKey Primary key of the object to get.
   * @return An object or <code>null</code> if not found.
   */
  public E get(Long primaryKey)
  {
    E ret=_cache.get(primaryKey);
    return ret;
  }

  /**
   * Put an object in this cache.
   * @param object Object to put.
   */
  public void put(E object)
  {
    Long id=object.getPrimaryKey();
    _cache.put(id,object);
  }

  /**
   * Put a series of objects in this cache.
   * @param collection Objects to put.
   */
  public void putAll(Collection<E> collection)
  {
    if ((collection!=null) && (collection.size()>0))
    {
      E current;
      for(Iterator<E> it=collection.iterator();it.hasNext();)
      {
        current=it.next();
        put(current);
      }
    }
  }

  /**
   * Remove an object from this cache.
   * @param primaryKey Primary key of the object to remove.
   */
  public void remove(Long primaryKey)
  {
    _cache.remove(primaryKey);
  }
}
