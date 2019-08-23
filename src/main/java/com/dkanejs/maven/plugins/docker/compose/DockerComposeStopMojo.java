package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Mojo(name = "stop", threadSafe = true)
public class DockerComposeStopMojo extends AbstractDockerComposeMojo {

	public void execute() throws MojoExecutionException {

		if (skip) {
			getLog().info("Skipping execution");
			return;
		}

		List<String> args = new ArrayList<>();

		args.add(Command.STOP.getValue());
		
		if (services != null && !services.isEmpty())
			args.addAll(services);

		super.execute(args);
	}
}
