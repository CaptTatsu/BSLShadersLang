package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;

/**
 * This tool is used to check which fields are missing or redundant in other language files relative to en_US.lang
 * Please replace the content of NEED_CHECK_FILE_PATH with the file path you need to check
 * */
public class CheckFieldUtil {
    public static final String EN_US_FILE_PATH = "./lang/en_US.lang";
    public static final String NEED_CHECK_FILE_PATH = "./lang/zh_CN.lang";

    public static void main(String[] args) {
        try {
            Properties sourceProps = loadProperties(NEED_CHECK_FILE_PATH);
            Properties enProps = loadProperties(EN_US_FILE_PATH);

            Set<String> sourceKeys = sourceProps.stringPropertyNames();
            Set<String> enKeys = enProps.stringPropertyNames();

            for (String key : sourceKeys) {
                if (!enKeys.contains(key)) {
                    System.out.println("Extra Key in " + NEED_CHECK_FILE_PATH + ": " + key);
                }
            }
            for (String key : enKeys) {
                if (!sourceKeys.contains(key)) {
                    System.out.println("Key missing in " + NEED_CHECK_FILE_PATH + ": " + key);
                }
            }
        } catch (IOException e) {
            System.out.println("IOException, Please check if your file path is correct.");
            e.printStackTrace();
        }
    }
    //Use UTF-8 to read File
    private static Properties loadProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
        }
        return properties;
    }
}