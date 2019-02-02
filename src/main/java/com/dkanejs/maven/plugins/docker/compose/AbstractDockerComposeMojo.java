package com.dkanejs.maven.plugins.docker.compose;

import java.nio.file.Path;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


abstract class AbstractDockerComposeMojo extends AbstractMojo {
    /**
     * Specify an alternate project name
     */
    @Parameter(property = "dockerCompose.projectName")
    String projectName;

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
	 * Remove images on down
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.removeImages")
	boolean removeImages;

	/**
	 * 'type' of images to remove on down
	 */
	@Parameter(defaultValue = "all", property = "dockerCompose.removeImages.type")
	String removeImagesType;

	/**
	 * Run in detached mode
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.detached")
	protected boolean detachedMode;

	/**
	 * Build containers before run
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.build")
	boolean build;

	/**
	 * The location of the Compose file. Value of this property is ignored if {@link #composeFiles} is set and non-empty.
	 */
	@Parameter(defaultValue = "${project.basedir}/src/main/resources/docker-compose.yml", property = "dockerCompose.file")
	private String composeFile;

	/**
	 * Location of the Compose files. If this property is set and non-empty then {@link #composeFile} is ignored.
	 */
	@Parameter
	private List<String> composeFiles;

	/**
	 * The Compose Api Version
	 */
	@Parameter(property = "dockerCompose.apiVersion")
	private String apiVersion;

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

	/**
	 * Remove containers for services not defined in the Compose file
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.removeOrphans")
	boolean removeOrphans;

	/**
	 * Properties file from which docker environment variables are set
	 */
	@Parameter(property = "dockerCompose.envFile")
	private String envFile;

	/**
	 * Cmd to run and wait for exit status 0
	 */
	@Parameter(property = "dockerCompose.awaitCmd")
	String awaitCmd;

	/**
	 * Arguments to awaitCmd (comma separated)
	 */
	@Parameter(property = "dockerCompose.awaitCmdArgs")
	String awaitCmdArgs;

	/**
	 * Timeout for await (seconds)
	 */
	@Parameter(property = "dockerCompose.awaitTimeout", defaultValue = "30")
	int awaitTimeout;

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

	private ProcessBuilder buildProcess(List<String> args) throws MojoExecutionException {

		List<String> command = buildCmd(args);

		ProcessBuilder pb = new ProcessBuilder(command).inheritIO();

		setEnvironment(pb);

		return pb;
	}

	private List<String> buildCmd(List<String> args) {
		List<String> composeFilePaths = new ArrayList<>();

		if (composeFiles != null && !composeFiles.isEmpty()) {
			composeFiles.stream()
				.map(Paths::get)
				.map(Path::toString)
				.forEachOrdered(composeFilePaths::add);
		} else {
			composeFilePaths.add(Paths.get(this.composeFile).toString());
		}

		getLog().info("Dockerfiles: " + String.join(", ", composeFilePaths));

		List<String> cmd = new ArrayList<>();
		cmd.add("docker-compose");

		composeFilePaths.forEach(composeFilePath -> {
			cmd.add("-f");
			cmd.add(composeFilePath);
		});

		if (verbose)
			cmd.add("--verbose");

		if (host != null) {
			cmd.add("-H");
			cmd.add(host);
		}

		if (projectName != null) {
	        cmd.add("-p");
	        cmd.add(projectName);
	    }

		cmd.addAll(args);

		return cmd;
	}

	private void setEnvironment(ProcessBuilder processBuilder) throws MojoExecutionException {
		Map<String, String> environment = processBuilder.environment();

		if (apiVersion != null) {
			getLog().info("COMPOSE_API_VERSION: " + apiVersion);
			environment.put("COMPOSE_API_VERSION", apiVersion);
		}

		if (envFile != null) {
			final Properties properties = new Properties();

			try {
				properties.load(new FileInputStream(envFile));
				properties.forEach((k, v) -> environment.put(k.toString(), v.toString()));
			} catch (final IOException e) {
				throw new MojoExecutionException(e.getMessage());
			}
		}
	}

	enum Command {
		UP("up"),
		DOWN("down");

		@SuppressWarnings("unused")
		private String value;

		Command(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
