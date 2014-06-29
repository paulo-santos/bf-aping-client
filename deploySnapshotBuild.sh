#!/bin/bash

if [ "$TRAVIS_REPO_SLUG" != "paulo-santos/bf-aping-client" ] && [ "$TRAVIS_BRANCH" != "master" ]  ; then
  echo "WARNING: Not publishing snapshots as this is a branch" >&2
  exit 1
fi

if [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
  echo "WARNING: Not publishing snapshots as this is a pull request" >&2
  exit 2
fi

if [ "$TRAVIS_JDK_VERSION" != "oraclejdk7" ]; then
    echo "WARNING: Not publishing this build as it will be depoyed by oraclejdk7 version build"
    exit 3
fi

mvn deploy -s upload_settings.xml -B