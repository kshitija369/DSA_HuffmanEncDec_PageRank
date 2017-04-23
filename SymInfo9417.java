/* PANSARE KSHITIJA DILIP cs610 9417 prp */


public class SymInfo9417 extends PrefixCode9417 {

	private static final long serialVersionUID = 2L;
	public long fileLength;
	public long encodingLength;
	
	public long freq;
	public byte symbol;
	public boolean isLeaf;

	public SymInfo9417 left;
	public SymInfo9417 right;

	public SymInfo9417(byte b, boolean leaf) {
		super();
		symbol = b;
		freq = 1;
		left = null;
		right = null;
		isLeaf = leaf;
	}
}
