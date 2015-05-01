#!/usr/bin/env bash
# ErRabit Run Script

settingLocation="settings.properties";
java -jar server-0.1.0-SNAPSHOT.war --spring.config.location=file:$settingLocation
