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

/**
 * Convert a bean into an array of tokens.
 * 
 * @param <T>
 *            type of source object
 * 
 * @author Dimitri Papaioannou
 */
public interface WriterRowMapper<T> {

    /**
     * Extract an array of tokens from a bean
     * 
     * @param bean
     *            the bean
     */
    String[] getRow(T bean);
}
