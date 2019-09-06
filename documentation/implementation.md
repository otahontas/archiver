


# Known problems
- LZW doesn't work if file contains all kind of bytes (all 256 values), 
    - LZW doesn't especially work for when there are certain kinds of negative bytes. This might have relation to value fixing happening while encoding, but I wasn't able to fix the problem during the course.
- HashMap has some problems when adding large amount of values, especially if values are integers turned to Strings.
