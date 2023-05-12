package com.cp.compiler.e2e.problems;

import com.cp.compiler.controllers.CompilerController;
import com.cp.compiler.models.Language;
import com.cp.compiler.models.Response;
import com.cp.compiler.models.Verdict;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@Slf4j
@DirtiesContext
@SpringBootTest
public class MakeEvenTests {
    
    @Autowired
    private CompilerController compilerController;
    
    @DisplayName("Make even Problem test case 1")
    @Test
    void makeEvenTest1ShouldReturnAcceptedVerdict() throws Exception {
        // Given
        File sourceCodeFile = new File("src/test/resources/sources/problems/MakeEven.py");
        MultipartFile sourceCode = new MockMultipartFile("MakeEven.py",
                                                         "MakeEven.py",
                                                         null,
                                                         new FileInputStream(sourceCodeFile));
        
        File expectedOutputFile = new File("src/test/resources/outputs/makeEven/makeEven-1.txt");
        MultipartFile expectedOutput = new MockMultipartFile("makeEven-1.txt",
                                                             "makeEven-1.txt",
                                                             null,
                                                             new FileInputStream(expectedOutputFile));
        
        File inputFile = new File("src/test/resources/inputs/makeEven/makeEven-1.txt");
        MultipartFile inputs = new MockMultipartFile("makeEven-1.txt",
                                                     "makeEven-1.txt",
                                                     null,
                                                     new FileInputStream(inputFile));
        
        // When
        ResponseEntity<Object> responseEntity = compilerController.compile(
                Language.PYTHON,
                sourceCode,
                inputs,
                expectedOutput,
                3,
                500,
                null,
                null,
                "");
        
        // Then
        Assertions.assertEquals(
                Verdict.ACCEPTED.getStatusResponse(),
                ((Response)responseEntity.getBody()).getVerdict());
    }
}
