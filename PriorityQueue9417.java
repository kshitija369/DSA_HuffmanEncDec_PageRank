/* PANSARE KSHITIJA DILIP cs610 9417 prp */


import java.util.ArrayList;

public class PriorityQueue9417 {
	
	public static int iheap_size;
	public static ArrayList<SymInfo9417> symList = new ArrayList<SymInfo9417>();
	
	public static ArrayList<SymInfo9417> getSymList() {
		return symList;
	}

	public static void setSymList(ArrayList<SymInfo9417> symList) {
		PriorityQueue9417.symList = symList;
	}

	public static SymInfo9417 FindMin (){
		return symList.get(0);
	}

	public static SymInfo9417 RemoveMin (){
		if (iheap_size < 1 ){
			System.out.println("Error. No heap elements");
			return null;
		}
		SymInfo9417 min = symList.get(0);
		symList.set(0, symList.get(iheap_size - 1));
		iheap_size = iheap_size - 1;
		MinDownHeap(0);
		return(min);
	}

	public static void InsertMinHeap ( SymInfo9417 newSym){
		iheap_size = iheap_size + 1;
		int i = iheap_size - 1;
		while((i > 0) && (parentFreq(i) > newSym.freq)){
			symList.set(i, parentSymInfo(i));
			i = symList.indexOf(parentSymInfo(i));
		}
		//symList.add(i, newSym);
		symList.set(i, newSym);
	}

	public static SymInfo9417 parentSymInfo(int i) {
		i = (int)java.lang.Math.floor((((double)i - 1)/2));
		return symList.get(i);
	}

	public static long parentFreq(int i) {
		i = (int)java.lang.Math.floor((((double)i - 1)/2));
		return symList.get(i).freq;
	}

	public static void MinDownHeap(int i) {
		int smallest = getSmallestIndx(i);
	
		if (smallest != i){
			exchange(i, smallest);
			MinDownHeap(smallest);	
		}
	}

	public static void exchange(int i, int smallest) {
		SymInfo9417 temp;
		temp = symList.get(i);
		symList.set(i, symList.get(smallest));
		symList.set(smallest, temp);
	}

	public static int getSmallestIndx(int i) {
		SymInfo9417 parent = symList.get(i);
		SymInfo9417 lchild = lChild(i);
		SymInfo9417 rchild = rChild(i);
		
		if(parent.freq <= lchild.freq){
			if(parent.freq <= rchild.freq){
				return symList.indexOf(parent);
			}else{
				return symList.indexOf(rchild);
			}
		}else{
			if(lchild.freq <= rchild.freq){
				return symList.indexOf(lchild);
			}else{
				return symList.indexOf(rchild);
			}
		}
	}

	public static SymInfo9417 lChild(int i) {
		int ret = i;
		i = 2*i + 1;
		if ( i >= iheap_size || i < 0)
			i = ret;
		return symList.get(i);
	}
	
	public static SymInfo9417 rChild(int i) {
		int ret = i;
		i = 2*i + 2;
		if ( i >= iheap_size || i < 0)
			i = ret;
		return symList.get(i);
	}

	public static void Build_MinHeap(ArrayList<SymInfo9417> symList_,
			int iheap_size_) {
		iheap_size = iheap_size_;
		for (int i = symList.indexOf(parentSymInfo(iheap_size - 1)); i >= 0; i--) {
			MinDownHeap(i);
		}
		
	}
}