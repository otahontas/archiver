# Guide

To use this program, you need to have Java (at least version 8) installed. Installing the program can be done either by cloning and building or by downloading executable jar-file from [Releases](https://github.com/otahontas/javasynth/releases/latest/). If you want to clone and build, you need to also have [Apache Maven](https://maven.apache.org/) installed. 

Cloning this repo and building executable jar file can be done as follows:
```
git clone https://github.com/otahontas/javasynth.git
mv archiver
mvn package
```
After building, you can find the arhiver.jar -file from target-folder. Program can be run with 'java -jar' handle:

```
java -jar archiver.jar

```

Launching the program without parameters gives unix type guide for using the program. The convention is pretty simple:
- Give two flags as options: first one defines which algorithm program uses to pack or unpack source file, second one defines whether to pack or unpack the file
- Then give two filenames as parameters: first one should be path to source file (either relative or absolute), second one is the name (or full path) to output file. Program automatically adds .compressed -extension to output file, when compressing.
- You can also pass -p flag to run basic performance tests.

Program should be able to handle all kinds of files, since it reads files on byte level and does the compression by calculating bytes. If program was installed by cloning and building, there are some testfiles in archiver/testfiles folder, also found [from here in the repo](archiver/testfiles).

There are some bugs happening, especially with LZW implementation. You can read more about them from [implementation document](implementation.md).
