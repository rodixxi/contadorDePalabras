package textManager;

import dataStructure.TSB_OAHashtable;


import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Shelf {

    private TSB_OAHashtable<Integer, Word> words;
    private File tableFile;
    private ArrayList<Book> booksInShelf = new ArrayList<>();

    public Shelf(){
        try {
            this.loadTable();
        } catch (IOException | ClassNotFoundException e) {
            this.words = new TSB_OAHashtable<>(101);
        }
    }

    public TSB_OAHashtable<Integer, Word> getWords() {
        return words;
    }

    public void loadTable() throws IOException, ClassNotFoundException {
        this.words = new TSB_OAHashtable<>(101);
        this.tableFile = new File("tabla.dat");
        FileInputStream in = new FileInputStream(this.tableFile);
        ObjectInputStream ifile = new ObjectInputStream(in);
        this.words = (TSB_OAHashtable)ifile.readObject();
        ifile.close();
        in.close();
    }


    public void addBook(File file) throws IOException, ClassNotFoundException {
        Book newBookToAdd = new Book(file);
        booksInShelf.add(newBookToAdd);
    }

    public void readBooks(){
        for (Book bookToRead: booksInShelf){
            bookToRead.toShelf(this.words);
        }
        booksInShelf = new ArrayList<>();
    }

    public void saveToFile() throws FileNotFoundException, IOException, ClassNotFoundException{
        try{
            this.tableFile= new File("tabla.dat");
            try (FileOutputStream out = new FileOutputStream(tableFile)) {
                ObjectOutputStream ofile = new ObjectOutputStream(out);
                ofile.writeObject(words);
                ofile.flush();
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.out.println("No se pudo guardar archivo");}

    }

    public int getValue(String str){
        Word wordObject = new Word(str);
        if (!words.isEmpty()) {
            Word x = this.words.get(wordObject.hashCode());
            if (x != null) {
                return x.getCount();
            }
        }
        return -1;
    }

    public ArrayList<String> getBooksNamesList(){
        ArrayList<String> booksNamesList = new ArrayList<>();
        if (booksInShelf.isEmpty()) {
            return  booksNamesList;
        }
        for (Book book: booksInShelf){
            booksNamesList.add(book.getName());
        }
        return booksNamesList;
    }

    public String getWordsCount() {

        return String.valueOf(words.size());
    }

    public boolean isEmpty(){
        return booksInShelf.isEmpty();
    }

    @Override
    public String toString() {
        String str = "";
        str += this.words.toString();
        return str;
    }

}
