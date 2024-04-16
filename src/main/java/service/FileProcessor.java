package service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class FileProcessor {
    public void processing(ConcurrentHashMap<String, Integer> statisticMap,
                           String attribute, File file,  JsonFactory factory)  {
        try(JsonParser parser = factory.createParser(file)){
            System.out.println(1);
        }catch (IOException e){
            System.err.println("Error occurred - " + e.getMessage());
        }
    }
}
