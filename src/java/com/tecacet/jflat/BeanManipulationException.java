/*
 Copyright 2008 TecaceT Ltd.

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
 * Exception that indicates a failure to manipulate a bean.
 * 
 * 
 * @author Dimitri Papaioannou
 *
 */
public class BeanManipulationException extends RuntimeException {
    
    private static final long serialVersionUID = -1272875023684890665L;

    public BeanManipulationException(Throwable t) {
       super(t);
    }

}
