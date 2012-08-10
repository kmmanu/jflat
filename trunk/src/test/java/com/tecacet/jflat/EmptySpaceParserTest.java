package com.tecacet.jflat;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class EmptySpaceParserTest {

    @Test
    public void testParseLine() throws IOException {
        EmptySpaceParser parser = new EmptySpaceParser();
        String[] tokens = parser.parseLine("  Hello       There    Judy");
        assertEquals(3, tokens.length);
    }

}
