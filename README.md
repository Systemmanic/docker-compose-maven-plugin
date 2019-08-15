# Docker Compose Maven Plugin
[![Build Status](https://travis-ci.org/dkanejs/docker-compose-maven-plugin.svg?branch=master)](https://travis-ci.org/dkanejs/docker-compose-maven-plugin)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.dkanejs.maven.plugins/docker-compose-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.dkanejs.maven.plugins/docker-compose-maven-plugin)

## Quickstart
Available on Maven Central.

```xml
<dependency>
    <groupId>com.dkanejs.maven.plugins</groupId>
    <artifactId>docker-compose-maven-plugin</artifactId>
    <version>$VERSION</version>
</dependency>
```

## About
Maven plugin for running basic `docker-compose` commands with Maven.

This can be used as part of the Maven lifecycle or as a utility to bring `docker-compose` commands to your Maven toolkit.

This plugin is designed to be light, fast and with minimum dependencies (only those required by Maven).

## Usage
### Goals
* up - runs `docker-compose up`
* down - runs `docker-compose down`
* pull - runs `docker-compose pull`

### Properties
#### composeFile
`composeFile` - Location of the compose file e.g. `${project.basedir}/docker-compose.yml`

The Plugin assumes your docker file is in `${project.basedir}/src/main/resources/docker-compose.yml`

This can be changed in the configuration section of the plugin:
```xml
<configuration>
    <composeFile>${project.basedir}/docker-compose.yml</composeFile>
</configuration>
```

If the property `composeFiles` which allows multiple compose files is present then the value of this `composeFile` property is ignored.

#### composeFiles
`composeFiles` - Location of multiple compose files. If this property is present then the value of the `composeFile` is ignored.

This can be configured in the configuration section of the plugin:
```xml
<configuration>
    <composeFiles>
        <composeFile>${project.basedir}/docker-compose.yml</composeFile>
        <composeFile>${project.basedir}/docker-compose.override.yml</composeFile>
    </composeFiles>
</configuration>
```

#### envFile
`envFile` - Location of a file containing environment variables for docker-compose in key=value format, one pair per line.

This can be configured in the configuration section of the plugin:
```xml
<configuration>
    <envFile>${project.basedir}/.env</envFile>
</configuration>
```

#### envVars
`envVars` - Environment variables to be set when running docker-compose. Values set here override those set in a configured `envFile`.

This can be configured in the configuration section of the plugin:
```xml
<configuration>
    <envVars>
        <serviceName>${project.groupId}.${project.artifactId}</serviceName>
    </envVars>
</configuration>
```

This allows you to parameterize your `docker-compose.yml`:

```yaml
version: '3.2'
services:
  service:
    image: busybox
    container_name: ${serviceName}-1
```

#### services
`services` - Names of services.

This property configures the plugin to only execute the commands on the services specified.

This can be configured in the configuration section of the plugin:
```xml
<configuration>
    <services>
        <service>service-1</composeFile>
        <service>service-2</composeFile>
    </composeFiles>
</configuration>
```
The following example will only start services: `test-1` and `test-2`:

```xml
<configuration>
    <services>
        <service>test-1</composeFile>
        <service>test-2</composeFile>
    </composeFiles>
</configuration>
```

Against the following `docker-compose` file:

```yaml
version: '3.2'
services:
  test-1:
    image: busybox
    container_name: container-1
  test-2:
    image: busybox
    container_name: container-2
  test-3:
    image: busybox
    container_name: container-3
```

Equivalent `docker-compose` command:

`docker-compose up service-1 service-2`

#### detachedMode
`detachedMode` - Run in detached mode

This adds `-d` to the up command.

The plugin will not run in detached mode by default.

This can be changed in the configuration section of the plugin:
```xml
<configuration>
    <detachedMode>true</detachedMode>
</configuration>
```

#### removeVolumes
`removeVolumes` - Delete volumes

This adds `-v` to the `down` command.

The plugin will not remove any volumes you create when using the `down` goal.

This can be changed in the configuration section of the plugin:
```xml
<configuration>
    <removeVolumes>true</removeVolumes>
</configuration>
```

#### apiVersion
`apiVersion` - Specify compose API version
```xml
<configuration>
   	<apiVersion>1.22</apiVersion>
</configuration>
```

#### verbose
`verbose` - Enable verbose output
```xml
<configuration>
   	<verbose>true</verbose>
</configuration>
```

#### skip
`skip` - Skip execution
```xml
<configuration>
   	<skip>true</skip>
</configuration>
```

#### projectName
`projectName` - Specify project name
```xml
<configuration>
    <projectName>customProjectName</projectName>
</configuration>
```

#### host
`host` - Specify host
```xml
<configuration>
    <host>unix:///var/run/docker.sock</host>
</configuration>
```

#### build
`build` - Build images before starting containers

This adds `--build` to the `up` command.

The plugin will not build images first by default.

This can be changed in the configuration section of the plugin:
```xml
<configuration>
    <build>true</build>
</configuration>
```

#### removeOrphans
`removeOrphans` - Remove containers for services not defined in the Compose file

This adds `--remove-orphans` to the `down` command.

The plugin will not remove orphans by default.

This can be changed in the configuration section of the plugin:
```xml
<configuration>
    <removeOrphans>true</removeOrphans>
</configuration>
```
#### removeImages
`removeImages` - Remove images when executing `down`

This adds `--rmi` to the `down` command.

The plugin will not remove images by default.

This can be changed in the configuration section of the plugin:
```xml
<configuration>
    <removeImages>true</removeImages>
</configuration>
```

Additional option `removeImagesType` allows to specify `type` parameter of `--rmi` docker compose flag.
`all` is the default value.
`local` is the second supported type.

```xml
<configuration>
    <removeImages>true</removeImages>
    <removeImagesType>local</removeImagesType>
</configuration>
```

#### ignorePullFailures
`ignorePullFailures` - Ignores failures when executing the pull goal

This adds `--ignore-pull-failures` to the `pull` command.

The plugin will not ignore pull failures by default.

This can be changed in the configuration section of the plugin:
```xml
<configuration>
    <ignorePullFailures>true</ignorePullFailures>
</configuration>
```

## Configuration
### Default
Below will allow use of the plugin from the `mvn` command line:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.dkanejs.maven.plugins</groupId>
            <artifactId>docker-compose-maven-plugin</artifactId>
            <version>$VERSION</version>
        </plugin>
    </plugins>
</build>
```
This assumes the compose file is in the default location and will not run in any phase of the build.

### Advanced
Below has customised the location of the `docker-compose.yml` file and has three executions defined:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.dkanejs.maven.plugins</groupId>
            <artifactId>docker-compose-maven-plugin</artifactId>
            <version>$VERSION</version>
            <executions>
                <execution>
                    <id>pull</id>
                    <phase>verify</phase>
                    <goals>
                        <goal>pull</goal>
                    </goals>
                    <configuration>
                        <composeFile>${project.basedir}/docker-compose.yml</composeFile>
                        <ignorePullFailures>true</ignorePullFailures>
                    </configuration>
                </execution>
                <execution>
                    <id>up</id>
                    <phase>verify</phase>
                    <goals>
                        <goal>up</goal>
                    </goals>
                    <configuration>
                        <composeFile>${project.basedir}/docker-compose.yml</composeFile>
                        <detachedMode>true</detachedMode>
                    </configuration>
                </execution>
                <execution>
                    <id>down</id>
                    <phase>verify</phase>
                    <goals>
                        <goal>down</goal>
                    </goals>
                    <configuration>
                        <composeFile>${project.basedir}/docker-compose.yml</composeFile>
                        <removeVolumes>true</removeVolumes>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

This will run the following as part of the `verify` phase:
 1. `docker-compose pull --ignore-pull-failures` using a `docker-compose.yml` file in a custom location
 2. `docker-compose up -d` using a `docker-compose.yml` file in a custom location
 3. `docker-compose down -v` using a `docker-compose.yml` file in a custom location
