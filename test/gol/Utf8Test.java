package gol;

import static gol.OutputPrimitives.start;

import org.junit.Test;

public class Utf8Test extends AbstractTest {

	@Test
	public void usingUtf8Blocks() {
		givenProgramArgs(" -s 0 -f examples/block -b ");

		output("░░░░");
		output("░██░");
		output("░██░");
		output("░░░░");
		output(start);
	}	
	
	@Test
	public void usingUtf8Circles() {
		givenProgramArgs(" -s 0 -f examples/block -c ");

		output("◌ ◌ ◌ ◌ ");
		output("◌ ● ● ◌ ");
		output("◌ ● ● ◌ ");
		output("◌ ◌ ◌ ◌ ");
		output(start);
	}

	
}
