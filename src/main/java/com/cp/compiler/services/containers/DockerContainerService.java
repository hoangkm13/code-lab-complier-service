package com.cp.compiler.services.containers;

import com.cp.compiler.exceptions.*;
import com.cp.compiler.mappers.ContainerInfoMapper;
import com.cp.compiler.models.containers.ContainerInfo;
import com.cp.compiler.models.processes.ProcessOutput;
import com.cp.compiler.wellknownconstants.WellKnownMetrics;
import com.cp.compiler.utils.CmdUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This class provides Docker utilities
 *
 * @author Zakaria Maaraki
 */
@Slf4j
@Service("docker")
public class DockerContainerService implements ContainerService {
    
    /**
     * The constant BUILD_TIMEOUT.
     */
    public static final int BUILD_TIMEOUT = 60000; // 1 minute
    
    /**
     * The constant COMMAND_TIMEOUT.
     */
    public static final int COMMAND_TIMEOUT = 10000; // 10 sec
    
    /**
     * The constant EXECUTION_PATH_ENV_VARIABLE.
     */
    public static final String EXECUTION_PATH_ENV_VARIABLE = "EXECUTION_PATH";
    
    /**
     * The Container info format to be returned by docker
     */
    private static final String CONTAINER_INFO_FORMAT = "--format={\"status\": \"{{.State.Status}}\", \"creationTime\": " +
            "\"{{.Created}}\", \"startTime\": \"{{.State.StartedAt}}\", \"endTime\": \"{{.State.FinishedAt}}\", \"exitCode\": " +
            "{{.State.ExitCode}}, \"error\": \"{{.State.Error}}\"}";
    
    /**
     * Container engine used
     */
    private static final String CONTAINERIZATION_NAME = "Docker";
    
    /**
     * The constant SOURCE_CODE_ENV_VARIABLE.
     */
    public static final String SOURCE_CODE_FILE_NAME_ENV_VARIABLE = "SOURCE_CODE_FILE_NAME";

    private final MeterRegistry meterRegistry;

    private Timer buildTimer;

    private Timer runTimer;
    
    /**
     * Instantiates a new Container service.
     *
     * @param meterRegistry the meter registry
     */
    public DockerContainerService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    
    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        buildTimer = meterRegistry.timer(WellKnownMetrics.CONTAINER_BUILD_TIMER, "container", "docker");
        runTimer = meterRegistry.timer(WellKnownMetrics.CONTAINER_RUN_TIMER, "container", "docker");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String buildImage(String contextPath, String imageName, String dockerfileName) {
        return buildTimer.record(() -> {
            String dockerfilePath = contextPath + "/" + dockerfileName;
            String[] buildCommand =
                    new String[]{
                            "docker",
                            "image",
                            "build",
                            "-f", dockerfilePath,
                            "-t", imageName,
                            contextPath};
            return executeContainerCommand(buildCommand, BUILD_TIMEOUT);
        });
    }
    
    @Override
    public ProcessOutput runContainer(String imageName,
                                      String containerName,
                                      long timeout,
                                      float maxCpus,
                                      Map<String, String> envVariables) {
        return runTimer.record(() -> {
            String[] dockerCommand = buildDockerCommand(containerName, envVariables, maxCpus, imageName);
            return CmdUtils.executeProcess(dockerCommand, timeout);
        });
    }
    
    private String[] buildDockerCommand(String containerName,
                                        Map<String, String> envVariables,
                                        float cpus,
                                        String imageName) {
        /**
         * docker run --name [containerName] (-e [envKey=envValue])* --cpus=[cpu] [imageName]
         */
        List<String> dockerCommandList = new ArrayList<>(Arrays.asList("docker", "run", "--name", containerName));
        for (String key : envVariables.keySet()) {
            dockerCommandList.add("-e");
            dockerCommandList.add(key + "=" + envVariables.get(key));
        }
        var cpuParam = "--cpus=" + cpus;
        dockerCommandList.add(cpuParam);
        dockerCommandList.add(imageName);
        return dockerCommandList.toArray(new String[0]);
    }
    
    @Override
    public ContainerInfo inspect(String containerName) {
        String[] command = {"docker", "container", "inspect", CONTAINER_INFO_FORMAT, containerName};
        String containerInfo = executeContainerCommand(command, COMMAND_TIMEOUT);
        ContainerInfo ci = null;
        try {
             ci = ContainerInfoMapper.toContainerInfo(containerInfo);
        } catch (JsonProcessingException e) {
            log.warn("Error during json deserialization, while trying to retrieve container info, ex: {}", e);
        }
        return ci;
    }
    
    @Override
    public void deleteContainer(String containerName) {
        String[] command = {"docker", "container", "rm", "-f", containerName};
        executeContainerCommand(command, COMMAND_TIMEOUT);
    }
    
    @Override
    public ProcessOutput runContainer(
            String imageName,
            String containerName,
            long timeout,
            String volumeMounting,
            String executionPath,
            String sourceCodeFileName) {
        
        return runTimer.record(() -> {
            String[] dockerCommand =
                    new String[]{
                            "docker",
                            "run",
                            "--name", containerName,
                            "-v", volumeMounting,
                            "-e", EXECUTION_PATH_ENV_VARIABLE + "=" + executionPath,
                            "-e", SOURCE_CODE_FILE_NAME_ENV_VARIABLE + "=" + sourceCodeFileName,
                            imageName};
    
            return CmdUtils.executeProcess(dockerCommand, timeout);
        });
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getRunningContainers() {
        String[] command = {"docker", "ps"};
        return executeContainerCommand(command, COMMAND_TIMEOUT);
    }
    
    @Override
    public String getContainersStats() {
        String[] command = {"docker", "stats", "--no-stream"};
        return executeContainerCommand(command, COMMAND_TIMEOUT);
    }
    
    @Override
    public String getAllContainersStats() {
        String[] command = {"docker", "stats", "--no-stream", "--all"};
        return executeContainerCommand(command, COMMAND_TIMEOUT);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getImages() {
        String[] command = {"docker", "images"};
        return executeContainerCommand(command, COMMAND_TIMEOUT);
    }
    
    @Override
    public void deleteImage(String imageName) {
        String[] command = {"docker", "rmi", "-f", imageName};
        executeContainerCommand(command, COMMAND_TIMEOUT);
    }
    
    @Override
    public boolean isUp() {
        String[] command = {"docker", "ps"};
        ProcessOutput processOutput = CmdUtils.executeProcess(command, COMMAND_TIMEOUT);
        return processOutput.getStdErr().isEmpty();
    }
    
    @Override
    public String getContainerizationName() {
        return CONTAINERIZATION_NAME;
    }
    
    private String executeContainerCommand(String[] command, long timeout) {
        try {
            ProcessOutput processOutput = CmdUtils.executeProcess(command, timeout);
            if (!processOutput.getStdErr().isEmpty()) {
                throw new ContainerFailedDependencyException("Error: " + processOutput.getStdErr());
            }
            return processOutput.getStdOut();
        } catch (ProcessExecutionException e) {
            throw new ContainerFailedDependencyException(e.getMessage());
        } catch (ProcessExecutionTimeoutException e) {
            throw new ContainerOperationTimeoutException(e.getMessage());
        }
    }
}
