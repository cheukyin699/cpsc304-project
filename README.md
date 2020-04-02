# Genetic Disorders Prognosis Diagnosis

[![Build Status](https://travis-ci.com/cheukyin699/cpsc304-project.svg?branch=master)](https://travis-ci.com/cheukyin699/cpsc304-project)

## Setup

We recommend you use Intellij IDEA to develop, but not having that is fine
because we have gradle. If you are using IDEA, you are all set and just need to
open this project up and everything will be magically set up for you.

## Running

Before running the project, you must use `ssh` to tunnel the Oracle Database
connection. Note that we recommend you to use an SSH key so that you don't have
to type in your password every single time.

```bash
ssh -l username -L localhost:1522:dbhost.students.cs.ubc.ca:1522 remote.students.cs.ubc.ca
```

If you don't want to do it via command line, [here's a nice
tutorial][run-own-machine] on how to run it from your own machine with SSH.

After all the oracle stuff to your machine, we can open up a separate terminal
and build this project:

```bash
# Set environmental variables for logging into database
export USER=ora_<cwl_id>
export PASS=a<student_id>

./gradlew bootRun   # for mac and unix machines
gradlew.bat bootRun # for windows
```

If you want, you can use Intellij to run it as well. The plus side is that it
is easier to set environmental variables this way.

To find out what else you can do, check the tasks:

```bash
./gradlew tasks
```

[run-own-machine]: https://www.students.cs.ubc.ca/~cs-304/resources/jdbc-oracle-resources/jdbc-java-setup.html#running-code-from-own-machine
