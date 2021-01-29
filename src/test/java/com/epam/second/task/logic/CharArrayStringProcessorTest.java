package com.epam.second.task.logic;

public class CharArrayStringProcessorTest extends StringProcessorTest {
    @Override
    protected StringProcessor createProcessor() {
        return new CharArrayStringProcessor();
    }
}
