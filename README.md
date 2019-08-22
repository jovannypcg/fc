# fc

![](https://travis-ci.org/jovannypcg/fc.svg?branch=master)

*fc* (Fraction Calculator) is a CLI tool that does math calculations with mixed numbers (mixed fractions) performing operations on fractions, whole numbers, integers, mixed numbers and improper fractions. *fc* can add, subtract, multiply and divide mixed numbers and fractions.

## Prerequisites

* Java 8

### SDKMan

Java 8 can be installed via [SDKMan](https://sdkman.io/install), which is a version manager for technologies related to the Java Virtual Machine (similar to `rbenv` for Ruby or `pyenv` for Python).

### Installing Java 8 using SDKMan

```shell
$ sdk install java 8u151-oracle
$ export JAVA_HOME=~/.sdkman/candidates/java/current
```

## Building

Since the application is written in Java, it is necessary to generate the compiled classes in order to be executed by the JVM. This task is readily achieved using Gradle.

```shell
$ ./gradlew clean build
```

## Usage

Once built, the generated Java artifact is located at `build/libs/fc.jar` and can be executed by running:

```shell
$ cd build/libs
$ java -jar fc.jar 1/2 * 3_3/4
```

## Testing

```shell
$ ./gradlew clean test
```

## Author

**Jovanny Cruz** - [jovannypcg](https://github.com/jovannypcg)
