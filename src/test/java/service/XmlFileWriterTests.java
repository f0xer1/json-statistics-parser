package service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class XmlFileWriterTests {
    private final XmlFileWriter xmlFileWriter = new XmlFileWriter();
    private static final String FILE_PATH = "src/test/resources/data/statistics_by_items.xml";
    private static final String ATTRIBUTE = "items";
    private static final String DIRECTORY_PATH = "src/test/resources/data";


    @Test
    @DisplayName("Test writing of valid data")
    void testWriteIsValid() throws ParserConfigurationException, IOException, SAXException {
        Map<String, Long> data = Map.of("A", 5L, "B", 10L);

        xmlFileWriter.write(data.entrySet(), ATTRIBUTE, DIRECTORY_PATH);

        Path path = Paths.get(FILE_PATH);
        assertTrue(Files.exists(path));

        Document document = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(FILE_PATH);
        Element root = document.getDocumentElement();
        assertEquals("statistics", root.getTagName());

        NodeList itemList = root.getElementsByTagName("item");
        assertEquals(data.size(), itemList.getLength());

        for (int i = 0; i < itemList.getLength(); i++) {
            Element item = (Element) itemList.item(i);
            String value = item.getElementsByTagName("value").item(0).getTextContent();
            String count = item.getElementsByTagName("count").item(0).getTextContent();
            assertEquals(String.valueOf(data.get(value)), count);
        }
        Files.deleteIfExists(path);
    }
    @DisplayName("Test writing if data is empty")
    @Test
    void testWriteIfMapIsEmpty() throws ParserConfigurationException, IOException, SAXException {
        Map<String, Long> data = Map.of();

        xmlFileWriter.write(data.entrySet(), ATTRIBUTE, DIRECTORY_PATH);

        Path path = Paths.get(FILE_PATH);
        assertTrue(Files.exists(path));

        Document document = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(FILE_PATH);
        Element root = document.getDocumentElement();
        assertEquals("statistics", root.getTagName());

        NodeList itemList = root.getElementsByTagName("item");
        assertEquals(0, itemList.getLength());

        Files.deleteIfExists(path);
    }

}

