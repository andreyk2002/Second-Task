package com.epam.second.task.logic;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public abstract class StringProcessorTest {
    private StringProcessor processor;
    private static final String NON_REPLACEABLE_DATA = "2 3 4";
    private static final String ONE_REPLACE_REQUIRED = "FDAd you do";
    private static final String ONE_REPLACE_APPLIED = "FDAH you do";
    private static final String MULTIPLE_REPLACES_REQUIRED = "Bi Wello Sow Bero";
    private static final String MULTIPLE_REPLACES_APPLIED = "Hi Hello How Hero";
    private static final String ONLY_VOWELS_STRING = "aaa ooo uuu";
    private static final String MULTIPLE_WORDS_TO_DELETE = "many data must be deleted";
    private static final String MULTIPLE_WORDS_DELETED = "be deleted";
    private static final char REPLACEMENT = 'H';

    protected abstract StringProcessor createProcessor();

    @BeforeTest
    public void init() {
        processor = createProcessor();
    }

    @Test
    public void testReplaceLetterInWordsShouldNotReplaceWhenItIsNothingToReplace()
            throws WrongSymbolPositionException {
        //when
        String result = processor.replaceLetterInWords(NON_REPLACEABLE_DATA, 2, 's');
        //then
        Assert.assertEquals(result, NON_REPLACEABLE_DATA);
    }

    @Test
    public void testReplaceLetterInWordsShouldReplaceWhenOneReplacementApplied()
            throws WrongSymbolPositionException {
        //when
        String result = processor.replaceLetterInWords(ONE_REPLACE_REQUIRED, 3, REPLACEMENT);
        //then
        Assert.assertEquals(result, ONE_REPLACE_APPLIED);
    }

    @Test
    public void testReplaceLetterInWordsShouldReplaceWhenMultipleReplacementApplied()
            throws WrongSymbolPositionException {
        //when
        String result = processor.replaceLetterInWords(MULTIPLE_REPLACES_REQUIRED, 0, REPLACEMENT);
        //then
        Assert.assertEquals(result, MULTIPLE_REPLACES_APPLIED);
    }

    @Test(expectedExceptions = WrongSymbolPositionException.class)
    public void testReplaceLetterInWordsShouldReplaceWhenPositionIsNegativeReplacementApplied()
            throws WrongSymbolPositionException {
        //when
        String result = processor.replaceLetterInWords(NON_REPLACEABLE_DATA, -1, REPLACEMENT);
        //then
        Assert.assertEquals(result, NON_REPLACEABLE_DATA);
    }

    @Test
    public void testDeleteConsonantStartedWordsShouldNotDeleteWhenOnlyVowelsApplied() {
        //when
        String result = processor.deleteConsonantStartedWords(ONLY_VOWELS_STRING, 3);
        //then
        Assert.assertEquals(result, ONLY_VOWELS_STRING);
    }

    @Test
    public void testDeleteConsonantStartedWordsShouldDeleteWhenMultipleSuitableWordsApplied() {
        //when
        String result = processor.deleteConsonantStartedWords(MULTIPLE_WORDS_TO_DELETE, 4);
        //then
        Assert.assertEquals(result, MULTIPLE_WORDS_DELETED);
    }

    @Test
    public void testDeleteConsonantStartedWordsShouldThrowAnExceptionWhenChosenLengthIsNegative() {
        //when
        String result = processor.deleteConsonantStartedWords(NON_REPLACEABLE_DATA, -1);
        //then
        Assert.assertEquals(result, NON_REPLACEABLE_DATA);
    }

}
