import java.util.TreeSet;

/**
 * A self balancing binary search tree with the
 * properties of a set; meaning that all elements
 * only exist once in the tree. The add() and contains()
 * are guaranteed to operate in O( log n ) time by
 * the occasional re-balancing of the tree. 
 * @author   Kyle McGlynn
 * @Author   Ajinkya Kolhe
 *
 * @param    <E>   this tree only accepts objects of
 *                 type E where type E is any class that
 *                 implements the Comparable interface.   
 */
@SuppressWarnings("serial")
public class StudentTreeSet < E extends Comparable < E > > 
                                extends TreeSet < E > {
	
	// The top of the tree
	private Node<E> root;
	
	// The number of elements in the tree
	private int size = 0;
	
	// Current height of the tree
	private int currentHeight = 0;
	
	// Max height of the tree, initialized to 3
	private int maxHeight = 3;
	
	// Constant factor used to determine when to 
	// increase the maximum height
	private double rangeFactor = 1.75;
		
	// Used for the inOrer method
	private int pointer = 0;
	
	// An array of iterators over an instance of this class
	private Object[] iterators = new Object[5];
	
	// Used to maintain the array of iterators
	private int iterPointer = 0;
	
	/**
	 * This method recursively determines a node's position
	 * in the binary tree by comparing its data to that of
	 * the current node. The method terminates once the node 
	 * is placed as a leaf.
	 * 
	 * Because this tree has the qualities of a set, if we
	 * find a node with the same data already in the tree,
	 * then this method returns false.
	 * 
	 * @param    ancestor     the current node that we are
	 *                        comparing to the node whose 
	 *                        position we want to find
	 * @param    descendant   the node whose position we are
	 *                        trying to determine
	 * @param    depth        how far from the root at which
	 *                        the node is place
	 * @return   boolean      true if the node was successfully
	 *                        added, false if a node with the
	 *                        same data is already in the tree 
	 */
	private boolean bubbleDown( Node<E> ancestor, Node<E> descendant, int depth ) {
		
		// If the depth exceeds the current height,
		// then update the current height
		if( ++depth > currentHeight ) {
			currentHeight = depth;
		}
		
		// Compare the data of the node against the current node
		int comparison = descendant.data.compareTo( ancestor.data );
		
		// If the data in the two nodes is the same,
		// return false
		if( comparison == 0 ) {
			return false;
		}		
		
		// If the data in the node is less than the data
		// in the current node
		else if(  comparison < 0 ){
			
			// If the left child position of the current node
			// is empty, put the node there
			if( ancestor.left == null ) {
				ancestor.left = descendant;
				descendant.parent = ancestor;
				return true;
			}
			
			// If the left child position is occupied, make
			// a recursive call on the left child
			else{
				return bubbleDown( ancestor.left, descendant, depth );
			}
		}
		
		// If the data in the node is greater than the
		// data in the current node
		else{
			
			// If the right child position of the current
			// node is empty, put the node there
			if( ancestor.right == null ) {
				ancestor.right = descendant;
				descendant.parent = ancestor;
				return true;
			}
			
			// If the right child position is occupied, make
			// a recursive call on the right child
			else{
				return bubbleDown( ancestor.right, descendant, depth );
			}
		}
	}
	
	/**
	 * This method rebuilds the tree in O(n) time.
	 * It does so by getting the elements of the 
	 * tree in order, finding the middle, using it
	 * as the new root, and then uses a recursive
	 * method on the left and right hand sides of
	 * ordered elements.
	 */
	@SuppressWarnings("unchecked")
	private void rebuild() {
		
		// Reset the current height to zero
		currentHeight = 0;
		
		// If the number of elements exceeds the 
		// amount that would be present in a full tree
		// of height equal to maxHeight, than increase
		// maxHeight by one.
		if( size() >= ( ( 2^maxHeight ) - 1 ) ) {
			maxHeight ++;
		}
		
		// The elements of the tree in order
		Object[] elements = inOrder();
		
		// Clear the tree
		clear();
		
		// Find the middle of the ordered elements
		int middle = elements.length / 2;
		
		// Add the middles element first. It will be
		// the root in the rebuilt tree
		add( ( E ) elements[middle] );
		
		// Call the recursive helpBuild method on the
		// left and right hand sides of the ordered elements.
		helpRebuild( 0, middle - 1, elements );
		helpRebuild( middle + 1, elements.length-1, elements );
	}
	
	/**
	 * This method recursively finds the middle element
	 * of a segment of the array of ordered elements and
	 * adds it to the tree.
	 * @param   start   the starting index of this segment
	 * @param   end     the ending index of this segment
	 * @param   arr     the array of ordered elements
	 */
	@SuppressWarnings("unchecked")
	private void helpRebuild( int start, int end, Object[] arr ) {
		
		// If start and end are equal, add the element
		// at that position and terminate the recursion.
		if( start == end ) {
			add( ( E ) arr[start] );
		}
		else{
			
			// Find the middle of this segment
			int middle = start + ( ( end - start ) / 2 );
			
			// If the calculated middle value
			// is truly between start and end
			if( middle < end || middle > start) {
				
				// Add the middle value to the tree
				add( ( E ) arr[middle] );
				
				// Make a recursive call on the left and
				// right hand sides of the ordered elements.
				helpRebuild( start, middle - 1, arr );
				helpRebuild( middle + 1, end, arr );
			}
		}	
	}
	
	/**
	 * This method adds an element of type E
	 * to the tree. If the element already 
	 * exists in this tree, then this method
	 * return false.
	 * @param    e         an element of type E to 
	 *                     be added to the tree
	 * @return   boolean   true if this element could
	 *                     be added, false otherwise
	 */
	public boolean add( E e ) {
		
		// So long as the element isn't null
		if( e != null ) {
			
			// Notify any iterators observing
			// this tree that a modification has
			// been made to the structure
			notifyIterators();
			
			// If the root is empty
			if( root == null ) {
				Node<E> node = new Node<E>( e );
				root = node;
			}
			
			// If the root is not empty, then
			// bubble down
			else{
				Node<E> node = new Node<E>( e );
				
				// If the element could not be added,
				// return false
				if( !bubbleDown( root, node, 0 ) ) {
					return false;
				}
			}
			
			// Increment the size of the tree
			size++;
			
			// If the current height exceeds the maximum height
			// times the range factor, rebuild the tree.
			if( currentHeight >= maxHeight * rangeFactor ) {
				rebuild();
			}
			return true;
		}
		
		// If the element to be added is null
		else{
			System.err.println( "Cannot add a null value." );
			System.exit(0);	
			return false;
		}
	}
	
	/**
	 * This method clears the tree of all elements, 
	 * and resets the size of the tree to zero.
	 */
    public void clear() {
    	notifyIterators();
    	root = null;
    	size = 0;
    }
    
    /**
     * This method uses a recursive method to
     * determine if an object exists in the tree.
     * 
     * @return   boolean   true the tree contains
     *                     the given object, false
     *                     otherwise
     */
    public boolean contains( Object object ) {
    	return helpContains( root, object );
    }
    
    /**
     * This method recursively looks for the given
     * object in the tree by comparing the data of
     * the current node to the object. If they are
     * equal this method returns true, if not then
     * a recursive call is made on either the right
     * or left child of the current node. If we reach
     * a null value, this method returns false.
     * @param    node      the current node under
     *                     consideration
     * @param    object    the object we are looking for
     * @return   boolean   true if the object is found
     *                     in the tree, false otherwise
     */
    @SuppressWarnings("unchecked")
	private boolean helpContains( Node<E> node, Object object ) {
    	
    	// If we have not reached a null value
    	if( node != null ){
    		
    		// Compare the object to the data in the current node
    		int comparison = ( ( E ) object ).compareTo( node.data ); 
    		
    		// If the object and the data of the current
    		// node are equal, return true
	    	if( comparison == 0 ) {
	    		return true;
	    	}
	    	
	    	// If the object is less than the data of the
	    	// current node, make a recursive call on the
	    	// left child.
	    	else if( comparison < 0 ) {
	    		return helpContains( node.left, object );
	    	}
	    	
	    	// If the object is greater than the data of the
	    	// current node, make a recursive call on the
	    	// right child.
	    	else{
	    		return helpContains( node.right, object );
	    	}    		    
    	}
    	
    	// If we reach a null value, return false
    	return false;
    }
    
    /**
     * This method determines returns true if the
     * tree is empty and false otherwise.
     * @return   boolean   true if the tree is empty,
     *                     false otherwise
     */
    public boolean isEmpty() {
    	
    	// If the size of the tree is zero,
    	// then there are no elements and the
    	// tree is empty
    	if( size() == 0 ) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * The method returns an in order array of the
     * tree's elements.
     * @return   Object[]   an in order array of the
     *                      elements in this tree
     */
    private Object[] inOrder() {
    	
    	// An array to hold the in order elements
    	Object[] elements;
    	
    	// If the tree is not empty
    	if( !isEmpty() ){
    		
    		// Initialize the array to hold
    		// all of the elements
    		elements = new Object[size()];
    		
    		// Use a recursive helper function
    		// to place the elements into the array
    		helpInOrder( root, elements );
    		
    		// Reset the pointer for the array
    		pointer = 0;
    	}
    	
    	// If the tree is empty, return an
    	// empty array
    	else{
    		elements = new Object[0];
    	}
    	return elements;    	
    }
    
    /**
     * This method recursively places elements of
     * the tree into an array in order.
     * @param    start      the node under consideration
     * @param    elements   the array of in order elements
     */
    private void helpInOrder( Node<E> start, Object[] elements ){
    	
    	// If the node is not a null value
    	if( start != null ){
    		
    		// Make a recursive call on the left child
    		helpInOrder( start.left, elements );
    		
    		// Add this element into the array
    		elements[pointer] = start.data;
    		
    		// Increment the pointer
    		pointer++;
    		
    		// Make a recursive call on the right child
    		helpInOrder( start.right, elements );
    	}
    }
    
    /**
     * This method returns a StudentIterator object.
     * @return   StudentIterator<E>   a StudentIterator object
     */
    public StudentIterator<E> iterator() {
    	
    	// If there is not enough room in the array of 
    	// StudentIterator references, increase the size
    	// of the array.
    	if( iterPointer == iterators.length ) {
    		Object[] copy = new Object[ iterators.length * 2 ];
    		for( int iter = 0; iter < iterators.length; iter++ ){
    			copy[iter] = iterators[iter];
    		}
    		iterators = copy;
    	}
    	
    	// Create a new StudentIterator object
    	StudentIterator<E> studentIterator =
    			new StudentIterator<E>( inOrder(), this );
    	iterators[iterPointer++] = studentIterator;
    	return studentIterator;
    }
    
    /**
     * Notifies the iterators observing this tree 
     * that a change has been made to its structure.
     */
    @SuppressWarnings("unchecked")
	private void notifyIterators() {
    	
    	// Loop over each iterator, setting its
    	// modification flag to true.
    	for( int iter = 0; iter < iterators.length; iter++ ) {
    		if( iterators[iter] != null ){
    			( ( StudentIterator<E> ) iterators[iter] ).modification = true;
    		}
    	}
    }
    
    /**
     * Returns the size of the tree.
     * @return   size   the number of elements
     *                  in the tree.
     */
    public int size() {
    	return size;
    }
    
    /** 
     * Returns an in order, string representation of
     * this tree as a string.
     * @return   representation   a string representation
     *                            of this tree
     */
    public String toString() {
    	
    	// A string representation of the tree
    	String representation;
    	
    	// If the tree is not empty
    	if( !isEmpty() ){
    		
    		// Get the elements of the tree in order
	    	Object[] elements = inOrder();
	    	representation = "[";
	    	
	    	// Loop over the in order elements
	    	for( int element = 0; element < elements.length - 1; element++ ) {
	    		
	    		// Call the elements toString() method and add the
	    		// result to the string representation.
	    		representation += elements[element].toString() + ", ";
	    	}
	    	representation += elements[elements.length - 1] + "]";
    	}
    	
    	// If the tree is empty 
    	else{
    		representation = "[]";
    	}
    	return representation;
    }
    
    /**
     * The main method. It tests the different methods
     * of the StudentTreeSet class.
     * @param   args   command line arguments ( not used )
     */
	public static void main( String [] args ) {
    	
    	// Create a StudentTreeSet object
    	StudentTreeSet<Integer> tree = new StudentTreeSet<Integer>();
    	
    	// Test the add() method for 10,000 elements
    	for( int integer = 0; integer < 10000; integer++ ) {
    		tree.add( new Integer( integer ) );
    	}
    	
    	// If adding 10,000 elements was successful, 
    	// isEmpty() should return false, and
    	// size() should return 10,000
    	System.out.println( "Tree is empty: " + tree.isEmpty() +
    			" Number of elements: " + tree.size() );
    	
    	// Test the boolean return values of add()
    	System.out.println( tree.add( new Integer( 5000 ) ) );   // False
    	System.out.println( tree.add( new Integer( 10001 ) ) );   // True
    	
    	// Test the clear() method
    	tree.clear();
    	System.out.println( "Test clear(): " + tree.isEmpty() );
    	
    	for( int integer = 0; integer < 100; integer++ ) {
    		tree.add( new Integer( integer ) );
    	}
    	
    	// Test the contains() method
    	System.out.println( tree.contains( new Integer( 102 ) ) );   // False
    	System.out.println( tree.contains( new Integer( 20 ) ) );   // True
    	
    	// Test the iterator
    	StudentIterator<Integer> iter = tree.iterator();
    	
    	// Test that the iterator is there
    	System.out.println( iter.hasNext() );
    	System.out.println( iter.next() );
    	
    	// Test the toString() method
    	System.out.println( tree );
    }

}
