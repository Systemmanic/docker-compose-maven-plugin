# Docker Compose Maven Plugin
[![Build Status](https://travis-ci.org/dkanejs/docker-compose-maven-plugin.svg?branch=master)](https://travis-ci.org/dkanejs/docker-compose-maven-plugin)
[![Join the chat at https://gitter.im/dkanejs/docker-compose-maven-plugin](https://badges.gitter.im/dkanejs/docker-compose-maven-plugin.svg)](https://gitter.im/dkanejs/docker-compose-maven-plugin?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.dkanejs.maven.plugins/docker-compose-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.dkanejs.maven.plugins/docker-compose-maven-plugin)

## Quickstart
Available on Maven Central.
```xml
<dependency>
    <groupId>com.dkanejs.maven.plugins</groupId>
    <artifactId>docker-compose-maven-plugin</artifactId>
    <version>2.4.0</version>
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
* ps - runs `docker-compose ps` and generates a properties file containing assigned port mappings.

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

#### mappingFile
`mappingFile` - Change the location of the generated port mapping properties file.

The `docker-compose:ps` command generates a .properties file at 
`${project.build.directory}/docker-compose-mappings.properties` that contains dynamic port mappings assigned by 
docker-compose. 

You can change the location of the generated file by setting the `mappingFile` configuration property:
```xml
<configuration>
    <mappingFile>${basedir}/some/other/path.properties</mappingFile>
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

## Configuration
### Default
Below will allow use of the plugin from the `mvn` command line:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.dkanejs.maven.plugins</groupId>
            <artifactId>docker-compose-maven-plugin</artifactId>
            <version>2.4.0</version>
        </plugin>
    </plugins>
</build>
```
This assumes the compose file is in the default location and will not run in any phase of the build.

### Advanced
Below has customised the location of the `docker-compose.yml` file and has two executions defined:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.dkanejs.maven.plugins</groupId>
            <artifactId>docker-compose-maven-plugin</artifactId>
            <version>2.4.0</version>
            <executions>
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
 1. `docker-compose up -d` using a `docker-compose.yml` file in a custom location
 2. `docker-compose down -v` using a `docker-compose.yml` file in a custom location
