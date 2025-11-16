use std::fs;
use std::io::{self, ErrorKind};
use std::path::Path;

pub struct FileIO;

impl FileIO {
    pub fn read_file(filename: &str) -> io::Result<Vec<u8>> {
        fs::read(filename)
    }

    pub fn write_file(filename: &str, bytes_to_write: &[u8]) -> io::Result<()> {
        if Path::new(filename).exists() {
            println!(
                "File {} already exists. Give new name for output.",
                filename
            );
            return Err(io::Error::new(
                ErrorKind::AlreadyExists,
                "File already exists",
            ));
        }
        fs::write(filename, bytes_to_write)
    }
}

pub struct Sorter;

impl Sorter {
    pub fn sort_char_array(original: &[char]) -> Vec<char> {
        let mut nums = original.to_vec();
        let l = nums.len();

        for current_place in 0..l.saturating_sub(1) {
            let mut smallest = char::MAX;
            let mut smallest_at = current_place + 1;

            for check in current_place..l {
                if nums[check] < smallest {
                    smallest_at = check;
                    smallest = nums[check];
                }
            }

            nums.swap(current_place, smallest_at);
        }

        nums
    }
}

pub struct Arrays;

impl Arrays {
    pub fn concat_byte_array(a: &[u8], b: &[u8]) -> Vec<u8> {
        let mut array = Vec::with_capacity(a.len() + b.len());
        array.extend_from_slice(a);
        array.extend_from_slice(b);
        array
    }
}

pub struct Math;

impl Math {
    pub fn abs(value: f64) -> f64 {
        if value >= 0.0 {
            value
        } else {
            -value
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_sorter() {
        let chars = vec!['d', 'b', 'c', 'a'];
        let sorted = Sorter::sort_char_array(&chars);
        assert_eq!(sorted, vec!['a', 'b', 'c', 'd']);
    }

    #[test]
    fn test_concat_byte_array() {
        let a = vec![1, 2, 3];
        let b = vec![4, 5, 6];
        let result = Arrays::concat_byte_array(&a, &b);
        assert_eq!(result, vec![1, 2, 3, 4, 5, 6]);
    }

    #[test]
    fn test_math_abs() {
        assert_eq!(Math::abs(5.0), 5.0);
        assert_eq!(Math::abs(-5.0), 5.0);
        assert_eq!(Math::abs(0.0), 0.0);
    }
}
