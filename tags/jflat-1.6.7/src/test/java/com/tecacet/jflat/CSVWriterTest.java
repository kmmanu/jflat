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

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CSVWriterTest {

    @Test
    public void testWriteAll() throws IOException, LineMergerException {
        Writer w = new FileWriter("test.csv");
        CSVWriter<String[]> writer = new DefaultCSVWriter(w);
        List<String[]> lines = new ArrayList<String[]>();
        lines.add(new String[] {"Lore","123","Resting"});
        lines.add(new String[] {"Oblivion","32","Mining"});
        writer.writeAll(lines);
        writer.close();
        w.close();
        
        CSVReader<String[]> reader = new DefaultCSVReader(new FileReader("test.csv"));
        List<String[]> all = reader.readAll();
        assertEquals(2,all.size());
        
        new File("test.csv").delete();
    }
    
    @Test
    public void testWriteAllWithHeader() throws IOException, LineMergerException {
        Writer w = new FileWriter("test.csv");
        CSVWriter<String[]> writer = new DefaultCSVWriter(w);
        List<String[]> lines = new ArrayList<String[]>();
        lines.add(new String[] {"Lore","123","Resting"});
        lines.add(new String[] {"Oblivion","32","Mining"});
        writer.writeNext(new String[] {"Word","Number","Activity"});
        writer.writeAll(lines);
        writer.close();
        w.close();
        
        CSVReader<String[]> reader = new DefaultCSVReader(new FileReader("test.csv"));
        List<String[]> all = reader.readAll();
        assertEquals(3,all.size());
        
        new File("test.csv").delete();
    }

}
