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

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tecacet.jflat.om.Contact;

public class CSVReaderTest {

    @Test
    public void testReadWithColumnPositionMapping() throws Exception {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, "firstName");
        map.put(1, "lastName");
        ColumnMapping mappingStrategy = new ColumnPositionMapping(map);
        ReaderRowMapper<Contact> rowMapper = new BeanReaderRowMapper<Contact>(Contact.class, mappingStrategy);
        FileReader reader = new FileReader("testdata/contacts.csv");
        CSVReader<Contact> csvReader = new CSVReader<Contact>(reader, rowMapper);
        csvReader.setSkipLines(1);
        List<Contact> contacts = csvReader.readAll();
        Contact c = contacts.get(1);
        assertEquals("Cohen",c.getLastName());
    }
    
    @Test
    public void testReadWithHeaderColumnNameMapping() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("First Name", "firstName");
        map.put("Last Name", "lastName");
        ColumnMapping mappingStrategy = new HeaderColumnNameMapping(map);
        ReaderRowMapper<Contact> rowMapper = new BeanReaderRowMapper<Contact>(Contact.class, mappingStrategy);
        FileReader reader = new FileReader("testdata/contacts.csv");
        CSVReader<Contact> csvReader = new CSVReader<Contact>(reader, rowMapper);
        List<Contact> contacts = csvReader.readAll();
        Contact c = contacts.get(1);
        assertEquals("Cohen",c.getLastName());
    }
    
    @Test
    public void testReadWithHeaderColumnNameMappingAsArray() throws Exception {
        ReaderRowMapper<Contact> rowMapper = new BeanReaderRowMapper<Contact>(Contact.class, 
                new String[] {"firstName", "lastName"},
                new String[] {"First Name","Last Name"});
        FileReader reader = new FileReader("testdata/contacts.csv");
        CSVReader<Contact> csvReader = new CSVReader<Contact>(reader, rowMapper);
        List<Contact> contacts = csvReader.readAll();
        Contact c = contacts.get(1);
        assertEquals("Cohen",c.getLastName());
    }
}
