package HomeWork8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Home {

    private Pet firstPet;
    private Pet secondPet;

    public Pet getFirstPet() {
        return firstPet;
    }

    public void setFirstPet(Pet firstPet) {
        this.firstPet = firstPet;
    }

    public Pet getSecondPet() {
        return secondPet;
    }

    public void setSecondPet(Pet secondPet) {
        this.secondPet = secondPet;
    }

    public static void main(String... args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Home home = new Home();

        BufferedReader reader = new BufferedReader(new FileReader("E:/settings.txt"));
        String line;
        List<String> lines = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        String[] in = lines.toArray(new String[lines.size()]);

        StringTokenizer st1 = new StringTokenizer(in[0]);
        st1.nextToken();
        Class cl = Class.forName(st1.nextToken());
        Pet p = (Pet) cl.newInstance();
        home.setFirstPet(p);

        st1 = new StringTokenizer(in[1]);
        st1.nextToken();
        cl = Class.forName(st1.nextToken());
        p = (Pet) cl.newInstance();
        home.setSecondPet(p);

        System.out.println(home.getFirstPet().getName());
        System.out.println(home.getSecondPet().getName());

    }
}
