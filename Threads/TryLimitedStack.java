package Lesson6;

public class TryLimitedStack {

    public static void main(String[] args) {
        final LimitedStack stack = new LimitedStack(5);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                for (;;) {
                    counter++;
                    try {
                        stack.push(counter);
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Object value = stack.pop();
                    System.out.println("Value: " + value);
                }
            }
        });
        t2.start();

    }
}
