# Salesforce Integration

#salesforce-scala
===============

This repository is the base repository for Expedite's back end platform. The platform is primarily built on the following technologies:

1. Scala 2.11.x
	1. Built upon many twitter open source projects - [finagle](https://twitter.github.io/scala_school/finagle.html), Futures, [twitterserver](https://twitter.github.io/twitter-server/), [scrooge](https://github.com/twitter/scrooge)
2. [thrift](https://thrift.apache.org/) - for service definition and cross platform endpoint/client generation
3. Java 8
4. sbt for building/packaging

### Setting up a Scala Development Environment

1. Download Java 8 from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html). After installation, verify that it is properly installed:

		$ java -version
		java version "1.8.0_25"
		Java(TM) SE Runtime Environment (build 1.8.0_25-b17)
		Java HotSpot(TM) 64-Bit Server VM (build 25.25-b02, mixed mode)

2. Set up your `JAVA_HOME` environment variable by running `/usr/libexec/java_home`:

		$ /usr/libexec/java_home -v 1.8
		/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home

		# Place this in your ~/.bash_profile
		export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home

3. (dev-bootstrap.sh takes care of this) Install scala command line tools using homebrew, and verify that the installation worked by running the scala REPL:

		$ brew install scala

		$ scala
		Welcome to Scala version 2.11.4 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_25).
		Type in expressions to have them evaluated.
		Type :help for more information.

		scala> println("Hello, world")
		Hello, world

4. (dev-bootstrap.sh takes care of this) Install [thrift](https://thrift.apache.org/) using homebrew: `brew install thrift`

5. (dev-bootstrap.sh takes care of this) Install [wkhtmltopdf](http://wkhtmltopdf.org/downloads.html) using homebrew cask: `brew cask install wkhtmltopdf`. This is used to convert html to pdfs.

6. Install Java6 from [Apple](https://support.apple.com/downloads/DL1824/en_US/JavaForOSX.dmg). This is required for IntelliJ to work. Running `java_version` should yield:

  ```
  $ /usr/libexec/java_home -v 1.6
  /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home
  ```

  You can install java6 through homebrew cask:

  ```
  $ brew cask install caskroom/homebrew-versions/java6
  ```

7. Install [IntelliJ Community Edition](https://www.jetbrains.com/idea/download/). If you plan to use IntelliJ for Scala development, you'll have to install the Scala language plugin. Download whatever plugins you want (e.g.: scala, IdeaVIM if you want vim behavior) and spend some time customizing your environment. Vince's [settings.jar](https://files.slack.com/files-pri/T02E2RSG4-F037HV40G/download/settings.jar) might be a good start (if you like vim)

  You can install through homebrew cask:

  ```
  $ brew tap caskroom/versions
  $ brew cask install intellij-idea-ce
  ```

8. Download the [expedite-scala repository](https://github.com/expedite/expedite-scala) (you're reading it now).

9. Explore the code! Look at the READMEs for the sub-projects for more details on how to run them and any additional environment setup you may need to do.

### sbt

Developers should not use a system-installed version of sbt, and should instead use the `./sbt` script, which ensures that a compatible version of sbt is available.

### Module Intro
Modules are either servers or cross-server libraries. A server is a full application designed to execute some business function for Expedite. A library is a collection of methods that is useful across servers.

1. *expedite-platform* - entrypoint and service host
2. *mrroboto* - business rules and workflows for handling mortgage operations
3. *lendingqb-client* - interface to lendingQB - the third party Loan Origination System we are using
4. *database-driver* - foundational library for interacting with databases
5. *lendingqb-client-wrapper* - an auto-generated wrapper around the LendingQB SOAP API
6. *expedite-journaling* - a JSON-over-HTTP api for accessing events created in the running stack
7. *expedite-analytics* - utilities for organizing analytics events and metrics
8. *expedite-server* - twitter server utilities customized for Expedite
9. *expedite-util* - lightweight utilities
10. *finicity-client* - finicity API wrapper
11. *expedite-logs* - thrift event log collector and http endpoint for json POST events
12. *expedite-thrift* - thrift models for data and services


## Learning Scala

A few resources to get you on your way with Scala.

1. [Scala School](https://twitter.github.io/scala_school/) - An opinionated tour of Scala designed "at Twitter to prepare experienced engineers to be productive Scala programmers"

2. [Effective Scala](http://twitter.github.io/effectivescala/) - Written by Marius Eriksen, author of Finagle. Good introduction to some patterns that people have found effective.


i'm not 100% sure (you should check with @brian) but i think what you will want to do is add a `processor` in `ClownCarServer#makeProcessors`; follow the pattern there to define an `EventMessage` topic and event name to listen to (it may be ok to use the "free" event that comes from the call to `updateEntity`), and define the logic for fetching the phone number (either from db or from the event payload), and ship that up to salesforce

re: specific question you had for how to send it an `EventMessage`: use kafka, use the existing thrift event capture infrastructure if appropriate, or define your own topic/event/payload
