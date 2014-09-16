package delta.common.framework.objects.sql;

import java.sql.Connection;

import delta.common.framework.objects.data.Identifiable;
import delta.common.framework.objects.data.ObjectsConnector;

/**
 * JDBC-based objects driver.
 * @author DAM
 * @param <E> Type of the data objects to manage.
 */
public class ObjectSqlDriver<E extends Identifiable<Long>> extends ObjectsConnector<E>
{
  private Connection _connection;
  private DatabaseType _dbType;

  protected ObjectSqlDriver()
  {
    // Nothing !!
  }

  protected boolean usesHSQLDB()
  {
    return (_dbType==DatabaseType.HSQLDB);
  }

  /**
   * Set the connection to use with this driver.
   * @param c Connection to use.
   * @param dbType Database type.
   */
  public void setConnection(Connection c, DatabaseType dbType)
  {
    if(c!=_connection)
    {
      Connection oldConnection=_connection;
      _connection=c;
      _dbType=dbType;
      connectionChanged(oldConnection, c);
    }
  }

  /**
   * Get the managed connection.
   * @return the managed connection.
   */
  public Connection getConnection()
  {
    return _connection;
  }

  protected void connectionChanged(Connection oldConnection, Connection newConnection)
  {
    destroyPreparedStatements(oldConnection);
    buildPreparedStatements(newConnection);
  }

  protected void destroyPreparedStatements(Connection oldConnection)
  {
    // Nothing to do !
  }

  protected void buildPreparedStatements(Connection newConnection)
  {
    // Nothing to do !
  }
}
