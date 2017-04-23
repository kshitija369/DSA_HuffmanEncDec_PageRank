
/* PANSARE KSHITIJA DILIP cs610 9417 prp */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class pgrk9417 {

	public static void main(String[] args){

		ArrayList<Link9417> adjList = null;
		double[] Src = null; double[] D = null;
		double d = 0.85;
		// Read arguments
		double iterations =  Math.pow(10, -5);
		double initial_val = 1;
		String filename = null; 
		if (args != null && args.length > 2){
			if(Integer.parseInt(args[0]) == 0)
				iterations = Math.pow(10, -5);
			else if(Integer.parseInt(args[0]) < 0)
				iterations = Math.pow(10, Integer.parseInt(args[0]));

			if(Integer.parseInt(args[1]) == 0)
				initial_val = 0;
			else if(Integer.parseInt(args[1]) == 1)
				initial_val = 1;
			filename = args[2];
		}

		System.out.println("Google's PageRank Algorithm");
		try {
			File file = new File(filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int lineCount = 0; int n = 0; int m = 0;
			String[] arrStr;
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				arrStr = line.split(" ");
				if(lineCount++ == 0){
					//First line of samplegraph
					n = Integer.parseInt(arrStr[0]);
					m = Integer.parseInt(arrStr[1]);

					if(n > 10){
						iterations = Math.pow(10, -5);
						initial_val = ((double)1)/n;
					}else{
						if(Integer.parseInt(args[1]) == -1){
							initial_val = ((double)1)/n;
						} else if(Integer.parseInt(args[1]) == -2){
							initial_val = ((double)1)/Math.sqrt(n);
						} 
					}
					adjList = new ArrayList<Link9417>(m);
					Src = new double[n];
					D = new double[n];
				}else{
					int i = Integer.parseInt(arrStr[0]);
					int k = Integer.parseInt(arrStr[1]);

					if(adjList.isEmpty()){
						Link9417 link = new Link9417( i, k, 1);
						adjList.add(link);
					}else{
						if(isVertexAdded(adjList, i)){
							addOutVertex(adjList, i, k);
						}else{
							Link9417 link = new Link9417( i, k, 1);
							adjList.add(link);
						}
					}
				}
			}

			// Initialization
			for (int u = 0; u < n; u++) {
				Src[u] = initial_val; 
			}

			int count = 0;
			boolean change = true;

			System.out.println("Adjacency List:");
			for (Link9417 link : adjList) {
				System.out.print(link.source_v + ":  ");
				for (Integer out_edge : link.outgoing_edges) {
					System.out.print(out_edge + "  ");
				}
				System.out.println();
			}

			if(n < 10){
				System.out.print("Base:  "  + count + ": ");
				for (int u = 0; u < n; u++) {
					System.out.print("P[" + u + "]:" + Src[u] + "  ");
				}
				System.out.println();
			}

			do{
				if(n < 10)
					System.out.println();
				count++;
				// Initialization
				for (int u = 0; u < n; u++) {
					D[u] = 0; 
				}

				for (Link9417 link : adjList) {
					for (Integer j: link.outgoing_edges) {
						D[j] = D[j] + Src[link.source_v]/link.out_deg;
					}
				}

				for (int i = 0; i < n; i++) {
					D[i] = 	d * D[i] + (1-d)/n;
				}

				if(Integer.parseInt(args[0]) <= 0){
					//Check if convergence achieved.
					for(int i = 0; i < n; i++){
						if(Math.abs(Src[i] - D[i]) > iterations){
							change = true;
							break;
						}else{
							change = false;
						}
					}
				}else{
					if(count == Integer.parseInt(args[0])){
						change = false;
					}else
						change = true;
				}
				//Store new rank values
				for (int i = 0; i < n; i++) {
					Src[i] = 	D[i];
				}

				if(n < 10){
					System.out.print("Iterat:"  + count + ": ");
					for (int u = 0; u < n; u++) {
						System.out.print("P[" + u + "]:" + Src[u] + "  ");
					}
					System.out.println();
				}

			}while(change == true);

			if(n >= 10){
				System.out.println("Iterat:"  + count + ": ");
				for (int u = 0; u < n; u++) {
					System.out.println("P[" + u + "]:  " + Src[u] );
				}
			}
			fileReader.close();
		} catch (IOException e) {
			//TODO
			e.printStackTrace();
		}
	}

	private static void addOutVertex(ArrayList<Link9417> adjList_, int source, int outVertex) {	
		for (Link9417 link : adjList_) {
			if(link.source_v == source){
				link.out_deg++;
				link.outgoing_edges.add(outVertex);
			}
		}
	}

	private static boolean isVertexAdded(ArrayList<Link9417> adjList_, int source) {	
		for (Link9417 link : adjList_) {
			if(link.source_v == source)
				return true;
		}
		return false;
	}
}
