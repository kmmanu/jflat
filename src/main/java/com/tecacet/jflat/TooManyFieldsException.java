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

public class TooManyFieldsException extends LineMergerException {

    private static final long serialVersionUID = 7577849352062678462L;

    private final int fieldCount;
    private final int maxFieldCount;
    
    public TooManyFieldsException(int fieldCount, int maxFieldCount) {
        super();
        this.fieldCount = fieldCount;
        this.maxFieldCount = maxFieldCount;
    }
    
    @Override
    public String getMessage(){
        String message = String.format("Too many fields.  Got: %d, Maximum: %d.", fieldCount, maxFieldCount);
        return message;
    }
    
    public int getFieldCount() {
        return fieldCount;
    }
    
    public int getMaxFieldCount() {
        return maxFieldCount;
    }
}

