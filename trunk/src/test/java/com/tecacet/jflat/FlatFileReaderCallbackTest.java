package com.tecacet.jflat;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tecacet.jflat.om.Customer;
import com.tecacet.jflat.om.Order;

public class FlatFileReaderCallbackTest {

    @Test
    public void testProcessRow() throws IOException {
        final List<Order> orders = new ArrayList<Order>();
        FlatFileReaderCallback<Order> callback = new FlatFileReaderCallback<Order>() {

            @Override
            public void processRow(int rowIndex, String[] tokens, Order order) {
                if (rowIndex == 1) {
                    return;
                }
                String[] name = tokens[1].split(",");
                String lastName = name[0];
                String firstName = name[1];
                order.setCustomer(new Customer());
                order.getCustomer().setLastName(lastName);
                order.getCustomer().setFirstName(firstName);
                orders.add(order);
            }
        };

        ReaderRowMapper<Order> rowMapper = new BeanReaderRowMapper<Order>(Order.class,
                new String[] { "number", "price" }, new String[] { "Number", "Price" });
        FileReader reader = new FileReader("testdata/orders.csv");
        CSVReader<Order> csvReader = new CSVReader<Order>(reader, rowMapper);
        csvReader.readWithCallback(callback);
        assertEquals(2, orders.size());

    }

}
