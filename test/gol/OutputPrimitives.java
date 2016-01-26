package gol;

import gol.util.Text;

import java.util.function.*;

public class OutputPrimitives {

	public static Consumer<Text> usage = out -> {
		out.ln();
		out.ln("Usage");
		out.ln(" java gol.Main [ARGUMENTS...]");
		out.ln();
		out.ln(" arguments:");
		out.ln("   -?              Prints this usage help.");
		out.ln("   -w <WIDTH>      Width of simulation view port. Default is 20.");
		out.ln("   -h <HEIGHT>     Height of simulation view port. Default is 15.");
		out.ln("   -f <FILE_PATH>  File with start state. Default is a random start state.");
		out.ln("   -@              Use spaced '@' and '.' instead of default '#' and '-'.");
		out.ln("   -O              Use 'O' instead of default '#'.");
		out.ln("   -s <STEPS>      Number of maximum generation steps. Default is 100.");
		out.ln("   -l <X>          Detect loops of maximum length x. Default is 0 - no loop detection.");
		out.ln("   -t <MS>         Time delay (ms) to wait between each step. Default is 0 ms.");
		out.ln("   -q              Quiet mode. Only outputs the last step in a simulation. Ignores time delay.");
	};

	public static Consumer<Text> step(int step) {
		return out -> {
			out.ln("step " + step);
			out.ln();
		};
	}

	public static Consumer<Text> start = out -> {
		out.ln("start");
		out.ln();
	};

	public static Consumer<Text> blinkerVertical = out -> {
		out.ln("-#-");
		out.ln("-#-");
		out.ln("-#-");
	};

	public static Consumer<Text> blinkerHorizontal = out -> {
		out.ln("---");
		out.ln("###");
		out.ln("---");
	};

	public static Consumer<Text> block = out -> {
		out.ln("----");
		out.ln("-##-");
		out.ln("-##-");
		out.ln("----");
	};
}
