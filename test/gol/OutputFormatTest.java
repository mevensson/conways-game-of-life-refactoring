package gol;

import static gol.OutputPrimitives.start;

import org.junit.Test;

public class OutputFormatTest extends AbstractTest {

	@Test
	public void usingBigOSign() {
		givenProgramArgs(" -s 0 -f examples/block -O ");

		output("----");
		output("-OO-");
		output("-OO-");
		output("----");
		output(start);
	}

	@Test
	public void usingAtSign() {
		givenProgramArgs(" -s 0 -f examples/block -@ ");

		output(". . . . ");
		output(". @ @ . ");
		output(". @ @ . ");
		output(". . . . ");
		output(start);
	}
}
