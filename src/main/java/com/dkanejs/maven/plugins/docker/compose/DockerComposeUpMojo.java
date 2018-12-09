package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.ArrayList;
import java.util.Arrays;
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

		List<String> args = new ArrayList<>();
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

		if (awaitCmd != null) {
			await(buildCmd(awaitCmd, awaitCmdArgs));
		}
	}

	private List<String> buildCmd(String cmd, String args) {
		String[] cmdArgs = null;
		if (args != null) {
			cmdArgs = args.split(",");
		}
		List<String> answer = new ArrayList<>();
		answer.add(cmd);
		if (cmdArgs != null) {
			answer.addAll(Arrays.asList(cmdArgs));
		}
		return answer;
	}

	private void await(List<String> awaitCmd) {
		Integer exitCode = null;
		ProcessBuilder pb = new ProcessBuilder(awaitCmd).inheritIO();
		long start = System.currentTimeMillis();
		try {
			boolean stillWaiting;
			do {
				Process process = null;
				if (exitCode != null) {
					Thread.sleep(1000);
				}
				exitCode = pb.start().waitFor();
				stillWaiting = (System.currentTimeMillis() - start) < (awaitTimeout * 1000);
			} while ((exitCode != 0) && stillWaiting);

			if (exitCode != 0) {
				throw new RuntimeException("await failed after " + awaitTimeout + " seconds");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
