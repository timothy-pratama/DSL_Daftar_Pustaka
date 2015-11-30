package Model;

import java.util.ArrayList;

/**
 * Created by timothy.pratama on 22-Nov-15.
 */
public class Author {
    private ArrayList<String> name = new ArrayList<>();
    private String fullname = "none";

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Author(String name) {
        String[] names = name.split(" ",2);
        for(String nama : names)
        {
            this.name.add(nama);
        }
        this.fullname = name;
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
        if(name.size()>1){
            return name.get(1);
        }
        else{
            return "";
        }
    }
}
