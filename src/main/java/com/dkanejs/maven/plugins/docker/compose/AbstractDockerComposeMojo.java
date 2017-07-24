package com.dkanejs.maven.plugins.docker.compose;

import lombok.Getter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
abstract class AbstractDockerComposeMojo extends AbstractMojo {

	protected String composeFilePath;

	protected List<String> command;

	@Parameter(defaultValue = "true", property = "dockerCompose.detached")
	protected boolean detachedMode;

	@Parameter(defaultValue = "${project.basedir}/src/main/resources/docker-compose.yml", property = "dockerCompose.file")
	private String composeFile;

	AbstractDockerComposeMojo() {
		this.composeFilePath = Paths.get(this.composeFile).toString();

		getLog().info("Dockerfile: " + composeFilePath);

		List<String> cmd = new ArrayList<>();
		cmd.add("docker-compose");
		command.add("docker-compose");
		command.add("-f");
		command.add(composeFilePath);

		this.command = cmd;
	}

	void execute(List<String> cmd) throws MojoExecutionException, MojoFailureException {

		ProcessBuilder pb = new ProcessBuilder(cmd);

		getLog().info("Running: " + StringUtils.join(cmd.iterator(), " "));

		try {
			Process p = pb.start();

			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;

			while ((line = br.readLine()) != null)
				getLog().info(line);

			int ec = p.waitFor();

			if (ec != 0)
				throw new DockerComposeException(IOUtil.toString(p.getErrorStream()));

		} catch (Exception e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}
}
