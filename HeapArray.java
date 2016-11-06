/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Assignment 3 - HeapSort
 * Creaated by - Daniel Olaya Nov 10th, 2016
*/
import java.util.*;

class HeapArray{
	protected Comparable [] hippieArray;
	protected int counter;
	protected int length;

	//Create a new heap array using index 1 for root
	//@param 
	public HeapArray (int n){
		this.hippieArray = new Comparable [n+1]; 
	
		length = n;
		counter = 0;
		hippieArray[counter] = 0; 
	}

	private void ranGen (int n, int low, int high){
		Random rand = new Random();

		int i = 0;
		while (i<n)	{
			rand.setSeed(System.nanoTime());
			int randomNum = rand.nextInt((high - low) + 1) + low;;
			insert(randomNum);
			i++;	
		}	
	}

	public int size (){
		return counter;
	}
	
	public java.lang.Comparable getMin (int i){
		return hippieArray[i];
	}

	private void minHeapify (Comparable[] arr, int i){
		int left = 2*i;
		int right = 2*i + 1;
		int smallest = i;
		if (left <= arr.length && arr[left] > arr [largest]){
			largest = left;
		} 
		if (right <= arr.length && arr[right] > arr [largest]){
			largest = right;
		} 
		if (largest != i){
			int temp = i;
			i = largest;
			largest = i;
			minHeapify (arr, largest);
		}


	}


	public void insert (int value){
		if (counter > length){
			throw new RuntimeException("Heap is full.  :( ");
		} else{
			hippieArray[counter+1] = value;
			counter ++;	
		}
	}

	public void print (){
		System.out.print("Array content: ");
		for (int i =0 ; i < length ; i++){
			System.out.print(hippieArray[i] + " "); 
		}
		System.out.println();
	}


	public static void main(String[] args) {
		int n = 10;
		HeapArray testy = new HeapArray (n);
		testy.ranGen(n, 0 ,1000);
		testy.print();
		System.out.println("Size " + testy.size());

	}
}