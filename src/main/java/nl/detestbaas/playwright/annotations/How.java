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

/**
 * Enumeration representing different strategies for locating an element.
 */
public enum How {
    /**
     * Represents an alternate text for an element.
     */
    ALT_TEXT,
    /**
     * Represents a label for an element.
     */
    LABEL,
    /**
     * Represents a placeholder value for an element.
     * This can be used to indicate that the element should display a temporary or default value until it is replaced with actual content.
     *
     * Usage:
     * PLACEHOLDER placeholder = PLACEHOLDER.PLACEHOLDER;
     */
    PLACEHOLDER,
    /**
     * Represents the role of an element.
     */
    ROLE,
    /**
     * Represents the ID associated with a test.
     */
    TEST_ID,
    /**
     * Represents a text value.
     *
     * This variable is used to store a text value that can be associated with different elements or objects in a system.
     * The text can be used as an alternate text, label, placeholder, role, test ID, title, or for other purposes.
     *
     */
    TEXT,
    /**
     * Represents the title of an element.
     */
    TITLE,
    /**
     * Represents an unset value for a field.
     *
     * <p>This value is used to indicate that a field has not been set or has been intentionally left blank.</p>
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * How how = How.UNSET;
     * }</pre>
     */
    UNSET
}
