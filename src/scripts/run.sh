#!/usr/bin/env bash
# ErRabit Run Script

settingLocation="settings.properties";
java -jar ErRabbitServer-1.0.1-RELEASE.war --spring.config.location=file:$settingLocation
