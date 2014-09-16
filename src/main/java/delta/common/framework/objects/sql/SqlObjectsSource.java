package delta.common.framework.objects.sql;

import delta.common.framework.objects.data.Identifiable;
import delta.common.framework.objects.data.ObjectsSource;

/**
 * Objects source of a single SQL data location.
 * @author DAM
 */
public abstract class SqlObjectsSource extends ObjectsSource
{
  private String _dbName;
  private ObjectSqlDriverManager _driver;
  private long _nbGetRequests;

  /**
   * Constructor.
   * @param dbName Name of the database to manage.
   */
  public SqlObjectsSource(String dbName)
  {
    _dbName=dbName;
    DatabaseConfiguration cfg=buildDatabaseConfiguration(dbName);
    _driver=new ObjectSqlDriverManager(dbName,cfg);
  }

  protected abstract DatabaseConfiguration buildDatabaseConfiguration(String dbName);

  /**
   * Get the name of the managed database.
   * @return the name of the managed database.
   */
  public String getDbName()
  {
    return _dbName;
  }

  /**
   * Register a new class.
   * @param c Class to manage.
   * @param driver Associated driver.
   */
  public <E extends Identifiable<Long>> void addClass(Class<E> c, ObjectSqlDriver<E> driver)
  {
    super.addClass(c);
    getManager(c).setDriver(driver);
    _driver.addClass(c,driver);
    driver.setObjectSource(this);
  }

  /**
   * Start this manager.
   * @throws Exception if a problem occurs.
   */
  public void start() throws Exception 
  {
    _driver.start();
  }

  /**
   * Get the number of load requests. 
   * @return a number of requests.
   */
  public long getNbGetRequests()
  {
    return _nbGetRequests;
  }

  @Override
  public <E extends Identifiable<Long>> E load(Class<E> c, Long key)
  {
    _nbGetRequests++;
    return super.load(c,key);
  }

  /**
   * Set the flag that drives foreign key checks.
   * @param doCheck <code>true</code> to perform checks, <code>false</code> otherwise.
   */
  public void setForeignKeyChecks(boolean doCheck)
  {
    _driver.setForeignKeyChecks(doCheck);
  }

  /**
   * Close this data source.
   */
  public void close()
  {
    if (_driver!=null)
    {
      _driver.close();
    }
  }
}
