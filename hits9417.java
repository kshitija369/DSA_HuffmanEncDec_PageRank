/* PANSARE KSHITIJA DILIP cs610 9417 prp */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class hits9417 {

	public static void main( String[] args){

		int[][] adjMatL = null;
		int[][] adjMatLT = null;
		double[] hub_val = null;
		double[] auth_val = null;
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

		System.out.println("Kleinberg’s HITS (Hub and Authority) Algorithm");
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

					auth_val = new double[n];
					hub_val = new double[n];

					if(n > 10){
						iterations = Math.pow(10, -5);
						initial_val = ((double)1)/n;
					}else{
						if(Integer.parseInt(args[1]) == -1){
							initial_val = ((double)1)/n;
						} else if(Integer.parseInt(args[1]) == -2){
							initial_val = ((double)1)/Math.sqrt(n);
						}
						Arrays.fill(auth_val, initial_val);
						Arrays.fill(hub_val, initial_val);
					}
					adjMatL = new int[n][n];
					adjMatLT = new int[n][n];
				}else{
					int i = Integer.parseInt(arrStr[0]);
					int j = Integer.parseInt(arrStr[1]);
					adjMatL[i][j] = 1;
				}
			}

			System.out.println("Adjacency Matrix:");
			// Calculate transpose of adjmatL
			for(int i = 0; i<n; i++ ){
				System.out.print(i + ": ");
				for(int j = 0; j<n; j++){
					adjMatLT[j][i] = adjMatL[i][j];
					System.out.print(adjMatL[i][j]+ "  ");
				}
				System.out.println();
			}

			int count = 0;
			boolean change = true;
			double[] tmp_auth = new double[n];
			double[] tmp_hub = new double[n];
			if(n < 10){
				System.out.println("Vectors initialed to: " + hub_val[0]);
				System.out.print("Base:  "  + count + ": ");
				for(int i = 0; i < n; i++){
					System.out.print("A/H[" + i + "]= " + auth_val[i] + "/" + hub_val[i] + "    ");
				}
			}

			do{
				if(n < 10)
					System.out.println();
				count++;
				// Calculate auth values
				for (int i = 0; i < n; i++) { // aRow
					for (int j = 0; j < n; j++) { // aColumn
						tmp_auth[i] += adjMatLT[i][j] * hub_val[j]; 
					}
				}

				// Calculate auth values
				for (int i = 0; i < n; i++) { // aRow
					for (int j = 0; j < n; j++) { // aColumn
						tmp_hub[i] += adjMatL[i][j] * tmp_auth[j]; 
					}
				}

				// perform scaling 
				double auth_deno  = 0;
				for(int i = 0; i< n; i++){
					auth_deno += Math.pow(tmp_auth[i], 2) ;
				}
				auth_deno = Math.sqrt(auth_deno);
				auth_deno = ((double)1)/auth_deno;

				double hub_deno  = 0;
				for(int i = 0; i< n; i++){
					hub_deno += Math.pow(tmp_hub[i], 2) ;
				}
				hub_deno = Math.sqrt(hub_deno);
				hub_deno = ((double)1)/hub_deno;

				//Scale
				for(int i = 0; i < n; i++){
					tmp_auth[i] = tmp_auth[i] * auth_deno;
					tmp_hub[i] = tmp_hub[i] * hub_deno;
				}

				if(Integer.parseInt(args[0]) <= 0){
					//Check if convergence achieved.
					for(int i = 0; i < n; i++){
						if(Math.abs(tmp_auth[i] - auth_val[i]) > iterations || Math.abs(tmp_hub[i] - hub_val[i]) > iterations){
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

				//Store new auth and hub values
				for(int i = 0; i < n; i++){
					auth_val[i] = tmp_auth[i];
					hub_val[i] = tmp_hub[i];
				}

				if(n < 10){
					System.out.print("Iterat:"  + count + ": ");
					for(int i = 0; i < n; i++){
						System.out.print("A/H[" + i + "]= " + auth_val[i] + "/" + hub_val[i] + "    ");
					}
				}

			}while(change == true);

			if(n >= 10){
				System.out.println("Iterat:"  + count + ": ");
				for(int i = 0; i < n; i++){
					System.out.println("A/H[" + i + "]= " + auth_val[i] + "/" + hub_val[i] + "    ");
				}
			}
			fileReader.close();
		} catch (IOException e) {
			//TODO
			e.printStackTrace();
		}

	}
}
