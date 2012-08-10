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

import java.io.Writer;

/**
 * A writer for CSV files
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public class CSVWriter<T> extends FlatFileWriter<T> {

    public CSVWriter(Writer writer, WriterRowMapper<T> mapper) {
        super(writer, new CSVLineMerger(), mapper);
    }

    public CSVWriter(Writer writer, Class<T> type, String[] properties) {
        super(writer, new CSVLineMerger(), new BeanWriterRowMapper<T>(type, properties));
    }
}
