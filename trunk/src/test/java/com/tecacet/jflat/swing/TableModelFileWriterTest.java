package com.tecacet.jflat.swing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.junit.Test;

import com.tecacet.jflat.LineMergerException;

public class TableModelFileWriterTest {

    @Test
    public void testWriteTable() throws IOException, LineMergerException {
        TableModel tableModel =
            new DefaultTableModel(new Object[][] { 
                { "Lore", "123", "Resting" }, 
                { "Oblivion", "32", "Mining" } }, 
                new String[] { "Word", "Number", "Activity" });
        FileWriter w = new FileWriter("test.csv");
        TableModelFileWriter writer = new TableModelFileWriter(w);
        writer.writeTable(tableModel);
        //TODO verify
        new File("test.csv").delete();
    }
}
