package testgen.app;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import testgen.cmd.OptionsCreator;

public class TestGenerator {

	public static void main(String[] args) {
		Options options = OptionsCreator.createOptions();
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = parseInputArgs(args, options, parser);

		handleHelp(commandLine, options);
		validateCommandLine(commandLine);

		
	}

	private static void handleHelp(CommandLine commandLine, Options options) {
		if(commandLine.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("testgen", options);
			System.exit(1);
		}
	}

	private static void validateCommandLine(CommandLine commandLine) {
		if(!commandLine.hasOption("i") || !commandLine.hasOption("o")) {
			System.out.println("Both input and output files are needed.");
			System.exit(1);
		}
	}

	private static CommandLine parseInputArgs(String[] args, Options options,
			CommandLineParser parser) {
		try {
			return parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			System.exit(1);
			return null;
		}
	}

}
