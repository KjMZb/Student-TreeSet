/**
 * This class is used as a node in a
 * binary search tree.
 * @author   Kyle McGlynn
 * @author   Ajinkya Kolhe
 *
 * @param    <T>   this node only accepts objects of
 *                 type T where type T is any class that
 *                 implements the Comparable interface.  
 */
public class Node < T extends Comparable < T > > {
	
	// The parent and children of
	// this node, initialized to null
	Node<T> parent = null;
	Node<T> left = null;
	Node<T> right = null;
	
	// The data stored in this node
	T data;
	
	/**
	 * The constructor. It initializes the data.
	 * @param   data   the data to be 
	 *                 stored in this node
	 */
	public Node( T data ) {
		this.data = data;
	}
	
	/**
	 * This method returns the hash code
	 * of the data stored in this node.
	 * @return   int   the hash code of 
	 *                 the data stored 
	 *                 in this node
	 */
	public int hashCode() {
		return data.hashCode();
	}
	
	/**
	 * This method returns a string
	 * representation of the data in
	 * this node.
	 * @return   String   the string 
	 *                    representation of
	 *                    the data in this node
	 */
	public String toString() {
		return data.toString();
	}
	
	/**
	 * The main method. It tests the different
	 * methods of the Node class.
	 * @param   args   command line arguments ( not used )
	 */
	public static void main( String [] args ) {
		
		// Create a node and place a value in it
		Node<Integer> node = new Node<Integer>( new Integer( 10 ) );
		
		// See if it returns the hash code of its data. 
		// Since the data is an integer value, the hash code
		// should be equal to the integer value as per the 
		// Java documentation for the Integer class.
		System.out.println( node.hashCode() );
		
		// Test the toString() of the node
		System.out.println( node );
		
		// Test the generic type parameter.
		// This line should not compile.
		// Node<Object> node = new Node<Object>( new Object() );
//		Node<String> node2 = new Node<String>( new String( "10" ) );
//		Node<Double> node3 = new Node<Double>( new Double( 10.0 ) );
		
		// These lines should not compile
		//node.left = node2;
		//node.right = node3;
		
		Node<Integer> node4 = new Node<Integer>( new Integer( 20 ) );
		Node<Integer> node5 = new Node<Integer>( new Integer( 30 ) );
		Node<Integer> node6 = new Node<Integer>( new Integer( 0 ) );
		
		// Test left, right, and parent connections
		node.left = node4;
		node.right = node5;
		node.parent = node6;
		
		System.out.println( node.left );
		System.out.println( node.right );
		System.out.println( node.parent );
		
	}
}