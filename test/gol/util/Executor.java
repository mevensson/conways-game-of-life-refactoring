package gol.util;


public interface Executor {
	void executeProgram(String args);
	
	void assertExpectations(Expectation expects);
}
