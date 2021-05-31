package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtil {
    /**
     * This method is used to get the properties inside any
     * .properties file using the given path
     *
     * @param propertyFilePath the path of the properties file
     * @return the properties inside the given file path
     * @throws IOException in case of having invalid file path
     */
    public static Properties getProperties(String propertyFilePath) throws IOException {
        var properties = new Properties();
        var reader = new BufferedReader(new FileReader(propertyFilePath));

        properties.load(reader);
        reader.close();

        return properties;
    }
}
