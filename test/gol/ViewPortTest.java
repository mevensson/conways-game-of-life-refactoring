package gol;

import org.junit.Test;

import static gol.OutputPrimitives.*;

public class ViewPortTest extends AbstractTest{
	@Test
	public void explicitWidthAndHeightBeforeFileGovernsForWideViewport() {
		givenProgramArgs(" -s 0 -w 10 -h 2 -f examples/block");

		output("----------");
		output("-##-------");
		output(start);
	}

	@Test
	public void explicitWidthAndHeightAfterFileGovernsForWideViewport() {
		givenProgramArgs(" -s 0 -f examples/block -w 10 -h 2");

		output("----------");
		output("-##-------");
		output(start);
	}

	@Test
	public void explicitWidthAndHeightBeforeFileGovernsForThinViewport() {
		givenProgramArgs(" -s 0 -w 2 -h 10 -f examples/block");

		output("--");
		output("-#");
		output("-#");
		output("--");
		output("--");
		output("--");
		output("--");
		output("--");
		output("--");
		output("--");
		output(start);
	}

	@Test
	public void explicitWidthAndHeightAfterFileGovernsForThinViewport() {
		givenProgramArgs(" -s 0 -f examples/block -w 2 -h 10");

		output("--");
		output("-#");
		output("-#");
		output("--");
		output("--");
		output("--");
		output("--");
		output("--");
		output("--");
		output("--");
		output(start);
	}	

	@Test
	public void fileWithZeroSize() {
		givenProgramArgs(" -f examples/empty -s 1");

		output(start);
		output(step(1));
	}

	@Test
	public void viewportWithZeroSizeAlsoGivesZeroWidth() {
		givenProgramArgs(" -f examples/blinker -s 1 -w 0 -h 0");

		output(start);
		output(step(1));
	}

	@Test
	public void viewportWithZeroWidthKeepsHeightOfDefaultViewport() {
		givenProgramArgs(" -f examples/blinker -s 1 -w 0 ");

		// blinker has height 3 and that is not overridden
		output();
		output();
		output();
		output(start);

		output();
		output();
		output();
		output(step(1));
	}

	@Test
	public void viewportWithZeroHeight() {
		givenProgramArgs(" -f examples/blinker -s 1 -h 0 ");

		output(start);
		output(step(1));
	}
}
