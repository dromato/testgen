package testgen.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import testgen.cmd.OptionsCreator;
import testgen.data.assembler.TestAssembler;
import testgen.data.io.XmlReader;
import testgen.data.model.Test;

public class TestGenerator {

	public static void main(String[] args) {
		Options options = OptionsCreator.createOptions();
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = parseInputArgs(args, options, parser);

		handleHelp(commandLine, options);
		validateCommandLine(commandLine);

		TestAssembler assembler = new TestAssembler();
		Document document = readInput(commandLine);
		Test test = assembler.assemblyFromXmlDocument(document);
		
		createOutputDirectory(commandLine.getOptionValue("o"));
		
		int nOfGroups = commandLine.hasOption("g") ? Integer.valueOf(commandLine.getOptionValue("g")).intValue() : 1;
		StringBuilder output;
		for(int i = 0; i < nOfGroups; i++) {
			output = assembler.assemblyStringFromTest(test, commandLine);
			writeOutput(commandLine, output, "group-" + (i + 1) + ".txt");
		}
		if(commandLine.hasOption("k")) {
			output = assembler.assemblyKeyFormTest(test);
			writeOutput(commandLine, output, "key.txt");
		}
	}

	private static void createOutputDirectory(String dir) {
		if(Files.exists(Paths.get(dir), LinkOption.NOFOLLOW_LINKS)) {
			return;
		}
		if(!new File(dir).mkdir()) {
			System.out.println("Can not create directory: " + dir);
			System.exit(1);
		}
	}

	private static void handleHelp(CommandLine commandLine, Options options) {
		if (commandLine.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("testgen", options);
			System.exit(1);
		}
	}

	private static void validateCommandLine(CommandLine commandLine) {
		if (!commandLine.hasOption("i") || !commandLine.hasOption("o")) {
			System.out.println("Both input and output files are needed.");
			System.exit(1);
		}
	}

	private static CommandLine parseInputArgs(String[] args, Options options, CommandLineParser parser) {
		try {
			return parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			System.exit(1);
			return null;
		}
	}

	private static Document readInput(CommandLine commandLine) {
		XmlReader reader = new XmlReader();
		Document document = null;
		try {
			document = reader.readXmlDocumentFrom(commandLine.getOptionValue("i"));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		return document;
	}

	private static void writeOutput(CommandLine commandLine, StringBuilder output, String fileName) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(commandLine.getOptionValue("o") + System.getProperty("file.separator") + fileName);
			writer.write(output.toString());
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					System.exit(1);
				}
			}
		}
	}
}
