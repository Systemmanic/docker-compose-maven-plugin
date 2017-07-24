package com.dkanejs.maven.plugins;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "up", threadSafe = true)
public class DockerComposeUpMojo extends AbstractDockerComposeMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        super.execute(Command.UP);
    }
}
