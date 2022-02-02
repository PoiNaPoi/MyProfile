import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static Node node = new Node(); // A variable keep node
	static ArrayList<Str> data = null; //A variable to keep list of char
	static String k = ""; //A constant that save current postfix and renew it every one char
	static String postfix;
	
	public static ArrayList<Str> Str(String x) {
		ArrayList<Str> d = new ArrayList<>();
		boolean check = true;
		
		for(int i=0;i<x.length();i++) {
			check = true;
			for(int j=0;j<d.size();j++) {
				if(d.get(j).str().equals(x.charAt(i))) {
					d.get(j).add();
					check = false;
					break;
				}
			}
			if(check) {
				Str s = null;
				if(x.charAt(i)==' ')
					s = new Str('_');
				else
					s = new Str(x.charAt(i));
				s.add();
				d.add(s);
			}
		}
		Str s = new Str('\\');
		s.add();
		d.add(s);
		
		return d;
	}
	
	@SuppressWarnings("resource")
	public static void input() {
		data = Str(new Scanner(System.in).nextLine());
	}

	public static void print(ArrayList<Str> d) {
		for(int i=0;i<d.size();i++) {
			d.get(i).print();
		}
		System.out.println("--------------------------------------");
	}
	
	public static ArrayList<Str> sortStr(){
		ArrayList<Str> d = new ArrayList<>();
		while(data.size()!=0) {
			int min = data.get(0).count();
			int po = 0;
			for(int i=data.size()-1;i>=0;i--) {
				if(data.get(i).count()<min) {
					min = data.get(i).count();
					po = i;
				}
			}
			d.add(data.get(po));
			
			data.remove(po);
		}
		print(d);
		return d;
	}
	
	public static ArrayList<Node> sortNode(ArrayList<Node> n){
		ArrayList<Node> d = new ArrayList<>();
		while(n.size()!=0) {
			int min = n.get(0).bit();
			int po = 0;
			for(int i=n.size()-1;i>=0;i--) {
				if(n.get(i).bit()<min) {
					min = n.get(i).bit();
					po = i;
				}
			}
			d.add(n.get(po));
			n.remove(po);
		}
		return d;
	}
	
	public static Node putnode() {
		ArrayList<Node> n = new ArrayList<>();
		for(int i=0;i<data.size();i++) {
			Node a = new Node();
			a.indata(data.get(i).str());
			a.addbit(data.get(i).count());
			n.add(a);
		}
		int i=1;
		while(n.size()>1) {
			Node a = new Node();
			a.linkL(n.get(0));
			a.linkR(n.get(1));
			a.addbit(n.get(0).bit()+n.get(1).bit());
			n.add(a);
			n.remove(0);
			n.remove(0);
			n = sortNode(n);
			for(int j=0;j<n.size();j++) {
				System.out.print(n.get(j).bit()+"-->");
				System.out.print(postfix=printBi(n.get(j)));
				System.out.println();
				k="";
			}
			System.out.println("=========="+i+"==========");
			i++;
		}
		return n.get(0);
	}
	
/*	public static void search(Node x,Character y) {
		if(x==null) {
			return;
		}
		else {
			search(x.nodeL(),y);
			search(x.nodeR(),y);
			if(x.outdata()!=null&&x.outdata()==y) {
				System.out.println(x.outdata()+" ");
				return;
			}
		}
	}*/
	
	public static String printBi(Node x) {

		if(x==null) {
			
		}
		else {
			printBi(x.nodeL());
			printBi(x.nodeR());
			if(x.outdata()==null) {
				k+="*";
			}
			else {
				k+=x.outdata();
			}
		}
		return k;
	}
	
	 public static void printCode(Node r, String s) 
	    { 
	        if (r.nodeL() == null && r.nodeR() == null) {
	            System.out.println(r.outdata() + ":" + s); 
	            return; 
	        } 
	        printCode(r.nodeL(), s + "0"); 
	        printCode(r.nodeR(), s + "1"); 
	    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("\\ is mean line feet\n_ is mean space\n---------------------------------------------");
		String test = "Hello World!";
		data = Str(test);
//		input();
		data = sortStr();
		node = putnode();
		printCode(node, "");
		System.out.println("Binary postfix is: "+postfix);
	}

}
