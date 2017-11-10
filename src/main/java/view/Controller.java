package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import textManager.TextFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private TextFile book;
    private List<File> list;
    @FXML
    private TextArea textBooksList;
    @FXML
    private TextField textToSearch;
    @FXML
    private Label wordsToShow;


    @FXML
    private void handleButtonBrowse(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException{
        String listOfFilesNames = textBooksList.getText();
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Abrir archivo de texto");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt"));
        list = chooser.showOpenMultipleDialog(null);
        if (!list.isEmpty()) {
            for (File file: list){
                listOfFilesNames = listOfFilesNames.concat(file.getName());
                listOfFilesNames = listOfFilesNames.concat("\n");
                book = new TextFile(file);
            }
            textBooksList.setText(listOfFilesNames);
        }
    }


    @FXML
    private void handleButtonLoad(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException{
        /*File file = new File("16082-8.txt");
        TextFile book = new TextFile(file);*/
        book.processFile();
        System.out.println(book.toString());
        book.saveToFile();

        System.out.println(book.getWordsCount());
        //TODO
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    @FXML
    private void handleButtonSearh(ActionEvent event){
        String str;
        str = String.valueOf(book.getValue(textToSearch.getText()));
        System.out.println(str);
        //TODO
    }

    @FXML
    public void handlebtnWordsFilter(ActionEvent actionEvent) {
        System.out.println("Quiero filtrar palabras");
        //TODO
    }

    @FXML
    public void handleBtnClean(ActionEvent actionEvent) {
        if (!list.isEmpty()) {
            list.clear();
            textBooksList.setText("");
            //TODO
        }
    }
}
