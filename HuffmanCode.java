///////////////////////////
//
//	Edward Pryor
//	CIS 303 Spring 2014
//	Huffman code
//	read in a file, huffman code  it
//////////////////////////
import java.io.*;
import java.util.*;

public class HuffmanCode {

    public static void main (String [] args) throws FileNotFoundException{
		// get the input file name
		String fileName = args[0];
		Scanner sc = new Scanner (new File(fileName));
		String outFileName = args[0].substring(0, args[0].indexOf("."))
			+ "Huff" + args[0].substring(args[0].indexOf("."));
		    // you take it from here...
		    
		    //build the frequency table
		Map<Character, Integer> frequencyTable= new HashMap<Character, Integer>();
		
		//loop over the curent line, putting its charecters into a hashmap
		while(sc.hasNext()){
			String line = sc.nextLine();
			for(int i = 0; i < line.length(); i++){
				if(frequencyTable.get(line.charAt(i)) == null)
					frequencyTable.put(line.charAt(i), 1);
				else{
					int temp = frequencyTable.get(line.charAt(i));
					temp++;
					frequencyTable.put(line.charAt(i), temp);
				}
			}
		}
		//end forloop for line processing		
		// now need to test that it worked
		
		List<Integer> viewVal = new ArrayList<Integer>(frequencyTable.values());
		List<Character> viewKey = new ArrayList<Character>(frequencyTable.keySet());
		
		// now i need to make  leaf nodes for all of these things.
		// an array of them
		LeafHuffNode []  leafList= new LeafHuffNode[viewVal.size()]; 
		for(int i = 0; i < leafList.length; i++){
			leafList[i] = new LeafHuffNode(viewKey.get(i), -1*(viewVal.get(i)));
		}
		//confirmed array constructed properly
		//now have an array of leaves. turn them into an huffman tree
		MaxHeap pileOfNodes = new MaxHeap(leafList.length);
		for(int i = 0; i< leafList.length; i++){
			//System.out.println(leafList[i].toString());
			pileOfNodes.insert(leafList[i]);
		}
		//System.out.println(pileOfNodes.toString());
		
		//remove top node, remove the new top node
		//turn those two nodes into a HuffNode
		//insert that HuffNode
		int nodeCount = leafList.length;
		while(nodeCount > 1){
			HuffNode tLeft = (HuffNode)pileOfNodes.removeMax();
			HuffNode tRight = (HuffNode)pileOfNodes.removeMax();
			HuffNode puff = new HuffNode((tLeft.freq+tRight.freq), tLeft, tRight);
			pileOfNodes.insert(puff);
			nodeCount--;
		}
		HuffNode workingHeap = (HuffNode)pileOfNodes.removeMax();
		//think we have the huffman tree constructed as needed. now to traverse
		Map<Character, String> lookUpTable = new HashMap<Character, String>();
		for(int i = 0; i < viewKey.size(); i++){
			lookUpTable.put(viewKey.get(i), traverse(workingHeap, viewKey.get(i)));
		}
		List<String> Val = new ArrayList<String>(lookUpTable.values());
		List<Character> Key = new ArrayList<Character>(lookUpTable.keySet());
		
		//wooo we did it. parallel arrays Val and Key now hold the characters and variable length encodings
		// now for file output of heading
		File out = new File(outFileName);
		PrintWriter pandemic = new PrintWriter(out);
		pandemic.println(Key.size());
		for(int i = 0; i < Val.size(); i++){
			pandemic.print(Val.get(i)+ "\t"+Key.get(i));
			pandemic.println();
		}
		// read in a line
		// take that line and look at each char sequentially
		// make a string by cacatanating each of the huffman codes to each other
		// print that string and loop
		// close original scanner
		sc.close();
		Scanner winning = new Scanner(new File(fileName));
		while(winning.hasNext()){
			String ponies = winning.nextLine();
			String outputBuffer = "";
			for(int i = 0; i < ponies.length(); i++){
			//	System.out.println(ponies.charAt(i));
				pandemic.print(lookUpTable.get(ponies.charAt(i)));
			}
			pandemic.println();
		}
		pandemic.close();
    }
    public static String traverse(HuffNode at, char find){
    	String leftSide = "";
    	String rightSide = "";
    	if(at.isLeaf()){
    		String t = at.toString();
    		if(t.charAt(1) == find)
    			return"";
    		else
    			return "F";
    	}
    	else{
    		leftSide = "0"+traverse(at.left, find);
    		rightSide = "1"+traverse(at.right, find);
    		if(leftSide.charAt(leftSide.length()-1) == 'F')
    			return rightSide;
    		else
    			return leftSide;
    	}
    }		   
}
    // some classes you should use to build your Huffman Code Tree
 
class HuffNode implements Comparable<HuffNode> {

    // fields
    public int freq;
    public HuffNode left;
    public HuffNode right;

    // constructor
    HuffNode(int freq, HuffNode left, HuffNode right) {
	this.freq = freq;
	this.left = left;
	this.right = right;
    }

    public String toString() {
	return "["+ left + " " + freq + " " + right + "]";
    }

    public  boolean isLeaf() {
	return false;
    }

    public int compareTo(HuffNode other) {
	return this.freq - other.freq;
    }


}

class LeafHuffNode extends HuffNode {

    // fields
    public  char ch;

    LeafHuffNode(char ch, int freq){
	super(freq, null, null);
	this.ch = ch;
    }

    public String toString() {
	return "(" + ch + " " + freq + ")";
    }

    public boolean isLeaf() {
	return true;
    }
}
