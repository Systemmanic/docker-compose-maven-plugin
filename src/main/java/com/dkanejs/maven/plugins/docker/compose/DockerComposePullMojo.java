package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.ArrayList;
import java.util.List;

/**
 * docker-compose pull
 */
@SuppressWarnings("unused")
@Mojo(name = "pull", threadSafe = true)
public class DockerComposePullMojo extends AbstractDockerComposeMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (skip) {
            getLog().info("Skipping execution");
            return;
        }

        List<String> args = new ArrayList<>();
        args.add(Command.PULL.getValue());

        if (ignorePullFailures) {
            getLog().info("Ignore pull failures");
            args.add("--ignore-pull-failures");
            args.add("--no-parallel");
        }

        super.execute(args);
    }
}
