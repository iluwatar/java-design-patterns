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
package com.iluwatar.slob.lob;

import java.io.Serializable;
import java.util.StringJoiner;
import javax.xml.parsers.ParserConfigurationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Application.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plant implements Serializable {

  private String name;
  private String type;

  /**
   * @param xmlDoc
   * @return
   * @throws ParserConfigurationException
   */
  public Element toXmlElement(Document xmlDoc) throws ParserConfigurationException {
    Element root = xmlDoc.createElement(Plant.class.getSimpleName());
    root.setAttribute("name", name);
    root.setAttribute("type", type);
    xmlDoc.appendChild(root);
    return xmlDoc.getDocumentElement();
  }

  public void createObjectFromXml(Node node) {
    NamedNodeMap attributes = node.getAttributes();
    name = attributes.getNamedItem("name").getNodeValue();
    type = attributes.getNamedItem("type").getNodeValue();
  }

  @Override
  public String toString() {
    StringJoiner stringJoiner = new StringJoiner(",");
    stringJoiner.add("Name = " + name);
    stringJoiner.add("Type = " + type);
    return stringJoiner.toString();
  }
}
