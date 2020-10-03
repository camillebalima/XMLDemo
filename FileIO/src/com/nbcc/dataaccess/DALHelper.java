/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.dataaccess;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author chris.cusack
 */
public class DALHelper {
    /**
     * @desciption Loads db.properties file into an instance of the properties
     * object
     * @return
     */
    public static Properties getProperties() throws IOException {
        //Project > New > Other > Other > Properties File > Name the file db
        Properties props = new Properties();
        FileInputStream in = null;

        try {
            String propertiesPath = System.getProperty("user.dir") + "\\db.properties";
            in = new FileInputStream(propertiesPath);
            props.load(in);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return props;
    }
}
