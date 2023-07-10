package priorityqueue;

import java.util.Comparator;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;
  private final static int INIT_SIZE = 5;

  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
      //TODO: Implement this method.
      this.comparator = comparator;
      this.isMaxHeap = isMaxHeap;
      heap = (T[]) new Object[INIT_SIZE];
      numElements = 0;
  }

  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in 
   * the array and then this method is called on that index. 
   *
   * @param index the index to bubble up
   * @throws IndexOutOfBoundsException if invalid index
   */
  public void bubbleUp(int index) throws IndexOutOfBoundsException{
      //TODO: Implement this method.
      if(index > numElements-1 || index < 0){
        throw new IndexOutOfBoundsException("Index out of bounds");
      }
      /*int parentIndex;
      while(index>0){
        parentIndex = (index-1)/2;
        if(compareElements(heap[index],heap[parentIndex])<0){
          return;
        }else{
          swapIndeces(index,parentIndex);
          index = parentIndex;
        }
      }*/
          
      int parentIndex = getParentOf(index);
      if(compareElements(heap[index],heap[parentIndex])>0){
        swapIndeces(index,parentIndex);
        bubbleUp(parentIndex);
      }else{
        return;
    }
  }

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   * @throws IndexOutOfBoundsException if invalid index
   */
  public void bubbleDown(int index) throws IndexOutOfBoundsException{
      //TODO: Implement this method.
      int childIndex = getLeftChildOf(index);
      T value = heap[index];
      while(childIndex<numElements){
        T maxValue = value;
        int maxIndex = -1;
        for(int i=0;i<2 && i+childIndex<numElements;i++){
          if(compareElements(heap[i+childIndex],maxValue)>0){
            maxValue = heap[i+childIndex];
            maxIndex = i+childIndex;
          }
        }
        if(maxValue == value){
          return;
        }else{
          swapIndeces(index,maxIndex);
          index = maxIndex;
          childIndex = getLeftChildOf(index);
        }
      }
  
  }

  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    return numElements == 0;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int getSize(){
    return numElements;
  }

  /**
   * Compare method to implement max/min heap behavior. It changes the value of a variable, compareSign, 
   * based on the state of the boolean variable isMaxHeap. It then calls the compare method from the 
   * comparator object and multiplies its output by compareSign.
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * negative int otherwise (if isMaxHeap),
   * return negative int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * positive int otherwise (if ! isMinHeap).
   */
  public int compareElements(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap 
   * without removing the element.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
     T data = null;
      //TODO: Implement this method.
    if(isEmpty()){
      throw new QueueUnderflowException();
    }

    data = heap[0];

    return data;
  }  

  /**
   * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeueElement() throws QueueUnderflowException{
    T data = null;
      //TODO: Implement this method.
      if(numElements == 0){
        throw new QueueUnderflowException();
      }
      if(numElements == 1){
        data = heap[0];
        heap[0] = null;
        numElements--;
        return data;
      }
      data = heap[0];
      heap[0] = heap[numElements-1];
      heap[numElements-1] = null;
      numElements--;

      bubbleDown(0);

    return data;
  }

  /**
   * Enqueue the element.
   * @param the new element
   */
  public void enqueueElement(T newElement) {
      //TODO: Implement this method.
    if(heap.length == numElements){
      expandCapacity();
    }
    if(numElements == 0){
      heap[0] = newElement;
      numElements++;
      return;
    }
    

    if(isMaxHeap){
      numElements+=1;
      heap[numElements-1] = newElement;
      bubbleUp(numElements-1);
    }else{
      numElements+=1;
      heap[numElements-1] = newElement;
      bubbleUp(numElements-1);
    }
  }

  private int getLeftChildOf(int parentIndex){
    return parentIndex*2+1;
  }

  private int getRightChildOf(int parentIndex){
    return parentIndex*2+2;
  } 

  private int getParentOf(int childIndex){
    return (childIndex-1)/2;
  }

  private void swapIndeces(int index1, int index2){
    T tempVal = heap[index1];
    heap[index1] = heap[index2];
    heap[index2] = tempVal;
  }

  private void expandCapacity(){
    T[] newArray = (T[]) new Object[heap.length*2];
    for(int i=0;i<heap.length;i++){
      newArray[i] = heap[i];
    }
    heap = newArray;
  }

}