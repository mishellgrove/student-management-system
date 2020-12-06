package model;

/**
 * The Class Node.
 *
 * @param <T> the generic type
 */
public class Node <T extends Comparable<T>>{
	
	/** The value. */
	T value;
	
	/** The left. */
	Node<T> left;
	
	/** The right. */
	Node<T> right;
	
	/**
	 * Instantiates a new node.
	 *
	 * @param value the value
	 */
	public Node(T value) {
		this.value = value;
		left = null;
		right = null;
	}

}