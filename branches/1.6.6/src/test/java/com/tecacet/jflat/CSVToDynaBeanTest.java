package com.tecacet.jflat;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.junit.Test;

public class CSVToDynaBeanTest {

	@Test
	public void noTest() {
		//Dynabeans are not supported yet
	}
	
	//@Test
	public void testReadCSV() throws IOException {
		DynaProperty[] properties = new DynaProperty[] {
				new DynaProperty("firstName"), new DynaProperty("lastName"),
				new DynaProperty("email"), new DynaProperty("rating") };

		String[] propertyNames = new String[] { "firstName", "lastName",
				"email", "rating" };
		String[] columnNames = new String[] { "First Name", "Last Name",
				"Email", "Rating" };

		DynaClass dynaClass = new BasicDynaClass("Contact",
				BasicDynaBean.class, properties);
		DynaBeanRowMapper rowMapper = new DynaBeanRowMapper(dynaClass,
				propertyNames, columnNames);

		FileReader fr = new FileReader("testdata/contacts.csv");
		CSVReader<DynaBean> reader = new CSVReader<DynaBean>(fr, rowMapper);
		List<DynaBean> beans = reader.readAll();
		assertEquals(2, beans.size());
		DynaBean bean = beans.get(1);
		assertEquals("Cohen", bean.get("lastName"));
		reader.close();
		fr.close();

	}
}
