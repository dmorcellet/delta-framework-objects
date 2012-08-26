package delta.common.framework.objects.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * Database types.
 * @author DAM
 */
public final class DatabaseType
{
  private static Map<String,DatabaseType> _map;

  private static Map<String,DatabaseType> getMap()
  {
    if (_map==null)
    {
      _map=new HashMap<String,DatabaseType>();
    }
    return _map;
  }

  private static void registerType(DatabaseType type)
  {
    getMap().put(type.getName(),type);
  }

  private String _name;

  /**
   * Name of the MySQL database type.
   */
  public static final String MYSQL_DBNAME="MYSQL";
  /**
   * Constant for the MySQL database type.
   */
  public static final DatabaseType MYSQL=new DatabaseType(MYSQL_DBNAME);
  /**
   * Name of the HSQLDB database type.
   */
  public static final String HSQLDB_DBNAME="HSQLDB";
  /**
   * Constant for the HSQLDB database type.
   */
  public static final DatabaseType HSQLDB=new DatabaseType(HSQLDB_DBNAME);
  /**
   * Name of the ORACLE database type.
   */
  public static final String ORACLE_DBNAME="ORACLE";
  /**
   * Constant for the Oracle database type.
   */
  public static final DatabaseType ORACLE=new DatabaseType(ORACLE_DBNAME);

  /**
   * Private constructor.
   * @param name Name of this type.
   */
  private DatabaseType(String name)
  {
    _name=name;
    registerType(this);
  }

  /**
   * Get the name of this database type.
   * @return the name of this database type.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get a database type by name.
   * @param name Name to search.
   * @return A database type or <code>null</code> if not found.
   */
  public static DatabaseType getDBTypeByName(String name)
  {
    DatabaseType ret=getMap().get(name);
    return ret;
  }
}
