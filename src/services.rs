use crate::compressionalgos::huffman::HuffmanCompressor;
use crate::compressionalgos::lzw::LZWCompressor;
use crate::compressionalgos::CompressionAlgo;
use crate::utils::{FileIO, Sorter};

pub struct CLIArgumentReader;

impl CLIArgumentReader {
    pub fn new() -> Self {
        CLIArgumentReader
    }

    pub fn parse(&self, args: &[String]) -> Vec<String> {
        if args.len() == 1 && args[0] == "-p" {
            return vec!["-p".to_string()];
        }

        if args.len() != 3 {
            self.print_error("argument");
            self.print_guide();
            return Vec::new();
        }

        let flags = &args[0];
        if flags.len() != 3 || !flags.starts_with('-') {
            self.print_error("flag");
            self.print_guide();
            return Vec::new();
        }

        let flags_chars: Vec<char> = flags.chars().collect();
        let flags_as_chars = Sorter::sort_char_array(&flags_chars);

        let command = flags_as_chars[1];
        let algo = flags_as_chars[2];

        if !((command == 'e' || command == 'c') && (algo == 'l' || algo == 'h')) {
            self.print_error("flagtype");
            self.print_guide();
            return Vec::new();
        }

        vec![
            command.to_string(),
            algo.to_string(),
            args[1].clone(),
            args[2].clone(),
        ]
    }

    fn print_guide(&self) {
        let info = "A CLI tool to compress and uncompress wav files";
        let usage = "Usage: archiver [-opts] source dest\n\
                    e.g. archiver -lc orig.wav compressed\n\n\
                    Define algorithm and desired functionality with opts:\n\
                    -l Use LZW compression \t -h Use Huffman compression\n\
                    -c Compress file \t -e Extract file\n\n\
                    Optionally performance tests can be run with flag -p";

        println!("{}\n{}", info, usage);
    }

    fn print_error(&self, error: &str) {
        let argument_error = "Wrong number of arguments! Correct usage below:\n";
        let flag_error = "Incorrect usage of flags! Correct usage below:\n";
        let flag_type_error = "Incorrect flag, see correct usage below\n";

        match error {
            "argument" => println!("{}", argument_error),
            "flag" => println!("{}", flag_error),
            "flagtype" => println!("{}", flag_type_error),
            _ => {}
        }
    }
}

pub struct CompressorService;

impl CompressorService {
    pub fn new() -> Self {
        CompressorService
    }

    pub fn run(&self, params: &[String]) {
        let command = params[0].chars().next().unwrap();
        let algo = params[1].chars().next().unwrap();
        let source = &params[2];
        let output = &params[3];

        let data_bytearray = match FileIO::read_file(source) {
            Ok(data) => data,
            Err(e) => {
                println!(
                    "File given as source doesn't exist or couldn't be read, see error below:"
                );
                println!("{}", e);
                return;
            }
        };

        let compressor: Box<dyn CompressionAlgo> = match algo {
            'l' => Box::new(LZWCompressor::new()),
            'h' => Box::new(HuffmanCompressor::new()),
            _ => {
                println!("Invalid algorithm");
                return;
            }
        };

        let new_data = match command {
            'c' => compressor.compress(&data_bytearray),
            'e' => compressor.decompress(&data_bytearray),
            _ => {
                println!("Invalid command");
                return;
            }
        };

        if new_data.is_empty() {
            println!("Couldn't compress or extract file given");
            return;
        }

        let final_output = if command == 'c' {
            format!("{}.compressed", output)
        } else {
            output.to_string()
        };

        match FileIO::write_file(&final_output, &new_data) {
            Ok(_) => {}
            Err(e) => {
                println!("Couldn't write the file, see error below:");
                println!("{}", e);
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_cli_argument_reader_valid_args() {
        let cli = CLIArgumentReader::new();
        let args = vec![
            "-ch".to_string(),
            "input.txt".to_string(),
            "output".to_string(),
        ];
        let result = cli.parse(&args);
        assert_eq!(result.len(), 4);
        assert_eq!(result[0], "c");
        assert_eq!(result[1], "h");
        assert_eq!(result[2], "input.txt");
        assert_eq!(result[3], "output");
    }

    #[test]
    fn test_cli_argument_reader_invalid_args() {
        let cli = CLIArgumentReader::new();
        let args = vec!["invalid".to_string()];
        let result = cli.parse(&args);
        assert_eq!(result.len(), 0);
    }

    #[test]
    fn test_cli_argument_reader_performance_flag() {
        let cli = CLIArgumentReader::new();
        let args = vec!["-p".to_string()];
        let result = cli.parse(&args);
        assert_eq!(result.len(), 1);
        assert_eq!(result[0], "-p");
    }
}
