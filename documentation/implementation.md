# Implementation 

## Program structure

Program structure is divided to four packages:
- Compression algorithms, which include interface for algos, package for LZW and package for Huffman
    - LZW could be refactored more, but Huffman follows quite clear convention for helper classes
- Data structures, including algo-spesific structures and couple of structures used by both algos
- Services, mainly handling the program logic, reading command line arguments etc.
- Utils, including some IO, math, sorting and array handling utilities
- (fifth package contains class for performance testing, but isn't necessary for program itself)

### Huffman

Huffman implementation happens in four phases: program counts frequencies for each byte and puts Huffman tree Nodes with frequencies and values into priority queue. The queue is keyed by Node frequencies and supports retrieving minimum frequency from queue. Secondly, huffman tree is formed from Node frequencies by huffmans algorithm itself (based on pseudocode from Cormens book "Introduction to algorithms"). Then, the huffman codewords are built based on huffman tree - basically every leaf with no children has some byte value and codeword which is formed by moving from root to leaf. After collecting all codewords and saving them to array, program goes through file byte by byte, writing codewords to bit array represented as booleans.

Finally, when huffman tree and decoded data are formed, program writes metadata and decoded data to file. To metadata part, the program writes three size, amount of node with some value, amount of trailing bits in the end of file, all node values, huffman tree in preorder traversal (where nodes with values are marked as 1s and nodes without values as 0s) and decoded data itself topped to end with trailing zeros. This is all info needed to decode data afterwards, is a quite efficient way to save tree information to file and doesn't take up too much space.

Decompressing happens in reversed order: first program reads header info described above. Then node values and preorder are extracted from file and huffman tree is formed. Finally program goes through decoded data bit by bit by using huffman tree and turns data as bytes. 

### LZW

LZW algoritmh is implemented by using hashmap as dictionary. Program reads through file and looks for reoccuring byte patterns, which are saved hashmap and retrieved again when encoutered. Program tries to be efficient by using integer count as hashmap index instead of using trie as dictionary. Created codes are taken from hashmap and saved as 12-bit codewords to byte array.

Method is a bit slow and code doesn't look that clear, since time kinda ran out when writing this part of program. It is still tested with same input as Huffman and should be working as well.

### Utils and services

File reading and writing are done with IO util, which is essentially a wrapper for Java NIO package. IO stuff is handled on byte level and class allows program to read and write files as byte arrays. Arrays, Sorter and Math classes include very few methods relevant to the topic, just basically array concatenation for Huffman compression, selection sort for handling command line arguments and Math for HashMap hashing. 

Services are not that important regarding algo or data structure implementations, but are rather useful by cleaning program handling and making program quite easily extendable. CompressorService for example could be extended to take different kind of other compressor algos as arguments (by constructor overloading) or extended by making different version which uses only Java data structures instead of self-written.
 
# Known problems and bugs
- LZW doesn't work if file contains all kind of bytes (all 256 values), 
    - LZW doesn't especially work for when there are certain kinds of negative bytes. This might have relation to value fixing happening while encoding, but I wasn't able to fix the problem during the course.
- HashMap has some problems when adding large amount of values, especially if values are integers turned to Strings (e.g. if one adds numbers 1 - 1000 to HashMap with Integer.toString() - method)
- LZW could definitely be faster since current implementation relies on handling bytes as Strings. Some performance could be achieved by using bit arrays (like in Huffman) or StringBuilder.
- Huffmans metadata could maybe be compressed a bit more
- Code should be abstracted more: List could be extended as BitList, which would have methods to convert between bytes as. 
- Could should be cleaner, since there are a lot of too long methods and classes
