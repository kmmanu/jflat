/*
 Copyright 2008 TecaceT Ltd.

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
 * Maps a row to a (property, value) map where the values are stored as Strings
 * 
 * @author Dimitri Papaioannou
 * 
 */
public class MapReaderRowMapper implements ReaderRowMapper<Map<String, String>> {

    private int headerRow = 1;

    private ColumnMapping columnMapping;

    public MapReaderRowMapper(ColumnMapping columnMapping) {
        this.columnMapping = columnMapping;
    }

    public Map<String, String> getRow(String[] row, int rowNumber) {
        if (rowNumber == headerRow) {
            if (columnMapping.requiresHeaderRow()) {
                columnMapping.setHeaderRow(row);
                return null;
            }
        }
        Map<String, String> mapRow = new HashMap<String, String>();
        for (int i = 0; i < row.length; i++) {
            String property = columnMapping.getProperty(i);
            if (property == null) {
                continue;
            }
            mapRow.put(property, row[i]);
        }
        return mapRow;
    }

}
