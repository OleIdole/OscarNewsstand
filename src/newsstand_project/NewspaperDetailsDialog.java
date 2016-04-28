/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsstand_project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 *
 * @author Claponio
 */
public class NewspaperDetailsDialog extends Dialog<Newspaper>       
{
    
    public NewspaperDetailsDialog()
    {
        super();
        setTitle("Newspaper Details");
        
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField title = new TextField();
        title.setPromptText("Title");
        
        TextField publisher = new TextField();
        publisher.setPromptText("Publisher");
        
        TextField issueNo = new TextField();
        issueNo.setPromptText("Issue Number");
        preventFromChar(issueNo);
        
        TextField genre = new TextField();
        genre.setPromptText("Genre");
        
        TextField price = new TextField();
        price.setPromptText("Price");
        preventFromChar(price);
        
        
        
        getDialogPane().setContent(grid);
  
        setResultConverter(new Callback<ButtonType, Newspaper>()
        {
            @Override
            public Newspaper call(ButtonType button) 
            {
                if(button == ButtonType.OK)
                {
                    int issueNumber = Integer.parseInt(issueNo.getText());
                    int paperPrice = Integer.parseInt(price.getText());
                    return new Newspaper(title.getText(), publisher.getText(), issueNumber,
                            genre.getText(), paperPrice);
                }
                return null;
            }
            
        });
    }
    /**
     * 
     * @param text 
     */
    private void preventFromChar(TextField text)
    {
           text.textProperty().addListener(new ChangeListener<String>() 
        {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) 
            {
                try
                {
                    if(newValue.length() > 0)
                    {
                        Integer.parseInt(newValue);
                    }
                }
                catch (NumberFormatException e)
                {
                    text.setText(oldValue);
                }
            }
        });
    }
}
