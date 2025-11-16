use crate::compressionalgos::CompressionAlgo;
use crate::datastructures::HashMap;

pub struct LZWCompressor;

impl LZWCompressor {
    pub fn new() -> Self {
        LZWCompressor
    }
}

impl CompressionAlgo for LZWCompressor {
    fn compress(&self, data_to_compress: &[u8]) -> Vec<u8> {
        let mut table = HashMap::new();
        for i in 0..256 {
            table.put(vec![i as u8], i);
        }
        let mut count = 256;

        let mut compressed_data = Vec::new();
        let mut buffer = [0u8; 3];
        let mut on_left = true;

        let mut temp = vec![data_to_compress[0]];

        for i in 1..data_to_compress.len() {
            let input = data_to_compress[i];
            let mut new_temp = temp.clone();
            new_temp.push(input);

            if table.contains(&new_temp) {
                temp = new_temp;
            } else {
                let code = table.get(&temp).unwrap();
                let s12 = to_12bit(code);

                if on_left {
                    buffer[0] = u8::from_str_radix(&s12[0..8], 2).unwrap();
                    buffer[1] = u8::from_str_radix(&format!("{:0<8}", &s12[8..12]), 2).unwrap();
                } else {
                    convert_to_byte(&mut compressed_data, &mut buffer, &s12);
                }
                on_left = !on_left;

                if count < 4096 {
                    table.put(new_temp, count);
                    count += 1;
                }
                temp = vec![input];
            }
        }

        let temp_12 = to_12bit(table.get(&temp).unwrap());
        if on_left {
            buffer[0] = u8::from_str_radix(&temp_12[0..8], 2).unwrap();
            buffer[1] = u8::from_str_radix(&format!("{:0<8}", &temp_12[8..12]), 2).unwrap();
            compressed_data.push(buffer[0]);
            compressed_data.push(buffer[1]);
        } else {
            convert_to_byte(&mut compressed_data, &mut buffer, &temp_12);
        }

        compressed_data
    }

    fn decompress(&self, data_to_decompress: &[u8]) -> Vec<u8> {
        let mut array_char = vec![Vec::new(); 4096];
        let mut count = 256;

        for i in 0..count {
            array_char[i] = vec![i as u8];
        }

        let mut decompressed_data = Vec::new();
        let mut buffer = [0u8; 3];
        let mut on_left = true;

        buffer[0] = data_to_decompress[0];
        buffer[1] = data_to_decompress[1];

        let mut priorword = get_value(buffer[0], buffer[1], on_left);
        on_left = !on_left;

        decompressed_data.extend_from_slice(&array_char[priorword]);

        let mut i = 2;
        while i < data_to_decompress.len() {
            let currword;

            if on_left {
                buffer[0] = data_to_decompress[i];
                i += 1;
                buffer[1] = data_to_decompress[i];
                i += 1;
                currword = get_value(buffer[0], buffer[1], on_left);
            } else {
                buffer[2] = data_to_decompress[i];
                i += 1;
                currword = get_value(buffer[1], buffer[2], on_left);
            }
            on_left = !on_left;

            if currword >= count {
                if count < 4096 {
                    let mut new_entry = array_char[priorword].clone();
                    new_entry.push(array_char[priorword][0]);
                    array_char[count] = new_entry.clone();
                    decompressed_data.extend_from_slice(&new_entry);
                } else {
                    let mut new_entry = array_char[priorword].clone();
                    new_entry.push(array_char[priorword][0]);
                    decompressed_data.extend_from_slice(&new_entry);
                }
                count += 1;
            } else {
                if count < 4096 {
                    let mut new_entry = array_char[priorword].clone();
                    new_entry.push(array_char[currword][0]);
                    array_char[count] = new_entry;
                }
                count += 1;
                decompressed_data.extend_from_slice(&array_char[currword]);
            }
            priorword = currword;
        }

        decompressed_data
    }
}

fn to_12bit(i: usize) -> String {
    format!("{:012b}", i)
}

fn convert_to_byte(compressed_data: &mut Vec<u8>, buffer: &mut [u8; 3], s12: &str) {
    buffer[1] += u8::from_str_radix(&s12[0..4], 2).unwrap();
    buffer[2] = u8::from_str_radix(&s12[4..12], 2).unwrap();
    compressed_data.push(buffer[0]);
    compressed_data.push(buffer[1]);
    compressed_data.push(buffer[2]);
    buffer[0] = 0;
    buffer[1] = 0;
    buffer[2] = 0;
}

fn get_value(b1: u8, b2: u8, on_left: bool) -> usize {
    let temp1 = format!("{:08b}", b1);
    let temp2 = format!("{:08b}", b2);

    if on_left {
        usize::from_str_radix(&format!("{}{}", temp1, &temp2[0..4]), 2).unwrap()
    } else {
        usize::from_str_radix(&format!("{}{}", &temp1[4..8], temp2), 2).unwrap()
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_lzw_compress_decompress_simple() {
        let compressor = LZWCompressor::new();
        let data = b"hello world";
        let compressed = compressor.compress(data);
        let decompressed = compressor.decompress(&compressed);
        assert_eq!(data, &decompressed[..]);
    }

    #[test]
    fn test_lzw_compress_decompress_repeated() {
        let compressor = LZWCompressor::new();
        let data = b"aaaaaaaaaa";
        let compressed = compressor.compress(data);
        let decompressed = compressor.decompress(&compressed);
        assert_eq!(data, &decompressed[..]);
    }

    #[test]
    fn test_lzw_compress_decompress_pattern() {
        let compressor = LZWCompressor::new();
        let data = b"abcabcabcabc";
        let compressed = compressor.compress(data);
        let decompressed = compressor.decompress(&compressed);
        assert_eq!(data, &decompressed[..]);
    }
}
