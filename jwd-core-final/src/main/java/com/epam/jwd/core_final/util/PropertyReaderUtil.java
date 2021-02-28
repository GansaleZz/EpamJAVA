package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyReaderUtil {

    private static final Properties properties = new Properties();

    public static Properties getProperties(){
        return properties;
    }

    private PropertyReaderUtil() {
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() {
        final String propertiesFileName = "/Users/andrew_wannasesh/Folders/EpamJAva/jwd-core-final/src/main/resources/application.properties";
        InputStream inputStream = null;
        Logger logger = LoggerFactory.getLogger("PropertyReaderUtil");
        try{
            inputStream = new FileInputStream(propertiesFileName);
            properties.load(inputStream);
            logger.info("Info from "+propertiesFileName+" were completely read");
        }catch (IOException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try{
                if(inputStream != null){
                    inputStream.close();
                }
            }catch(IOException e){
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
