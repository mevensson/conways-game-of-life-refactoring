package gol;

import static gol.OutputPrimitives.start;
import static gol.OutputPrimitives.usage;

import org.junit.Test;

public class ArgumentsTest extends AbstractTest {
	
	@Test
	public void invalidArgumentShouldGiveUsage() {
		givenProgramArgs("-x foo");

		output("Unknown argument -x");
		output(usage);
	}

	@Test
	public void printHelp() {
		givenProgramArgs(" -f examples/block -? -s 2");

		output("Help requested");
		output(usage);
	}

	@Test
	public void unknownFileDisplaysUsage() {
		givenProgramArgs("-f examples/nonexisting");

		output("examples/nonexisting (No such file or directory)");
		output(usage);
	}

	@Test
	public void fileIsADirectoryDisplaysUsage() {
		givenProgramArgs("-f examples");

		output("examples (Is a directory)");
		output(usage);
	}

	@Test
	public void minusOneInArgumentDisplaysUsage() {
		givenProgramArgs("-s -1");

		output("Invalid argument value -1");
		output(usage);
	}

	@Test
	public void minusTwoInArgumentDisplaysUsage() {
		givenProgramArgs("-s -2");

		output("Invalid argument value -2");
		output(usage);
	}

	@Test
	public void tryToLoadFaultyCharactersWorldDisplaysUsage() {
		givenProgramArgs("-f examples/blinker_bad_characters");

		output("Invalid character '.' on line 1 in file examples/blinker_bad_characters");
		output(usage);
	}

	@Test
	public void loadBadlyFormattedWorld() {
		givenProgramArgs("-f examples/badFormating -s 0");

		output("--##--");
		output("------");
		output("--##--");
		output("------");
		output("------");
		output(start);
	}
}
