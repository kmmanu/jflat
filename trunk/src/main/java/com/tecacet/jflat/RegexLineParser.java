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
import java.util.regex.Pattern;

/**
 * Uses a Java regular expression to parse lines
 * 
 * @author Dimitri Papaioannou
 * 
 */
public class RegexLineParser implements LineParser {

    private Pattern pattern;
    private int limit = 0;

    public RegexLineParser(String regex) {
        pattern = Pattern.compile(regex);
    }

    public String[] parseLine(String line) throws IOException {
        return pattern.split(line, limit);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
