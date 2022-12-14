package co.elasticsearch.enterprisesearch;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtil {

    public static String readResourceFile(String path){
        Path resourcePath = Paths.get("src/test/resources",path);
        try {
            return Files.readString(resourcePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
