#!/bin/sh

echo "Running static analysis..."

# Validate Kotlin code with detekt
./gradlew app:detekt --stacktrace --no-daemon

status=$?

if [ "$status" = 0 ] ; then
    echo "Static analysis found no issues. Proceeding with push."
    exit 0
else
#    echo 1>&2 "Static analysis found issues you need to fix before committing."
    echo "***********************************************"
    echo "                 Detekt failed                 "
    echo " Please fix the above issues before committing "
    echo "***********************************************"
    exit 1
fi