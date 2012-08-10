package com.tecacet.jflat;

import java.io.BufferedReader;
import java.io.IOException;

public class BufferedReaderLineIterator implements LineIterator {

	protected BufferedReader br;

	public BufferedReaderLineIterator(BufferedReader br) {
		super();
		this.br = br;
	}

	@Override
	public String getNextLine() throws IOException {
		return br.readLine();
	}

	@Override
	public void close() throws IOException {
		br.close();
	}
}
