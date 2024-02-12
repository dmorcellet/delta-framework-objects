package delta.common.framework.objects.xml;

import java.io.File;

import delta.common.framework.objects.data.Identifiable;
import delta.common.framework.objects.data.ObjectsManager;
import delta.common.framework.objects.data.ObjectsSource;

/**
 * Source for objects persisted in XML.
 * @author DAM
 */
public class XmlObjectsSource extends ObjectsSource
{
  private File _rootDir;

  /**
   * Constructor.
   * @param rootDir Root directory.
   */
  public XmlObjectsSource(File rootDir)
  {
    _rootDir=rootDir;
  }

  /**
   * Get the file to use for a given class name.
   * @param className Class name.
   * @return A file.
   */
  public File getXmlFileForClass(String className)
  {
    String filename=className+".xml";
    return new File(_rootDir,filename);
  }

  /**
   * Start:
   * <ul>
   * <li>Load all objects
   * </ul>
   */
  public void start()
  {
    for(Class<?> managedClass : getManagedClasses())
    {
      ObjectsManager<?> objectsMgr=getRawManager(managedClass);
      objectsMgr.loadAll();
    }
  }

  /**
   * Close this source.
   */
  public void close()
  {
    // Nothing!
  }

  /**
   * Register a new class.
   * @param c Class to manage.
   * @param driver Associated driver.
   */
  public <E extends Identifiable<Long>> void addClass(Class<E> c, ObjectXmlDriver<E> driver)
  {
    super.addClass(c);
    ObjectsManager<E> objectsMgr=getManager(c);
    objectsMgr.setDriver(driver);
    driver.setObjectsManager(objectsMgr);
  }
}
