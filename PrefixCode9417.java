/* PANSARE KSHITIJA DILIP cs610 9417 prp */

import java.io.Serializable;
import java.util.BitSet;

public class PrefixCode9417 implements Serializable{

	private static final long serialVersionUID = 1L;
	public BitSetWrapper9417 prefix;
	
	public PrefixCode9417(){
		prefix = new BitSetWrapper9417(new BitSet()) ;
	}
	
	public PrefixCode9417(Object prefix){
		this.prefix = (BitSetWrapper9417) prefix;
	}

	public BitSetWrapper9417 getPrefix() {
		return prefix;
	}

	public void setPrefixCode(PrefixCode9417 prefix) {
		this.prefix = prefix.prefix;
	}
}
