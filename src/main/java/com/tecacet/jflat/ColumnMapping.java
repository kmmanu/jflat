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

/**
 * This interface is used to map columns of a flat file to bean properties.
 * 
 * @author Dimitri Papaioannou
 *
 */
public interface ColumnMapping {

   
    /**
     * Return a bean property for a given column index.
     * @param col the column index
     * @return a property identifier
     */
    String getProperty(int col);
    
    /**
     * Get the number of columns <i>that are mapped</i>
     * This may be a smaller number than the actual columns in the file.
     * @return
     */
    int getNumberOfColumns();
    
    /**
     * Indicates whether this mapping requires information from the header row
     * @return 
     */
    boolean requiresHeaderRow();
    
    /**
     * If requiresHeaderRow() returns true, this method will be invoked to provide the implementation
     * of this class with the header row details. The implementation decides how to use this information 
     * to construct mappings.
     * 
     * @param header the header row in tokens
     */
    void setHeaderRow(String[] header);
}