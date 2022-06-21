package com.webui.helpers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertyHelper {
    private static Logger logger = LoggerFactory.getLogger(PropertyHelper.class);
    private static PropertyHelper propertiesHelper;
    private Properties properties = new Properties();


    /**
     * Restricting the instantiation of class to one object.
     */
    public static PropertyHelper getInstance() {
        if (propertiesHelper == null) {
            propertiesHelper = new PropertyHelper();
        }
        return propertiesHelper;
    }

    /**
     *Load the Properties under resources folder
     */
    public void loadLocalizableProperties(String resourceFile) {
        try {
            final InputStream input = new FileInputStream(resourceFile);
            properties.load(new InputStreamReader(input, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *Get the String Value for the particular id which is stored as key in the properties file
     *
     * @param prop
     *@return String
     *
     */
    public String getStringForProperty(final String prop) throws Exception {
        String label = null;

        if (!properties.isEmpty()) {
            logger.debug("String Id is : {}", prop);
            label = properties.getProperty(prop);
            if (label == null) {
                final String errorMessage = String.format("No Strings found for the property : %s. Test Execution "
                        + "stopped", prop);
                throw new Exception(errorMessage);
            }
            logger.debug("String value returned is : {}", label);
        }
        return label;
    }

}

