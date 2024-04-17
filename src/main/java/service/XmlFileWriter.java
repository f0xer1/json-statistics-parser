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

    public void write(Set<Map.Entry<String, Long>> map, String attribute) {
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
            writeXmlToFile(document, attribute);
        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        }

    }

    private void appendChildWithTextNode(Document document, Element parent, String childName, String textContent) {
        Element child = document.createElement(childName);
        child.appendChild(document.createTextNode(textContent));
        parent.appendChild(child);
    }

    private void writeXmlToFile(Document document, String attribute) throws TransformerException {
        TransformerFactory.newInstance().newTransformer()
                .transform(new DOMSource(document), new StreamResult(
                        "src/main/resources/xml/" + "statistics_by_"
                                + attribute.toLowerCase() + ".xml"));
    }
}