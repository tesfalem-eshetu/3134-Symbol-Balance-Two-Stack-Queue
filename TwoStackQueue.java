import java.util.Stack;

public class TwoStackQueue<T> implements TwoStackQueueInterface<T>{
    private Stack<T> inputStack;
    private Stack<T> outputStack;

    public TwoStackQueue() {
        this.inputStack = new Stack<T>();
        this.outputStack = new Stack<T>();
    }

    public void enqueue(T x) {
        inputStack.push(x);
    }

    public T dequeue() {
        if (outputStack.isEmpty()) {
            while (!inputStack.isEmpty()) {
                outputStack.push(inputStack.pop());
            }
        }
        return outputStack.pop();
    }

    public boolean isEmpty() {
        return inputStack.isEmpty() && outputStack.isEmpty();
    }

    public int size() {
        return inputStack.size() + outputStack.size();
    }
    public static void main(String[] args){
        TwoStackQueue<Integer> mukera = new TwoStackQueue<Integer>();
        mukera.enqueue(34);
        mukera.enqueue(345);
        mukera.enqueue(3464);
        mukera.enqueue(3478);
       System.out.println(mukera.dequeue());
       System.out.println(mukera.dequeue());

    }
}
