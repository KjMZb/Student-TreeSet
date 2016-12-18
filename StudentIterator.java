import java.util.Iterator;

/**
 * This class implements the Iterator interface 
 * and serves as an iterator over a StudentTreeSet object. 
 * @author   Ajinkya Kolhe
 * @author   Kyle McGlynn
 *
 * @param    <E>   Because this iterator works on a 
 *                 StudentTreeSet object, its type parameter
 *                 must be the same as the StudentTreeSet object.
 *                 So, E is any class that implements the
 *                 Comparable interface.
 */
public class StudentIterator < E extends Comparable < E > > 
								implements Iterator < E > {
	
	// The in order elements of the StudentTreeSet 
	// object that creates this iterator.
	Object[] elements;
	
	// A reference to the creating
	// StudentTreeSet object
	StudentTreeSet<E> tree;
	
	// A pointer to the current position in the array 
	// of elements. It is initialized to 0.
	int pointer = 0;
	
	// A flag that is set to true if any modification
	// is made to the structure of the non-empty tree 
	// after this iterator has been created.
	boolean modification = false;
	
	/**
	 * The constructor. It takes as arguments an array
	 * of elements in the StudentTreeSet object that creates
	 * it and a reference to that same StudentTreeSet object.
	 * @param    elements   an array of in order elements of the tree
	 * @param    tree       a reference to the StudentTreeSet object
	 *                      that created this iterator
	 */
	public StudentIterator( Object[] elements, StudentTreeSet<E> tree ){
		this.elements = elements;
		this.tree = tree;
	}
	
	/**
	 * This method returns true if there is an element
	 * at the current pointer position. 
	 * @return   boolean   true if there is an element at
	 *                     the position indicated by pointer,
	 *                     false otherwise
	 */
	public boolean hasNext() {
		
		// If pointer has not reached the end and
		// the element at pointer is not null
		if( pointer < elements.length &&
				elements[ pointer ] != null ) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Returns the element at the position
	 * indicated by pointer.
	 */
	@SuppressWarnings("unchecked")
	public E next() {
		
		// If modifications have not been made
		// to the non-empty tree structure.
		if( !modification && elements.length > 0 ) {
			
			// If pointer hasn't reached the end
			if( pointer < elements.length ){
				return (E) elements[pointer++];
			}
			
			// If pointer is at the end, display
			// an error message and close the program.
			else{
				System.err.println( "No more elements." );
				System.exit(0);
				return null;
			}
		}
		
		// If a modification has been made to the non-empty
		// tree structure, display an error message and
		// close the program.
		else{
			System.err.println( "Cannot make concurrent modifications!" );
			System.exit(0);
			return null;
		}
	}
}
