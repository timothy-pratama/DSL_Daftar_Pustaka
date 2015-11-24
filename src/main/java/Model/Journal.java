package Model;

import java.util.ArrayList;

/**
 * Created by timothy.pratama on 22-Nov-15.
 */
public class Journal {
    private ArrayList<Author> authors = new ArrayList<>();
    private String articleTitle;
    private String journalTitle;
    private String volumeNumber;
    private String issueNumber;
    private int year;
    private String inclusivePage;

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    public String getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(String volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getInclusivePage() {
        return inclusivePage;
    }

    public void setInclusivePage(String inclusivePage) {
        this.inclusivePage = inclusivePage;
    }
}
