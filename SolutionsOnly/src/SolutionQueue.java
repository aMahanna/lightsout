/**
 * A specialized Queue interface which handles instances of Solution
 *
*/
public interface SolutionQueue {

	/**
     * Returns true if the Queue is currenly empty
     * @return 
     *		true if the queue is empty 
     */
    boolean isEmpty();


	/**
     * Add the reference to Solution at the rear of
     * the queue. Assumes s is not null
     * @param s
     *		The (non null) reference to the new element
     */
    void enqueue(Solution s);

	/**
     * Removes the reference to Solution at the front of
     * the queue. Assumes the queue is not empty
     * @return 
     *		The reference to removed Solution
     */
    Solution dequeue();
}
