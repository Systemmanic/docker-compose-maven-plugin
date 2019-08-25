package com.dkanejs.maven.plugins.docker.compose;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.ArrayList;
import java.util.List;

/**
 * docker-compose build
 */
@SuppressWarnings("unused")
@Mojo(name = "build", threadSafe = true)
public class DockerComposeBuildMojo extends AbstractDockerComposeMojo {

    public void execute() throws MojoExecutionException {

        if (skip) {
            getLog().info("Skipping execution");
            return;
        }

        final List<String> args = new ArrayList<>();

        args.add(Command.BUILD.getValue());


        if (buildArgs.forceRm) {
            getLog().info("Always remove intermediate containers.");
            args.add("--force-rm");
        }


        if (buildArgs.noCache) {
            getLog().info("Do not use cache when building the image.");
            args.add("--no-cache");
        }

        if (buildArgs.alwaysPull) {
            getLog().info("Always attempt to pull a newer version of the image.");
            args.add("--pull");
        }

        if (buildArgs.args != null && !buildArgs.args.isEmpty()) {
            getLog().info("Adding build args");
            buildArgs.args.forEach((key, value) -> {
                args.add("--build-arg");
                args.add(key + "=" + value);
            });
        }

        if (services != null && !services.isEmpty()) {
            args.addAll(services);
        }


        super.execute(args);
    }

}
