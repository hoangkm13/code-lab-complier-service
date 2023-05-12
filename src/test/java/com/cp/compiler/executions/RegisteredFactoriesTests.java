package com.cp.compiler.executions;

import com.cp.compiler.executions.languages.*;
import com.cp.compiler.models.testcases.ConvertedTestCase;
import com.cp.compiler.models.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@DirtiesContext
@SpringBootTest
public class RegisteredFactoriesTests {
    
    private MultipartFile file = new MockMultipartFile(
            "test.txt",
            "test.txt",
            null,
            (byte[]) null);
    
    @Test
    void javaExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.JAVA);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.JAVA));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof JavaExecution);
    }
    
    @Test
    void kotlinExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.KOTLIN);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.KOTLIN));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof KotlinExecution);
    }
    
    @Test
    void scalaExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.SCALA);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.SCALA));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof ScalaExecution);
    }
    
    @Test
    void csExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.CS);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.CS));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof CSExecution);
    }
    
    @Test
    void goExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.GO);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.GO));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof GoExecution);
    }
    
    @Test
    void cExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.C);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.C));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof CExecution);
    }
    
    @Test
    void cppExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.CPP);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.CPP));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof CPPExecution);
    }
    
    @Test
    void pythonExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.PYTHON);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.PYTHON));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof PythonExecution);
    }
    
    @Test
    void rustExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.RUST);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.RUST));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof RustExecution);
    }
    
    @Test
    void rubyExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.RUBY);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.RUBY));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof RubyExecution);
    }
    
    @Test
    void haskellExecutionFactoryShouldBeRegistered() {
        var testCase = new ConvertedTestCase("id", file, "test");
        Execution execution = ExecutionFactory.createExecution(file, List.of(testCase), 10, 100, Language.HASKELL);
        Assertions.assertTrue(ExecutionFactory.getRegisteredFactories().contains(Language.HASKELL));
        Assertions.assertNotNull(execution);
        Assertions.assertTrue(execution instanceof HaskellExecution);
    }
}
