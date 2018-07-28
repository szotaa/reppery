```diff
- Work in progress
```
# reppery [![Build Status](https://travis-ci.org/szotaa/reppery.svg?branch=master)](https://travis-ci.org/szotaa/reppery) ![Coverage Ratio](https://sonarcloud.io/api/project_badges/measure?project=pl.szotaa%3Areppery%3Areppery-backend&metric=coverage) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
Spaced repetition learning web application.

## documentation
### javadoc
View <a href="https://szotaa.github.io/reppery/">javadocs</a>.

## developing process
### tasks

Trello task board is accessible <a href="https://trello.com/b/D4X3n5zD/reppery">here</a>.

### serving application
To ease development process I created a Gradle's `serve` task which runs both frontend Angular application and Spring 
Boot backend application in development modes. Execute `./gradlew serve --parallel` command from root project directory 
and go to <a href="http://localhost:4200">localhost:4200</a> to access your local deployment.

## build, execution and deployment
### CI/CD pipeline description
Whenever there are changes detected on master branch of this repository, Travis CI triggers new build. At first code 
coverage is checked (code coverage ratio has to be greater than 0.5) via Jacoco Gradle Plugin. Tests are executed 
afterwards and if they pass without failure code is pushed to SonarCloud where static code analysis is performed. 
Next, code arrives to Heroku which runs `./gradlew stage -x test`. This command builds Angular client in production 
mode, copies it into /resources/static Spring Boot directory and at that point Spring Boot application is being built.
Finally, Heroku executes commands from `Procfile` which boot the application which is accessible 
<a href="http://reppery.herokuapp.com/">here</a>.
