package HomeWork6;

public class Robot {
    public static class RobotEx extends Thread {
        private String name;
        private static Object lock = new Object();

        public RobotEx(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (;;) {
                synchronized (lock) {
                    step();
                    lock.notifyAll();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void step() {
            System.out.println("Step: " + name);
        }
    }

    public static void main(String[] args) {
        new Thread(new RobotEx("left")).start();
        new Thread(new RobotEx("right")).start();
    }
}
