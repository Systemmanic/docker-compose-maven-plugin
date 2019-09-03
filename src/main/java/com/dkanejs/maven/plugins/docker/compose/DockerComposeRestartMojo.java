package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Mojo(name = "restart", threadSafe = true)
public class DockerComposeRestartMojo extends AbstractDockerComposeMojo {

    public void execute() throws MojoExecutionException {

        if (skip) {
            getLog().info("Skipping execution");
            return;
        }

        List<String> args = new ArrayList<>();

        args.add(Command.RESTART.getValue());

        if (services != null && !services.isEmpty())
            args.addAll(services);

        super.execute(args);
    }
}
