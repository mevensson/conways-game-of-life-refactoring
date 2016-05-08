package gol.game.output;

public enum OutputFormat {
	DEFAULT("#", "-"),
	AT_SIGNS("@ ", ". "),
	O_SIGNS("O", "-");

	private String aliveSign;
	private String deadSign;

	private OutputFormat(final String aliveSign, final String deadSign) {
		this.aliveSign = aliveSign;
		this.deadSign = deadSign;
	}

	public String getSign(final boolean alive) {
		return alive ? aliveSign : deadSign;
	}
}
