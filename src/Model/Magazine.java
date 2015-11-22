package Model;

import java.util.ArrayList;

/**
 * Created by timothy.pratama on 22-Nov-15.
 */
public class Magazine {
    private ArrayList<Author> authors = new ArrayList<>();
    private int year;
    private String title;
    private String periodicalTitle;
    private String volumeNumber;
    private String inclusivePage;

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPeriodicalTitle() {
        return periodicalTitle;
    }

    public void setPeriodicalTitle(String periodicalTitle) {
        this.periodicalTitle = periodicalTitle;
    }

    public String getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(String volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public String getInclusivePage() {
        return inclusivePage;
    }

    public void setInclusivePage(String inclusivePage) {
        this.inclusivePage = inclusivePage;
    }
}
