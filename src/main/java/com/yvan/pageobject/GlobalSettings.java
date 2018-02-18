package com.yvan.pageobject;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Function：浏览器全局设置
 * Created by YangWang on 2018-01-04 21:15.
 */
public class GlobalSettings {

    public static Properties prop = getProperties();

    public static int Mode = Integer.parseInt(prop.getProperty("Mode", "2"));
    public static int browserType = Integer.parseInt(prop.getProperty("BrowserType", "2"));
    public static String Host = prop.getProperty("Host", "127.0.0.1:4444");
    public static String timeout = prop.getProperty("Timeout", "10000");

    public static String getProperty(String property) {
        return prop.getProperty(property);
    }

    public static Properties getProperties() {
        Properties prop = new Properties();
        try {
            FileInputStream file = new FileInputStream("prop.properties");
            prop.load(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }
}

