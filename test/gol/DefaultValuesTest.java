package gol;

import org.junit.Test;

public class DefaultValuesTest extends AbstractTest {

	@Test
	public void everythingIsDefault() {
		givenProgramArgs("");

		outputHasStepCount(100);
		viewportHasWidth(20);
		viewportHasHeight(15);
	}

	@Test
	public void everythingIsDefaultButFile() {
		givenProgramArgs("-f examples/centinal");

		outputHasStepCount(100);
		viewportHasWidth(52);
		viewportHasHeight(17);
	}


	@Test
	public void everythingIsDefaultButNumberOfSteps() {
		givenProgramArgs("-s 4");

		outputHasStepCount(4);
		viewportHasWidth(20);
		viewportHasHeight(15);
	}

	@Test
	public void everythingIsDefaultButViewportWidth() {
		givenProgramArgs("-w 7");

		outputHasStepCount(100);
		viewportHasWidth(7);
		viewportHasHeight(15);
	}

	@Test
	public void everythingIsDefaultButViewportHeight() {
		givenProgramArgs("-h 63");

		outputHasStepCount(100);
		viewportHasWidth(20);
		viewportHasHeight(63);
	}
}
