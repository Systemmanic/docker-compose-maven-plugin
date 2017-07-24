package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "down", threadSafe = true)
public class DockerComposeDownMojo extends AbstractDockerComposeMojo {

	public void execute() throws MojoExecutionException, MojoFailureException {

		command.add(Command.DOWN.getValue());

		super.execute(command);
	}
}
