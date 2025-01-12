package delta.common.framework.objects.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Objects manager.
 * Manages all the objects of a class in a single data location.
 * @author DAM
 * @param <E> Type of the data objects to manage.
 */
public class ObjectsManager<E extends Identifiable<Long>>
{
  /**
   * Number of load requests.
   */
  public static long _nbGetRequests=0;
  private ObjectsCache<E> _cache;
  private ObjectsConnector<E> _driver;

  /**
   * Constructor.
   */
  public ObjectsManager()
  {
    // By default: no cache!
    _cache=null;
  }

  /**
   * Use cache or not.
   * @param useCache <code>true</code> to use cache.
   */
  public void useCache(boolean useCache)
  {
    if (useCache)
    {
      _cache=new ObjectsCache<E>();
    }
    else
    {
      _cache=null;
    }
  }

  /**
   * Get the managed cache.
   * @return the managed cache.
   */
  public ObjectsCache<E> getCache()
  {
    return _cache;
  }

  /**
   * Get the driver.
   * @return the driver.
   */
  public ObjectsConnector<E> getDriver()
  {
    return _driver;
  }

  /**
   * Set driver.
   * @param driver Driver to use to create/read/update/delete objects.
   */
  public void setDriver(ObjectsConnector<E> driver)
  {
    _driver=driver;
  }

  /**
   * Build a proxy for an object of this source.
   * @param key Identifier for the proxied object.
   * @return A proxy.
   */
  public DataProxy<E> buildProxy(Long key)
  {
    return new DataProxy<E>(key,this);
  }

  /**
   * Create an object in the managed persistence system.
   * @param object Object to create.
   */
  public void create(E object)
  {
    _driver.create(object);
    if (_cache!=null)
    {
      _cache.put(object);
    }
  }

  /**
   * Update an object in the managed persistence system.
   * @param object Object to update.
   */
  public void update(E object)
  {
    _driver.update(object);
    if (_cache!=null)
    {
      _cache.put(object);
    }
  }

  /**
   * Delete an object in the managed persistence system.
   * @param primaryKey Primary of the object to delete.
   */
  public void delete(Long primaryKey)
  {
    _driver.delete(primaryKey);
    if (_cache!=null)
    {
      _cache.remove(primaryKey);
    }
  }

  /**
   * Load an object (from cache or from the managed persistence system).
   * @param primaryKey Identifying key for the targeted object.
   * @return The loaded object or <code>null</code> if not found.
   */
  public E load(Long primaryKey)
  {
    E ret=null;
    if (_cache!=null)
    {
      ret=_cache.get(primaryKey);
    }
    if (ret==null)
    {
      if(_driver!=null)
      {
        ret=_driver.getByPrimaryKey(primaryKey);
        _nbGetRequests++;
        if ((ret!=null) && (_cache!=null))
        {
          _cache.put(ret);
        }
      }
    }
    else
    {
      //System.out.println("Cache hit for : "+ret);
    }
    return ret;
  }

  /**
   * Partially load an object from the managed persistence system
   * (that is: only load the main attributes of the object, and not
   * other fields or aggregated objects).
   * @param primaryKey Identifying key for the targeted object.
   * @return The loaded object or <code>null</code> if not found.
   */
  public E loadPartial(Long primaryKey)
  {
    E ret=null;
    if(_driver!=null)
    {
      ret=_driver.getPartialByPrimaryKey(primaryKey);
    }
    return ret;
  }

  /**
   * Get all the objects of the managed class.
   * @return a list of such objects.
   */
  public List<E> loadAll()
  {
  	List<E> ret=new ArrayList<E>();
  	if(_driver!=null)
    {
  	  ret=_driver.getAll();
      if (_cache!=null)
      {
        _cache.putAll(ret);
        //System.out.println("Cache/loadAll : "+_driver.getClass().getName());
      }
    }
  	return ret;
  }

  /**
   * Get the objects that belong to the designated set, using the given parameters
   * (the number and types of the parameters depend on the nature of the designated
   * set).
   * @param setID Name of the set to use.
   * @param parameters Parameters for this set.
   * @return A list of such objects.
   */
  public List<E> loadObjectSet(String setID, Object[] parameters)
  {
  	List<E> ret=null;
    if(_driver!=null)
    {
      List<Long> ids=_driver.getObjectIDsSet(setID, parameters);
      ret=loadAll(ids);
    }
    return ret;
  }

  /**
   * Get a series of objects of the managed class, designated by their primary key.
   * @param primaryKeys Primary keys of the objects to get.
   * @return a list of these objects.
   */
  public List<E> loadAll(List<Long> primaryKeys)
  {
  	List<E> ret=new ArrayList<E>();
  	if(_driver!=null)
    {
      int nb=primaryKeys.size();
      E o=null;
      Long id;
      for(int i=0;i<nb;i++)
      {
        id=primaryKeys.get(i);
        o=load(id);
        if(o!=null)
        {
          ret.add(o);
        }
      }
    }
    if (_cache!=null)
    {
      _cache.putAll(ret);
    }
  	return ret;
  }

  /**
   * Get a series of objects of the managed class, designated by their primary key.
   * Each object is partially loaded (see loadPartial for further explanations).
   * @param primaryKeys Primary keys of the objects to get.
   * @return a list of these objects.
   */
  public List<E> loadAllUsingPartials(List<Long> primaryKeys)
  {
    List<E> ret=new ArrayList<E>();
    if(_driver!=null)
    {
      int nb=primaryKeys.size();
      E o=null;
      for(int i=0;i<nb;i++)
      {
        o=_driver.getPartialByPrimaryKey(primaryKeys.get(i));
        if(o!=null)
        {
          ret.add(o);
        }
      }
    }
    return ret;
  }

  /**
   * Get the objects related to object whose primary
   * key is <code>primaryKey</code> using the designated relation.
   * @param relationName Name of the relation to use.
   * @param primaryKey Primary key of the root object.
   * @return A list of such objects.
   */
  public List<E> loadRelation(String relationName, Long primaryKey)
  {
  	List<E> ret=null;
    if(_driver!=null)
    {
      List<Long> ids=_driver.getRelatedObjectIDs(relationName, primaryKey);
      ret=loadAll(ids);
    }
    return ret;
  }

  /**
   * Get the objects related to object whose primary
   * key is <code>primaryKey</code> using the designated relation.
   * Each object is partially loaded (see loadPartial for further explanations).
   * @param relationName Name of the relation to use.
   * @param primaryKey Primary key of the root object.
   * @return A list of such objects.
   */
  public List<E> loadRelationUsingPartials(String relationName, Long primaryKey)
  {
    List<E> ret=null;
    if(_driver!=null)
    {
      List<Long> ids=_driver.getRelatedObjectIDs(relationName, primaryKey);
      ret=loadAllUsingPartials(ids);
    }
    return ret;
  }
}
