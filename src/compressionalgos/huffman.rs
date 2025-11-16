use crate::compressionalgos::CompressionAlgo;
use crate::datastructures::{HuffmanTree, List, MinimumPriorityQueue, Node};
use crate::utils::Arrays;

pub struct HuffmanCompressor;

impl HuffmanCompressor {
    pub fn new() -> Self {
        HuffmanCompressor
    }
}

impl CompressionAlgo for HuffmanCompressor {
    fn compress(&self, data_to_compress: &[u8]) -> Vec<u8> {
        let mut freq_calc = FrequencyCalculator::new();
        let mut prio_queue = freq_calc.create_node_queue(data_to_compress);

        let mut huff_tree = HuffmanTree::new();
        huff_tree.form_huffman_tree_for_encoding(&mut prio_queue);

        let mut constructor = CodeConstructor::new();
        constructor.construct_code_words_preorder_and_node_values(&mut huff_tree);

        let codewords = constructor.get_codewords();
        let mut compressed = List::new();

        for &b in data_to_compress {
            let index = (b as i8 as i32 + 128) as usize;
            compressed.extend(&codewords[index]);
        }

        let huffman_tree_values = huff_tree.get_values();
        let header = collect_header_information_for_compression(
            compressed.size(),
            &huff_tree,
            huffman_tree_values.len(),
        );
        let tree_order_and_data = collect_data_for_compression(&compressed, header[3], &huff_tree);

        let mut compressed_data_to_write = Arrays::concat_byte_array(&header, &huffman_tree_values);
        compressed_data_to_write =
            Arrays::concat_byte_array(&compressed_data_to_write, &tree_order_and_data);

        compressed_data_to_write
    }

    fn decompress(&self, data_to_decompress: &[u8]) -> Vec<u8> {
        let header = collect_header_information_for_decompression(data_to_decompress);

        let mut node_values = vec![0u8; header[1] as usize];
        for i in 0..header[1] as usize {
            node_values[i] = data_to_decompress[i + 4];
        }

        let header_size = 4 + node_values.len();

        let extracted = extract_huffman_tree_and_data(&header, header_size, data_to_decompress);

        let mut huff_tree = HuffmanTree::new();
        huff_tree.form_huffman_tree_for_decoding(&extracted[0], &node_values);

        decode_data(&extracted[1], &huff_tree)
    }
}

struct FrequencyCalculator {
    frequencies: Vec<i32>,
}

impl FrequencyCalculator {
    fn new() -> Self {
        FrequencyCalculator {
            frequencies: vec![0; 256],
        }
    }

    fn create_node_queue(&mut self, bytes: &[u8]) -> MinimumPriorityQueue {
        self.count_frequencies(bytes);
        let mut node_queue = MinimumPriorityQueue::new();

        for i in 0..self.frequencies.len() {
            if self.frequencies[i] != 0 {
                node_queue.insert(Node::new(self.frequencies[i], i as i32 - 128));
            }
        }

        node_queue
    }

    fn count_frequencies(&mut self, bytes: &[u8]) {
        for &b in bytes {
            let index = (b as i8 as i32 + 128) as usize;
            self.frequencies[index] += 1;
        }
    }
}

struct CodeConstructor {
    codewords: Vec<List<bool>>,
    tree_pre_order: List<bool>,
    values: List<u8>,
}

impl CodeConstructor {
    fn new() -> Self {
        CodeConstructor {
            codewords: (0..256).map(|_| List::new()).collect(),
            tree_pre_order: List::new(),
            values: List::new(),
        }
    }

    fn construct_code_words_preorder_and_node_values(&mut self, huff_tree: &mut HuffmanTree) {
        let root = huff_tree.get_root();
        self.form_code_words_recursively(root, &List::new());
        huff_tree.set_tree_pre_order(self.tree_pre_order.clone());
        huff_tree.set_values(self.values.clone());
    }

    fn get_codewords(&self) -> &Vec<List<bool>> {
        &self.codewords
    }

    fn form_code_words_recursively(&mut self, node: &Node, code: &List<bool>) {
        if node.get_value() != 128 {
            self.tree_pre_order.add(true);
            self.values.add(node.get_value() as u8);
        } else {
            self.tree_pre_order.add(false);
        }

        if node.get_left_child().is_none() && node.get_right_child().is_none() {
            // Only assign codeword if it's not a dummy node (value != 128)
            if node.get_value() != 128 {
                let index = (node.get_value() + 128) as usize;
                self.codewords[index] = code.clone();
            }
            return;
        }

        let mut left = code.clone();
        let mut right = code.clone();
        left.add(false);
        right.add(true);

        if let Some(left_child) = node.get_left_child() {
            self.form_code_words_recursively(left_child, &left);
        }
        if let Some(right_child) = node.get_right_child() {
            self.form_code_words_recursively(right_child, &right);
        }
    }
}

fn collect_header_information_for_compression(
    size_of_compressed_bits: usize,
    huff_tree: &HuffmanTree,
    values_size: usize,
) -> Vec<u8> {
    let mut header = vec![0u8; 4];
    let tree_size = huff_tree.get_tree_size();
    let tree_size_bytes = tree_size.to_be_bytes();
    header[0] = tree_size_bytes[0];
    header[1] = tree_size_bytes[1];
    header[2] = (values_size as i32 - 129) as u8;
    header[3] = (8 - ((tree_size as usize + size_of_compressed_bits) % 8)) as u8;
    header
}

fn collect_data_for_compression(
    compressed: &List<bool>,
    trailing: u8,
    huff_tree: &HuffmanTree,
) -> Vec<u8> {
    let mut rest_bits = huff_tree.get_tree_pre_order().clone();
    rest_bits.extend(compressed);

    for _ in 0..trailing {
        rest_bits.add(false);
    }

    let mut rest_bytes = vec![0u8; rest_bits.size() / 8];
    for entry in 0..rest_bytes.len() {
        for bit in 0..8 {
            if rest_bits.get(entry * 8 + bit) {
                rest_bytes[entry] |= 128 >> bit;
            }
        }
    }

    rest_bytes
}

fn collect_header_information_for_decompression(data_to_decompress: &[u8]) -> Vec<i16> {
    let tree_size = i16::from_be_bytes([data_to_decompress[0], data_to_decompress[1]]);
    // Treat the byte as signed (i8) then convert to i16
    let values_count = 129 + (data_to_decompress[2] as i8) as i16;
    let trailing = data_to_decompress[3] as i16;

    vec![tree_size, values_count, trailing]
}

fn turn_bytes_to_bits(rest_bytes: &[u8]) -> List<bool> {
    let mut bits = List::new();
    for &b in rest_bytes {
        for i in (0..8).rev() {
            bits.add((b & (1 << i)) != 0);
        }
    }
    bits
}

fn decode_data(encoded_data: &List<bool>, decode_tree: &HuffmanTree) -> Vec<u8> {
    let root = decode_tree.get_root();
    let mut buffer = Vec::new();

    // Handle edge case: if root is a leaf (single unique character)
    if root.get_left_child().is_none() && root.get_right_child().is_none() {
        // For each bit in encoded data, output the root's value
        for _ in 0..encoded_data.size() {
            buffer.push(root.get_value() as u8);
        }
        return buffer;
    }

    let mut current = root;
    for i in 0..encoded_data.size() {
        current = if encoded_data.get(i) {
            current.get_right_child().unwrap()
        } else {
            current.get_left_child().unwrap()
        };

        if current.get_left_child().is_none() && current.get_right_child().is_none() {
            buffer.push(current.get_value() as u8);
            current = root;
        }
    }

    buffer
}

fn extract_huffman_tree_and_data(
    header: &[i16],
    header_size: usize,
    data_to_decompress: &[u8],
) -> Vec<List<bool>> {
    let rest_bytes = &data_to_decompress[header_size..];
    let mut rest_bits = turn_bytes_to_bits(rest_bytes);
    rest_bits.remove_last(header[2] as usize);

    let mut huffman_tree_in_preorder = List::new();
    let mut encoded_data = List::new();

    for i in 0..header[0] as usize {
        huffman_tree_in_preorder.add(rest_bits.get(i));
    }

    for i in header[0] as usize..rest_bits.size() {
        encoded_data.add(rest_bits.get(i));
    }

    vec![huffman_tree_in_preorder, encoded_data]
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_huffman_compress_decompress_simple() {
        let compressor = HuffmanCompressor::new();
        let data = b"hello world";
        let compressed = compressor.compress(data);
        let decompressed = compressor.decompress(&compressed);
        assert_eq!(data, &decompressed[..]);
    }

    #[test]
    fn test_huffman_compress_decompress_all_bytes() {
        let compressor = HuffmanCompressor::new();
        let data: Vec<u8> = (0..=255).collect();
        let compressed = compressor.compress(&data);
        let decompressed = compressor.decompress(&compressed);
        assert_eq!(data, decompressed);
    }

    #[test]
    fn test_huffman_compress_decompress_repeated() {
        let compressor = HuffmanCompressor::new();
        let data = b"aaaaaaaaaa";
        let compressed = compressor.compress(data);
        let decompressed = compressor.decompress(&compressed);
        assert_eq!(data, &decompressed[..]);
    }
}
