package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "up", threadSafe = true)
public class DockerComposeUpMojo extends AbstractDockerComposeMojo {

	public void execute() throws MojoExecutionException, MojoFailureException {

		command.add(Command.UP.getValue());

		if (detachedMode)
			command.add("-d");

		super.execute(command);
	}
}
