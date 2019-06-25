package com.dkanejs.maven.plugins.docker.compose;

import java.util.Map;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Arguments which are for the {@link DockerComposeBuildMojo}
 */
public class BuildArguments {

	/**
	 * Always remove intermediate containers.
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.buildArgs.forceRm")
	protected boolean forceRm;

	/**
	 *  Do not use cache when building the image.
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.buildArgs.noCache")
	protected boolean noCache;

	/**
	 *  Always attempt to pull a newer version of the image.
	 */
	@Parameter(defaultValue = "false", property = "dockerCompose.buildArgs.alwaysPull")
	protected boolean alwaysPull;

	/**
	 *  Always attempt to pull a newer version of the image.
	 */
	@Parameter(property = "dockerCompose.buildArgs.args")
	protected Map<String,String> args;


}
