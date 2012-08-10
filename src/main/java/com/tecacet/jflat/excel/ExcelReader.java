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
package com.tecacet.jflat.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.tecacet.jflat.DefaultRowMapper;
import com.tecacet.jflat.StructuredFileReader;
import com.tecacet.jflat.FlatFileReaderCallback;
import com.tecacet.jflat.ReaderRowMapper;

/**
 * Reads an excel file into a collection of beans. It uses a LineParser to parse
 * each line into tokens and a ReaderRowMapper to convert the tokens to beans.
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public class ExcelReader<T> implements StructuredFileReader<T>{

    /**
     * The default line to start reading.
     */
    protected static final int DEFAULT_SKIP_LINES = 0;

    /**
     * lines to skip before reading the first line
     */
    protected int skipLines;

    protected Workbook workbook = null;
    protected Sheet currentSheet = null;

    protected ReaderRowMapper<T> rowMapper;

    public ExcelReader(String filename) throws BiffException, IOException {
        this(filename, new DefaultRowMapper());
    }

    public ExcelReader(String filename, ReaderRowMapper<T> mapper) throws BiffException, IOException {
        workbook = Workbook.getWorkbook(new File(filename)); // TODO
        currentSheet = workbook.getSheet(0);
        this.rowMapper = mapper;
        this.skipLines = DEFAULT_SKIP_LINES;
    }

    /**
     * Navigate the flat file and invoke the callback for each row.
     * 
     * @param callback
     * @throws IOException
     */
    public void readWithCallback(FlatFileReaderCallback<T> callback) {
        readSheet(currentSheet, callback);
    }

    protected void readSheet(Sheet sheet, FlatFileReaderCallback<T> callback) {
        int rows = sheet.getRows();
        for (int r = 0; r < rows; r++) {
            if (r < skipLines) {
                continue;
            }
            Cell[] row = sheet.getRow(r);
            String[] tokens = readRow(row);
            T bean = rowMapper.getRow(tokens, r + 1);
            callback.processRow(r, tokens, bean);
        }
    }

    protected String[] readRow(Cell[] row) {
        String[] tokens = new String[row.length];
        for (int c = 0; c < row.length; c++) {
            Cell cell = row[c];
            tokens[c] = cell.getContents();
        }
        return tokens;
    }

    /**
     * Reads the entire file into a List. Each element is a bean of type T
     * produced by the RowMapper
     * 
     * @return a List of String[], with each String[] representing a line of the
     *         file.
     * 
     * @throws IOException
     *             if bad things happen during the read
     */
    public List<T> readAll() throws IOException {
        final List<T> allElements = new ArrayList<T>();
        readWithCallback(new FlatFileReaderCallback<T>() {

            public void processRow(int rowIndex, String[] tokens, T bean) {
                if (bean != null) {
                    allElements.add(bean);
                }
            }
        });
        return allElements;

    }

    /**
     * The number of lines to skip before reading a file
     * 
     * @return number of skipped lines
     */
    public int getSkipLines() {
        return skipLines;
    }

    /**
     * Set the number of lines to skip before reading a file.
     * 
     * @param skipLines
     *            number of skipped lines
     */
    public void setSkipLines(int skipLines) {
        this.skipLines = skipLines;
    }

    /**
     * Clean up resources
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        workbook.close();
    }

}
