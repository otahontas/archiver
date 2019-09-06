# CLI archiver program

This repo contains an archive tool implemented with both [Lempel-Ziw-Welch (LWZ)](https://en.wikipedia.orga/wiki/Lempel–Ziv–Welch) and [Huffman](https://en.wikipedia.org/wiki/Huffman_coding) algorithms. Tool relies on packing files on byte level so it should work for any kind of files. Program follows usual Unix CLI tool conventions with flags and targets. 

Quick installing guide is provided below and more precise documentation can be found from documentation folder. 

# Quick install and usage guide

Cloning and starting
```
git clone https://github.com/otahontas/javasynth.git
mv archiver
```

Generating jar file:
```
mvn package
```

Running the program after generating jar
```
java -jar target/archiver.jar
```
*(Launching the program without parameters gives unix type guide for using the program)*

Tests can be run with command:
```
mvn test
```

Jacoco test coverage report can be created and examined with commands:
```
mvn jacoco:report
open target/site/jacoco/index.html (MacOS)
xdg-open target/site/jacoco/index.html (Most linux distros)
```

Checkstyle code style coverage report can be created and examined with commands:
```
mvn jxr:jxr checkstyle:checkstyle
open target/site/checkstyle.html (MacOS)
xdg-open target/site/checkstyle.html (Most linux distros)
```

# Documentation
- [User guide](documentation/guide.md)
- [Project definition](documentation/definition.md)
- [Testing and performance documentation](documentation/testingandperformance.md)
- [Implementation document](documentation/implementation.md)

# TODO
- LZW loppuun
- HashMap loppuun
- Varmista testit
- CLI parser?
    - http://tutorials.jenkov.com/java-howto/java-command-line-argument-parser.html
    - https://github.com/jjenkov/cli-args
- Chechstyle
- Dokkarit
    - Performance test
