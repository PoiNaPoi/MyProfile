
public class Node {
	private Character data;
	private Node left;
	private Node right;
	private int bit;
	
	public Node() {
		data = null;
		left = null;
		right = null;
		bit = 0;
	}
	public Character outdata() {
		return data;
	}
	public void indata(Character x) {
		data = x;
	}
	public Node nodeL() {
		return left;
	}
	public void linkL(Node x) {
		left = x;
	}
	public Node nodeR() {
		return right;
	}
	public void linkR(Node x) {
		right = x;
	}
	public void addbit(int x) {
		bit = x;
	}
	public int bit() {
		return bit;
	}
}
