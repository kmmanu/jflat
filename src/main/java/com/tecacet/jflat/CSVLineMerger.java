/*
 Copyright 2008  TecAceT Ltd.

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

/**
 * Merges tokens into a line using a CSV separator and adding quotes
 * 
 * @author Dimitri Papaioannou
 * 
 */
public class CSVLineMerger implements LineMerger {

    /** The character used for escaping quotes. */
    public static final char DEFAULT_ESCAPE_CHARACTER = '"';

    /** The default separator to use if none is supplied to the constructor. */
    public static final char DEFAULT_SEPARATOR = ',';

    /**
     * The default quote character to use if none is supplied to the constructor.
     */
    public static final char DEFAULT_QUOTE_CHARACTER = '"';

    /** The quote constant to use when you wish to suppress all quoting. */
    public static final char NO_QUOTE_CHARACTER = '\u0000';
    /** The escape constant to use when you wish to suppress all escaping. */
    public static final char NO_ESCAPE_CHARACTER = '\u0000';

    private char separator;

    private char quotechar;

    private char escapechar;

    /**
     * Create a merger with the standard CSV attributes.
     */
    public CSVLineMerger() {
        separator = DEFAULT_SEPARATOR;
        quotechar = DEFAULT_QUOTE_CHARACTER;
        escapechar = DEFAULT_ESCAPE_CHARACTER;
    }

    public String makeLine(String[] nextLine) {
        if (nextLine == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < nextLine.length; i++) {

            if (i != 0) {
                sb.append(separator);
            }

            String nextElement = nextLine[i];
            if (nextElement == null) {
                continue;
            }
            if (quotechar != NO_QUOTE_CHARACTER) {
                sb.append(quotechar);
            }
            for (int j = 0; j < nextElement.length(); j++) {
                char nextChar = nextElement.charAt(j);
                if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
                    sb.append(escapechar).append(nextChar);
                } else if (escapechar != NO_ESCAPE_CHARACTER && nextChar == escapechar) {
                    sb.append(escapechar).append(nextChar);
                } else {
                    sb.append(nextChar);
                }
            }
            if (quotechar != NO_QUOTE_CHARACTER) {
                sb.append(quotechar);
            }
        }
        return sb.toString();
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

    public char getEscapechar() {
        return escapechar;
    }

    public void setEscapechar(char escapechar) {
        this.escapechar = escapechar;
    }

}
