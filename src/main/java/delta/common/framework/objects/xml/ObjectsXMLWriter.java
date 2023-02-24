package delta.common.framework.objects.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.framework.objects.data.Identifiable;
import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;

/**
 * Writes objects to XML files.
 * @param <E> Type of managed objects.
 * @author DAM
 */
public class ObjectsXMLWriter<E extends Identifiable<Long>>
{
  private ObjectXMLWriter<E> _writer;

  /**
   * Constructor.
   * @param writer Specific writer for objects.
   */
  public ObjectsXMLWriter(ObjectXMLWriter<E> writer)
  {
    _writer=writer;
  }

  /**
   * Write a file with objects.
   * @param toFile Output file.
   * @param objects Registry of objects to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeObjectsFile(File toFile, List<E> objects)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeObjects(hd,objects);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private void writeObjects(TransformerHandler hd, List<E> objects) throws SAXException
  {
    AttributesImpl emptyAttrs=new AttributesImpl();
    hd.startElement("","",ObjectsXMLConstants.OBJECTS_TAG,emptyAttrs);
    for(E object : objects)
    {
      writeObject(hd,object);
    }
    hd.endElement("","",ObjectsXMLConstants.OBJECTS_TAG);
  }

  private void writeObject(TransformerHandler hd, E object) throws SAXException
  {
    AttributesImpl objectAttrs=new AttributesImpl();
    // Identifier
    Long id=object.getPrimaryKey();
    if (id!=null)
    {
      objectAttrs.addAttribute("","",ObjectsXMLConstants.ID_ATTR,XmlWriter.CDATA,id.toString());
    }
    if (_writer!=null)
    {
      _writer.writeMainAttributes(hd,objectAttrs,object);
    }
    hd.startElement("","",ObjectsXMLConstants.OBJECT_TAG,objectAttrs);
    if (_writer!=null)
    {
      _writer.writeChildTags(hd,object);
    }
    hd.endElement("","",ObjectsXMLConstants.OBJECT_TAG);
  }
}
