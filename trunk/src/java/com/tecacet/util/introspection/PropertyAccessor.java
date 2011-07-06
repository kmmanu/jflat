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
package com.tecacet.util.introspection;

/**
 * Abstraction for getting and setting object properties
 * 
 * @author Dimitri Papaioannou
 * 
 * @param <T>
 */
public interface PropertyAccessor<T> {

    /**
     * Get a named property value from a bean Return NULL if any object is NULL
     * when trying to get the specified property
     * 
     * @param bean
     * @param propertyName
     */
    Object getProperty(T bean, String propertyName) throws BeanIntrospectorException;

    /**
     * Set a named property value
     * 
     * @param bean
     * @param propertyName
     * @param value
     * @throws BeanIntrospectorException
     */
    void setProperty(T bean, String propertyName, Object value) throws BeanIntrospectorException;

}