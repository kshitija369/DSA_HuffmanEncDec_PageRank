/* PANSARE KSHITIJA DILIP cs610 9417 prp */
import java.util.ArrayList;

public class Link9417 {
	
	public Link9417(int i, int k, int outdeg) {
		source_v = i;
		outgoing_edges = new ArrayList<Integer>();
		outgoing_edges.add(k);
		out_deg = outdeg;
	}
	
	public int source_v;
	public int out_deg;
	ArrayList<Integer> outgoing_edges;
}
