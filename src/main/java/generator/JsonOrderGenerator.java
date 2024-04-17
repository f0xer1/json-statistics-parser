package generator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class JsonOrderGenerator {
    private final Random random;
    private final JsonFactory factory;

    private final Path resultFileDirectory = Path.of("src", "main", "resources", "json");
    private final List<String> items = Arrays.asList("Shirt, Pen, Lock", "Pants", "Shoes, Tripod",
            "Laptop, Camera", "Mouse");
    private final List<Double> totalAmounts = Arrays.asList(222.3, 235.2, 6727.0, 324.0, 2226.3);
    private final List<String> customers = Arrays.asList("John Doe", "Jane Smith", "Michael Johnson",
            "Emily Williams", "David Lee");

    private static int NUMBER = 0;


    public static void main(String[] args) {
        JsonOrderGenerator orderGenerator = new JsonOrderGenerator(new Random(), new JsonFactory());
        orderGenerator.generateOrders();
    }

    public void generateOrders() {
        int numberOfFiles = getRandomValueByMaxValue(20) + 1;
        for (int i = 0; i < numberOfFiles; i++) {
            generateJsonFile();
        }
    }

    private void generateJsonFile() {
        Path outputFile = resultFileDirectory.resolve(getOutputFileName());
        try (JsonGenerator generator = factory.createGenerator(Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8))) {
            generator.writeStartArray();
            for (int i = 0; i <= getRandomValueByMaxValue(Integer.MAX_VALUE); i++) {
                generateJson(generator);
            }
            generator.writeEndArray();
        } catch (IOException e) {
            System.err.println("Error occurred - " + e.getMessage());
        }

    }

    private void generateJson(JsonGenerator generator) throws IOException {
        generator.writeStartObject();
        generator.writeNumberField("order_number", getRandomValueByMaxValue(50000));
        generator.writeStringField("items", items.get(getRandomValueByMaxValue(5)));
        generator.writeNumberField("total_amount", totalAmounts.get(getRandomValueByMaxValue(5)));
        generator.writeStringField("customer", customers.get(getRandomValueByMaxValue(5)));
        generator.writeEndObject();
    }

    private String getOutputFileName() {
        return String.format("json%s.json", NUMBER++);
    }

    private int getRandomValueByMaxValue(int value) {
        return random.nextInt(value);
    }
}
