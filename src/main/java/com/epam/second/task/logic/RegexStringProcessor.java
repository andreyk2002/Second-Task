package com.epam.second.task.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexStringProcessor implements StringProcessor {

    private final static Logger LOGGER = LogManager.getLogger(RegexStringProcessor.class);
    private final static String WORD_WITH_DELIMITERS = "\\S+\\s+|\\S+\\s*$";
    private final static String DELETING_WORD_PATTERN = "[^\\saeiouyAEIOUY]\\S*";

    @Override
    public String replaceLetterInWords(String string, int position, char newSymbol) throws WrongSymbolPositionException {
        Matcher matcher = getMatcher(string);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String modifiedWord = modifyWord(position, newSymbol, matcher);
            result.append(modifiedWord);
        }
        return result.toString();
    }

    private String modifyWord(int position, char newSymbol, Matcher matcher) throws WrongSymbolPositionException {
        String modifiedWord;
        String group = matcher.group();
        String word = group.trim();
        if (word.length() <= position) {
            return group;
        }
        try {
            StringBuilder replacedGroup = replaceSymbol(position, newSymbol, group);
            modifiedWord = replacedGroup.toString();
        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
            throw new WrongSymbolPositionException(e.getMessage(), e);
        }
        return modifiedWord;
    }

    @Override
    public String deleteConsonantStartedWords(String string, int chosenLength) {
        Matcher matcher = getMatcher(string);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String group = matcher.group();
            String word = group.trim();
            if (!shouldDelete(chosenLength, word)) {
                result.append(group);
            }
        }
        return result.toString();
    }

    private boolean shouldDelete(int chosenLength, String word) {
        return word.matches(DELETING_WORD_PATTERN) && word.length() == chosenLength;
    }

    private Matcher getMatcher(String string) {
        Pattern pattern = Pattern.compile(WORD_WITH_DELIMITERS);
        return pattern.matcher(string);
    }

    private StringBuilder replaceSymbol(int position, char newSymbol, String word) {
        StringBuilder replacedWord = new StringBuilder(word);
        String replacement = String.valueOf(newSymbol);
        replacedWord.replace(position, position + 1, replacement);
        return replacedWord;
    }


}
