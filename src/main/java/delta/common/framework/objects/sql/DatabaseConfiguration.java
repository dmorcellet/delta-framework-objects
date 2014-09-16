package delta.common.framework.objects.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Access to database parameters.
 * @author DAM
 */
public class DatabaseConfiguration
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private String _dbType;
  private String _jdbcDriver;
  private String _dbName;
  private String _jdbcUrl;
  private String _jdbcUser;
  private String _jdbcPassword;

  /**
   * Constructor.
   * @param propertiesPath Path to database properties.
   */
  public DatabaseConfiguration(String propertiesPath)
  {
    InputStream is=getClass().getClassLoader().getResourceAsStream(propertiesPath);
    Properties props=new Properties();
    try
    {
      props.load(is);
    }
    catch(IOException ioe)
    {
      _logger.error("",ioe);
    }
    loadFromProperties(props);
  }
  
  private void loadFromProperties(Properties props)
  {
    _dbType=props.getProperty("database.type","MySQL");
    _dbName=props.getProperty("database.defaultName","mydb");
    _jdbcDriver=props.getProperty(_dbType+".jdbc.driver","com.mysql.jdbc.Driver");
    _jdbcUrl=props.getProperty(_dbType+".jdbc.urlPattern","jdbc:mysql://localhost:3306/${dbName}");
    _jdbcUser=props.getProperty(_dbType+".jdbc.user","scott");
    _jdbcPassword=props.getProperty(_dbType+".jdbc.password","tiger");
    if (_logger.isInfoEnabled())
    {
      _logger.info("_dbType="+_dbType);
      _logger.info("_jdbcDriver="+_jdbcDriver);
      _logger.info("_jdbcUrl="+_jdbcUrl);
      _logger.info("_jdbcUser="+_jdbcUser);
      _logger.info("_jdbcPassword="+_jdbcPassword);
    }
  }

  /**
   * Get the database type.
   * @return the database type.
   */
  public String getDBType()
  {
    return _dbType;
  }

  /**
   * Get the JDBC driver class.
   * @return the JDBC driver class.
   */
  public String getJdbcDriver()
  {
    return _jdbcDriver;
  }

  /**
   * Get the default database name.
   * @return the default database name.
   */
  public String getDefaultDbName()
  {
    return _dbName;
  }

  /**
   * Get the pattern for JDBC database URLs.
   * @return the pattern for JDBC database URLs.
   */
  public String getJdbcUrlPattern()
  {
    return _jdbcUrl;
  }

  /**
   * Get a JDBC database URL.
   * @param dbName Database name.
   * @return A JDBC database URL.
   */
  public String getJdbcUrl(String dbName)
  {
    String jdbcUrl=getJdbcUrlPattern();
    String url=jdbcUrl.replace("${dbName}",dbName);
    return url;
  }
  
  /**
   * Get the JDBC user name.
   * @return the JDBC user name.
   */
  public String getJdbcUser()
  {
    return _jdbcUser;
  }

  /**
   * Get the password of JDBC user.
   * @return the password of JDBC user.
   */
  public String getJdbcPassword()
  {
    return _jdbcPassword;
  }
}
