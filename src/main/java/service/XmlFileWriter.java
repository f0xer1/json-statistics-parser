package service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Map;
import java.util.Set;

/**
 * The `XmlFileWriter` class is responsible for writing the statistics of a set of JSON files
 * to an XML file.
 */
public class XmlFileWriter {
    /**
     * Writes the statistics of a set of JSON files to an XML file.
     *
     * @param map           the set of statistics entries, where the key is the value and the
     *                      value is the count
     * @param attribute     the attribute used to generate the statistics
     * @param directoryPath the directory path to write the XML file to
     * @return a message indicating whether the file was successfully written or an
     * error message if an exception occurred
     */
    public String write(Set<Map.Entry<String, Long>> map, String attribute, String directoryPath) {
        try {
            Document document = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("statistics");
            document.appendChild(root);
            map.forEach(entry -> {
                Element item = document.createElement("item");
                appendChildWithTextNode(document, item, "value", entry.getKey());
                appendChildWithTextNode(document, item, "count", entry.getValue().toString());
                root.appendChild(item);
            });
            return writeXmlToFile(document, attribute, directoryPath);
        } catch (ParserConfigurationException | TransformerException e) {
            System.err.println("Error processing XML: " + e.getMessage());
        }
        return "The program terminated due to an error";
    }

    /**
     * Appends a child element with the specified name and text content to the
     * given parent element.
     *
     * @param document    the XML document
     * @param parent      the parent element
     * @param childName   the name of the child element
     * @param textContent the text content of the child element
     */
    private void appendChildWithTextNode(Document document, Element parent, String childName, String textContent) {
        Element child = document.createElement(childName);
        child.appendChild(document.createTextNode(textContent));
        parent.appendChild(child);
    }

    /**
     * Writes the given XML document to a file in the specified directory.
     *
     * @param document      the XML document to write
     * @param attribute     the attribute used to generate the statistics
     * @param directoryPath the directory path to write the file to
     * @return a message indicating whether the file was successfully written
     * @throws TransformerException if an error occurs during the XML transformation
     */
    private String writeXmlToFile(Document document, String attribute, String directoryPath) throws TransformerException {
        String fileName = "statistics_by_" + attribute.toLowerCase() + ".xml";
        TransformerFactory.newInstance().newTransformer()
                .transform(new DOMSource(document), new StreamResult(directoryPath + "/" + fileName));
        return "Successfully created statistics file '" + fileName + "' in directory: " + directoryPath;

    }
}