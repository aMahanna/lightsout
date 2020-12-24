import java.util.ArrayList;

/**
 * An implementation of the interface <b>SolutionQueue</b>
 */
public class ArrayListSolutionQueue implements SolutionQueue {


    /**
     * <b>queue</b> stores the references of the elements
     * currentluy in the queue
     */
    private ArrayList<Solution> queue;

    /**
     * Constructor, initializes  <b>queue</b>
     */
    public ArrayListSolutionQueue() {

        queue = new ArrayList<Solution>();
        
    }

    /**
     * implementation of the method <b>enqueue</b> 
     * from the interface <b>SolutionQueue</b>.
     * @param value
     *      The reference to the new element
     */
    public void enqueue(Solution value) {

        if (value != null) {
            queue.add(value);
        } else {
            System.out.println(value + " is null.");
        }

    }

    /**
     * implementation of the method <b>dequeue</b> 
     * from the interface <b>SolutionQueue</b>.
     * @return 
     *      The reference to removed Solution
     */
    public Solution dequeue() {

        Solution dequeued = queue.get(0); 
        queue.remove(0);
        return dequeued;
        
    }

    /**
     * implementation of the method <b>isEmpty</b> 
     * from the interface <b>SolutionQueue</b>.
     * @return 
     *      true if the queue is empty 
     */
    public boolean isEmpty() {

        return queue.isEmpty();
        
    }
}