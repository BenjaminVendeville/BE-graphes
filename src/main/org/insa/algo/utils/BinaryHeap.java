//
// ******************PUBLIC OPERATIONS*********************
// void insert( x ) --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( ) --> Return smallest item
// boolean isEmpty( ) --> Return true if empty; else false
// ******************ERRORS********************************
// Throws RuntimeException for findMin and deleteMin when empty

package org.insa.algo.utils;

import java.util.ArrayList;

/**
 * Implements a binary heap. Note that all "matching" is based on the compareTo
 * method.
 * 
 * @author Mark Allen Weiss
 * @author DLB
 */
public class BinaryHeap<E extends Comparable<E>> implements PriorityQueue<E>
{

	// The heap array.
	private final ArrayList<E> array;

	/**
	 * Construct a new empty binary heap.
	 */
	public BinaryHeap()
	{
		this.array = new FastSearchArrayList<E>();
	}

	/**
	 * Construct a copy of the given heap.
	 * 
	 * @param heap
	 *            Binary heap to copy.
	 */
	public BinaryHeap(BinaryHeap<E> heap)
	{
		this.array = new FastSearchArrayList<E>(heap.array);
	}

	/**
	 * Set an element at the given index.
	 * 
	 * @param index
	 *            Index at which the element should be set.
	 * @param value
	 *            Element to set.
	 */
	private void arraySet(int index, E value)
	{
		if (index == this.array.size())
		{
			this.array.add(value);
		} else
		{
			this.array.set(index, value);
		}
	}

	/**
	 * @return Index of the parent of the given index.
	 */
	private int index_parent(int index)
	{
		return (index - 1) / 2;
	}

	/**
	 * @return Index of the left child of the given index.
	 */
	private int index_left(int index)
	{
		return index * 2 + 1;
	}

	/**
	 * Internal method to percolate up in the heap.
	 * 
	 * @param index
	 *            Index at which the percolate begins.
	 */
	private void percolateUp(int index)
	{
		E x = this.array.get(index);

		for (; index > 0 && x.compareTo(this.array.get(index_parent(index))) < 0; index = index_parent(index))
		{
			E moving_val = this.array.get(index_parent(index));
			this.arraySet(index, moving_val);
		}

		this.arraySet(index, x);
	}

	/**
	 * Internal method to percolate down in the heap.
	 * 
	 * @param index
	 *            Index at which the percolate begins.
	 */
	private void percolateDown(int index)
	{
		int ileft = index_left(index);
		int iright = ileft + 1;

		if (ileft < this.array.size())
		{
			E current = this.array.get(index);
			E left = this.array.get(ileft);
			boolean hasRight = iright < this.array.size();
			E right = (hasRight) ? this.array.get(iright) : null;

			if (!hasRight || left.compareTo(right) < 0)
			{
				// Left is smaller
				if (left.compareTo(current) < 0)
				{
					this.arraySet(index, left);
					this.arraySet(ileft, current);
					this.percolateDown(ileft);
				}
			} else
			{
				// Right is smaller
				if (right.compareTo(current) < 0)
				{
					this.arraySet(index, right);
					this.arraySet(iright, current);
					this.percolateDown(iright);
				}
			}
		}
	}

	@Override
	public boolean isEmpty()
	{
		return this.array.size() == 0;
	}

	@Override
	public int size()
	{
		return this.array.size();
	}

	@Override
	public void insert(E x)
	{
		int index = this.array.size();
		this.arraySet(index, x);
		this.percolateUp(index);
	}

	@Override
	public void remove(E x) throws ElementNotFoundException
	{
		int index = array.indexOf(x);
		if (index == -1 || index >= this.array.size())
		{
			throw new ElementNotFoundException(x);
		}
		E lastItem = this.array.remove(this.array.size() - 1);
		if (index < this.array.size())
		{
			this.arraySet(index, lastItem);
			this.percolateUp(index);
			this.percolateDown(index);
		}
	}

	@Override
	public E findMin() throws EmptyPriorityQueueException
	{
		if (isEmpty())
			throw new EmptyPriorityQueueException();
		return this.array.get(0);
	}

	@Override
	public E deleteMin() throws EmptyPriorityQueueException
	{
		E minItem = findMin();
		if (this.array.size() == 1)
		{
			this.array.clear();
		} else
		{
			E lastItem = this.array.remove(this.array.size() - 1);
			this.arraySet(0, lastItem);
			this.percolateDown(0);
		}
		return minItem;
	}

	/**
	 * Prints the heap
	 */
	public void print()
	{
		System.out.println();
		System.out.println("========  HEAP  (size = " + this.array.size() + ")  ========");
		System.out.println();

		for (int i = 0; i < this.array.size(); i++)
		{
			System.out.println(this.array.get(i).toString());
		}

		System.out.println();
		System.out.println("--------  End of heap  --------");
		System.out.println();
	}

	/**
	 * Prints the elements of the heap according to their respective order.
	 */
	public void printSorted()
	{

		BinaryHeap<E> copy = new BinaryHeap<E>(this);

		System.out.println();
		System.out.println("========  Sorted HEAP  (size = " + this.array.size() + ")  ========");
		System.out.println();

		while (!copy.isEmpty())
		{
			System.out.println(copy.deleteMin());
		}

		System.out.println();
		System.out.println("--------  End of heap  --------");
		System.out.println();
	}

}
