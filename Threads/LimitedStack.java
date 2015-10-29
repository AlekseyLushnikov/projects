package Lesson6;
import java.util.Stack;

public class LimitedStack {

    private int maxSize;
    public LimitedStack(int maxSize) {
        this.maxSize = maxSize;
    }

    private static Stack<Object> stack = new Stack<Object>();

    private Object lock = new Object();

    public void push(Object value) throws InterruptedException {
        synchronized (lock) {

            stack.push(value);
            System.out.println("[INFO] Added object");

            if (stack.size() == maxSize) {
                System.out.println("[INFO] Waiting!");
                lock.wait();
            }
        }
    }

    public Object pop() {
        synchronized (lock) {
            if (stack.size() == 0) {
                return "[WARN] Element NOT found!";
            } else {
                System.out.println("[INFO] Object returned");
            }
            Object o = stack.pop();
            lock.notify();
            return o;
        }
    }

    public int size() {
        return stack.size();
    }
}

