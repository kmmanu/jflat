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
 * 
 * Used by a Flat File Reader to map a parsed line into an Object
 * 
 * @param <T> type of target object
 * 
 * @author Dimitri Papaioannou
 */
public interface ReaderRowMapper<T> {

    /**
     * Return an object for the given row.
     * If the value of the return object is null, the object is not added to the list
     * @param row
     * @param rowNumber
     * @return the bean corresponding to this row. Implementations should return null if the row is null or if it is not mapped.
     */
    T getRow(String[] row, int rowNumber);
}
