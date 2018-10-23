package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
@Mojo(name = "down", threadSafe = true)
public class DockerComposeDownMojo extends AbstractDockerComposeMojo {

	public void execute() throws MojoExecutionException {

		if (skip) {
			getLog().info("Skipping execution");
			return;
		}

		Deque<String> args = new LinkedList<>();

		args.add(Command.DOWN.getValue());

		if (removeVolumes) {
			getLog().info("Removing volumes");
			args.add("-v");
		}


		if (removeImages) {
			getLog().info("Removing images");
			args.add("--rmi");
			args.add(removeImagesType);
		}

		if (removeOrphans) {
			getLog().info("Removing orphans");
			args.add("--remove-orphans");
		}

		super.execute(args);
	}
}
