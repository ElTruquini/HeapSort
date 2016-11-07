/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Assignment 3 - HeapSort
 * Creaated by - Daniel Olaya Nov 7th, 2016
*/
import java.util.*;
import java.lang.*;
import java.io.*;

class HeapArray{
	protected int [] hippieArray;
	protected int counter;
	protected int length;

	//Create a new heap array using index 1 for root
	public HeapArray (int n){
		length = n+1; //indexing starts at 1
		this.hippieArray = new int [length]; 
		counter = 0;
		hippieArray[counter] = 0; 
	}

	//generates n random numbers in a range (from low to high) and inserts them into the heap array.
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

	//returns number of elements in the heapArray
	public int size (){
		return counter;
	}
	
	//returns min element which is at index 1
	public int getMin (){
		return hippieArray[1];
	}

	//Used to build a min Heaparray from an unsorted array
	public int buildHeap (int[] arr, int i){
		int parent = (int)(Math.floor( (i)/2 ));
		if (i <= 1){ 	// base case, i is root
			return 0; 
		}
		if (arr[i] < arr[parent]){
			int temp = arr[parent];
			arr[parent] = arr[i];
			arr[i] = temp;
			buildHeap (arr, parent);
		}
		return -1; //this shall never be reached
	}

	// Fixes any violations on the heapArray after deletion of min element.
	private void minHeapify (int i){
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
			int temp = hippieArray[i];
			hippieArray[i] = hippieArray[smallest];
			hippieArray[smallest] = temp;
			minHeapify (smallest);
		}
	}

	//deletes and returns the min element in the heapArray and corrects any violations in the heap after deletion of min element
	public int deleteMin (){
		int temp = hippieArray[1];
		if (counter == 0){ //empty array
			return 0;
		}
		hippieArray[1] = hippieArray[counter];
		hippieArray[counter] = 0;
		counter--;
		minHeapify(1);
		return temp;
	}


	//Inserts an integer into the heap using the min heap properties
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

	// doubles the size of the heapArray and copies al elements
	public void growCopy (){
		int [] temp = new int [counter * 2]; 
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
			int temp = hippieArray[i];
			hippieArray[i] = hippieArray[j];
			hippieArray[j] = temp;
	}

	// Sorts in ascending order
	public int heapSort(){
		int [] sorted = new int [length];
		int tempCounter = counter;
		for (int i = 1; i < length ; i++){
			sorted[i]=deleteMin();
		}
		hippieArray = sorted;
		counter = tempCounter; //restores the counter 
		return 0;
	}

	//TEST 1: Create a heap with 10 random ints with a range from 0 to 100
	//TEST 2: Create a heap with 100 random ints with a range from 0 to 1000
	//TEST 3: Testing makeHeap method with a reverse sorted array into, uses growCopy method to double array size
	//TEST 4: Testing makeHeap method with a sorted array into, uses growCopy method to double array size
	//TEST 5: Testing makeHeap method with a unsorted array, uses growCopy method to double array size 
	//TEST 6: Testing delMin with empty array
	//TEST 7: Testing growCopy, size, deleteMin and minHeapify methods
	//TEST 8: Testing heapsort, must return an array sorted in ascending order.
	//TEst 9, 10, 11, 12, 13 : Performance tests, check terminal for results.
	public void test (int test){
		try{
			report(test);

		}catch (IOException e){
			System.out.printf("Main: Unable to open file");
			return;
		}
	}

	//Writes test results into pq_test.txt file.
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
				case 6:
					writer.append("\nTEST 6: TEST 6: Testing delMin with empty array");	
					writer.append("\nOutput: \n");
					break;
				case 7:
					writer.append("\nTEST 7: Testing growCopy, size, deleteMin and minHeapify methods");	
					writer.append("\nArray after deleting MIN: \n");
					break;	
				case 8:
					writer.append("\nTEST 8: Testing heapsort, must return an array sorted in ascending order.");	
					writer.append("\nArray after sorting in ascending order: \n");
					break;

			}
			for (int i : hippieArray){
				writer.append( i +" ");
			}
			writer.append("\nThe size of the heapArray is: " + counter + "\n");	
			writer.close();
	}

	//All test cases devoleped in main. To test just remove "/*" at the beginning of each test case code section.
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

	//	TEST 6: Testing delMin with empty array
			/*
			int n = 10;
			HeapArray testy = new HeapArray (n);
			testy.deleteMin();
			System.out.print("Initial: ");
			testy.test(0);
			testy.print();
			testy.deleteMin();
			System.out.print("Final: ");
			testy.print(); 
			testy.test(6);
			//*/

	//	TEST 7: Testing growCopy, size, deleteMin and minHeapify methods
			/*
			int n = 10;
			HeapArray testy = new HeapArray (n);
			testy.insert (4);
			testy.insert (3);
			testy.insert (2);
			testy.insert (1);
			testy.insert (9);
			testy.insert (3);
			System.out.print("Initial: ");
			testy.print();
			testy.test(0);
			System.out.println("Size " + testy.size());  //must return 6
			testy.deleteMin();
			testy.test(7);
			System.out.println("Size: " + testy.size()); //must return 5
			testy.deleteMin();
			testy.test(7);
			System.out.println("Size: " + testy.size()); //must return 4
			testy.deleteMin();
			testy.test(7);
			System.out.println("Size: " + testy.size()); //must return 3
			testy.deleteMin();
			testy.test(7);
			System.out.println("Size: " + testy.size()); //must return 2
			testy.deleteMin();
			testy.test(7);		
			System.out.println("Size: " + testy.size()); //must return 1
			testy.deleteMin();
			testy.test(7);	
			System.out.println("Size: " + testy.size()); //must return 0
			System.out.print("FINAL: ");
			testy.print();
			//*/

	//	TEST 8: Testing heapsort, must return an array sorted in ascending order. Change n to modify size of heapArray
			/*
			int n = 200;
			int hiRange = 1000;
			HeapArray testy = new HeapArray (n);
			testy.ranGen(n, 0 ,hiRange);
			System.out.print("Main FINAL: ");
			System.out.print("Initial heapArray: ");
			testy.test(0);
			testy.print();
			System.out.println("PRE SORT Counter is: " +testy.size() );
			System.out.println();
			testy.heapSort();
			testy.test(8);
			System.out.print("FINAL Sorted Array - ");
			testy.print();
			System.out.println("Counter is: " +testy.size() );
			//*/

	//TEST 9: Testing performance n=100 with random generated ints with a range from 0 to 1,000
			/*
			long startTime = System.currentTimeMillis();
			int n = 100;
			int hiRange = 1000;
			HeapArray testy = new HeapArray (n);
			testy.ranGen(n, 0 ,hiRange);
			System.out.print("Main FINAL: ");
			testy.print();
			System.out.println();
			testy.test(1);
			long endTime = System.currentTimeMillis();
			double totalTimeSeconds = (endTime - startTime) / 1000.0;
			System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
			//*/

	//TEST 10: Testing performance n=1000 with random generated ints with a range from 0 to 1,000
			/*
			long startTime = System.currentTimeMillis();
			int n = 1000;
			int hiRange = 1000;
			HeapArray testy = new HeapArray (n);
			testy.ranGen(n, 0 ,hiRange);
			System.out.print("Main FINAL: ");
			testy.print();
			System.out.println();
			testy.test(1);
			long endTime = System.currentTimeMillis();
			double totalTimeSeconds = (endTime - startTime) / 1000.0;
			System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
			//*/

	//TEST 11: Testing performance n=10,000 with random generated ints with a range from 0 to 1,000
			/*
			long startTime = System.currentTimeMillis();
			int n = 10000;
			int hiRange = 1000;
			HeapArray testy = new HeapArray (n);
			testy.ranGen(n, 0 ,hiRange);
			System.out.print("Main FINAL: ");
			testy.print();
			System.out.println();
			testy.test(1);
			long endTime = System.currentTimeMillis();
			double totalTimeSeconds = (endTime - startTime) / 1000.0;
			System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
			//*/
	
	//TEST 12: Testing performance n=100,000 with random generated ints with a range from 0 to 1,000
			/*
			long startTime = System.currentTimeMillis();
			int n = 100000;
			int hiRange = 1000;
			HeapArray testy = new HeapArray (n);
			testy.ranGen(n, 0 ,hiRange);
			System.out.print("Main FINAL: ");
			testy.print();
			System.out.println();
			testy.test(1);
			long endTime = System.currentTimeMillis();
			double totalTimeSeconds = (endTime - startTime) / 1000.0;
			System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
			//*/

	//TEST 13: Testing performance n=1,000,000 with random generated ints with a range from 0 to 1,000
			/*
			long startTime = System.currentTimeMillis();
			int n = 1000000;
			int hiRange = 1000;
			HeapArray testy = new HeapArray (n);
			testy.ranGen(n, 0 ,hiRange);
			System.out.print("Main FINAL: ");
			testy.print();
			System.out.println();
			testy.test(1);
			long endTime = System.currentTimeMillis();
			double totalTimeSeconds = (endTime - startTime) / 1000.0;
			System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
			//*/
	}
}