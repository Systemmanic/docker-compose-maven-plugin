package com.dkanejs.maven.plugins.docker.compose;

import java.util.ArrayList;
import java.util.List;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * docker-compose build
 */
@SuppressWarnings("unused")
@Mojo(name = "push", threadSafe = true)
public class DockerComposePushMojo extends AbstractDockerComposeMojo {

	public void execute() throws MojoExecutionException {

		if (skip) {
			getLog().info("Skipping execution");
			return;
		}

		final List<String> args = new ArrayList<>();
		args.add(Command.PUSH.getValue());

		if (services != null && !services.isEmpty()) {
			args.addAll(services);
		}

		super.execute(args);
	}

}
