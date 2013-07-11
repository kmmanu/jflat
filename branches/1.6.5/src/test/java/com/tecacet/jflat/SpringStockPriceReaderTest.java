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
import com.tecacet.util.conversion.ConverterPropertyEditor;
import com.tecacet.util.conversion.DateToStringConverter;
import com.tecacet.util.introspection.spring.SpringBeanWrapperPropertyAccessor;

public class SpringStockPriceReaderTest {

    @Test
    public void testSimpleRead() throws IOException, LineMergerException {

        // construct a bean row mapper for the LehmnaPrice object using the map
        ColumnMapping columnMapping = new HeaderColumnNameMapping(new String[] { "date", "openPrice", "closePrice",
                "volume" }, new String[] { "Date", "Open", "Close", "Volume" });
        SpringBeanWrapperPropertyAccessor<StockPrice> propertyAccessor = new SpringBeanWrapperPropertyAccessor<StockPrice>();

        final DateConverter dateConverter = new DateConverter(null);
        dateConverter.setPattern("yyyy-MM-dd");
        propertyAccessor.getBeanWrapper().registerCustomEditor(Date.class,
                new ConverterPropertyEditor(Date.class, dateConverter));
        
        ReaderRowMapper<StockPrice> rowMapper = new BeanReaderRowMapper<StockPrice>(StockPrice.class, propertyAccessor,
                columnMapping);
        // create a normal file reader
        FileReader fr = new FileReader("testdata/prices.csv");
        // finally a CSV reader
        com.tecacet.jflat.StructuredFileReader<StockPrice> csvReader = new CSVReader<StockPrice>(fr, rowMapper);

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
        ColumnMapping columnMapping2 = new ColumnPositionMapping(properties);

        BeanWriterRowMapper<StockPrice> rowMapper2 = new BeanWriterRowMapper<StockPrice>(columnMapping2,
                propertyAccessor);
        rowMapper2.registerConverter(Date.class, new DateToStringConverter("dd-MM-yyyy"));
        FlatFileWriter<StockPrice> csvWriter = new FlatFileWriter<StockPrice>(fw, new CSVLineMerger(), rowMapper2);
        String[] header = new String[] { "Volume", "Average Price", "Date" };
        csvWriter.writeNext(header);
        csvWriter.writeAll(prices);
        csvWriter.close();

        // create a normal file reader
        fr = new FileReader("test.out");

        //  read back
        DefaultCSVReader rereader = new DefaultCSVReader(fr);

        List<String[]> contents = rereader.readAll();
        assertEquals(253, prices.size());
        String[] row = contents.get(1);
        assertEquals("31-12-2008", row[2]);

        new File("test.out").delete();

    }
}