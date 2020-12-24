public interface Queue<E> {

	public abstract E dequeue();
	public abstract void enqueue(E object);
	public abstract boolean isEmpty();

}
