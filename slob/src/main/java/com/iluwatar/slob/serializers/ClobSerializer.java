package com.iluwatar.slob.serializers;

import com.iluwatar.slob.lob.Forest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Creates a Serializer that uses Character based serialization and deserialization of objects graph
 * to and from XML Representation.
 */
public class ClobSerializer extends LobSerializer {

  public static final String TYPE_OF_DATA_FOR_DB = "TEXT";

  public ClobSerializer() throws SQLException {
    super(TYPE_OF_DATA_FOR_DB);
  }

  /**
   * Converts the input node to its XML String Representation
   *
   * @param node XML Node that is to be converted to string
   * @return String representation of XML parsed from the Node
   * @throws TransformerException If any issues occur in Transformation from Node to XML
   */
  private static String elementToXmlString(Element node) throws TransformerException {
    StringWriter sw = new StringWriter();
    Transformer t = TransformerFactory.newDefaultInstance().newTransformer();
    t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    t.setOutputProperty(OutputKeys.INDENT, "yes");
    t.transform(new DOMSource(node), new StreamResult(sw));
    return sw.toString();
  }

  /**
   * Serialize the input object graph to its XML Representation using DOM Elements
   *
   * @param forest Object which is to be serialized
   * @return Serialized object
   * @throws ParserConfigurationException If any issues occur in parsing input object
   * @throws TransformerException         If any issues occur in Transformation from Node to XML
   */
  @Override
  public Object serialize(Forest forest) throws ParserConfigurationException, TransformerException {
    Element xmlElement = forest.toXmlElement();
    return elementToXmlString(xmlElement);
  }

  /**
   * Deserialize the input XML string using DOM Parser and return its Object Graph Representation
   *
   * @param toDeserialize Input Object to De-serialize
   * @return Deserialized Object
   * @throws ParserConfigurationException If any issues occur in parsing input object
   * @throws IOException                  if any issues occur during reading object
   * @throws SAXException                 If any issues occur in Transformation from Node to XML
   */
  @Override
  public Forest deSerialize(Object toDeserialize)
      throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance()
        .newDocumentBuilder();
    var stream = new ByteArrayInputStream(toDeserialize.toString().getBytes());
    Document parsed = documentBuilder.parse(stream);
    Forest forest = new Forest();
    forest.createObjectFromXml(parsed);
    return forest;
  }
}
