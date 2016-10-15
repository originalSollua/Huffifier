////////////////////
//
//	Edward Pryor
// 	CIS 303	Spring 2014
//	Max heap.java - with implementation for insert
///////////////////
import java.util.*;

public class MaxHeap <E extends Comparable<? super E>> {

    private E[] heap; // The array to holding the heap
    private int cap;  // max size or capacity of the heap
    private int n;    // current size of heap

    // constructors
    public MaxHeap (E[] heap, int cap, int n) {
		this.heap = heap; this.cap = cap; this.n = n;
		buildHeap();
    }

    public MaxHeap (int cap) {
		this.cap = cap;
		this.heap = (E[]) new Comparable<?>[cap];
		this.n = 0;
    }

    // remove and return maximum in the heap
    public E removeMax () {
        if (n <= 0)
	    	throw new IllegalStateException("removing from empty heap");
		return this.remove(0);
    }

    // remove and return element at specified position, pos
    public E remove(int pos) {
		if (pos < 0 || pos >=n)
			throw new IllegalArgumentException();
		E result = this.heap[pos];
		// last leaf here
		this.heap[pos] = this.heap[n-1];
		this.n--;
		// if this heap is empty or we removed the last position
		if (this.n == 0 || this.n == pos)
			return result;
		// if this new element > parent, sift up
		if (this.heap[pos].compareTo(this.heap[parent(pos)]) >0)
			siftup(pos);
		// else sift it down
		else 
			siftdown(pos);
		return result;
		    
    }

    // heapify contents of the array
    private void buildHeap() {
	for (int i = n/2-1; i>=0; i--)
	    siftdown(i);
    }
    
    
	// insertion going here
	public void insert(E element){
		
			int pos = this.n;
			this.heap[pos] = element;
			this.n++;
			this.siftup(pos);
	}


    // put element i of the array in its correct place
    private void siftdown(int pos) {
		if (pos < 0 || pos >= this.n)
			throw new IllegalArgumentException(" pos = " + pos);
		while (!this.isLeaf(pos)) {
			int i = this.leftChild(pos);
			// get the larger child
			if (i < n-1 && this.heap[i].compareTo(this.heap[i+1]) < 0)
				i++;
		    if (this.heap[pos].compareTo(this.heap[i]) >=0)
				return; // what is at pos is larger than both children
		    this.swap(pos, i); // swap pos and i
		    pos = i; // move pos down
		}
    }

    // put element i of the array in its correct place from pos
    public void siftup(int pos) {
		int parent = 0;
        	if (pos > 0)
	    		parent = parent(pos);
			while (pos > 0 && 
          	this.heap[pos].compareTo(this.heap[parent]) > 0) {
            	swap(pos, parent);
	   			pos = parent;
	    		parent = parent(pos);
			}
    }

    public String toString () {
		return Arrays.toString(this.heap);
    }

    // returns the position of the left child of element at pos
    private int leftChild (int pos) {
		if (2*pos + 1 >= this.n)
	    throw new IllegalArgumentException("no left child");
		return 2*pos + 1;
    }

    // return the position of the right child of element at pos
    private int rightChild(int pos) {
		if (2*pos + 2 >= this.n)
	    	throw new IllegalArgumentException("no right child");
		return 2*pos + 2;
    }

   // return the position of the parent of element at pos
    private int parent(int pos) {
		if (pos < 0 || pos >= n)
	   		throw new IllegalArgumentException("pos = " + pos
                    + " n = " + n);
        	return (pos-1)/2;
    }

    // returns true if this is a leaf
    private boolean isLeaf(int pos) {
		return pos >= n/2 && pos < n;
    }

    // swap positions i and j
    private void swap (int i, int j) {
		E temp = this.heap[i];
		this.heap[i] = this.heap[j];
		this.heap[j] = temp;
    }
}
