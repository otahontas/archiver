use archiver::performance::Performance;
use archiver::services::{CLIArgumentReader, CompressorService};
use std::env;

fn main() {
    let args: Vec<String> = env::args().skip(1).collect();

    let cs = CompressorService::new();
    let cli = CLIArgumentReader::new();

    let arguments = cli.parse(&args);

    match arguments.len() {
        1 => {
            let p = Performance::new();
            p.run_tests();
        }
        4 => {
            cs.run(&arguments);
        }
        _ => {}
    }
}
