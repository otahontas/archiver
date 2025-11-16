# CLI archiver program

This repo contains an archive tool implemented with both [Lempel-Ziv-Welch (LZW)](https://en.wikipedia.org/wiki/Lempel–Ziv–Welch) and [Huffman](https://en.wikipedia.org/wiki/Huffman_coding) algorithms. Tool relies on packing files on byte level so it should work for any kind of files. Program follows usual Unix CLI tool conventions with flags and targets.

Quick installing guide is provided below and more precise documentation can be found from documentation folder.

# Quick install and usage guide

Cloning and starting
```
git clone https://github.com/otahontas/archiver.git
cd archiver
```

Building the program:
```
cargo build --release
```

Running the program after building:
```
./target/release/archiver
```
*(Launching the program without parameters gives unix type guide for using the program)*

Example usage:
```
# Compress a file using LZW
./target/release/archiver -lc input.txt output

# Decompress a file using LZW
./target/release/archiver -le input.txt.compressed output.txt

# Compress a file using Huffman
./target/release/archiver -hc input.txt output

# Run performance tests
./target/release/archiver -p
```

Tests can be run with command:
```
cargo test
```

# Documentation
- [Implementation document](documentation/implementation.md)
- [User guide](documentation/guide.md)
- [Testing and performance documentation](documentation/testingandperformance.md)
