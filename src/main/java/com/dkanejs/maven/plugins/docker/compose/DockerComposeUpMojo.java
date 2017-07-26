package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.ArrayList;
import java.util.List;

@Mojo(name = "up", threadSafe = true)
public class DockerComposeUpMojo extends AbstractDockerComposeMojo {

	public void execute() throws MojoExecutionException, MojoFailureException {

		List<String> args = new ArrayList<>();

		args.add(Command.UP.getValue());

		if (detachedMode) {
			getLog().info("Running in detached mode");
			args.add("-d");
		}

		super.execute(args);
	}
}
