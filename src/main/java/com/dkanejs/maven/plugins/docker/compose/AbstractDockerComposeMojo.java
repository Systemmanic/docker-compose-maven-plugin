package com.dkanejs.maven.plugins.docker.compose;

import lombok.Getter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
abstract class AbstractDockerComposeMojo extends AbstractMojo {

	/**
	 * Docker host to interact with
	 */
	@Parameter(property = "dockerCompose.host")
	String host;

	/**
	 * Remove volumes on down
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.removeVolumes")
	boolean removeVolumes;

	/**
	 * Run in detached mode
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.detached")
	protected boolean detachedMode;

	/**
	 * The location of the Compose file
	 */
	@Parameter(defaultValue = "${project.basedir}/src/main/resources/docker-compose.yml", property = "dockerCompose.file")
	private String composeFile;

	/**
	 * The Compose Api Version
	 */
	@Parameter(property = "dockerCompose.apiVersion")
	private ComposeApiVersion apiVersion;

	/**
	 * Verbose
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.verbose")
	private boolean verbose;

	/**
	 * Skip
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.skip")
	boolean skip;

	void execute(List<String> args) throws MojoExecutionException {

		ProcessBuilder pb = buildProcess(args);

		getLog().info("Running: " + StringUtils.join(pb.command().iterator(), " "));

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

	private ProcessBuilder buildProcess(List<String> args) {

		List<String> command = buildCmd(args);

		ProcessBuilder pb = new ProcessBuilder(command).inheritIO();

		setEnvironment(pb);

		return pb;
	}

	private List<String> buildCmd(List<String> args) {
		String composeFilePath = Paths.get(this.composeFile).toString();

		getLog().info("Dockerfile: " + composeFilePath);

		List<String> cmd = new ArrayList<>();
		cmd.add("docker-compose");
		cmd.add("-f");
		cmd.add(composeFilePath);

		if (verbose) {
			cmd.add("--verbose");
		}

		if (host != null) {
			cmd.add("-H");
			cmd.add(host);
		}

		cmd.addAll(args);
		return cmd;
	}

	private void setEnvironment(ProcessBuilder processBuilder) {
		Map<String, String> environment = processBuilder.environment();
		if (apiVersion != null) {
			getLog().info("COMPOSE_API_VERSION: " + apiVersion.version);
			environment.put("COMPOSE_API_VERSION", apiVersion.version);
		}
	}

	enum Command {
		UP("up"),
		DOWN("down");

		@SuppressWarnings("unused")
		@Getter
		private String value;

		Command(String value) {
			this.value = value;
		}
	}

	@SuppressWarnings("unused")
	enum ComposeApiVersion {
		V1_30("1.30"),
		V1_25("1.25"),
		V1_24("1.24"),
		V1_22("1.22"),
		V1_21("1.21");

		private String version;

		ComposeApiVersion(String version) {
			this.version = version;
		}
	}
}
