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

import java.io.IOException;

/**
 * Parses a line with fixed width fields
 * 
 * @author Dimitri Papaioannou
 * 
 */
public class FixedWidthParser implements LineParser {

    private int[] widths;

    /**
     * 
     * @param widths
     *            the width of each token in the line
     */
    public FixedWidthParser(int[] widths) {
        this.widths = widths;
    }

    public String[] parseLine(String line) throws IOException {
        int lastIndex = 0;
        String[] tokens = new String[widths.length];
        for (int i = 0; i < widths.length; i++) {
            tokens[i] = line.substring(lastIndex, lastIndex + widths[i]);
            lastIndex += widths[i];
        }
        return tokens;
    }

}
