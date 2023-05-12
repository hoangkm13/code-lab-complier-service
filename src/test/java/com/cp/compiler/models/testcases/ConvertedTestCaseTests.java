package com.cp.compiler.models.testcases;

import com.cp.compiler.mappers.TestCaseMapper;
import com.cp.compiler.models.testcases.ConvertedTestCase;
import com.cp.compiler.models.testcases.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ConvertedTestCaseTests {
    
    @Test
    void freeMemoryShouldSetAttributeToNull() throws IOException {
        // Given
        var testCase = new TestCase("input", "expectedOutput");
        ConvertedTestCase convertedTestCase = TestCaseMapper.toConvertedTestCase(testCase, "testCase1");
        
        // When
        convertedTestCase.freeMemorySpace();
        
        // Then
        Assertions.assertNull(convertedTestCase.getInputFile());
        Assertions.assertNull(convertedTestCase.getExpectedOutput());
    }
}
