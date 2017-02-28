package com.afollestad.ason;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.afollestad.ason.Util.*;
import static org.junit.Assert.*;

/**
 * @author Aidan Follestad (afollestad)
 */
public class UtilTest {

    @SuppressWarnings("unused") static class DefaultCtorClass {

        private String hiddenField;

        @SuppressWarnings("unused") public DefaultCtorClass() {
        }
    }

    @SuppressWarnings("unused") static class NoDefaultCtorClass {

        @SuppressWarnings("unused") public NoDefaultCtorClass(String name) {
        }
    }

    @SuppressWarnings({"FieldCanBeLocal", "unused", "MismatchedQueryAndUpdateOfCollection"})
    private List<Ason> listField;

    @Test public void generic_list_type_test() throws Exception {
        listField = new ArrayList<>(0);
        Field field = getClass().getDeclaredField("listField");
        assertEquals(Ason.class, listGenericType(field));
    }

    @Test public void test_is_number_true() {
        assertTrue(isNumber("1234"));
        assertTrue(isNumber("67891023231"));
    }

    @Test public void test_is_number_false() {
        assertFalse(isNumber("hi"));
        assertFalse(isNumber("@1234"));
        assertFalse(isNumber("1234!%"));
    }

    @Test public void test_is_json_array_true() {
        assertTrue(isJsonArray("[]"));
        assertTrue(isJsonArray("   []    "));
    }

    @Test public void test_is_json_array_false() {
        assertFalse(isJsonArray(""));
        assertFalse(isJsonArray("{}"));
        assertFalse(isJsonArray("  abc"));
    }

    @Test public void test_no_default_ctor() {
        try {
            getDefaultConstructor(NoDefaultCtorClass.class);
            assertFalse("No exception thrown for no default constructor!", false);
        } catch (IllegalStateException ignored) {
        }
    }

    @Test public void test_cant_access_field() throws Exception {
        DefaultCtorClass instance = (DefaultCtorClass) getDefaultConstructor(
                DefaultCtorClass.class).newInstance();
        Field field = DefaultCtorClass.class.getDeclaredField("hiddenField");
        try {
            setFieldValue(field, instance, "Test");
            assertFalse("No exception was thrown for accessing inaccessible field!", false);
        } catch (RuntimeException ignored) {
        }
    }
}
