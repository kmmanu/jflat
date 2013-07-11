package com.tecacet.jflat.util;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.tecacet.jflat.util.MapReader;

public class MapReaderTest {

    @Test
    public void testReadMap() throws IOException {
        String columns = "build-code.properties";
        MapReader mapReader = new MapReader();
        Map<String, String> map = mapReader.readMap(new FileInputStream(columns));
        //TODO
    }

}
