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

public class FixedWidthParserTest {

    @Test
    public void testParseLine() throws IOException {
        FixedWidthParser parser = new FixedWidthParser(new int[] { 1, 9, 9 });
        String line = "D000375204000013900";
        String[] tokens = parser.parseLine(line);
        assertEquals("D", tokens[0]);
        assertEquals("000375204", tokens[1]);
        assertEquals("000013900", tokens[2]);
        
        line = "H0000L463070123B";
        parser = new FixedWidthParser(new int[] { 1, 4, 4 });
        tokens = parser.parseLine(line);
        assertEquals("H", tokens[0]);
        assertEquals("0000", tokens[1]);
        assertEquals("L463", tokens[2]);
         
        line = "   XXX   ";
        parser = new FixedWidthParser(new int[] {3,3,3});
        tokens = parser.parseLine(line);
        assertEquals(null, tokens[0]);
        assertEquals("XXX", tokens[1]);
        assertEquals(null, tokens[2]);
        
    }

    
}
