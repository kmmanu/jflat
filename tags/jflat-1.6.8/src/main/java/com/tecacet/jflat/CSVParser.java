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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses a line into tokens based on a CSV separator. Uses a LineIterator to
 * access the next line if an entry spans multiple lines. This is possible for
 * values within quotes.
 * 
 * @author Dimitri Papaioannou
 * 
 */
public class CSVParser implements LineParser {

	/** The default separator to use if none is supplied to the constructor. */
	private static final char DEFAULT_SEPARATOR = ',';

	/**
	 * The default quote character to use if none is supplied to the
	 * constructor.
	 */
	private static final char DEFAULT_QUOTE_CHARACTER = '"';

	private char separator;

	private char quotechar;

	private LineIterator lineIterator;

	public CSVParser(LineIterator lineIterator) {
		this.lineIterator = lineIterator;
		this.separator = DEFAULT_SEPARATOR;
		this.quotechar = DEFAULT_QUOTE_CHARACTER;
	}

	public String[] parseLine(String nextLine) throws IOException {

		if (nextLine == null) {
			return null;
		}

		List<String> tokens = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		boolean inQuotes = false;
		do {
			if (inQuotes) {
				// continuing a quoted section, re-append newline
				sb.append("\n");
				nextLine = lineIterator.getNextLine();
				if (nextLine == null)
					break;
			}
			for (int i = 0; i < nextLine.length(); i++) {

				char c = nextLine.charAt(i);
				if (c == quotechar) {
					// this gets complex... the quote may end a quoted block, or
					// escape another quote.
					// do a 1-char lookahead:
					if (inQuotes // we are in quotes, therefore there can be
							// escaped quotes in here.
							&& nextLine.length() > (i + 1) // there is indeed
							// another character
							// to check.
							&& nextLine.charAt(i + 1) == quotechar) { // ..and
						// that char is a quote also.
						// we have two quote chars in a row == one quote char,
						// so consume them both and
						// put one on the token. we do *not* exit the quoted
						// text.
						sb.append(nextLine.charAt(i + 1));
						i++;
					} else {
						inQuotes = !inQuotes;
						// the tricky case of an embedded quote in the middle:
						// a,bc"d"ef,g
						if (i > 2 // not on the begining of the line
								&& nextLine.charAt(i - 1) != this.separator // not
								// at the begining
								// of an escape sequence
								&& nextLine.length() > (i + 1)
								&& nextLine.charAt(i + 1) != this.separator // not
						// at the end of an escape sequence
						) {
							sb.append(c);
						}
					}
				} else if (c == separator && !inQuotes) {
					// an empty field equates to null
					tokens.add(sb.length() > 0 ? sb.toString() : null);

					// start work on next token, if there is one
					sb = new StringBuffer();
				} else {
					sb.append(c);
				}
			}
		} while (inQuotes);
		// to account for final field...
		tokens.add(sb.length() > 0 ? sb.toString() : null);
		return tokens.toArray(new String[0]);

	}

	public char getSeparator() {
		return separator;
	}

	public void setSeparator(char separator) {
		this.separator = separator;
	}

	public char getQuotechar() {
		return quotechar;
	}

	public void setQuotechar(char quotechar) {
		this.quotechar = quotechar;
	}

	public LineIterator getLineIterator() {
		return lineIterator;
	}

	public void setLineIterator(LineIterator lineIterator) {
		this.lineIterator = lineIterator;
	}

}
