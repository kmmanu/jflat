package com.tecacet.jflat;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import com.tecacet.jflat.om.StockPrice;
import com.tecacet.util.conversion.ConverterRegistry;
import com.tecacet.util.conversion.GenericConverterRegistry;

public class StockPriceReaderTest {

    @Test
    public void testSimpleRead() throws IOException, LineMergerException {

        //TODO Problem: converters must be registered BEFORE BeanReaderRowMapper is initialized
        
        // The following code registers a converted with Commons Beans so that
        // it knows how to create dates from string
        // (required commons-beanutils-1.8)
        DateConverter dateConverter = new DateConverter(null);
        dateConverter.setPattern("yyyy-MM-dd");
        ConverterRegistry converterRegistry = GenericConverterRegistry.getInstance();
        converterRegistry.registerConverter(Date.class, dateConverter);

        // construct a bean row mapper for the LehmnaPrice object using the map
        ReaderRowMapper<StockPrice> rowMapper = 
        		new BeanReaderRowMapper<StockPrice>(StockPrice.class, new String[] {
                "date", "openPrice", "closePrice", "volume" }, new String[] { "Date", "Open", "Close", "Volume" });

        
        converterRegistry.registerConverter(Date.class, dateConverter);

        // create a normal file reader
        FileReader fr = new FileReader("testdata/prices.csv");
        // finally a CSV reader
        CSVReader<StockPrice> csvReader = new CSVReader<StockPrice>(fr, rowMapper);

        // read all the prices
        List<StockPrice> prices = csvReader.readAll();
        assertEquals(253, prices.size());
        StockPrice price = prices.get(0);
        assertEquals(1550.87, price.getOpenPrice(), 0.0001);
        assertEquals(1577.03, price.getClosePrice(), 2);
        assertEquals(1521220000, price.getVolume());
        assertEquals("Wed Dec 31 00:00:00 PST 2008", price.getDate().toString());

        FileWriter fw = new FileWriter("test.out");
        String[] properties = new String[] { "volume", "averagePrice", "date" };
        CSVWriter<StockPrice> csvWriter = new CSVWriter<StockPrice>(fw, StockPrice.class, properties);
        String[] header = new String[] { "Volume", "Average Price", "Date" };
        csvWriter.writeNext(header);
        csvWriter.writeAll(prices);
        csvWriter.close();

        // create a normal file reader
        fr = new FileReader("test.out");
        // finally a CSV reader
        rowMapper = new BeanReaderRowMapper<StockPrice>(StockPrice.class, properties, header);
        csvReader = new CSVReader<StockPrice>(fr, rowMapper);

        prices = csvReader.readAll();
        assertEquals(253, prices.size());
        price = prices.get(0);

        assertEquals(1521220000, price.getVolume());

        new File("test.out").delete();

    }
}