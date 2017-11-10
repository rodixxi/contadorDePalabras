package textManager;

import dataStructure.TSB_OAHashtable;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFile {

    private final File file;
    public TSB_OAHashtable<Integer, Word> words;
    private File tableFile;
    

    /**
     * Read a file and load the words in a hashtable
     * @param file to be read
     */
    public TextFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException{
        
      /*  this.tableFile= new File("tabla.dat");
        FileInputStream in = new FileInputStream(tableFile);
        ObjectInputStream ifile = new ObjectInputStream(in);
        this.words = (TSB_OAHashtable)ifile.readObject();
        ifile.close();
        in.close(); 
        System.out.println(words.toString());*/
        
        
        this.words = new TSB_OAHashtable<>(101);        
        this.file = new File(file.getPath());
       
    }
    
    public TextFile() throws FileNotFoundException, IOException, ClassNotFoundException{
        
        this.file = new File("tabla.dat");
        FileInputStream in = new FileInputStream(this.file);
        ObjectInputStream ifile = new ObjectInputStream(in);
        this.words = (TSB_OAHashtable)ifile.readObject();
        ifile.close();
        in.close(); 
        System.out.println(words.toString());
        
    }


    public String getPath() {
        return file.getAbsolutePath();
    }

    public void processFile() {
        String pattern = wordPattern();
        Pattern matchPattern = Pattern.compile(pattern);
        Matcher matcher;

        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), "ISO-8859-1");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String textLine = bufferedReader.readLine();
            long counterOfLines = 0;
            while (textLine != null) {
                String[] words_list = textLine.split("[ ¿¡(\\[{\"'!;,:\\.\\?)\"'\\]}\\-]");
                counterOfLines ++;
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
                        if (!words.isEmpty()) {
                            Word x = this.words.get(wordObject.hashCode());
                            if (x != null) {
                                x.addCount();
                                continue;
                            }
                        }
                        words.put(wordObject.hashCode(), wordObject);
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

    @Override
    public String toString() {
        String str = "";
        str += this.words.toString();
        return str;
    }
    
    public void saveToFile() throws FileNotFoundException, IOException, ClassNotFoundException{
        System.out.println(this.words.size());
        System.out.println(this.words.showLen());
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

    public String getWordsCount() {
        return String.valueOf(words.size());
    }
}
