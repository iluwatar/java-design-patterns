package com.iluwater.twostepview;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;




public class XsltTransformer {
    public static void main(String[] args) {
        String xslFile ="coursetostudent.xsl";
        String inputFile = "course.xml";
        String outputFile = "students.xml";

        StreamSource xsl = new StreamSource(new File(xslFile));
        StreamSource input = new StreamSource(new File(inputFile));
        StreamResult output = new StreamResult(new File(outputFile));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer trans;
        try{
            trans = transformerFactory.newTransformer(xsl);
            trans.transform(input,output);
        } catch (TransformerConfigurationException e){
            e.printStackTrace();
        } catch (TransformerException e){
            e.printStackTrace();
        }

    }
}
