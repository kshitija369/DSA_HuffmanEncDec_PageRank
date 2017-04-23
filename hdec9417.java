/* PANSARE KSHITIJA DILIP cs610 9417 prp */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.BitSet;
import java.util.StringTokenizer;

public class hdec9417 {
	public static byte[] buffer;
	public static byte[] decodings;
	public static int charCount;
	public static byte defaultSym  = '\0';	
	public static SymInfo9417 prefixCode ;

	public static void main(String[] args){

		// Read filename
		String filename = null;
		String strPrefixFilename = null;
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
		String strDecFilename = filename.substring(0, (filename.lastIndexOf('.') == -1)?(filename.length() - 1):(filename.lastIndexOf('.')));
		
		prefixCode = new SymInfo9417(defaultSym, false);
		try {
			strPrefixFilename = filename.concat(".prefixCode");
			FileInputStream streamIn = new FileInputStream(strPrefixFilename);
			ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
			prefixCode = (SymInfo9417) objectinputstream.readObject();
		} catch (Exception e) {	
			e.printStackTrace();	
		}
		decodings = new byte[(int) prefixCode.fileLength];
		
		BitSet bitBuffer = BitSet.valueOf(buffer);
		SymInfo9417 start = prefixCode;
		decode(bitBuffer, start, prefixCode.encodingLength);

		try {
			Util9417.writeBinaryFile(decodings, strDecFilename);
			File fileToDel = new File(strPrefixFilename);
			fileToDel.delete();
			
			fileToDel = new File(filename);
			fileToDel.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void decode(BitSet bitBuffer, SymInfo9417 currNode, long encodingLength) {

		for (int i = 0; i < encodingLength; i++ ) {

			if(bitBuffer.get(i) && currNode.right != null){
				currNode = currNode.right;
			}else if(!bitBuffer.get(i)  && currNode.left != null){
				currNode = currNode.left;			
			}
			if(currNode.isLeaf){
				decodings[charCount++] = currNode.symbol;
				//System.out.println(currNode.symbol);
				currNode = prefixCode;
			}	
			if(charCount == prefixCode.fileLength){
				return;
			}
		}
	}

}
