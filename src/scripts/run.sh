#!/usr/bin/env bash
# ErRabit Run Script

settingLocation="settings.properties";
java -jar ErRabbitServer-0.1.0-SNAPSHOT4.war --spring.config.location=file:$settingLocation
