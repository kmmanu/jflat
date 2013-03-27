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

import java.io.Reader;

/**
 * A reader for CSV files
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public class CSVReader<T> extends FlatFileReader<T> {

	public CSVReader(Reader reader, Class<T> type, String[] columns) {
		this(reader, new BeanReaderRowMapper<T>(type, new ColumnPositionMapping(columns)));
	}
	
    public CSVReader(Reader reader, ReaderRowMapper<T> mapper) {
        super(reader, mapper);
        lineParser = new CSVParser(lineIterator);
    }

    public char getSeparator() {
        return ((CSVParser) lineParser).getSeparator();
    }

    public void setSeparator(char separator) {
        ((CSVParser) lineParser).setSeparator(separator);
    }

    public char getQuotechar() {
        return ((CSVParser) lineParser).getQuotechar();
    }

    public void setQuotechar(char quotechar) {
        ((CSVParser) lineParser).setQuotechar(quotechar);
    }

}
