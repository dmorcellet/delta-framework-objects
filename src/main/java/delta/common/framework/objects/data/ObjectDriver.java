package delta.common.framework.objects.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Driver that manages the persistence of a single data objects class.
 * @author DAM
 * @param <E> Type of the data objects to manage.
 */
public class ObjectDriver<E extends Identifiable<Long>>
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Default constructor.
   */
  public ObjectDriver()
  {
    // Nothing to do !
  }

  /**
   * Load an object from the data source.
   * @param primaryKey Identifying key for the targeted object.
   * @return The loaded object or <code>null</code> if not found.
   */
  public E getByPrimaryKey(Long primaryKey)
  {
    return null;
  }

  /**
   * Partially load an object from the data source (that is: only load the main
   * attributes of the object, and not other fields or aggregated objects).
   * @param primaryKey Identifying key for the targeted object.
   * @return The loaded object or <code>null</code> if not found.
   */
  public E getPartialByPrimaryKey(Long primaryKey)
  {
    return getByPrimaryKey(primaryKey);
  }

  /**
   * Get all the objects of the managed class.
   * @return a list of such objects.
   */
  public List<E> getAll()
  {
  	return new ArrayList<E>();
  }

  /**
   * Get a list of objects of the managed class, designated by
   * their primary keys.
   * @param primaryKeys List of primary keys to use.
   * @return A list of objects. If an object cannot be found, it is not
   * put in the list, so that the size of the returned list may be less than
   * the size of the given primary keys list.
   */
  public List<E> getByPrimaryKeyList(List<Long> primaryKeys)
  {
    List<E> list=new ArrayList<E>();
    Long l;
    E element;
    for(Iterator<Long> it=primaryKeys.iterator();it.hasNext();)
    {
      l=it.next();
      element=getByPrimaryKey(l);
      if (element!=null)
      {
        list.add(element);
      }
      else
      {
        _logger.error("Object not found : "+l.longValue());
      }
    }
    return list;
  }

  /**
   * Get the primary keys of the objects related to object whose primary
   * key is <code>primaryKey</code> using the designated relation.
   * @param relationName Name of the relation to use.
   * @param primaryKey Primary key of the root object.
   * @return A list of primary keys.
   */
  public List<Long> getRelatedObjectIDs(String relationName, Long primaryKey)
  {
    return new ArrayList<Long>();
  }

  /**
   * Get the primary keys of the objects that belong to the
   * designated set, using the given parameters (the number and types of
   * the parameters depend on the nature of the designated set).
   * @param setID Name of the set to use.
   * @param parameters Parameters for this set.
   * @return A list of primary keys.
   */
  public List<Long> getObjectIDsSet(String setID, Object[] parameters)
  {
    return new ArrayList<Long>();
  }

  /**
   * Create an object in the managed persistence system.
   * @param objectToCreate Object to create.
   */
  public void create(E objectToCreate)
  {
    // Nothing to do !
  }

  /**
   * Update an object in the managed persistence system.
   * @param objectToUpdate Object to create.
   */
  public void update(E objectToUpdate)
  {
    // Nothing to do !
  }

  /**
   * Delete an object in the managed persistence system.
   * @param primaryKey Primary of the object to delete.
   */
  public void delete(long primaryKey)
  {
    // Nothing to do !
  }
}
