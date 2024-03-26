/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
   * Converts the input node to its XML String Representation.
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
   * Serializes the input object graph to its XML Representation using DOM Elements.
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
   * Deserializes the input XML string using DOM Parser and return its Object Graph Representation.
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
