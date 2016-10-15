import java.io.*;
import java.util.*;

public class Dehuffifier{
	public static void main (String[] args) throws FileNotFoundException{
		String fileName = args[0];
		Scanner sc = new Scanner (new File(fileName));
		//input the name of the file to de-huff
		String t = sc.nextLine();
		int encodeLines = Integer.parseInt(t);
		System.out.println(encodeLines);
		Map<String, Character> dehuftable = new HashMap<String, Character>();
		for(int i = 0; i < encodeLines; i++){
			String lin = sc.nextLine();
			String[] temp = lin.split("\t");
			dehuftable.put(temp[0], temp[1].charAt(0));
		}
		File out = new File("UnHuffed.txt");
		PrintWriter pandemic = new PrintWriter(out);
		while(sc.hasNext()){
			String inputLine = sc.nextLine();
			String hold = "";
			String outputbuffer = "";
			for(int i = 0; i < inputLine.length(); i++){
				hold = hold+inputLine.charAt(i);
				if(dehuftable.get(hold) !=null){
					outputbuffer = outputbuffer+dehuftable.get(hold);
					hold = "";
				}
			}
			pandemic.println(outputbuffer);
		}
		System.out.println("File unhufficated");
		pandemic.close();
		sc.close();
		
	}
}
