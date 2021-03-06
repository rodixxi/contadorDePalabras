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
    private Label statusLabel;

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

        statusLabel.setText("Loading...");
        String previosLoadedFiles = "";
        shelf.readBooks();
        previosLoadedFiles = previosLoadedFiles.concat(textBooksLoadedList.getText());
        previosLoadedFiles = previosLoadedFiles.concat(textBooksList.getText());
        textBooksLoadedList.setText(previosLoadedFiles);
        textBooksList.setText("");
        textTotalCountWords.setText(shelf.getWordsCount());
        tableWordsList.setText(shelf.toString());
        statusLabel.setText("Status... OK");

        //TODO
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shelf = new Shelf();
        tableWordsList.setText(shelf.toString());
        textTotalCountWords.setText(shelf.getWordsCount());
        // TODO
    }


    @FXML
    private void handleButtonSearch(ActionEvent event){
        String str;
        str = String.valueOf(shelf.getValue(textWordToSearch.getText())).toLowerCase();
        textWordsToShowCount.setText(str);
        //TODO
    }


    @FXML
    public void handleBtnClean(ActionEvent actionEvent) {
        if (!list.isEmpty()) {
            list = new ArrayList<File>();
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
