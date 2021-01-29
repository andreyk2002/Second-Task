package com.epam.second.task.logic;

public class RegexStringProcessorTest extends StringProcessorTest {

    @Override
    protected StringProcessor createProcessor() {
        return new RegexStringProcessor();
    }
}
