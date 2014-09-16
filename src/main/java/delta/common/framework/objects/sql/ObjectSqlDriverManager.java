package delta.common.framework.objects.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Manages the persistence drivers for an objects source.
 * @author DAM
 */
public class ObjectSqlDriverManager
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();
  private String _dbName;
  private DatabaseConfiguration _databaseCfg;
  private DatabaseType _databaseType;
  private Connection _dbConnection;
  private HashMap<Class<?>,ObjectSqlDriver<?>> _drivers;

  /**
   * Default constructor.
   * @param dbName Database name.
   * @param databaseCfg Database configuration.
   */
  public ObjectSqlDriverManager(String dbName, DatabaseConfiguration databaseCfg)
  {
    _dbName=dbName;
    _databaseCfg=databaseCfg;
    _drivers=new HashMap<Class<?>,ObjectSqlDriver<?>>();
  }

  /**
   * Register a new class and its driver.
   * @param c Class to manage.
   * @param driver Associated driver.
   */
  public void addClass(Class<?> c, ObjectSqlDriver<?> driver)
  {
    _drivers.put(c,driver);
  }

  /**
   * Start this manager.
   * @throws Exception if a problem occurs.
   */
  public void start() throws Exception
  {
    buildConnection();
    for(ObjectSqlDriver<?> driver : _drivers.values())
    {
      driver.setConnection(_dbConnection,_databaseType);
    }
  }

  private void buildConnection() throws Exception
  {
    String dbType=_databaseCfg.getDBType();
    _databaseType=DatabaseType.getDBTypeByName(dbType);
    String driver=_databaseCfg.getJdbcDriver();
    Class.forName(driver);
    String url=_databaseCfg.getJdbcUrl(_dbName);
    String user=_databaseCfg.getJdbcUser();
    String password=_databaseCfg.getJdbcPassword();
    _dbConnection=DriverManager.getConnection(url,user,password);
  }

  /**
   * Set the flag that drives foreign key checks.
   * @param doCheck <code>true</code> to perform checks, <code>false</code> otherwise.
   */
  public void setForeignKeyChecks(boolean doCheck)
  {
    Statement s=null;
    try
    {
      s=_dbConnection.createStatement();
      String sql="SET FOREIGN_KEY_CHECKS="+(doCheck?"1":"0");
      s.execute(sql);
    }
    catch(Exception e)
    {
      _logger.error("",e);
    }
    finally
    {
      if (s!=null)
      {
        try
        {
          s.close();
          s=null;
        }
        catch(Exception e)
        {
          _logger.error("",e);
        }
      }
    }
  }

  /**
   * Get the database type.
   * @return a database type.
   */
  public DatabaseType getDatabaseType()
  {
    return _databaseType;
  }

  /**
   * Terminate this driver.
   */
  public void close()
  {
    try
    {
      if (_dbConnection!=null)
      {
        _dbConnection.close();
      }
      _dbConnection=null;
    }
    catch (SQLException e)
    {
      _logger.error("",e);
    }
  }
}
