#!/bin/bash
mvn exec:java -Dexec.mainClass="com.codeforanyone.codeanalyzer.Main" -Dexec.args="./wars/wicket-source-demo-7.0.1-SNAPSHOT.war"
