package textManager;

import dataStructure.TSB_OAHashtable;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Book {

    private final File file;

    /**
     * Read a file and load the words in a hashtable
     * @param file to be read
     */
    public Book(File file) throws IOException, ClassNotFoundException{
        
      /*  this.tableFile= new File("tabla.dat");
        FileInputStream in = new FileInputStream(tableFile);
        ObjectInputStream ifile = new ObjectInputStream(in);
        this.words = (TSB_OAHashtable)ifile.readObject();
        ifile.close();
        in.close(); 
        System.out.println(words.toString());*/

        this.file = new File(file.getPath());
       
    }


    public String getPath() {
        return file.getAbsolutePath();
    }

    public void toShelf(TSB_OAHashtable<Integer, Word> shelf) {
        String pattern = wordPattern();
        Pattern matchPattern = Pattern.compile(pattern);
        Matcher matcher;

        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), "ISO-8859-1");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String textLine = bufferedReader.readLine();
            while (textLine != null) {
                String[] words_list = textLine.split("[ ¿¡(\\[{\"'!;,:\\.\\?)\"'\\]}\\-]");
                for (String word : words_list) {
                    word = word.toLowerCase();
                    matcher = matchPattern.matcher(word);
                    if (matcher.matches()) {/*
                        String[] temp = word.split("([^a-záéíóúüñ]+?)");

                        if (temp.length == 1) {
                            word = temp[0];
                        } else {
                            word = temp[1];
                        }*/

                        Word wordObject = new Word(word);
                        if (!shelf.isEmpty()) {
                            Word x = shelf.get(wordObject.hashCode());
                            if (x != null) {
                                x.addCount();
                                continue;
                            }
                        }
                        shelf.put(wordObject.hashCode(), wordObject);
                    }
                }
                textLine = bufferedReader.readLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error de apertura");
        } catch (IOException ex) {
            System.out.println("Error al cerrar archivo");
        }

    }

    public String wordPattern(){
        return "^[\u00C0-\u017E a-z']+"; //"^[\u00C0-\u017E a-zA-Z\']+";   //"([^a-záéíóúüñ0-9]*)([a-záéíóúüñ]+)([^a-záéíóúüñ0-9]*)";
    }


    public String getName() {
        return file.getName();
    }
}
