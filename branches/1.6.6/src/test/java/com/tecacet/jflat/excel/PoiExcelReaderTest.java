package com.tecacet.jflat.excel;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import com.tecacet.jflat.BeanReaderRowMapper;
import com.tecacet.jflat.FlatFileReaderCallback;
import com.tecacet.jflat.ReaderRowMapper;
import com.tecacet.jflat.om.StockPrice;

public class PoiExcelReaderTest {

	@Test
	public void testReadNewExcel() throws IOException {
		readAll("testdata/prices.xlsx");

	}

	@Test
	public void testReadOldExcel() throws  IOException {
		readAll("testdata/prices.xls");
		
	}
	
	private void readAll(String filename) throws IOException {
		ReaderRowMapper<StockPrice> rowMapper = new BeanReaderRowMapper<StockPrice>(
				StockPrice.class, new String[] { "date", "openPrice",
						"closePrice", "volume" }, new String[] { "Date",
						"Open", "Close", "Volume" });
		DateConverter dateConverter = new DateConverter(null);
		dateConverter.setPattern("yyyy-MM-dd");
		ConvertUtils.register(dateConverter, Date.class);
		ExcelReader<StockPrice> reader = new PoiExcelReader<StockPrice>(
				filename, rowMapper);
		// read all the prices
		List<StockPrice> prices = reader.readAll();
		assertEquals(253, prices.size());
		StockPrice price = prices.get(0);
		assertEquals(1550.87, price.getOpenPrice(), 0.0001);
		assertEquals(1577.03, price.getClosePrice(), 2);
		assertEquals(1521220000, price.getVolume());
		
		reader.readWithCallback(new FlatFileReaderCallback<StockPrice>() {

			@Override
			public void processRow(int rowIndex, String[] tokens,
					StockPrice bean) {
				System.out.println(bean);
			}
		});
	}

}
