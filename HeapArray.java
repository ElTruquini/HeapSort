/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Assignment 3 - HeapSort
 * Creaated by - Daniel Olaya Nov 10th, 2016
*/
import java.util.*;
import java.lang.*;
import java.io.*;

class HeapArray{
	protected int [] hippieArray;
	protected int counter;
	protected int length;

	//Create a new heap array using index 1 for root
	//@param 
	public HeapArray (int n){
		length = n+1; //indexing starts at 1
		this.hippieArray = new int [length]; 
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
	
	public int getMin (int i){
		return hippieArray[i];
	}

	public int buildHeap (int[] arr, int i){
		//System.out.println("BUILDHEAP with: " + hippieArray[i]);
		int parent = (int)(Math.floor( (i)/2 ));
		if (i <= 1){ 	// base case, i is root
			return 0; 
		}
		if (arr[i] < arr[parent]){
			//System.out.println("====Swaping: " +arr[i] + " with " + arr[parent]);
			int temp = arr[parent];
			arr[parent] = arr[i];
			arr[i] = temp;
			buildHeap (arr, parent);
		}
		return -1; //this shall never be reached
	}


	private void minHeapify (int i){
		//System.out.println("MINHEAPIFY with:" + hippieArray[i]);
		int left = 2*i;
		int right = 2*i + 1;
		int smallest = i;

		if (left <= counter){ 	//avoids comparing with the 0 of empty array
			if (left <= hippieArray.length){
				if (hippieArray[left] < hippieArray[smallest] ){
					smallest = left;
				}
			} 
		}
		if (right <= counter){	//avoids comparing with the 0 of empty array
			if (right <= hippieArray.length){
				if (hippieArray[right] < hippieArray[smallest] ){
					smallest = right;
				}
			} 
		}

		if (smallest != i){
			//System.out.println("MINHEAPIFY SWAPPING: " +hippieArray[i] + " " + hippieArray[smallest]);
			int temp = hippieArray[i];
			hippieArray[i] = hippieArray[smallest];
			hippieArray[smallest] = temp;
			minHeapify (smallest);
		}
	}

	public int deleteMin (){
		int temp = hippieArray[1];
		//System.out.println("Deleting Min: " + temp + ", counter: " + counter);
		if (counter == 0){ //empty array
			return 0;
		}
		hippieArray[1] = hippieArray[counter];
		hippieArray[counter] = 0;
		counter--;
		minHeapify(1);
		return temp;
	}


	//Inserts an integer into the heap and fixes any violations on Heap properties
	public void insert (int value){
		counter++;
		if (counter == length){
			growCopy();
			hippieArray[counter] = value;
			buildHeap(hippieArray, counter);
		} else{
			hippieArray[counter] = value;
			buildHeap(hippieArray, counter);
		}
	}

	//Inserts an integer into the heapArray WITHOUT checking Heap properties, method is used to test makeHeap.
	public void insertUnsorted (int value){
		counter++;
		if (counter == length){
			growCopy();
			hippieArray[counter] = value;
		} else{
			hippieArray[counter] = value;
		}
	}

	public void print (){
		System.out.print("Array content: ");
		for (int i =0 ; i < length ; i++){
			System.out.print(hippieArray[i] + " "); 
		}
		System.out.println();
	}

	public void growCopy (){
		int [] temp = new int [counter * 2]; //doubles the size of the current array
		for (int i = 0 ; i<counter ; i++){
			temp[i] = hippieArray[i];
		}
		length = temp.length;
		hippieArray = temp;
	}

	//takas an unsorted array and turns it into a heapArray
	public HeapArray makeHeap (){
		HeapArray temp = new HeapArray (counter);
		for (int i = 1 ; i <= counter ; i++){
			temp.insert(hippieArray[i]);
		}
		return temp;
	}

	//helper method for heapSort
	public void swap (int i, int j){
			System.out.println("swapppppiiiiing " + hippieArray[i] +" with " + hippieArray[j]);
			int temp = hippieArray[i];
			hippieArray[i] = hippieArray[j];
			hippieArray[j] = temp;
	}

	// Sorts in ascending order
	public int heapSort(){
		int [] sorted = new int [length];
		for (int i = 1; i < length ; i++){
			sorted[i]=deleteMin();
		}
		hippieArray = sorted;
		return 0;
	}

	//TEST 1: Create a heap with 10 random ints with a range from 0 to 100
	//TEST 2: Create a heap with 100 random ints with a range from 0 to 1000
	//TEST 3: Testing makeHeap method with a reverse sorted array into, uses growCopy method to double array size
	//TEST 4: Testing makeHeap method with a sorted array into, uses growCopy method to double array size
	//TEST 5: Testing makeHeap method with a unsorted array, uses growCopy method to double array size 
	public void test (int test){
		try{
			report(test);

		}catch (IOException e){
			System.out.printf("Main: Unable to open file");
			return;
		}
	}

	public void report(int test) throws IOException {

			FileWriter file = new FileWriter("pq_test.txt", true);
			BufferedWriter writer = new BufferedWriter (file);


			switch (test){
				case 0:
					writer.append("\n---+++ Integer input +++--- \n");
					break;
				case 1:
					writer.append("\nTEST 1: Create a heap with 10 random ints with a range from 0 to 100 ");
					writer.append("\nOutput: \n");
					break;
				case 2:
					writer.append("\nTEST 2: Create a heap with 100 random ints with a range from 0 to 1000	 ");
					writer.append("\nOutput: \n");
					break;
				case 3: 
					writer.append("\nTEST 3: Testing makeHeap method with a reverse sorted array, uses growCopy method to double array size");
					writer.append("\nOutput: \n");
					break;
				case 4:
					writer.append("\nTEST 4: Testing makeHeap method with a sorted array into, uses growCopy method to double array size");
					writer.append("\nOutput: \n");
					break;
				case 5: 
					writer.append("\nTEST 5: Testing makeHeap method with a unsorted array, uses growCopy method to double array size ");	
					writer.append("\nOutput: \n");
					break;

			}

				for (int i : hippieArray){
					writer.append( i +" ");

					}
					writer.append("\n");
					
				

			//file.close();
			writer.close();


		
	}

	public static void main(String[] args) {
		

	// TEST1: Create a heap with 10 random ints with a range from 0 to 1000
			/*
			int n = 10;
			int hiRange = 100;
			HeapArray testy = new HeapArray (n);
			testy.ranGen(n, 0 ,hiRange);
			System.out.print("Main FINAL: ");
			testy.print();
			System.out.println();
			testy.test(1);
			*/
			
	// TEST2: Create a heap with 100 random ints with a range from 0 to 1000
			/*
			int n = 100;
			int hiRange = 1000;
			HeapArray testy = new HeapArray (n);
			testy.ranGen(n, 0 ,hiRange);
			System.out.print("Main FINAL: ");
			testy.print();
			System.out.println();
			testy.test(2);
			*/


	//TEST3: Testing makeHeap method with a reverse sorted array, uses growCopy method to double array size
			/*
			HeapArray unsorted  = new HeapArray (10);
			unsorted.insertUnsorted (5);
			unsorted.insertUnsorted (4);
			unsorted.insertUnsorted (3);
			unsorted.insertUnsorted (2);
			unsorted.insertUnsorted (1);
			System.out.print("MAIN Initial Sorted Array: ");
			unsorted.print();
			unsorted.test(0);
			System.out.println();
			unsorted = unsorted.makeHeap();
			System.out.print("MAIN makeHeap result: ");
			unsorted.print();
			unsorted.test(3);
			//*/

	//	TEST 4: Testing makeHeap method with a sorted array, uses growCopy method to double array size
			/*
			HeapArray unsorted  = new HeapArray (10);
			unsorted.insertUnsorted (1);
			unsorted.insertUnsorted (2);
			unsorted.insertUnsorted (3);
			unsorted.insertUnsorted (4);
			unsorted.insertUnsorted (5);
			unsorted.insertUnsorted (6);
			unsorted.insertUnsorted (7);
			unsorted.insertUnsorted (8);
			System.out.print("Initial Sorted Array: ");
			unsorted.print();
			unsorted.test(0);
			System.out.println();
			unsorted = unsorted.makeHeap();
			System.out.print("makeHeap result: ");
			unsorted.print();
			unsorted.test(4);

		//*/

	//	TEST 5: Testing makeHeap method with a unsorted array, uses growCopy method to double array size
			/*
			HeapArray unsorted  = new HeapArray (10);
			unsorted.insertUnsorted (4);
			unsorted.insertUnsorted (3);
			unsorted.insertUnsorted (2);
			unsorted.insertUnsorted (1);
			unsorted.insertUnsorted (9);
			unsorted.insertUnsorted (3);
			System.out.print("Initial Unsorted Array: ");
			unsorted.print();
			unsorted.test(0);
			System.out.println();
			unsorted = unsorted.makeHeap();
			System.out.print("makeHeap result: ");
			unsorted.print();
			unsorted.test(5);
		//*/




		//	testing heapsort
		/*
		testy.insert (4);
		testy.insert (1700);
		testy.insert (1);
		testy.insert (5);
		testy.insert (3);
		testy.insert (2);
		testy.insert (10);
		testy.insert (19999);
		testy.insert (7);
		testy.insert (30);
		testy.insert (6);
		//testy.insertUnsorted (3);
		System.out.print("Initial: ");
		testy.print();
		System.out.println();

		testy.heapSort();

		System.out.print("FINAL: ");
		testy.print();
		

		

		//	growCopy, size and minHeapify
		/*
		testy.insert (4);
		testy.insert (3);
		testy.insert (2);
		testy.insert (1);
		testy.insert (9);
		testy.insert (3);
		System.out.print("Initial: ");
		testy.print();
		//System.out.println("Size " + testy.size());
		//testy.deleteMin();
		//System.out.println("Size: " + testy.size()); //must return 0
		//testy.insert (5);
		//System.out.println("Size: " + testy.size()); //must return 1
		//testy.insert (0);
		//System.out.println("Size: " + testy.size()); //must return 8
		System.out.print("FINAL: ");
		testy.print();
		*/

		//	 delMin with empty array
		/*
		testy.deleteMin();
		System.out.print("Initial: ");
		testy.print();
		testy.deleteMin();
		testy.print(); 
		*/

		// 	testing delMin 
		/*
		testy.insert (5);
		testy.insert (4);
		testy.insert (3);
		testy.insert (2);
		testy.insert (8);
		testy.insert (6);
		testy.insert (1);
		System.out.print("Initial: ");
		testy.print();
		testy.deleteMin();
		testy.deleteMin();
		testy.deleteMin();
		testy.print(); 
		*/

		//	testing delMin 1 element and size
		/*
		testy.insert (5);
		System.out.print("Initial: ");
		testy.print();
		System.out.println("Size " + testy.size()); //must return 1
		testy.deleteMin();
		System.out.println("Size: " + testy.size()); //must return 0
		testy.insert (4);
		System.out.println("Size: " + testy.size()); //must return 1
		System.out.print("FINAL: ");
		testy.print(); //only 4
		*/
	}
}