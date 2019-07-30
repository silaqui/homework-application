package com.example.homework.application.dao.helpers;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QueryHelperTest {

    private static final String STRING_ONE = "abCde";
    private static final String STRING_TWO = "kJl";
    private static final String SUB_STRING_ONE = "BcD";
    private static final String INVALID_SUB_STRING = "XYZ";

    private QueryHelper tested = new QueryHelper();

    @Test
    public void shouldPhraseInString() {
        //given
        //when
        boolean actual = tested.isPhraseInString(STRING_ONE, SUB_STRING_ONE);
        //then
        assertThat(actual).isTrue();
    }

    @Test
    public void shouldPhraseInStringIsFalse() {
        //given
        //when
        boolean actual = tested.isPhraseInString(STRING_ONE, INVALID_SUB_STRING);
        //then
        assertThat(actual).isFalse();
    }

    @Test
    public void shouldPhraseInListOfString() {
        //given
        List<String> list = Arrays.asList(STRING_ONE, STRING_TWO);
        //when
        boolean actual = tested.isPhraseInListOfString(list, SUB_STRING_ONE);
        //then
        assertThat(actual).isTrue();
    }

    @Test
    public void shouldPhraseInListOfStringIsFalse() {
        //given
        List<String> list = Arrays.asList(STRING_ONE, STRING_TWO);
        //when
        boolean actual = tested.isPhraseInListOfString(list, INVALID_SUB_STRING);
        //then
        assertThat(actual).isFalse();
    }

    @Test
    public void shouldPhraseInListOfStringNullList() {
        //given
        List<String> list = null;
        String subString = "XYZ";
        //when
        boolean actual = tested.isPhraseInListOfString(list, subString);
        //then
        assertThat(actual).isFalse();
    }
}
