use crate::compressionalgos::huffman::HuffmanCompressor;
use crate::compressionalgos::lzw::LZWCompressor;
use crate::compressionalgos::CompressionAlgo;
use crate::utils::FileIO;
use std::io;
use std::time::Instant;

pub struct Performance;

impl Performance {
    pub fn new() -> Self {
        Performance
    }

    pub fn run_tests(&self) {
        loop {
            println!("Select which type of test to run by giving number:");
            println!("1 - test with different file types");
            println!("2 - linear test with doubling file size");
            println!("0 - quit");

            let mut input = String::new();
            io::stdin()
                .read_line(&mut input)
                .expect("Failed to read line");

            let choice = input.trim();

            match choice {
                "0" => break,
                "1" => {
                    let filenames = vec![
                        "testfiles/smalltestwithtext",
                        "testfiles/smalltestwithwav",
                        "testfiles/mediumtestwithtext",
                        "testfiles/largetestwithwav",
                        "testfiles/largetestwithtext",
                        "testfiles/testwithrandomfile",
                    ];

                    for filename in filenames {
                        self.run_individual_test(filename);
                    }
                }
                "2" => {
                    for i in 1..=12 {
                        let filename = format!("testfiles/lineartesting/{}", i);
                        self.run_individual_test(&filename);
                    }
                }
                _ => println!("Invalid choice, please try again."),
            }
        }
    }

    pub fn run_individual_test(&self, filename: &str) {
        // Get content
        let content = match FileIO::read_file(filename) {
            Ok(data) => data,
            Err(e) => {
                println!("{}", e);
                return;
            }
        };

        println!("====== RUNNING TESTS FOR FILE: {} ======\n", filename);

        // Run LZW tests
        let compressor = LZWCompressor::new();

        let lzw_comp_start = Instant::now();
        let compressed = compressor.compress(&content);
        let lzw_comp_time = lzw_comp_start.elapsed();
        let lzw_ratio = 100.0 * compressed.len() as f64 / content.len() as f64;

        let lzw_decomp_start = Instant::now();
        compressor.decompress(&compressed);
        let lzw_decomp_time = lzw_decomp_start.elapsed();

        // Run Huffman tests
        let compressor = HuffmanCompressor::new();
        let huff_comp_start = Instant::now();

        let compressed = compressor.compress(&content);
        let huff_comp_time = huff_comp_start.elapsed();
        let huff_ratio = 100.0 * compressed.len() as f64 / content.len() as f64;

        let huff_decomp_start = Instant::now();
        compressor.decompress(&compressed);
        let huff_decomp_time = huff_decomp_start.elapsed();

        println!("=== Test results with LZW: ===");
        println!("1. Compression time (ms): {}", lzw_comp_time.as_millis());
        println!(
            "2. Decompression time (ms): {}",
            lzw_decomp_time.as_millis()
        );
        println!("3. Compression ratio achieved: {:.0}%\n", lzw_ratio);

        println!("=== Test results with Huffman: ===");
        println!("1. Compression time (ms): {}", huff_comp_time.as_millis());
        println!(
            "2. Decompression time (ms): {}",
            huff_decomp_time.as_millis()
        );
        println!("3. Compression ratio achieved:{:.0}%\n", huff_ratio);
        println!("==================================\n");
    }
}
