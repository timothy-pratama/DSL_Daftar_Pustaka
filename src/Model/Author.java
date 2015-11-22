package Model;

import java.util.ArrayList;

/**
 * Created by timothy.pratama on 22-Nov-15.
 */
public class Author {
    private ArrayList<String> name = new ArrayList<>();

    public Author(String name){
        String[] names = name.split(" ");
        for(String nama : names)
        {
            this.name.add(nama);
        }
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public String getFirstName() {
        return name.get(0);
    }

    public String getLastName() {
        return name.get(name.size()-1);
    }
}
