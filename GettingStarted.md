# Getting started with JFlat #

## How to read a CSV file into an list of String[.md](.md) ##


```
FileReader fr = new FileReader("testdata/prices.csv");
DefaultCSVReader reader = new DefaultCSVReader(fr);
List<String[]> lines = reader.readAll();
```

## High Level JFlat Architecture ##

The main class responsible for <i>reading</i> flat files is **FlatFileReader**. The FlatFileReader's behavior is configured by two interfaces:

  * The <b>LineParser</b> determines how each line in the flat file is parsed into tokens.
  * The <b>ReaderRowMapper</b> determines how a array of tokens is translated into an object.


## How to read a CSV file into a list of beans ##

A **CSVReader** is a FlatFileReader configured with a **CSVParser** which parses CSV lines into an array of tokens. The CSVReader needs a ReaderRowMapper to translate the parsed lines into objects.
The **BeanReaderRowMapper** can be used to map lines in a flat file into Java beans. To configure a BeanReaderRowMapper you must tell it how to map column into bean properties. The most common way of doing this is by mapping column names to property names.

You can do this by explicitly supplying a map of column names to properties:
```
        Map<String, String> map = new HashMap<String, String>();
        map.put("First Name", "firstName");
        map.put("Last Name", "lastName");
        ColumnMapping mappingStrategy = new HeaderColumnNameMapping(map);
        ReaderRowMapper<Contact> rowMapper = new BeanReaderRowMapper<Contact>(Contact.class, mappingStrategy);
        FileReader reader = new FileReader("testdata/contacts.csv");
        CSVReader<Contact> csvReader = new CSVReader<Contact>(reader, rowMapper);
```


or in a more compact form by passing an array of properties and an array of headers:

```
        ReaderRowMapper<Contact> rowMapper = new BeanReaderRowMapper<Contact>(Contact.class, 
                new String[] {"firstName", "lastName"},
                new String[] {"First Name","Last Name"});
        FileReader reader = new FileReader("testdata/contacts.csv");
        CSVReader<Contact> csvReader = new CSVReader<Contact>(reader, rowMapper);
        List<Contact> contacts = csvReader.readAll();
```