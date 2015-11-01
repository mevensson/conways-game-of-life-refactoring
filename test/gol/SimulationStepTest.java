package gol;

import org.junit.Test;

import static gol.OutputPrimitives.*;

public class SimulationStepTest extends AbstractTest {

	@Test
	public void blinkerDoBlink() {
		givenProgramArgs(" -s 3 -f examples/blinker ");

		output(blinkerHorizontal);
		output(start);

		output(blinkerVertical);
		output(step(1));

		output(blinkerHorizontal);
		output(step(2));

		output(blinkerVertical);
		output(step(3));
	}

	@Test
	public void overpopulationWillMakeCellsDie() {
		givenProgramArgs(" -s 2 -f examples/cross ");

		output("-----");
		output("--#--");
		output("-###-");
		output("--#--");
		output("-----");
		output(start);

		output("-----");
		output("-###-");
		output("-#-#-");
		output("-###-");
		output("-----");
		output(step(1));

		output("--#--");
		output("-#-#-");
		output("#---#");
		output("-#-#-");
		output("--#--");
		output(step(2));
	}
	
	@Test
	public void aliveCellsOutsideViewPortHorizontal() {
		givenProgramArgs(" -s 3 -f examples/blinker_thin_horizontal ");

		output("--###--");
		output(start);

		output("---#---");
		output(step(1));

		output("--###--");
		output(step(2));

		output("---#---");
		output(step(3));
	}

	@Test
	public void aliveCellsOutsideViewPortVertical() {
		givenProgramArgs(" -s 3 -f examples/blinker_thin_vertical ");

		output("-");
		output("#");
		output("#");
		output("#");
		output("-");
		output(start);

		output("-");
		output("-");
		output("#");
		output("-");
		output("-");
		output(step(1));

		output("-");
		output("#");
		output("#");
		output("#");
		output("-");
		output(step(2));

		output("-");
		output("-");
		output("#");
		output("-");
		output("-");
		output(step(3));
	}
}
