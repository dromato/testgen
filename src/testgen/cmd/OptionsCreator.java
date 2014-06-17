package testgen.cmd;

import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class OptionsCreator {
	@SuppressWarnings("static-access")
	public static Options createOptions() {
		Options options = new Options();
		options.addOption(OptionBuilder
				.withLongOpt("input-file")
				.withDescription("Path to input XML file containing all test questions.")
				.hasArg()
				.create("i"));
		options.addOption(OptionBuilder
				.withLongOpt("output-file")
				.withDescription("Path to which output will be written.")
				.hasArg()
				.create("o"));
		options.addOption(OptionBuilder
				.withLongOpt("help")
				.withDescription("Prints this help.")
				.create("h"));
		
		return options;
	}
}
