# CLI archiver program

This repo contains an archive tool implemented with both [Lempel-Ziw-Welch (LWZ)](https://en.wikipedia.orga/wiki/Lempel–Ziv–Welch) and [Huffman](https://en.wikipedia.org/wiki/Huffman_coding) algorithms. Tool relies on packing files on byte level so it should work for any kind of files. Program follows usual Unix CLI tool conventions with flags and targets. 

Quick installing guide is provided below and more precise documentation can be found from documentation folder. 

# Quick install and usage guide

Cloning and starting
```
git clone https://github.com/otahontas/archiver-java.git
cd src
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

Jacoco test coverage report can be created with:
```
mvn jacoco:report
```

Checkstyle code style coverage report can be created and examined with commands:
```
mvn jxr:jxr checkstyle:checkstyle
```

# Documentation
- [Implementation document](documentation/implementation.md)
- [User guide](documentation/guide.md)
- [Testing and performance documentation](documentation/testingandperformance.md)

# TODO
- fix lzw implementation
