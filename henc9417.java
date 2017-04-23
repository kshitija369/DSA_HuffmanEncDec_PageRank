/* PANSARE KSHITIJA DILIP cs610 9417 prp */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.BitSet;

public class henc9417 {

	public static ArrayList<SymInfo9417> symList;
	public static byte[] buffer;
	public static BitSetWrapper9417 bits = new BitSetWrapper9417(new BitSet());		
	public static int i, count = 0;
	public static int tempSize = 0;
	public static BitSetWrapper9417 encodings;
	public static SymInfo9417 node ; 
	public static byte defaultSym = '\0';	

	public static void main(String[] args){
		// Read filename
		String filename = null; 
		if (args != null && args.length > 0){
			filename = args[0];
		}

		// Read bytes of the file
		try {
			buffer = Util9417.readBinaryFile(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Generate frequency table
		ArrayList<SymInfo9417> freqTable = new ArrayList<SymInfo9417>();
		freqTable = generateFrequencies(buffer);

		// Huffman encoding
		node  = Huffman(freqTable);
		generatePrefixCode(node);
		encodings = new BitSetWrapper9417(new BitSet(tempSize));
		//System.out.println("encoding size:" + tempSize);
		node.fileLength = buffer.length;
		node.encodingLength = tempSize;
		replace( freqTable );

		try {
			String strEncFilename = filename.concat(".huf");
			Util9417.writeBinaryFile(encodings.bits.toByteArray(), strEncFilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			String strPrefixFilename = (filename.concat(".huf")).concat(".prefixCode");
			fout = new FileOutputStream(strPrefixFilename);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(node);
			File fileToDel = new File(filename);
			fileToDel.delete();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}
	
	public static byte[] toByteArray(BitSet bits, int length) 
	{
	    byte[] bytes = new byte[(length + 7) / 8];       
	    for (int i = 0; i < length; i++) {
	        if (bits.get(i)) {
	            bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
	        }
	    }
	    return bytes;
	}
	
	private static void replace(ArrayList<SymInfo9417> freqTable) {
		for (int i = 0; i < buffer.length; i++) {
			for (int j = 0; j < freqTable.size(); j++) {
				if ( buffer[i] == freqTable.get(j).symbol){
					//System.out.println("Symbol: " + freqTable.get(j).symbol);
					combine(freqTable.get(j).prefix);
				}
			}
		}	
	}

	public static void combine(BitSetWrapper9417 prefix) 
	{      
		for (int i = 0; i < prefix.length; i++) {
			if (prefix.bits.get(i)) {
				encodings.bits.set(i + count); 
			}else{
				encodings.bits.clear(i + count); 
			}
		}
		count = count + prefix.length;
	}

	public static void generatePrefixCode( SymInfo9417 currnode) {

		if(currnode.left != null) {
			bits.bits.clear(i++);
			generatePrefixCode (currnode.left);
		}
		if(currnode.right != null) {   	
			bits.bits.set(i++);
			generatePrefixCode (currnode.right);
		}
		if(currnode.left == null && currnode.right == null) {			
			bits.length = i;
			currnode.prefix.bits = (BitSet) bits.bits.clone();
			currnode.prefix.length = bits.length;
			tempSize = tempSize + (int)currnode.freq*(bits.length);
			//System.out.println("Prefix length for " + currnode.symbol + ": " + (bits.length) +  " i: " + i);
			//for(int j = 0; j < i; j++){
			//	System.out.println(" Bit set:" + bits.bits.get(j));
			//}
		}
		bits.bits.clear(i--);
	}

	private static SymInfo9417 Huffman(ArrayList<SymInfo9417> freqTable) {

		symList = PriorityQueue9417.getSymList();
		for(int c = 0; c < freqTable.size(); c++){
			symList.add (freqTable.get(c));
		}

		PriorityQueue9417.iheap_size = freqTable.size();
		PriorityQueue9417.Build_MinHeap (symList, PriorityQueue9417.iheap_size);
		int n = PriorityQueue9417.iheap_size;
		for (int c = 0; c < (n-1); c++) {
			SymInfo9417 z = new SymInfo9417(defaultSym, false);

			SymInfo9417 x = PriorityQueue9417.RemoveMin();
			SymInfo9417 y = PriorityQueue9417.RemoveMin();

			z.left = x;
			z.right = y;
			z.freq = x.freq + y.freq;

			PriorityQueue9417.InsertMinHeap(z);		
		}
		return PriorityQueue9417.RemoveMin();
	}

	private static ArrayList<SymInfo9417> generateFrequencies(byte[] buffer) {

		ArrayList<SymInfo9417> freq = new ArrayList<SymInfo9417>();
		boolean exists = false;
		for (byte b : buffer) {
			if(freq.size() == 0){
				SymInfo9417 newSym = new SymInfo9417(b, true);
				freq.add(newSym);
			}else{
				exists = false;
				for (SymInfo9417 symInfo : freq) {
					if (b == symInfo.symbol){
						//exists
						exists = true;
						symInfo.freq = symInfo.freq + 1;
						break;
					}
				}
				if(exists == false){
					SymInfo9417 newSym = new SymInfo9417(b, true);
					freq.add(newSym);					
				}	
			}
		}
		return freq;
	}
}
