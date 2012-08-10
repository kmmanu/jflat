package com.tecacet.jflat;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

public class LineNumberLineIterator implements LineIterator {

    private LineNumberReader lineNumberReader;

    public LineNumberLineIterator(Reader reader) {
        lineNumberReader = new LineNumberReader(reader);
    }

    @Override
    public String getNextLine() throws IOException {
        return lineNumberReader.readLine();
    }

    public int getLineNumber() {
        return lineNumberReader.getLineNumber();
    }

    @Override
    public void close() throws IOException {
        lineNumberReader.close();
    }

}
