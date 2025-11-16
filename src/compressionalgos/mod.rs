pub mod huffman;
pub mod lzw;

pub trait CompressionAlgo {
    fn compress(&self, data_to_compress: &[u8]) -> Vec<u8>;
    fn decompress(&self, data_to_decompress: &[u8]) -> Vec<u8>;
}
