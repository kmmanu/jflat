/*
 Copyright 2005  TecaceT Ltd.

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

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class CSVParserTest {

    @Test
    public void testParseLine() throws IOException {
        CSVParser parser = new CSVParser(null);
        String line = "foo,bar,baz";
        String[] tokens = parser.parseLine(line);
        assertEquals(3, tokens.length);
        assertEquals("foo", tokens[0]);
        assertEquals("bar", tokens[1]);
        assertEquals("baz", tokens[2]);
        
        line = "  foo  ,  bar  ,  baz  ";
        tokens = parser.parseLine(line);
        assertEquals(3, tokens.length);
        assertEquals("  foo  ", tokens[0]);
        assertEquals("  bar  ", tokens[1]);
        assertEquals("  baz  ", tokens[2]);
         
        line = ",bar,";
        tokens = parser.parseLine(line);
        assertEquals(3, tokens.length);
        assertEquals(null, tokens[0]);
        assertEquals("bar", tokens[1]);
        assertEquals(null, tokens[2]);
        
    }

    
}
