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

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.tecacet.jflat.DefaultRowMapper;
import com.tecacet.jflat.FlatFileReaderCallback;
import com.tecacet.jflat.ReaderRowMapper;

/**
 * Reads an excel file into a collection of beans. It uses a ReaderRowMapper to
 * convert the tokens to beans.
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public class JxlExcelReader<T> extends ExcelReader<T> {

	protected Workbook workbook = null;
	protected Sheet currentSheet = null;

	@SuppressWarnings("unchecked")
	public JxlExcelReader(String filename) throws IOException {
		this(filename, new DefaultRowMapper());
	}

	public JxlExcelReader(String filename, ReaderRowMapper<T> mapper)
			throws IOException {
		try {
			workbook = Workbook.getWorkbook(new File(filename));
		} catch (BiffException e) {
			throw new IOException(e);
		} 
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
	 * Clean up resources
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		workbook.close();
	}

}
