package HomeWork8;

import java.util.Stack;

/**
 * Created by ayratn on 23/10/15.
 */
public class LimitedStack {

    private Stack<Object> stack = new Stack<>();

    private final Object lock = new Object();

    public void pushNonSynchronized(final Object value) {
        if (stack.size() == 5) {
            System.out.println("[WARN] Limit exceeded!");
            return;
        }
        stack.push(value);
    }

    @Benchmark(warmCount = 5, iterationCount = 10000)
    public void push(final Object value) {
        synchronized (lock) {
            if (stack.size() == 20000) {
                System.out.println("[WARN] Limit exceeded!");
                return;
            }
            stack.push(value);
        }
        System.out.println("[INFO] Added object");
    }

    public void pushWait(final Object value) {
        boolean isAdded = false;
        while (!isAdded) {
            synchronized (lock) {
                if (stack.size() == 20000) {
                    System.out.println("[WARN] Limit exceeded!");
                } else {
                    stack.push(value);
                    isAdded = true;
                }
            }
            if (!isAdded) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("[INFO] Added object");
    }

@Benchmark(warmCount = 5, iterationCount = 10000)
    public Object poll() {
        synchronized (lock) {
            if (stack.size() == 0) {
                System.out.println("[WARN ]No element found!");
            }
            System.out.println("[INFO] object returned");
            return stack.pop();
        }

    }

}
