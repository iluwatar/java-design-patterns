package com.iluwatar.slob.serializers;

import com.iluwatar.slob.lob.Customer;
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

public class ClobSerializer extends LobSerializer {

  public ClobSerializer() throws SQLException {
    super();
  }

  /**
   * @param node
   * @return
   * @throws TransformerException
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
   * @param customer
   * @return
   * @throws ParserConfigurationException
   * @throws TransformerException
   */
  @Override
  public Object serialize(Customer customer)
      throws ParserConfigurationException, TransformerException {
    Document xmlDoc = getXmlDoc();
    return elementToXmlString(customer.departmentsToXmlElement(xmlDoc));
  }

  /**
   * @return
   * @throws ParserConfigurationException
   */
  private Document getXmlDoc() throws ParserConfigurationException {
    return DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().newDocument();
  }

  /**
   * @param toDeSerialize
   * @return
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   */
  @Override
  public Customer deSerialize(Object toDeSerialize)
      throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance()
        .newDocumentBuilder();
    var stream = new ByteArrayInputStream(toDeSerialize.toString().getBytes());
    Document parse = documentBuilder.parse(stream);
    Customer customer = new Customer();
    return customer.readDepartments(parse.getDocumentElement());
  }
}
