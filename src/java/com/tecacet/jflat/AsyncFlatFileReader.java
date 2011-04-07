package com.tecacet.jflat;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <b>Experimental </b> Asynchronous reader
 * 
 * @author dimitri
 *
 * @param <T>
 */
public class AsyncFlatFileReader<T> extends FlatFileReader<T> {

	private transient ExecutorService executor;
	private transient int threads;

	public AsyncFlatFileReader(Reader reader, LineParser parser,
			ReaderRowMapper<T> mapper, int threads) {
		super(reader, parser, mapper);
		this.threads = threads;
	}

	@Override
	public void readWithCallback(FlatFileReaderCallback<T> callback)
			throws IOException {
		executor = Executors.newFixedThreadPool(threads);
		super.readWithCallback(callback);
		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void processRow(FlatFileReaderCallback<T> callback, int row,
			String[] nextLineAsTokens, T bean) {
		LineWorker lineWorker = new LineWorker(callback, nextLineAsTokens, row,
				bean);
		executor.execute(lineWorker);
	}

	private class LineWorker implements Runnable {

		private FlatFileReaderCallback<T> callback;
		private String[] nextLineAsTokens;
		private int row;
		private T bean;

		public LineWorker(FlatFileReaderCallback<T> callback,
				String[] nextLineAsTokens, int row, T bean) {
			super();
			this.callback = callback;
			this.nextLineAsTokens = nextLineAsTokens;
			this.row = row;
			this.bean = bean;
		}
		
		@Override
		public void run() {
			callback.processRow(row, nextLineAsTokens, bean);

		}

	}
}
