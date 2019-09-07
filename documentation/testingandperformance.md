# Testing and program performance

Program contains class for testing purposes, which can be run by giving -p flag to program without other parameters. Test includes running both algorithms against different type of files with filesize from 10 kB to 10 mB. Output was following when run with 2,8 Ghz i7 Macbook Pro 2017:

```
====== RUNNING TESTS FOR FILE: testfiles/smalltestwithtext ======

=== Test results with LZW: ===
1. Compression time (ms): 3
2. Decompression time (ms): 0
3. Compression ratio achieved: 95%

=== Test results with Huffman: ===
1. Compression time (ms): 2
2. Decompression time (ms): 0
3. Compression ratio achieved:86%

==================================

====== RUNNING TESTS FOR FILE: testfiles/smalltestwithwav ======

=== Test results with LZW: ===
1. Compression time (ms): 213
2. Decompression time (ms): 149
3. Compression ratio achieved: 139%

=== Test results with Huffman: ===
1. Compression time (ms): 98
2. Decompression time (ms): 84
3. Compression ratio achieved:83%

==================================

====== RUNNING TESTS FOR FILE: testfiles/mediumtestwithtext ======

=== Test results with LZW: ===
1. Compression time (ms): 713
2. Decompression time (ms): 217
3. Compression ratio achieved: 56%

=== Test results with Huffman: ===
1. Compression time (ms): 184
2. Decompression time (ms): 209
3. Compression ratio achieved:57%

==================================

====== RUNNING TESTS FOR FILE: testfiles/largetestwithwav ======

=== Test results with LZW: ===
1. Compression time (ms): 2932
2. Decompression time (ms): 2922
3. Compression ratio achieved: 142%

=== Test results with Huffman: ===
1. Compression time (ms): 4041
2. Decompression time (ms): 3606
3. Compression ratio achieved:94%

==================================

====== RUNNING TESTS FOR FILE: testfiles/largetestwithtext ======

=== Test results with LZW: ===
1. Compression time (ms): 2115
2. Decompression time (ms): 1110
3. Compression ratio achieved: 56%

=== Test results with Huffman: ===
1. Compression time (ms): 1869
2. Decompression time (ms): 2132
3. Compression ratio achieved:57%

==================================

====== RUNNING TESTS FOR FILE: testfiles/testwithrandomfile ======

=== Test results with LZW: ===
1. Compression time (ms): 2292
2. Decompression time (ms): 2071
3. Compression ratio achieved: 93%

=== Test results with Huffman: ===
1. Compression time (ms): 2786
2. Decompression time (ms): 3325
3. Compression ratio achieved:75%

==================================
```

I also conducted some tests following [yussiv's documentation from same kind of project](https://github.com/yussiv/Compress/blob/master/documentation/performance.md). Testing was conducted by using text files created by multiplying original 512 byte text file. Testing was done with same performance testing class as which used above. Results were following:

## Huffman

| input size (bytes) | compression ratio (%) | compression time (ms) | decompression time (ms) |
|---|---|---|---|
| 512 | 78 | 0 | 0 |
| 1 024 | 70 |  1  | 1 |
| 2 048 | 66 | 1 | 2 |
| 4 096 | 65 | 1 | 4 |
| 8 192 | 64 | 3 | 6 |
| 16 384 | 63 | 5 | 4 |
| 32 768 | 63 | 6 | 5 |
| 65 536 | 63 | 7 | 7 |
| 131 072 | 63 | 16 | 9 |
| 262 144 | 63 | 36 | 39 |
| 524 288 | 63 | 51 | 80 |
| 1 048 576 | 63 | 91 | 150 |

## LZW 

| input size (bytes) | compression ratio (%) | compression time (ms) | decompression time (ms) |
|---|---|---|---|
| 512 | 108 | 6 | 2 |
| 1 024 | 93 |  2  | 2 |
| 2 048 | 78 | 2 | 1 |
| 4 096 | 67 | 4 | 2 |
| 8 192 | 51 | 7 | 4 |
| 16 384 | 40 | 13 | 5 |
| 32 768 | 32 | 21 | 9 |
| 65 536 | 28 | 17 | 5 |
| 131 072 | 26 | 29 | 11 |
| 262 144 | 25 | 56 | 12 |
| 524 288 | 25 | 81 | 23  |
| 1 048 576 | 24 | 116 | 50 |


As we can see LZW is quite superior to Huffman regarding compression ratio. Huffman is a little bit faster, but at this level it doesn't really matter. 

Both of these tests above can be run again by using -p flag when running the program from CLI (e.g. "java -jar archiver.jar -p"). Note that you need to have testfiles-folder clone to project root when running the tests.

## Big files

Comparing LZW, Huffman and unix zip-program with random 10 MB file created with "base64 /dev/urandom | head -c 10000000 > file.txt"
- Original: 10MB
- LZW: 7,15 MB
- Huffman: 7,15 MB
- Zip: 7,22 MB

## Closing

Regarding compression efficiency, performance and their balance, I'm quite happy with the final results. It seems that LZW is quite more powerful on text files that have a lot of repeating bytes (this is quite expected since it's a bit more efficient on paper), but huffman reaches better results on files with more random byte distribution. My huffman implementation is a little bit faster than LZW, eventhough they're both on same O(n log n) level on paper. This might be related to too slow Java implementation, which uses Strings, rather than Bit arrays. 

Both algoritmhs have their upsides and downsides and with a little bit of tweaking and some heurestics, this tool could be modified to always use best algo for certain filetypes. So not bad, at least comparing to how much time I was able to put on this project.
