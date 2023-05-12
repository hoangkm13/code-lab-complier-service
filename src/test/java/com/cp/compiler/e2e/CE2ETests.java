package com.cp.compiler.e2e;

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
class CE2ETests {
    
    @Autowired
    private CompilerController compilerController;
    
    /**
     * Should return accepted statusResponse.
     *
     * @throws Exception the exception
     */
    @DisplayName("C Accepted Verdict")
    @Test
    void shouldReturnAcceptedVerdict() throws Exception {
        // Given
        File sourceCodeFile = new File("src/test/resources/sources/c/Test1.c");
        MultipartFile sourceCode = new MockMultipartFile(
                "Test1.c",
                "Test1.c",
                null,
                new FileInputStream(sourceCodeFile));
        
        File expectedOutputFile = new File("src/test/resources/outputs/Test1.txt");
        MultipartFile expectedOutput = new MockMultipartFile(
                "Test1.txt",
                "Test1.txt",
                null,
                new FileInputStream(expectedOutputFile));
        
        // When
        ResponseEntity<Object> responseEntity = compilerController.compile(
                Language.C,
                sourceCode,
                null,
                expectedOutput,
                10,
                500,
                null,
                null,
                "");
        
        // Then
        Assertions.assertEquals(
                Verdict.ACCEPTED.getStatusResponse(),
                ((Response)responseEntity.getBody()).getVerdict());
    }
    
    /**
     * Should return time limit exceeded statusResponse.
     *
     * @throws Exception the exception
     */
    @DisplayName("C Time Limit Exceeded")
    @Test
    void shouldReturnTimeLimitExceededVerdict() throws Exception {
        // Given
        File sourceCodeFile = new File("src/test/resources/sources/c/Test2.c");
        MultipartFile sourceCode = new MockMultipartFile(
                "Test2.c",
                "Test2.c",
                null,
                new FileInputStream(sourceCodeFile));
        
        File expectedOutputFile = new File("src/test/resources/outputs/Test1.txt");
        MultipartFile expectedOutput = new MockMultipartFile(
                "Test1.txt",
                "Test1.txt",
                null,
                new FileInputStream(expectedOutputFile));
        
        // When
        ResponseEntity<Object> responseEntity = compilerController.compile(
                Language.C,
                sourceCode,
                null,
                expectedOutput,
                10,
                500,
                null,
                null,
                "");
        
        // Then
        Assertions.assertEquals(
                Verdict.TIME_LIMIT_EXCEEDED.getStatusResponse(),
                ((Response)responseEntity.getBody()).getVerdict());
    }
    
    /**
     * Should return compilation error statusResponse.
     *
     * @throws Exception the exception
     */
    @DisplayName("C Compilation Error")
    @Test
    void shouldReturnCompilationErrorVerdict() throws Exception {
        // Given
        File sourceCodeFile = new File("src/test/resources/sources/c/Test3.c");
        MultipartFile sourceCode = new MockMultipartFile(
                "Test3.c",
                "Test3.c",
                null,
                new FileInputStream(sourceCodeFile));
        
        File expectedOutputFile = new File("src/test/resources/outputs/Test1.txt");
        MultipartFile expectedOutput = new MockMultipartFile(
                "Test1.txt",
                "Test1.txt",
                null,
                new FileInputStream(expectedOutputFile));
        
        // When
        ResponseEntity<Object> responseEntity = compilerController.compile(
                Language.C,
                sourceCode,
                null,
                expectedOutput,
                10,
                500,
                null,
                null,
                "");
        
        // Then
        Assertions.assertEquals(
                Verdict.COMPILATION_ERROR.getStatusResponse(),
                ((Response)responseEntity.getBody()).getVerdict());
    }
    
    /**
     * Should return wrong answer statusResponse.
     *
     * @throws Exception the exception
     */
    @DisplayName("C Wrong Answer")
    @Test
    void shouldReturnWrongAnswerVerdict() throws Exception {
        // Given
        File sourceCodeFile = new File("src/test/resources/sources/c/Test4.c");
        MultipartFile sourceCode = new MockMultipartFile(
                "Test4.c",
                "Test4.c",
                null,
                new FileInputStream(sourceCodeFile));
        
        File expectedOutputFile = new File("src/test/resources/outputs/Test1.txt");
        MultipartFile expectedOutput = new MockMultipartFile(
                "Test1.txt",
                "Test1.txt",
                null,
                new FileInputStream(expectedOutputFile));
        
        // When
        ResponseEntity<Object> responseEntity = compilerController.compile(
                Language.C,
                sourceCode,
                null,
                expectedOutput,
                10,
                500,
                null,
                null,
                "");
        
        // Then
        Assertions.assertEquals(
                Verdict.WRONG_ANSWER.getStatusResponse(),
                ((Response)responseEntity.getBody()).getVerdict());
    }
    
    /**
     * Should return out of memory statusResponse.
     *
     * @throws Exception the exception
     */
    @DisplayName("C Out Of Memory Error")
    @Test
    void shouldReturnOutOfMemoryVerdict() throws Exception {
        // Given
        File sourceCodeFile = new File("src/test/resources/sources/c/Test5.c");
        MultipartFile sourceCode = new MockMultipartFile(
                "Test5.c",
                "Test5.c",
                null,
                new FileInputStream(sourceCodeFile));
        
        File expectedOutputFile = new File("src/test/resources/outputs/Test1.txt");
        MultipartFile expectedOutput = new MockMultipartFile(
                "Test1.txt",
                "Test1.txt",
                null,
                new FileInputStream(expectedOutputFile));
        
        // When
        ResponseEntity<Object> responseEntity = compilerController.compile(
                Language.C,
                sourceCode,
                null,
                expectedOutput,
                10,
                1,
                null,
                null,
                "");
        
        // Then
        Assertions.assertEquals(
                Verdict.OUT_OF_MEMORY.getStatusResponse(),
                ((Response)responseEntity.getBody()).getVerdict());
    }
    
    /**
     * Should return runtime error statusResponse.
     *
     * @throws Exception the exception
     */
    @DisplayName("C Runtime Error")
    @Test
    void shouldReturnRuntimeErrorVerdict() throws Exception {
        // Given
        File sourceCodeFile = new File("src/test/resources/sources/c/Test6.c");
        MultipartFile sourceCode = new MockMultipartFile("Test6.c",
                                                         "Test6.c",
                                                         null,
                                                         new FileInputStream(sourceCodeFile));
        
        File expectedOutputFile = new File("src/test/resources/outputs/Test1.txt");
        MultipartFile expectedOutput = new MockMultipartFile("Test1.txt",
                                                             "Test1.txt",
                                                             null,
                                                             new FileInputStream(expectedOutputFile));
        
        // When
        ResponseEntity<Object> responseEntity = compilerController.compile(
                Language.C,
                sourceCode,
                null,
                expectedOutput,
                10,
                500,
                null,
                null,
                "");
        
        // Then
        Assertions.assertEquals(
                Verdict.RUNTIME_ERROR.getStatusResponse(),
                ((Response)responseEntity.getBody()).getVerdict());
    }
    
}
