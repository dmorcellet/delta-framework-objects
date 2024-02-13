package delta.common.framework.objects.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Objects source of a single data location.
 * @author DAM
 */
public class ObjectsSource
{
  private HashMap<Class<?>,ObjectsManager<?>> _managers;

  /**
   * Constructor.
   */
  public ObjectsSource()
  {
    _managers=new HashMap<Class<?>,ObjectsManager<?>>();
  }

  /**
   * Build a proxy for an object.
   * @param c Class of object to be proxified.
   * @param primaryKey Identifying key for the targeted object.
   * @return A proxy.
   */
  public <E extends Identifiable<Long>> DataProxy<E> buildProxy(Class<E> c, Long primaryKey)
  {
    ObjectsManager<E> mgr=getManager(c);
    return new DataProxy<E>(primaryKey,mgr);
  }

  /**
   * Get the managed classes.
   * @return A list of managed classes.
   */
  public List<Class<?>> getManagedClasses()
  {
    List<Class<?>> ret=new ArrayList<Class<?>>(_managers.keySet());
    return ret;
  }

  /**
   * Get the manager for a class.
   * @param c Class of objects.
   * @return A manager or <code>null</code> if not found.
   */
  @SuppressWarnings("unchecked")
  public <E extends Identifiable<Long>> ObjectsManager<E> getManager(Class<E> c)
  {
    return (ObjectsManager<E>)_managers.get(c);
  }

  /**
   * Get the manager for a class.
   * @param c Class of objects.
   * @return A manager or <code>null</code> if not found.
   */
  public ObjectsManager<?> getRawManager(Class<?> c)
  {
    return _managers.get(c);
  }

  /**
   * Create an object.
   * @param c Class of object to create.
   * @param object Object to create.
   */
  public <E extends Identifiable<Long>> void create(Class<E> c, E object)
  {
    getManager(c).create(object);
  }

  /**
   * Update an object.
   * @param c Class of object to update.
   * @param object Object to update.
   */
  public <E extends Identifiable<Long>> void update(Class<E> c, E object)
  {
    getManager(c).update(object);
  }

  /**
   * Load an object.
   * @param c Class of object to load.
   * @param primaryKey Identifying key for the targeted object.
   * @return The loaded object or <code>null</code> if not found.
   */
  public <E extends Identifiable<Long>> E load(Class<E> c, Long primaryKey)
  {
    return getManager(c).load(primaryKey);
  }

  /**
   * Get all the objects of the managed class.
   * @param c Class of objects to load.
   * @return a list of such objects.
   */
  public <E extends Identifiable<Long>> List<E> loadAll(Class<E> c)
  {
    return getManager(c).loadAll();
  }

  /**
   * Get the objects related to object whose primary
   * key is <code>primaryKey</code> using the designated relation.
   * @param c Class of objects to load.
   * @param relationName Name of the relation to use.
   * @param primaryKey Primary key of the root object.
   * @return A list of such objects.
   */
  public <E extends Identifiable<Long>> List<E> loadRelation(Class<E> c, String relationName, Long primaryKey)
  {
    return getManager(c).loadRelation(relationName,primaryKey);
  }

  /**
   * Get the objects that belong to the designated set, using the given parameters
   * (the number and types of the parameters depend on the nature of the designated
   * set).
   * @param c Class of objects to load.
   * @param setID Name of the set to use.
   * @param parameters Parameters for this set.
   * @return A list of such objects.
   */
  public <E extends Identifiable<Long>> List<E> loadObjectSet(Class<E> c, String setID, Object... parameters)
  {
    return getManager(c).loadObjectSet(setID,parameters);
  }

  /**
   * Delete an object in the managed persistence system.
   * @param c Class of objects to delete.
   * @param primaryKey Primary of the object to delete.
   */
  public <E extends Identifiable<Long>> void delete(Class<E> c, Long primaryKey)
  {
    getManager(c).delete(primaryKey);
  }

  /**
   * Register a new class.
   * @param c Class to manage.
   */
  public <E extends Identifiable<Long>> void addClass(Class<E> c)
  {
    ObjectsManager<E> manager=new ObjectsManager<E>();
    _managers.put(c,manager);
  }

  /**
   * Close this data source.
   */
  public void close()
  {
    // Nothing!
  }
}
