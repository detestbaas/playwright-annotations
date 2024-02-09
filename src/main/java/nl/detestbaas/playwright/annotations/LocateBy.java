/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
package nl.detestbaas.playwright.annotations;

import com.microsoft.playwright.options.AriaRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that is used to specify how to locate an element.
 * This annotation can be applied to fields to specify the strategy to locate the element.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LocateBy {
    /**
     * Returns the 'How' strategy used to locate an element.
     *
     * @return The 'How' strategy used to locate an element.
     */
    How how() default How.UNSET;

    /**
     * Returns the value of the "value" parameter of the annotation.
     *
     * @return the value of the "value" parameter
     */
    String value() default "";

    /**
     * Returns the pattern used to locate an element.
     *
     * @return The pattern used to locate an element.
     */
    String pattern() default "";

    /**
     * Returns the AriaRole value specified in the {@link LocateBy} annotation.
     * AriaRole is an enumeration that represents the ARIA roles defined in the W3C ARIA specification.
     * It is used to locate an element based on its role attribute.
     *
     * @return The AriaRole value specified in the {@link LocateBy} annotation. If not specified, returns AriaRole.NONE.
     * @see LocateBy
     * @see AriaRole
     */
    AriaRole ariaRole() default AriaRole.NONE;
}
