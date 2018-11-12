package com.example.t3hjeff.bookshelf;

public class Books {
    public String title,author,authorname,authoraddress,booksimage;

    public Books(){

    }

    public Books(String title, String author, String authorname, String authoraddress, String booksimage) {
        this.title = title;
        this.author = author;
        this.authorname = authorname;
        this.authoraddress = authoraddress;
        this.booksimage = booksimage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getAuthoraddress() {
        return authoraddress;
    }

    public void setAuthoraddress(String authoraddress) {
        this.authoraddress = authoraddress;
    }

    public String getBooksimage() {
        return booksimage;
    }

    public void setBooksimage(String booksimage) {
        this.booksimage = booksimage;
    }
}
