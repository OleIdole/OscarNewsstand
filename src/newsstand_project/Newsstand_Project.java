/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsstand_project;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Claponio
 */
public class Newsstand_Project extends Application 
{
    private Register literatureReg;
    private ObservableList<Literature> litList;
    private Stage mainStage;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
 @Override
    public void start(Stage primaryStage) 
    {
       this.mainStage = primaryStage;
       literatureReg = new Register();
       this.fillTableWithDummies();
        
       BorderPane root = new BorderPane();
       VBox topContainer = new VBox();
       MenuBar mainMenu = createMenus();
        
       topContainer.getChildren().add(mainMenu);
       topContainer.getChildren().add(createSearchAndButtons());
       
       root.setTop(topContainer);

       root.setCenter(createCentreTable());
       
       
       Scene scene = new Scene(root, 1000, 500);
       mainStage.setTitle("Literatur Register");
       mainStage.setScene(scene);
       mainStage.show();
 

    }
    /**
     * Creates the menubar.
     * @return The menubar
     */
    private MenuBar createMenus()
    {
        MenuBar menuBar = new MenuBar();
        
        // The File menu
        Menu menuFile = new Menu("File");
        MenuItem exitApp = new MenuItem("Exit");
        menuFile.getItems().add(exitApp);
        
        // The About menu
        Menu menuHelp = new Menu("Help");
        MenuItem about = new MenuItem("About");
        menuHelp.getItems().add(about);
        
  
        
        // Handle exit event
        exitApp.setOnAction((ActionEvent event) -> 
        {
            doExitApplication();
        });
        
        // Handle about event
        about.setOnAction((ActionEvent event) -> 
        {
            showAbout();
        });
                
        //Adding the submenus to the menubar.
        menuBar.getMenus().addAll(menuFile, menuHelp);
        
        
        return menuBar;
    }
    
    
    private void showAbout()
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About this awesome Application");
        alert.setHeaderText("Vomma v1.0");
        alert.setContentText("This app was created by: Ultimate Vomme");
        
        Optional<ButtonType> result = alert.showAndWait();
        
    }
    
    private void doExitApplication()
    {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confimation Dialog");
        alert.setHeaderText("Exit Application ?");
        alert.setContentText("Are you sure you want to exit?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == ButtonType.OK)
        {
            Platform.exit();  
        }
        
        else
        {
            
        }
    }
    
    private void fillTableWithDummies()
    {
        this.literatureReg.addLiterature(new Book("Morten", "Kniven AS", 20, "Action", 1, "Solli"));
        this.literatureReg.addLiterature(new Newspaper("Sondre går berserk", "Kniven", 1, "Action", 50));
        this.literatureReg.addLiterature(new Newspaper("Henrik går berserk", "Kniven", 2, "Action", 50));
        this.literatureReg.addLiterature(new Newspaper("Ole går berserk", "Kniven", 3, "Action", 50));
    }
    
    private ObservableList<Literature> getRegisterList()
    {
        litList = FXCollections.observableArrayList(this.literatureReg.getCollection());
        return litList;
    }
    
    private void updateObservableList()
    {
        this.litList.setAll(this.literatureReg.getCollection()); 
        
    }
    
    /**
     * 
     * Creates the search field and the add and remove button.
     * @return 
     */
    private Node createSearchAndButtons()
    {
        HBox hb = new HBox();
        hb.setPadding(new Insets(10));
        hb.setSpacing(8);
        hb.setAlignment(Pos.CENTER);
        
        
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
      
        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        grid.add(searchField, 0, 0);
        searchField.textProperty().addListener(
            new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, 
                                    Object oldVal, Object newVal) {
                    handleSearchByKey((String)oldVal, (String)newVal);
                }
            });
        
  
        Hyperlink advancedSearch = new Hyperlink("Advanced");
        grid.add(advancedSearch, 1, 0);
        
        Button addButton = new Button("Add");
        addButton.setMaxSize(100, 10);
        addButton.setOnAction((ActionEvent e) -> 
        {
            doSelectLiterature();
        });
                
        Button removeButton = new Button("Remove");
        removeButton.setMaxSize(100, 10);
        
        VBox vb = new VBox();
        vb.setPadding(new Insets(10));
        vb.setSpacing(8);

        
        vb.getChildren().add(addButton);
        vb.getChildren().add(removeButton);
        
        hb.getChildren().addAll(grid, vb);
             
        return hb;  
        
    }
    
    private void doSelectLiterature()
    {
        List<String> choices = new ArrayList();
        choices.add("Newspaper");
        choices.add("Book");
        choices.add("Magazine");
        
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Select", choices);
        dialog.setTitle("Literatures list");
        dialog.setHeaderText("Add new literature!");
        dialog.setContentText("Add");
        
        Optional<String> result = dialog.showAndWait();
        if("Newspaper".equals(result.get()))
        {
            doAddNewspaper();
        }
        
        else if("Book".equals(result.get()))
        {
            //doAddBook();
        }
        
        else if("Magazine".equals(result.get()))
        {
           // doAddMagazine();
        }

    }
    
    private void doAddNewspaper()
    {
        NewspaperDetailsDialog npDialog = new NewspaperDetailsDialog();
        
        Optional<Newspaper> result = npDialog.showAndWait();
        
        if(result.isPresent())
        {
            Newspaper newspaper = result.get();
            literatureReg.addLiterature(newspaper);
            updateObservableList();
        }
        
        this.updateObservableList();
        
    }
    
    private Node createCentreTable()
    {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        TableView tableview = new TableView();
        
        
        TableColumn<Literature, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(300);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        
        TableColumn<Literature, String> publisherColumn = new TableColumn("Publisher");
        publisherColumn.setMinWidth(300);
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        
                
        tableview.setItems(this.getRegisterList());
        tableview.getColumns().addAll(titleColumn, publisherColumn);
        
        vbox.getChildren().add(tableview);
        
        return vbox;
    }



    public void handleSearchByKey(String oldVal, String newVal) {
        // Break out all of the parts of the search text 
        // by splitting on white space
        String[] parts = newVal.toLowerCase().split(" ");        


        // If the number of characters in the text box is less than last time
        // it must be because the user pressed delete
        if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
            // Restore the lists original set of entries 
            // and start from the beginning
            updateObservableList();
            
            // Updates the result with the input in the searchbar
            updateObservableListFromSearch(parts);
        }
        
         else {  
            /// Updates the result with the input in the searchbar
            updateObservableListFromSearch(parts);        
        }
    }

    
    private void updateObservableListFromSearch(String[] parts) {
        for ( String part : parts) {
            Register newLiteratureReg = new Register();
            Iterator<Literature> lit = literatureReg.searchContainTitleOrPublisher(part);
            while (lit.hasNext()) {
                newLiteratureReg.addLiterature(lit.next());
            }
            this.litList.setAll(newLiteratureReg.getCollection());
        }
    }
    
    
}
