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

import com.microsoft.playwright.Page;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/**
 * The PageFactory class is responsible for initializing annotated locators in a page object.
 */
public class PageFactory {
    /**
     * A factory class for creating locators based on different strategies.
     */
    public PageFactory() {
        // For javadoc purpose
    }

    /**
     * Map containing locators for different How types.
     * The map is initialized with lambda expressions that invoke static methods from PageFactory class to obtain locators.
     * The keys of the map are of type How enum and the values are BiFunction objects that accept a Page object and a LocateBy object
     * and return a generic type value.
     */
    private static final Map<How, BiFunction<Page, LocateBy, ?>> LOCATORS_MAP = new HashMap<>() {
        {
            put(How.ALT_TEXT, PageFactory::getLocatorByAltText);
            put(How.LABEL, PageFactory::getLocatorByLabel);
            put(How.PLACEHOLDER, PageFactory::getLocatorByPlaceholder);
            put(How.ROLE, PageFactory::getLocatorByRole);
            put(How.TEST_ID, PageFactory::getLocatorByTestId);
            put(How.TEXT, PageFactory::getLocatorByText);
            put(How.TITLE, PageFactory::getLocatorByTitle);
        }
    };

    /**
     * Returns the locator object based on the provided parameters.
     *
     * @param getByValue    the function that retrieves the locator object by value
     * @param getByPattern  the function that retrieves the locator object by pattern
     * @param locateBy      the LocateBy object containing the value and pattern parameters
     * @return the locator object retrieved based on the parameters
     */
    private static Object getLocator(Function<String, ?> getByValue, Function<Pattern, ?> getByPattern, LocateBy locateBy) {
        String pattern = locateBy.pattern();
        return pattern.isEmpty() ? getByValue.apply(locateBy.value()) :
                getByPattern.apply(Pattern.compile(pattern));
    }

    /**
     * Returns the locator object based on the specified alt text.
     *
     * @param page The page object containing the getByAltText methods.
     * @param locateBy The specific locateBy object to use.
     * @return The locator object based on the alt text.
     */
    private static Object getLocatorByAltText(Page page, LocateBy locateBy) {
        return getLocator(page::getByAltText, page::getByAltText, locateBy);
    }

    /**
     * Gets the locator by label for the given page and locateBy enum
     *
     * @param page The page object to get the locator from
     * @param locateBy The type of locator to retrieve
     * @return The locator object for the given locateBy type
     */
    private static Object getLocatorByLabel(Page page, LocateBy locateBy) {
        return getLocator(page::getByLabel, page::getByLabel, locateBy);
    }

    /**
     * Retrieves the locator by placeholder.
     *
     * @param page      the page to retrieve the placeholder from
     * @param locateBy  the locate by strategy
     * @return the locator object
     */
    private static Object getLocatorByPlaceholder(Page page, LocateBy locateBy) {
        return getLocator(page::getByPlaceholder, page::getByPlaceholder, locateBy);
    }

    /**
     * Returns the locator by role for the given page and locateBy object.
     *
     * @param page The page object to locate the element in.
     * @param locateBy The locateBy object that specifies the role and value.
     * @return The locator object returned by the page getByRole method.
     */
    private static Object getLocatorByRole(Page page, LocateBy locateBy) {
        return page.getByRole(locateBy.ariaRole(), new Page.GetByRoleOptions().setName(locateBy.value()));
    }

    /**
     * Returns the locator object by test id.
     *
     * @param page The Page object to search for the test id.
     * @param locateBy The LocateBy enum specifying the type of search.
     * @return The locator object.
     */
    private static Object getLocatorByTestId(Page page, LocateBy locateBy) {
        return getLocator(page::getByTestId, page::getByTestId, locateBy);
    }

    /**
     * Returns the locator for the specified text using the provided page object and locateBy strategy.
     *
     * @param page       The page object to use for locating the element.
     * @param locateBy   The strategy to use for locating the element.
     * @return The locator object.
     */
    private static Object getLocatorByText(Page page, LocateBy locateBy) {
        return getLocator(page::getByText, page::getByText, locateBy);
    }

    /**
     * Retrieves the locator based on the given title.
     *
     * @param page      the Page object used to retrieve the locator
     * @param locateBy  the LocateBy object used to specify the search criteria
     * @return the locator object matching the given title
     */
    private static Object getLocatorByTitle(Page page, LocateBy locateBy) {
        return getLocator(page::getByTitle, page::getByTitle, locateBy);
    }

    /**
     * Generates a locator for a given page and locateBy strategy.
     *
     * @param page      The page object to generate the locator for.
     * @param locateBy  The LocateBy strategy to use.
     * @return The generated locator object.
     */
    private static Object generateLocator(Page page, LocateBy locateBy) {
        return LOCATORS_MAP.getOrDefault(locateBy.how(), (p, l) -> p.locator(l.value())).apply(page, locateBy);
    }

    /**
     * Initializes the field locator for a given page object field.
     *
     * @param page        The current page object.
     * @param pageObject  The instance of the page object containing the field.
     * @param field       The field for which the locator needs to be initialized.
     */
    private static void initializeFieldLocator(Page page, Object pageObject, Field field) {
        LocateBy locateBy = field.getAnnotation(LocateBy.class);
        field.setAccessible(true);
        try {
            if (locateBy.how() != How.UNSET) {
                Object locator = generateLocator(page, locateBy);
                field.set(pageObject, locator);
            } else {
                field.set(pageObject, page.locator(locateBy.value()));
            }
        } catch (IllegalAccessException ignored) {
        }
    }

    /**
     * Initializes annotated locators in a page object.
     *
     * @param page        the page object to initialize locators for
     * @param pageObject  the instance of the page object to initialize locators on
     */
    public static void initAnnotatedLocators(Page page, Object pageObject) {
        Arrays.stream(pageObject.getClass().getDeclaredFields())
                .filter(field -> !isNull(field.getAnnotation(LocateBy.class)))
                .forEach(field -> initializeFieldLocator(page, pageObject, field));
    }
}
