package com.tecacet.jflat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Like java.util.Properties but respects spaces
 * 
 * @author Dimitri Papaioannou
 * 
 */
public class MapReader {

    private Log log = LogFactory.getLog(this.getClass());
    
    private boolean convertToLowerCase = false;
    
    /**
     * Read a properties file into a map
     * 
     * @param is
     * @return
     * @throws IOException
     */
    public Map<String, String> readMap(InputStream is) throws IOException {
        Map<String, String> map = new LinkedHashMap<String, String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        while (line != null) {
            // skip comments
            if (line.startsWith("#") || line.trim().equals("")) {
                line = br.readLine();
                continue;
            }
            String[] nextLineAsTokens = line.split("=");
            if (nextLineAsTokens.length < 2) {
                log.warn("Line does not contain an assignment: " + line);
                line = br.readLine();
                continue;
            }
            if (nextLineAsTokens.length > 2) {
                log.warn("Ambiguous assignement: " + line);
                line = br.readLine();
                continue;
            }
            String property = nextLineAsTokens[0].trim();
            if (convertToLowerCase) {
                property = property.toLowerCase();
            }
            map.put(property, nextLineAsTokens[1].trim());
            line = br.readLine();
        }
        br.close();
        return map;
    }

    public boolean isConvertToLowerCase() {
        return convertToLowerCase;
    }

    public void setConvertToLowerCase(boolean convertToLowerCase) {
        this.convertToLowerCase = convertToLowerCase;
    }

}
