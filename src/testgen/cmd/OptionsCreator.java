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
				.withArgName("Input file path")
				.create("i"));
		options.addOption(OptionBuilder
				.withLongOpt("output-file")
				.withDescription("Path to which output will be written.")
				.hasArg()
				.withArgName("Output file path")
				.create("o"));
		options.addOption(OptionBuilder
				.withLongOpt("help")
				.withDescription("Prints this help.")
				.create("h"));
		options.addOption(OptionBuilder
				.withLongOpt("size")
				.withDescription("Specifies how many questions should be taken into test.")
				.hasArg()
				.withArgName("No. of questions")
				.create("s"));
		options.addOption(OptionBuilder
				.withLongOpt("equal-distribution")
				.withDescription("Tries to distributes equally questions from sections.")
				.create("e"));
		options.addOption(OptionBuilder
				.withLongOpt("randomize")
				.withDescription("Randomize answers.")
				.create("r"));
		options.addOption(OptionBuilder
				.withLongOpt("groups")
				.withDescription("Specifies how many test groups will be created.")
				.hasArg()
				.withArgName("No. of groups")
				.create("g"));
		options.addOption(OptionBuilder
				.withLongOpt("distinct-groups")
				.withDescription("Make every group distincs.")
				.create("d"));
		return options;
	}
}
