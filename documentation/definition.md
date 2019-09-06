# Definition

## What data structures and algorithms will you be using

I'm implementing Huffman and LZW packing algoritmhs. Huffman uses tree called Huffman tree and heap-based priorityqueue to form it. LZW uses hashtable to form keywords. I'm also needing List for HashMap and boolean lists I'm using and pair for hashmap.

## What is the program input and how will it be used

When compressing, program takes any kind of file as input and packs it. Decompression is done by reading compressed file and unpacking it as a perfect copy of original file.

## What problem are you solving and why did you chose these specific data structures and algorithms

Program is trying to create efficient compressing and decompressing methods regarding both compressed file size and compression time. 

## Expected time and space complexities of the program (big-O notations)

Huffmans priority queue should be able to add new node in O(log n) and get minimum value in constant time O(1). Forming priority queue should take O(n log n), where n is amount of different bytes (256). Huffman tree should also have adding and searching operations in O(h), where h is trees height (max 255 for 256 different bytes). It also takes O(n) to count frequencies of each byte from file. 

LZW uses hashtable as dictionary for packing, which should support adding, searching and deleting operations in O(1).

## Lähteet
- https://www.cs.helsinki.fi/courses/582487/2015/K/K/1
- Cormen: Introduction to algorithms (2009, 3rd edition)
- https://en.wikipedia.org/wiki/Hash_table
- https://en.wikipedia.org/wiki/Huffman_coding
- https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch
