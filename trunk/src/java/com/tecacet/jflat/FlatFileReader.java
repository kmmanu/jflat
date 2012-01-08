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
package com.tecacet.jflat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads a flat file into a collection of beans. It uses a LineParser to parse
 * each line into tokens and a ReaderRowMapper to convert the tokens to beans.
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public class FlatFileReader<T> implements StructuredFileReader<T> {

	protected LineIterator lineIterator;

	/**
	 * The default line to start reading.
	 */
	protected static final int DEFAULT_SKIP_LINES = 0;

	/**
	 * lines to skip before reading the first line
	 */
	protected int skipLines;

	protected LineParser lineParser;

	protected ReaderRowMapper<T> rowMapper;

	@SuppressWarnings("unchecked")
	public FlatFileReader(Reader reader, LineParser parser) {
		this(reader, parser, new DefaultRowMapper());
	}

	public FlatFileReader(Reader reader, LineParser parser,
			ReaderRowMapper<T> mapper) {
		lineIterator = new BufferedReaderLineIterator(
				new BufferedReader(reader));
		this.lineParser = parser;
		this.rowMapper = mapper;
		this.skipLines = DEFAULT_SKIP_LINES;
	}

	public FlatFileReader(Reader reader, ReaderRowMapper<T> mapper) {
		this(reader, null, mapper);
	}

	/* (non-Javadoc)
     * @see com.tecacet.jflat.FileReader#readWithCallback(com.tecacet.jflat.FlatFileReaderCallback)
     */
	@Override
    public void readWithCallback(FlatFileReaderCallback<T> callback)
			throws IOException {
		for (int i = 0; i < skipLines; i++) {
			lineIterator.getNextLine();
		}
		int row = 0;
		String[] nextLineAsTokens = readNext();
		while (nextLineAsTokens != null) {
			T bean = rowMapper.getRow(nextLineAsTokens, ++row);
			processRow(callback, row, nextLineAsTokens, bean);
			nextLineAsTokens = readNext();
		}
	}
	
	protected void processRow(FlatFileReaderCallback<T> callback, int row, String[] nextLineAsTokens, T bean) {
		callback.processRow(row, nextLineAsTokens, bean);
	}

	/* (non-Javadoc)
     * @see com.tecacet.jflat.FileReader#readAll()
     */
	@Override
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

	protected String[] readNext() throws IOException {
		String line = lineIterator.getNextLine();
		if (line == null) {
			return null;
		}
		return lineParser.parseLine(line);
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
		lineIterator.close();
	}

}
