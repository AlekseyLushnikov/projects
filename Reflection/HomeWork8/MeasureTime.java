package HomeWork8;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;

public class MeasureTime {
        public static LimitedStack limitedStack = new LimitedStack();

    public static void main(String[] args) throws InterruptedException, InvocationTargetException, IllegalAccessException {

        for (int i = 0; i < 15000; i++) {
            limitedStack.push(i);
        }
        long timeS = System.currentTimeMillis();
        for(int i = 0; i <10000; i++) {
            limitedStack.poll();
        }
        long timeF = System.currentTimeMillis();
        long time = timeF-timeS;
        System.out.println("Time = " + time);
        System.out.println();
        informationClass(limitedStack.getClass());
    }

    public static void informationClass(Class cv) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = cv.getMethods();
        System.out.println("Methods:");
        for (Method method : methods) {
            System.out.println(method.getName() + " " + method.getReturnType() + " " + Arrays.toString(method.getParameterTypes()));
            for(Annotation annotation : method.getAnnotations()) {
                System.out.println("ANNOTATION: " + annotation);
                if (annotation instanceof Benchmark) {
                    int count = method.getParameterCount();
                    String[] array = new String[count];
                    if (count > 0) {
                        for (int i = 0; i < count; i++) {
                            array[i] = "arg";
                        }
                    }
                    int warm = ((Benchmark) annotation).warmCount();
                    for (int i = 0; i < warm; i++) {
                        method.invoke(limitedStack, array);
                    }
                    long timeS = System.currentTimeMillis();
                    int iter = ((Benchmark) annotation).iterationCount();
                    for (int i = 0; i < iter; i++) {
                        method.invoke(limitedStack, array);
                    }
                    long timeF = System.currentTimeMillis();
                    double time = timeF-timeS;

                    System.out.println("Runtime: " + time);
                    System.out.println("Average Time: " + time/iter);
                }
            }
        }
    }
}
