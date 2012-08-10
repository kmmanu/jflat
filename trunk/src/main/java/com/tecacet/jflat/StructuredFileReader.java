package com.tecacet.jflat;

import java.io.IOException;
import java.util.List;

public interface StructuredFileReader<T> {

    /**
     * Navigate the flat file and invoke the callback for each row.
     * 
     * @param callback
     * @throws IOException
     */
    void readWithCallback(FlatFileReaderCallback<T> callback) throws IOException;

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
    List<T> readAll() throws IOException;

    void close() throws IOException;
}