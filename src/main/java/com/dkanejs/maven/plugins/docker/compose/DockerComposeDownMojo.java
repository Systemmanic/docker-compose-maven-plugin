package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.ArrayList;
import java.util.List;

@Mojo(name = "down", threadSafe = true)
public class DockerComposeDownMojo extends AbstractDockerComposeMojo {

	public void execute() throws MojoExecutionException, MojoFailureException {

		List<String> args = new ArrayList<>();

		args.add(Command.DOWN.getValue());

		if (removeVolumes) {
			getLog().info("Removing volumes");
			args.add("-v");
		}

		super.execute(args);
	}
}
