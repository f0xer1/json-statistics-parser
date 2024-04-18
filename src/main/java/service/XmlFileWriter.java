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

public class XmlFileWriter {

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

    private void appendChildWithTextNode(Document document, Element parent, String childName, String textContent) {
        Element child = document.createElement(childName);
        child.appendChild(document.createTextNode(textContent));
        parent.appendChild(child);
    }

    private String writeXmlToFile(Document document, String attribute, String directoryPath) throws TransformerException {
        String fileName = "statistics_by_" + attribute.toLowerCase() + ".xml";
        TransformerFactory.newInstance().newTransformer()
                .transform(new DOMSource(document), new StreamResult(directoryPath + "/" + fileName));
        return "Successfully created statistics file '" + fileName + "' in directory: " + directoryPath;

    }
}