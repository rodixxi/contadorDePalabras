package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import textManager.Shelf;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {


    private Shelf shelf;
    private List<File> list = new ArrayList<>();

    @FXML
    private TextArea textBooksList;
    @FXML
    private TextField textWordToSearch;
    @FXML
    private TextField textWordsToShowCount;
    @FXML
    private TextField textTotalCountWords;
    @FXML
    public TextArea textBooksLoadedList;
    @FXML
    public Button btnExit;
    @FXML
    private TextArea tableWordsList;
    @FXML
    private TableColumn tableColumnWord;
    @FXML
    private TableColumn tableColumnCount;

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
                shelf.addBook(file);
            }
            textBooksList.setText(listOfFilesNames);

        }
    }


    @FXML
    private void handleButtonLoad(ActionEvent event) throws IOException, FileNotFoundException, ClassNotFoundException{
        /*File file = new File("16082-8.txt");
        Book book = new Book(file);*/
        String previosLoadedFiles = "";
        shelf.readBooks();
        System.out.println(shelf.toString());
        previosLoadedFiles = previosLoadedFiles.concat(textBooksLoadedList.getText());
        previosLoadedFiles = previosLoadedFiles.concat(textBooksList.getText());
        textBooksLoadedList.setText(previosLoadedFiles);
        textBooksList.setText("");
        textTotalCountWords.setText(shelf.getWordsCount());
        tableWordsList.setText(shelf.toString());

        //TODO
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String listOfFilesNames = "";
        shelf = new Shelf();
        Set set = shelf.getWords().entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()){
            Map.Entry me = (Map.Entry) i.next();
        }
        if (!shelf.isEmpty()){
            for (String name : shelf.getBooksNamesList()){
                listOfFilesNames = listOfFilesNames.concat(name);
                listOfFilesNames = listOfFilesNames.concat("\n");
            }
            textBooksLoadedList.setText(listOfFilesNames);
        }
        textTotalCountWords.setText(shelf.getWordsCount());
        // TODO
    }


    @FXML
    private void handleButtonSearch(ActionEvent event){
        String str;
        str = String.valueOf(shelf.getValue(textWordToSearch.getText())).toLowerCase();
        System.out.println(str);
        textWordsToShowCount.setText(str);
        //TODO
    }


    @FXML
    public void handleBtnClean(ActionEvent actionEvent) {
        if (!list.isEmpty()) {
            //list.clear();
            textBooksList.setText("");
            textWordToSearch.setText("");
            textWordsToShowCount.setText("0");
            //TODO
        }
    }

    @FXML
    public void handleBtnSave(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        shelf.saveToFile();
    }

    @FXML
    public void handleBtnExit(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }
}
