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
package iluwater.twostepview;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.*;


/**
 * In this class,it is the first stage of the two-step view pattern which produced page in logical representation.
 * It transforms the input XML document (couse.xml) with XSLT stylesheet (coursetostudent.xsl) to describe
 * how the course data is transformed into another XML document (students.xml) that uses the formatting of
 * remove the course title and map each student's personal details (userID, firstName, lastName and DOB).
 */
public class XsltTransformer {

    /**
     * @author Stephanie Sun
     * @param args transform XML to another XML with XSLT
     */
    public static void main(String[] args) {

        // Files that requires for the transformation process
        String xslFile ="two-step-view/src/main/xml.xslt/coursetostudent.xsl";
        String inputFile = "two-step-view/src/main/xml.xslt/course.xml";
        String outputFile = "two-step-view/src/main/xml.xslt/students.xml";

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
