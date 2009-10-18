/*
 Copyright 2008 TecAceT Ltd.

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

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class FlatFileReaderTest {

    @Test
    public void testCustomParser() throws Exception {
        DefaultRowMapper mapper = new DefaultRowMapper();
        LineParser lineParser = new LineParser() {

            LineParser headerParser = new FixedWidthParser(new int[] { 1, 4, 4 });

            LineParser bodyParser = new FixedWidthParser(new int[] { 1, 7, 7 });

            public String[] parseLine(String line) throws IOException {
                char c = line.charAt(0);
                if (c == 'H') {
                    return headerParser.parseLine(line);
                }
                if (c == 'D') {
                    return bodyParser.parseLine(line);
                }
                return null;
            }

        };
        FileReader reader = new FileReader("testdata/ffwheaderAndFooter.txt");
        FlatFileReader<String[]> flatReader = new FlatFileReader<String[]>(reader, lineParser, mapper);
        List<String[]> lines = flatReader.readAll();
        assertEquals(2, lines.size());
        assertEquals("L463", lines.get(0)[2]);
        assertEquals("0003752", lines.get(1)[1]);
    }

}
