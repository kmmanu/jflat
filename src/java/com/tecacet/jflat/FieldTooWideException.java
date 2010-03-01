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

public class FieldTooWideException extends LineMergerException {

    
    private static final long serialVersionUID = -1468338565590374371L;
    
    private final String fieldValue;
    private final int maxFieldWidth;
    
    public FieldTooWideException(String fieldValue, int maxFieldWidth) {
        super();
        this.fieldValue = fieldValue;
        this.maxFieldWidth = maxFieldWidth;
    }
    
    @Override
    public String getMessage(){
        String messageFormat = "Value '%s' is too wide.  Actual width: %d, Maximum width: %d.";
        String message = String.format( messageFormat, fieldValue, fieldValue.length(), maxFieldWidth);
        return message;
    }
    
    public String getFieldValue() {
        return fieldValue;
    }
    
    public int getMaxFieldWidth() {
        return maxFieldWidth;
    }
}
