package delta.common.framework.objects.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.common.framework.objects.data.Identifiable;
import delta.common.framework.objects.data.IdentifiableComparator;
import delta.common.framework.objects.data.ObjectsConnector;

/**
 * XML-based objects driver.
 * @author DAM
 * @param <E> Type of the data objects to manage.
 */
public class ObjectXmlDriver<E extends Identifiable<Long>> extends ObjectsConnector<E>
{
  private File _xmlFile;
  private ObjectXMLReader<E> _reader;
  private ObjectXMLWriter<E> _writer;

  /**
   * Constructor.
   * @param xmlFile Storage file.
   * @param reader Specific reader.
   * @param writer Specific writer.
   */
  public ObjectXmlDriver(File xmlFile, ObjectXMLReader<E> reader, ObjectXMLWriter<E> writer)
  {
    _xmlFile=xmlFile;
    _reader=reader;
    _writer=writer;
  }

  @Override
  public List<E> getAll()
  {
    ObjectsXMLReader<E> r=new ObjectsXMLReader<E>(_reader);
    if (_xmlFile.exists())
    {
      List<E> objects=r.readObjectsFile(_xmlFile);
      return objects;
    }
    return new ArrayList<E>();
  }

  /**
   * Save the given objects to the storage file.
   * @param objects Objects to save.
   */
  public void saveAll(List<E> objects)
  {
    ObjectsXMLWriter<E> w=new ObjectsXMLWriter<>(_writer);
    List<E> sortedObjects=new ArrayList<E>(objects);
    Collections.sort(sortedObjects,new IdentifiableComparator<E>());
    w.writeObjectsFile(_xmlFile,sortedObjects);
  }
}
