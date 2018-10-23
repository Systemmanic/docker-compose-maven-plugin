package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * docker-compose up
 */
@SuppressWarnings("unused")
@Mojo(name = "up", threadSafe = true)
public class DockerComposeUpMojo extends AbstractDockerComposeMojo {

	public void execute() throws MojoExecutionException {

		if (skip) {
			getLog().info("Skipping execution");
			return;
		}

		Deque<String> args = new LinkedList<>();
		args.add(Command.UP.getValue());

		if (detachedMode) {
			getLog().info("Running in detached mode");
			args.add("-d");
		}

		if (build) {
			getLog().info("Building images");
			args.add("--build");
		}

		args.add("--no-color");

		super.execute(args);
	}
}
