/*
 * 
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Class BinaryTree.
 *
 * @param <T> the generic type
 */
public class BinaryTree<T extends Comparable<T>> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The root. */
	private Node<T> root;

	/**
	 * Instantiates a new binary tree.
	 *
	 * @param value the value
	 */
	public BinaryTree(T value) {
		root = new Node<T>(value);
	}

	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public Node<T> getRoot() {
		return root;
	}

	/**
	 * Sets the root.
	 *
	 * @param root the new root
	 */
	public void setRoot(Node<T> root) {
		this.root = root;
	}

	/**
	 * Adds the.
	 *
	 * @param current the current
	 * @param value   the value
	 * @return the node
	 */
	private Node<T> add(Node<T> current, T value) {

		if (current == null) {
			return new Node<T>(value);
		}
		if (current.value.compareTo(value) == 1) {
			current.left = add(current.left, value);
		} else if (current.value.compareTo(value) == -1 || current.value.compareTo(value) == 0) {
			current.right = add(current.right, value);
		}
		return current;
	}

	public void add(T value) {
		add(root, value);
	}

	private void doPreorder(ArrayList<T> elements, Node<T> current) {
		if (current != null) {
			elements.add(current.value);
			doPreorder(elements, current.left);
			doPreorder(elements, current.right);
		}
	}

	private void doInOrder(ArrayList<T> elements, Node<T> current) {
		if (current != null) {
			doInOrder(elements, current.left);
			elements.add(current.value);
			doInOrder(elements, current.right);
		}
	}

	private void doPostOrder(ArrayList<T> elements, Node<T> current) {
		if (current != null) {
			doPostOrder(elements, current.left);
			doPostOrder(elements, current.right);
			elements.add(current.value);
		}
	}

	public ArrayList<T> getPreOrder() {
		ArrayList<T> elements = new ArrayList<T>();
		doPreorder(elements, this.root);
		return elements;
	}

	public ArrayList<T> getInOrder() {
		ArrayList<T> elements = new ArrayList<T>();
		doInOrder(elements, this.root);
		return elements;
	}

	public ArrayList<T> getPostOrder() {
		ArrayList<T> elements = new ArrayList<T>();
		doPostOrder(elements, this.root);
		return elements;
	}

}
