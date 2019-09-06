package com.otahontas.archiver.compressionalgos.lzw;

import java.util.HashMap;
import java.util.Map;

import com.otahontas.archiver.compressionalgos.CompressionAlgo;
import com.otahontas.archiver.datastructures.List;

public class LZWCompressor implements CompressionAlgo {
	/**
	 * Compresses given data array.
     * Method reads bytes and converts them to a 12 bit long words
	 * Then it takes words, puts them in a buffer array of 3 bytes and writes 
     * buffer to byte list. Method uses list since it can be 
     * @param dataToCompress Byte array from file to be compressed
	 */

    public byte[] compress(byte[] dataToCompress) {
		HashMap<String, Integer> table = new HashMap<>();
		for (int i = 0; i < 256; i++) {
			table.put(Character.toString((char) i), i);
		}
		int count = 256;

        List<Byte> compressedData = new List<>();

		String temp = "";
		byte[] buffer = new byte[3];
		boolean onleft = true;

        int firstByte = new Byte(dataToCompress[0]).intValue();
        if (firstByte < 0) firstByte += 256;
        char firstByteAsChar = (char) firstByte;
        temp = "" + firstByteAsChar;

        for (int i = 1; i < dataToCompress.length; i++) {
            int input = new Byte(dataToCompress[i]).intValue();
            if (input < 0) input += 256;
            char inputAsChar = (char) input;

            if (table.containsKey(temp + inputAsChar)) {
                temp = temp + inputAsChar;
            } else {
                String s12 = to12bit(table.get(temp));
				if (onleft) {
						buffer[0] = (byte) Integer.parseInt(
								s12.substring(0, 8), 2);
						buffer[1] = (byte) Integer.parseInt(
								s12.substring(8, 12) + "0000", 2);
					} else {
					convertToByte(compressedData, buffer, s12);
				}
					onleft = !onleft;
					if (count < 4096) {
						table.put(temp + inputAsChar, count++);
					}
					temp = "" + inputAsChar;
            }
        }
			
        String temp_12 = to12bit(table.get(temp));
			if (onleft) {
				buffer[0] = (byte) Integer.parseInt(temp_12.substring(0, 8), 2);
				buffer[1] = (byte) Integer.parseInt(temp_12.substring(8, 12)
						+ "0000", 2);
                compressedData.add(buffer[0]);
                compressedData.add(buffer[1]);
			} else {
				convertToByte(compressedData, buffer, temp_12);
			}

        byte[] compressed = new byte[compressedData.size()];
        for (int i = 0; i< compressed.length; i++) {
            compressed[i] = compressedData.get(i);
        }

        return compressed;
    }

	private void convertToByte(List<Byte> compressedData, byte[] buffer, String s12) {
		buffer[1] += (byte) Integer.parseInt(
				s12.substring(0, 4), 2);
		buffer[2] = (byte) Integer.parseInt(
				s12.substring(4, 12), 2);
		for (int b = 0; b < buffer.length; b++) {
compressedData.add(buffer[b]);
			buffer[b] = 0;
		}
	}

	public byte[] decompress(byte[] dataToDecompress) {
        String[] Array_char = new String[4096];
		int count = 256;

        for (int i = 0; i < count; i++) {
            Array_char[i] = Character.toString((char) i);
        }

        List<Byte> decompressedData = new List<>();

		int currword, priorword;
		byte[] buffer = new byte[3];
		boolean onleft = true;

			/**
			 * Get the first word in code and output its corresponding character
			 */
			buffer[0] = dataToDecompress[0];
			buffer[1] = dataToDecompress[1];

			priorword = getvalue(buffer[0], buffer[1], onleft);
			onleft = !onleft;
            byte[] bytes = Array_char[priorword].getBytes();

            for (int i = 0; i < bytes.length; i++) {
                decompressedData.add(bytes[i]);
            }

			/**
			 * Read every 3 bytes and generate a corresponding characters - 2
			 * character
			 */
            int i = 2;
			while (i < dataToDecompress.length) {

				if (onleft) {
					buffer[0] = dataToDecompress[i++];
					buffer[1] = dataToDecompress[i++];
					currword = getvalue(buffer[0], buffer[1], onleft);
				} else {
					buffer[2] = dataToDecompress[i++];
					currword = getvalue(buffer[1], buffer[2], onleft);
				}
				onleft = !onleft;
				if (currword >= count) {
					if (count < 4096) {
                        Array_char[count] = Array_char[priorword] + Array_char[priorword].charAt(0);
                    }
					count++;
                    bytes = (Array_char[priorword] + Array_char[priorword].charAt(0)).getBytes();
                    for (int j = 0; j < bytes.length; j++) {
                        decompressedData.add(bytes[j]);
                    }
				} else {
					if (count < 4096) {
                        Array_char[count] = Array_char[priorword] + Array_char[currword].charAt(0);
                    }
					count++;
                    bytes = (Array_char[currword]).getBytes();
                    for (int j = 0; j < bytes.length; j++) {
                        decompressedData.add(bytes[j]);
                    }
				}
				priorword = currword;
			}
            byte[] decompressed = new byte[decompressedData.size()];
            for (int j = 0; j< decompressed.length; j++) {
                decompressed[j] = decompressedData.get(j);
            }
        return decompressed;
    }


	/** Convert 8 bit to 12 bit */
	private String to12bit(int i) {
		String temp = Integer.toBinaryString(i);
		while (temp.length() < 12) {
			temp = "0" + temp;
		}
		return temp;
	}


	/**
	 * Extract the 12 bit key from 2 bytes and get the int value of the key
	 * 
	 * @param b1
	 * @param b2
	 * @param onleft
	 * @return an Integer which holds the value of the key
	 */
	public int getvalue(byte b1, byte b2, boolean onleft) {
		String temp1 = Integer.toBinaryString(b1);
		String temp2 = Integer.toBinaryString(b2);
		while (temp1.length() < 8) {
			temp1 = "0" + temp1;
		}
		if (temp1.length() == 32) {
			temp1 = temp1.substring(24, 32);
		}
		while (temp2.length() < 8) {
			temp2 = "0" + temp2;
		}
		if (temp2.length() == 32) {
			temp2 = temp2.substring(24, 32);
		}

		/** On left being true */
		if (onleft) {
			return Integer.parseInt(temp1 + temp2.substring(0, 4), 2);
		} else {
			return Integer.parseInt(temp1.substring(4, 8) + temp2, 2);
		}
	}
}
