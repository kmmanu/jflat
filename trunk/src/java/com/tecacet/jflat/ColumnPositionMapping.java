/*
 Copyright 2008  TecaceT Ltd.

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
 * Map columns to properties based on the column index
 * 
 * @author Dimitri Papaioannou
 * 
 */
public class ColumnPositionMapping implements ColumnMapping {

    protected Map<Integer, String> columnMapping;

    protected ColumnPositionMapping() {
        
    }
    
    /**
     * 
     * @param mapping
     *            this map determines which property is mapped to which column
     */
    public ColumnPositionMapping(Map<Integer, String> mapping) {
        this.columnMapping = mapping;
    }

    /**
     * 
     * @param mapping
     *            maps the array index to the property corresponding to the
     *            array value
     */
    public ColumnPositionMapping(String[] mapping) {
        columnMapping = new HashMap<Integer, String>();
        for (int i = 0; i < mapping.length; i++) {
            columnMapping.put(i, mapping[i]);
        }
    }

    public String getProperty(int col) {
        return columnMapping.get(col);
    }

    public int getNumberOfColumns() {
        return columnMapping.size();
    }

    public boolean requiresHeaderRow() {
        return false;
    }

    public void setHeaderRow(String[] header) {

    }

}