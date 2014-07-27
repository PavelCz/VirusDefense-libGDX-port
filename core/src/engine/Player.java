package engine;

public class Player {
	private int lives;
	private int money;
	private int score;
	private String name;

	public Player(String name, int lives, int money, int score) {
		this.name = name;
		this.lives = lives;
		this.money = money;
		this.score = score;
	}

	public int getMoney() {
		return this.money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void reduceMoney(int money) {
		this.money -= money;
	}

	public void addMoney(int money) {
		this.money += money;
	}

	public int getLives() {
		return this.lives;
	}

	public void reduceLives() {
		this.lives--;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String playerName) {
		this.name = playerName;

	}

}
