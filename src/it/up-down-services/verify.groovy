String buildLog = new File("${basedir}/build.log").getText("UTF-8")

assert buildLog.contains("Creating mpdc-it-up-down-service-1" as CharSequence)
assert buildLog.contains("Creating mpdc-it-up-down-service-2" as CharSequence)
assert !buildLog.contains("Creating mpdc-it-up-down-service-3" as CharSequence)

evaluate(new File("src/it/prune_docker.groovy"))
