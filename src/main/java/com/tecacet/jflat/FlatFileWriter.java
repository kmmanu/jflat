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

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

/**
 * Writes a collection of beans in flat file format. Uses a WriterRowMapper to
 * map beans to tokens and a LineMerger to construct a line from tokens. The
 * write methods can also be used to create the file line by line.
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public class FlatFileWriter<T> {
    
    protected static final String DEFAULT_LINE_END = System.getProperty("line.separator");


    private PrintWriter pw;

    /**
     * The line merger merges sting arrays into lines
     */
    protected LineMerger lineMeger;

    /**
     * RowMapper used to convert beans to tokens
     * 
     */
    protected WriterRowMapper<T> rowMapper;

    /**
     * 
     * @param writer
     * @param merger
     * @param mapper
     */
    public FlatFileWriter(Writer writer, LineMerger merger, WriterRowMapper<T> mapper) {
        pw = new PrintWriter(writer);
        this.lineMeger = merger;
        this.rowMapper = mapper;
    }

    /**
     * Writes an entire list of beans to a flat file.
     * 
     * @param beans
     *            a List of beans, each one representing a source of a line in
     *            the file.
     */
    public void writeAll(Collection<T> beans) throws LineMergerException {
        for (T bean : beans) {
            writeOne(bean);
        }
    }

    /**
     * Writes a single bean to a flat file.
     * 
     * @param beans
     *            representing a source of a line in the file.
     */
    public void writeOne(T bean) throws LineMergerException {
        String[] nextLine = rowMapper.getRow(bean);
        writeNext(nextLine);
    }
    
    /**
     * Writes the next line to the file.
     * 
     * @param line
     *            a string array with string of tokens as a separate entry. Uses
     *            a LineMerger to compose the line
     */
    public void writeNext(String[] line) throws LineMergerException{
        writeNext(lineMeger.makeLine(line));
    }

    /**
     * Writes the next line to the file.
     * 
     * @param line
     *            The line to write
     */
    public void writeNext(String line) {
        pw.write(line);
        pw.write(DEFAULT_LINE_END);
    }

    /**
     * Flush underlying stream to writer.
     * 
     */
    public void flush() {
        pw.flush();
    }

    /**
     * Close the underlying stream writer flushing any buffered content.
     * 
     */
    public void close() {
        pw.flush();
        pw.close();
    }

}
