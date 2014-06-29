#!/bin/bash

echo "JDK Version " $TRAVIS_JDK_VERSION

if [ "$TRAVIS_REPO_SLUG" != "paulo-santos/bf-aping-client" ] && [ "$TRAVIS_BRANCH" != "master" ]  ; then
  echo "WARNING: Not publishing snapshots as this is a branch" >&2
  exit 1
fi

if [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
  echo "WARNING: Not publishing snapshots as this is a pull request" >&2
  exit 2
fi

mvn deploy -s upload_settings.xml -B