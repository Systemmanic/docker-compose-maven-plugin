String buildLog = new File("${basedir}/build.log").getText("UTF-8")

assert buildLog.contains("Skipping execution" as CharSequence)
assert !buildLog.contains("Docker Compose has run successfully")

evaluate(new File("src/it/prune_docker.groovy"))
