package com.dkanejs.maven.plugins;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
abstract class AbstractDockerComposeMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.basedir}/src/main/resources/docker-compose.yml", property = "dockerCompose.file")
    private String composeFile;

    @Parameter(defaultValue = "true", property = "dockerCompose.detached")
    private boolean detachedMode;

    void execute(Command command) throws MojoExecutionException, MojoFailureException {

        List<String> cmd = new ArrayList<>();
        cmd.add("docker-compose");
        cmd.add("-f");
        cmd.add(composeFile());
        cmd.add(command.name().toLowerCase());

        if (detachedMode && command == Command.UP)
            cmd.add("-d");

        ProcessBuilder pb = new ProcessBuilder(cmd);

        getLog().info("Running: " + StringUtils.join(cmd.iterator(), " "));

        try {
            Process p = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;

            while ((line = br.readLine()) != null)
                getLog().info(line);

            int ec = p.waitFor();

            if (ec != 0)
                throw new DockerComposeException(IOUtil.toString(p.getErrorStream()));

        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage());
        }
    }

    private String composeFile() {
        String path = Paths.get(getComposeFile()).toString();
        getLog().info("Dockerfile: " + path);
        return path;
    }
}
