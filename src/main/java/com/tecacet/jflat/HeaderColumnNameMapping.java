/*
 Copyright 2008 TecAceT Ltd.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.tecacet.jflat;

import java.util.HashMap;
import java.util.Map;

/**
 * A Column Mapping that uses header information to determine the property that a column is mapped to. It can be used in
 * two ways: 1. Use the header name as the property 2. Use an additional mapping to translate a header name into a
 * property.
 * 
 * @author Dimitri Papaioannou
 * 
 */
public class HeaderColumnNameMapping implements ColumnMapping {
    protected String[] header;
    protected Map<String, String> columnMapping = null;
    
    private boolean trimWhiteSpace = false;
    private boolean convertToLowerCase = false;
    
    /**
     * Empty protected constructor as a convenience for extending 
     */
    protected HeaderColumnNameMapping() {
        
    }
    
    public HeaderColumnNameMapping(Map<String, String> columnMapping) {
        this.columnMapping = columnMapping;
    }

    public HeaderColumnNameMapping(String[] properties, String[] columns) {
        this.columnMapping = new HashMap<String, String>();
        for (int i = 0; i < columns.length; i++) {
            columnMapping.put(columns[i], properties[i]);
        }
    }

    @Override
    public String getProperty(int col) {
        if (header == null) {
            throw new IllegalStateException("getProperty() is called but the header is not set.");
        }
        if (columnMapping != null) {
            return columnMapping.get(header[col]);
        }
        return header[col];
    }
    
    @Override
    public int getNumberOfColumns() {
        if (columnMapping != null) {
            return columnMapping.size();
        }
        return header.length;
    }

    @Override
    public boolean requiresHeaderRow() {
        return true;
    }

    public void setHeaderRow(final String[] aHeader) {
        this.header = aHeader;
        if (trimWhiteSpace || convertToLowerCase) {
            for (int i=0;i<header.length;i++) {
                if (trimWhiteSpace) {
                    header[i] = header[i].trim();
                }
                if (convertToLowerCase) {
                    header[i] = header[i].toLowerCase();
                }
            }
        }
        
    }

    public boolean isTrimWhiteSpace() {
        return trimWhiteSpace;
    }

    public void setTrimWhiteSpace(boolean trimWhiteSpace) {
        this.trimWhiteSpace = trimWhiteSpace;
    }

    public boolean isConvertToLowerCase() {
        return convertToLowerCase;
    }

    public void setConvertToLowerCase(boolean convertToLowerCase) {
        this.convertToLowerCase = convertToLowerCase;
    }

}
