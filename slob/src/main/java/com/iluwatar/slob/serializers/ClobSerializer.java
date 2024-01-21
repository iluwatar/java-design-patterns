package com.iluwatar.slob.serializers;

import com.iluwatar.slob.lob.Forest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;

public class ClobSerializer extends LobSerializer {

    public static final String typeOfDataForDB = "CLOB";

    public ClobSerializer() throws SQLException {
        super(typeOfDataForDB);
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
     * @param forest
     * @return
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    @Override
    public Object serialize(Forest forest)
            throws ParserConfigurationException, TransformerException {
        Element xmlElement = forest.toXmlElement();
        return elementToXmlString(xmlElement);
    }

    /**
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    @Override
    public Forest deSerialize(Object toDeSerialize) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
        var stream = new ByteArrayInputStream(toDeSerialize.toString().getBytes());
        Document parsed = documentBuilder.parse(stream);
        Forest forest = new Forest();
        forest.createObjectFromXml(parsed);
        return forest;
    }
}
