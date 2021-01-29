package com.epam.second.task.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CharArrayStringProcessor implements StringProcessor {

    private final static Logger LOGGER = LogManager.getLogger(CharArrayStringProcessor.class);

    @Override
    public String replaceLetterInWords(String string, int position, char replacement)
            throws WrongSymbolPositionException {
        checkPosition(position);
        StringBuilder result = new StringBuilder();
        int wordBegin = 0;
        int wordEnd = 0;
        for (int i = 0; i < string.length(); i++) {
            if (isWordEnd(string, i)) {
                String newWord = replaceSymbol(string, position, replacement, wordBegin, wordEnd);
                result.append(newWord);
                wordBegin = i + 1;
            }
            wordEnd++;
        }
        return result.toString();
    }

    private void checkPosition(int position) throws WrongSymbolPositionException {
        if (position < 0) {
            WrongSymbolPositionException exception =
                    new WrongSymbolPositionException("Wrong position for replacing symbols");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    private String replaceSymbol(String string, int position, char replacement, int wordBegin, int wordEnd) {
        StringBuilder newWord = new StringBuilder();
        for (int i = wordBegin; i <= wordEnd; i++) {
            if (shouldReplace(string, position, wordBegin, i)) {
                newWord.append(replacement);
            } else {
                newWord.append(string.charAt(i));
            }
        }
        return newWord.toString();
    }

    @Override
    public String deleteConsonantStartedWords(String string, int chosenLength) {
        StringBuilder result = new StringBuilder();
        int wordBegin = 0;
        int wordEnd = 0;
        for (int i = 0; i < string.length(); i++) {
            if (isWordEnd(string, i)) {
                if (!shouldDelete(string, wordBegin, wordEnd, chosenLength)) {
                    String word = extractWord(string, wordBegin, wordEnd);
                    result.append(word);
                }
                wordBegin = i + 1;
            }
            wordEnd++;
        }
        return result.toString();
    }

    private boolean shouldReplace(String string, int position, int wordBegin, int i) {
        return i - wordBegin == position && string.charAt(i) != ' ';
    }

    private boolean isWordEnd(String string, int i) {
        char letter = string.charAt(i);
        return i == string.length() - 1 || letter == ' ';
    }

    private String extractWord(String string, int wordBegin, int wordEnd) {
        StringBuilder word = new StringBuilder();
        for (int j = wordBegin; j <= wordEnd; j++) {
            word.append(string.charAt(j));
        }
        return word.toString();
    }

    private boolean shouldDelete(String string, int wordBegin, int wordEnd, int chosenLength) {
        char last = string.charAt(wordEnd);
        int end = (last == ' ') ? wordEnd : wordEnd + 1;
        if (end - wordBegin != chosenLength) {
            return false;
        }
        char firstLetter = string.charAt(wordBegin);
        return (firstLetter != 'a' && firstLetter != 'e' && firstLetter != 'y' &&
                firstLetter != 'u' && firstLetter != 'i' && firstLetter != 'o');
    }
}
