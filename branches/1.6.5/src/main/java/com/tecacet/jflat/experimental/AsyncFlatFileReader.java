package com.tecacet.jflat.experimental;


import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.tecacet.jflat.FlatFileReaderCallback;
import com.tecacet.jflat.LineNumberLineIterator;
import com.tecacet.jflat.LineParser;
import com.tecacet.jflat.ReaderRowMapper;
import com.tecacet.jflat.StructuredFileReader;



/**
 * <b>Experimental </b> Asynchronous reader
 * 
 * @author dimitri
 * 
 * @param <T>
 */
public class AsyncFlatFileReader<T> implements StructuredFileReader<T> {

    private LineNumberLineIterator lineIterator;
    private final LineParser lineParser;
    private final ReaderRowMapper<T> rowMapper;

    private transient int threads;
    private transient int batchSize;

    public AsyncFlatFileReader(Reader reader, LineParser parser, ReaderRowMapper<T> rowMapper, int threads, int batchSize) {
        lineIterator = new LineNumberLineIterator(reader);
        this.lineParser = parser;
        this.rowMapper = rowMapper;
        this.threads = threads;
        this.batchSize = batchSize;
    }

    @Override
    public void readWithCallback(FlatFileReaderCallback<T> callback) throws IOException {
        Integer i;
        do {
            i = readWithCallback(callback, batchSize);
        } while (i != null);
        
    }

    public Integer readWithCallback(final FlatFileReaderCallback<T> callback, int lines) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        final AtomicInteger rows = new AtomicInteger(0);

        // TODO skip lines
        while (lineIterator.getLineNumber() < lines) {
            final String line = lineIterator.getNextLine();
            if (line == null) {
                break;
            }
            final int lineNumber = lineIterator.getLineNumber();
            executor.execute(new RowRunnable(lineParser, callback, rowMapper, rows, lineNumber, line));
        }// end for
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); //TODO
        }

        return rows.get() < lines ? null : lines;
    }

    private final class RowRunnable implements Runnable {

        private final LineParser lineParser;
        private final ReaderRowMapper<T> rowMapper;
        private final FlatFileReaderCallback<T> callback;

        private final AtomicInteger rows;
        private final int lineNumber;
        private final String line;

        public RowRunnable(LineParser lineParser, FlatFileReaderCallback<T> callback, ReaderRowMapper<T> rowMapper,
                AtomicInteger rows, int lineNumber, String line) {
            super();
            this.lineParser = lineParser;
            this.rowMapper = rowMapper;
            this.rows = rows;
            this.lineNumber = lineNumber;
            this.line = line;
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                String[] nextLineAsTokens = lineParser.parseLine(line);
                T bean = (T) rowMapper.getRow(nextLineAsTokens, rows.incrementAndGet());
                callback.processRow(lineNumber, nextLineAsTokens, bean);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws IOException {
        lineIterator.close();
    }

    @Override
    public List<T> readAll() throws IOException {
       throw new UnsupportedOperationException();
    }

}
