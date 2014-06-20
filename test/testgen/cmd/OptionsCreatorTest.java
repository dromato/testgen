package testgen.cmd;

import junit.framework.Assert;

import org.apache.commons.cli.Options;
import org.junit.Test;

public class OptionsCreatorTest {
	
	@Test
	public void optionCreationTest() {
		Options options = OptionsCreator.createOptions();
		Assert.assertTrue(options.hasOption("i"));
		Assert.assertTrue(options.hasOption("o"));
		Assert.assertTrue(options.hasOption("r"));
		Assert.assertTrue(options.hasOption("s"));
		Assert.assertTrue(options.hasOption("e"));
		Assert.assertTrue(options.hasOption("k"));
		Assert.assertTrue(options.hasOption("h"));
		Assert.assertTrue(options.hasOption("g"));
	}
}
