
public class Str {
	private Character str;
	private int count;
	public Str(Character str) {
		this.str = str;
		count = 0;
	}
	public void add() {
		count++;
	}
	public int count() {
		return count;
	}
	public Character str() {
		return str;
	}
	public void print() {
		System.out.println(count+"-->"+str.toString());
	}
}
