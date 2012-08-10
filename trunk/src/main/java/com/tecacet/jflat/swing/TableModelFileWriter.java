/*
 Copyright 2008  TecaceT Ltd.

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

package com.tecacet.jflat.swing;

import java.io.Writer;

import javax.swing.table.TableModel;

import com.tecacet.jflat.CSVLineMerger;
import com.tecacet.jflat.FlatFileWriter;
import com.tecacet.jflat.LineMerger;
import com.tecacet.jflat.LineMergerException;

/**
 * Exports a Swing TableModel to a flat file.
 * 
 * @author Dimitri Papaioannou
 *
 */
public class TableModelFileWriter extends FlatFileWriter {

    private boolean includeColumnNames = true;

    /**
     * The default constructor will write the table in CSV format
     * @param writer
     */
    public TableModelFileWriter(Writer writer) {
        this(writer, new CSVLineMerger());
    }
    
    public TableModelFileWriter(Writer writer, LineMerger merger) {
        super(writer, merger, null);
    }

    public void writeTable(TableModel tableModel) throws LineMergerException {
        if (includeColumnNames) {
            writeColumnNames(tableModel);
        }
        for (int r = 0; r < tableModel.getRowCount(); r++) {
            String[] row = new String[tableModel.getColumnCount()];
            for (int c = 0; c < tableModel.getColumnCount(); c++) {
                Object value = tableModel.getValueAt(r, c);
                row[c] = value == null ? null : value.toString();
            }
            writeNext(row);
        }
        close();
    }

    private void writeColumnNames(TableModel tableModel) throws LineMergerException {
        String[] columns = new String[tableModel.getColumnCount()];
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            columns[i] = tableModel.getColumnName(i);
        }
        writeNext(columns);
    }

    public boolean isIncludeColumnNames() {
        return includeColumnNames;
    }

    public void setIncludeColumnNames(boolean includeColumnNames) {
        this.includeColumnNames = includeColumnNames;
    }
    
}
