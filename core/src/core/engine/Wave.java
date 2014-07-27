package core.engine;

public class Wave {
	private int number;
	private int[] percentages;

	public Wave(int number, int[] enemy) {
		this.number = number;
		percentages = enemy;
	}

	public int getNumber() {
		return number;

	}

	public int getPercentage(int index) {
		return percentages[index];

	}

	public int[] getPercentages() {
		return percentages;
	}

}
