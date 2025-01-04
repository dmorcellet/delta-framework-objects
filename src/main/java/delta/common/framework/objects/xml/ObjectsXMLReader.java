package delta.common.framework.objects.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import delta.common.framework.objects.data.Identifiable;
import delta.common.utils.xml.DOMParsingTools;

/**
 * Read objects from XML files.
 * @param <E> Type of managed objects.
 * @author DAM
 */
public class ObjectsXMLReader<E extends Identifiable<Long>>
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ObjectsXMLReader.class);

  private ObjectXMLReader<E> _reader;

  /**
   * Constructor.
   * @param reader Specific object reader.
   */
  public ObjectsXMLReader(ObjectXMLReader<E> reader)
  {
    _reader=reader;
  }

  /**
   * Read objects from a file.
   * @param fromFile Output file.
   * @return a list of loaded objects.
   */
  public List<E> readObjectsFile(File fromFile)
  {
    Element root=DOMParsingTools.parse(fromFile);
    if (root==null)
    {
      throw new IllegalStateException("Cannot read XML file: "+fromFile);
    }

    LOGGER.info("Reading objects file: {}", fromFile);

    List<E> ret=new ArrayList<E>();
    // Objects
    List<Element> objectTags=DOMParsingTools.getChildTagsByName(root,ObjectsXMLConstants.OBJECT_TAG);
    LOGGER.debug("Got {} object tags!",Integer.valueOf(objectTags.size()));
    for(Element objectTag : objectTags)
    {
      // Identifier
      long idValue=DOMParsingTools.getLongAttribute(objectTag.getAttributes(),ObjectsXMLConstants.ID_ATTR,-1);
      Long id=(idValue>=0)?Long.valueOf(idValue):null;
      E object=_reader.readObject(objectTag,id);
      if (object!=null)
      {
        ret.add(object);
      }
      else
      {
        LOGGER.warn("Read a null object!");
      }
    }
    LOGGER.debug("Read {} objects!",Integer.valueOf(ret.size()));
    return ret;
  }
}
