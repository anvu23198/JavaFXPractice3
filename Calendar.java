package project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Calendar extends Application
{
   private BorderPane containerPane = new BorderPane();
   private LocalDateTime date = LocalDateTime.now();
   private String appointmentFile = "src/project4/appointmentFile.csv";
   private Scene scene;
   private String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
   private char[] arrDay = {'S','M','T','W','T','F','S'};
   private int currentYear = date.getYear();

   private int yearPrevious = date.getYear();
   private int monthPrevious = date.getMonthValue();
   private BorderPane setUp = new BorderPane();
   private Stage appointmentStage = new Stage();
   private ArrayList<String> apointment = new ArrayList<>();
   private boolean yearClicked = false;

   @Override
   public void start(Stage primaryStage)
   {
      scene = new Scene(containerPane, 1000, 800);
      containerPane.setStyle("-fx-background-color: whitesmoke;");
      setupTopPane();
      GridPane gp = setupMonthPane(date.getYear(), date.getMonthValue());
      containerPane.setCenter(gp);
      primaryStage.setTitle("Calendar");
      primaryStage.setMinWidth(1000);
      primaryStage.setMinHeight(800);
      primaryStage.setScene(scene);
      primaryStage.show();
      


      Scene appointmentScene = new Scene(setUp, 350, 250);
      appointmentStage.setTitle("Add Event");
      appointmentStage.setScene(appointmentScene);
   }
    
   public void setupTopPane()
   {
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
	   BorderPane border = new BorderPane();
	   int monthTop = date.getMonthValue();
	   int yearTop = date.getYear();
	   Text monthYear = new Text(monthNames[monthTop-1]+"   "+yearTop);
	   monthYear.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
	   border.setLeft(monthYear);
	   border.setPadding(new Insets(10, 10, 5, 15));
	   
	   HBox hbox = new HBox(5);
	   Button previous = new Button("<");
	   previous.setOnAction(e ->{
		   yearClicked = false;
		   if(monthPrevious == 1)
		   {
			   monthPrevious = 12;
			   yearPrevious--;
		   }
		   else monthPrevious--;
		   Text setAgain = new Text(monthNames[monthPrevious-1]+"   "+yearPrevious);
		   setAgain.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
		   border.setLeft(setAgain);
		   setupMonthPane(yearPrevious, monthPrevious);
	   });
	   
	   Button year = new Button("Year");
	   year.setOnAction(e ->{
		   yearClicked = true;
		   twelveMonthsPane();
	   });
	   
	   Button today = new Button("Today");
	   today.setOnAction(e ->{
		   yearClicked = false;
		   monthPrevious = monthTop;
		   yearPrevious = yearTop;
		   setupMonthPane(yearTop, monthTop);
		   border.setLeft(monthYear);
	   });
	   
	   Button next = new Button(">");
	   next.setOnAction(e ->{
		   yearClicked = false;
		   if(monthPrevious == 12)
		   {
			   monthPrevious = 1;
			   yearPrevious++;
		   }
		   else monthPrevious++;
		   Text setAgain = new Text(monthNames[monthPrevious-1]+"   "+yearPrevious);
		   setAgain.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
		   border.setLeft(setAgain);
		   setupMonthPane(yearPrevious, monthPrevious);
	   });
	   
	   year.setStyle("-fx-pref-width: 80px;");
	   today.setStyle("-fx-pref-width: 80px;");
	   hbox.getChildren().addAll(previous,year,today,next);
	   
	   border.setRight(hbox);
	   containerPane.setTop(border);
   
   }
   
   public GridPane setupMonthPane(int yearValue, int monthValue)
   {
      GridPane monthPane = new GridPane();
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
      monthPane.setPadding(new Insets(10,10,30,10));
      for(int i = 0; i < 7; i++) 
      {
	      Label day = new Label("");
	      day.setStyle("-fx-border-color: black;-fx-border-width: 0.3px;");
	      day.setPadding(new Insets(5));
	      day.setAlignment(Pos.CENTER);
	      
	      StackPane stackpane = new StackPane();
	      Text text = new Text(arrDay[i] + "");
	      text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
	      stackpane.getChildren().addAll(text,day);
	      
	      monthPane.add(stackpane, i, 0);
	      day.prefWidthProperty().bind(containerPane.widthProperty());
      }
      
      for(int i = 1; i < 7; i++) 
      {
    	  for(int j = 0; j < 7; j++) 
          {
		      Label dateSetup = new Label("");
		      dateSetup.setStyle("-fx-border-color: black;-fx-border-width: 0.3px;");
		      dateSetup.setPadding(new Insets(5));
		      dateSetup.setAlignment(Pos.CENTER);
		      dateSetup.prefHeightProperty().bind(containerPane.heightProperty());
		      dateSetup.prefWidthProperty().bind(containerPane.widthProperty());
		      
		      StackPane stackpane = new StackPane();
		      Text text = new Text("");
		      stackpane.getChildren().addAll(text,dateSetup);
		      
		      monthPane.add(stackpane, j, i);
          }
      }
      containerPane.setCenter(monthPane);
      fillUpMonth(monthPane,yearValue,monthValue);
      
      return monthPane;
   }
   
   public void fillUpMonth(GridPane monthGP, int yearValue, int monthValue)
   {
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS	
	   int lastMonth = monthValue - 1;
	   int yearSpecialCase = yearValue;
	   if(monthValue == 1) 
	   {
		   lastMonth = 12;
		   yearSpecialCase = yearValue - 1;
	   }
	   
	   YearMonth ym = YearMonth.of(yearSpecialCase, lastMonth);
	   LocalDate endOfMonth = ym.atEndOfMonth() ;
	   LocalDate lastSundayOfPriorMonth = endOfMonth.with( TemporalAdjusters.previousOrSame( DayOfWeek.SATURDAY ) ) ;
	   
	   int dayPrevious = Integer.parseInt((lastSundayOfPriorMonth+"").split("-")[2]);
	   YearMonth yearLastMonthObject = YearMonth.of(yearSpecialCase, lastMonth);
	   int daysInLastMonth = yearLastMonthObject.lengthOfMonth(); 
	   

	   YearMonth yearThisMonthObject = YearMonth.of(yearValue, monthValue);
	   int daysInThisMonth = yearThisMonthObject.lengthOfMonth();
	   
	   int dayStart = daysInLastMonth - dayPrevious;
	   
	   int today = date.getDayOfMonth();
	   
	   int day = 1, k = 0, nextMonth = 1;
	   boolean here = false;
	    for (Node node : monthGP.getChildren())
	    {

	    	StackPane stackMain = (StackPane) node;
	    	if(GridPane.getRowIndex(node) == 0) continue;
	    	VBox vbox = new VBox();
	    	vbox.setAlignment(Pos.CENTER);
	    	k++;
	    	if(k <= dayStart) 
	    	{
		    	displayAppointments(yearValue,monthValue-1,dayPrevious,stackMain,vbox);
				dayPrevious++;
				Text text = new Text(dayPrevious+"");
			    text.setFill(Color.DARKGREY);
			    vbox.getChildren().add(text);
	    	}
	    	else if(day > daysInThisMonth) 
	    	{
				Text text = new Text(nextMonth+"");
			    text.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 12));
			    vbox.getChildren().add(text);
			    text.setFill(Color.DARKGREY);
		    	displayAppointments(yearValue,monthValue+1,nextMonth,stackMain,vbox);
				nextMonth++;
				here = true;
	    	}
	    	else if(day == today && monthValue == date.getMonthValue())
	    	{
				Text text = new Text(day+"");
			    text.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 12));
				text.setFill(Color.WHITE);
				Circle cir = new Circle(10);
				cir.setFill(Color.RED);
		    	StackPane stack = new StackPane();
		    	stack.getChildren().addAll(cir,text);
			    vbox.getChildren().add(stack);
		    	displayAppointments(yearValue,monthValue,day,stackMain,vbox);
				day++;
	    	}
	    	else 
	    	{
				Text text = new Text(day+"");
			    text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
			    vbox.getChildren().add(text);
		    	displayAppointments(yearValue,monthValue,day,stackMain,vbox);
				day++;
	    	}
	    	int s = day-1;
	    	if(s > 0 && here == false)
	    	stackMain.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    	    @Override
		    	    public void handle(MouseEvent mouseEvent) {
		    	        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		    	            if(mouseEvent.getClickCount() == 2){
		    	              setupAppointmentPane(yearValue,monthValue,s);
		    	      	      appointmentStage.show();
		    	            }
		    	        }
		    	    }
		    });

	    }

   }
    
   public GridPane twelveMonthsPane()
   {
      GridPane twelve = new GridPane();
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
      
      twelve = (GridPane) containerPane.getCenter();
      twelve.getChildren().clear();
      
      BorderPane top = (BorderPane) containerPane.getTop();
	  Text topYear = new Text("" + date.getYear());
	  topYear.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
      top.setLeft(topYear);
      
      int month = 1;
      for(int a = 0; a < 3; a++) 
      {
    	  for(int b = 0; b < 4; b++) 
          {
    		  BorderPane border = new BorderPane();
    	      GridPane monthPane = new GridPane();
    	      monthPane.setPadding(new Insets(10,10,30,10));
    	      for(int i = 0; i < 7; i++) 
    	      {
    		      Label day = new Label("");
    		      day.setPadding(new Insets(5));
    		      day.setAlignment(Pos.CENTER);
    		      
    		      StackPane stackpane = new StackPane();
    		      Text text = new Text(arrDay[i] + "");
    		      text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
    		      stackpane.getChildren().addAll(text,day);
    		      
    		      monthPane.add(stackpane, i, 0);
    		      day.prefWidthProperty().bind(containerPane.widthProperty());
    	      }
    	      
    	      for(int i = 1; i < 7; i++) 
    	      {
    	    	  for(int j = 0; j < 7; j++) 
    	          {
    			      Label dateSetup = new Label("");
    			      dateSetup.setPadding(new Insets(5));
    			      dateSetup.setAlignment(Pos.CENTER);
    			      dateSetup.prefHeightProperty().bind(containerPane.heightProperty());
    			      dateSetup.prefWidthProperty().bind(containerPane.widthProperty());
    			      
    			      StackPane stackpane = new StackPane();
    			      Text text = new Text("");
    			      stackpane.getChildren().addAll(text,dateSetup);
    			      
    			      monthPane.add(stackpane, j, i);
    	          }
    	      }
    	      Text text = new Text(monthNames[month-1].toUpperCase());
    	      border.setTop(text);
    	      BorderPane.setAlignment(text, Pos.CENTER);
    	      border.setCenter(monthPane);
    	      twelve.add(border, b, a);
    	      
    	      fillUpMonth(monthPane,currentYear,month);
    	      month++;
          }
      }
      
      
      return twelve;
   }
   
   
   
   //The following is for the extra credit
    
   public void setupAppointmentPane(int yearValue, int monthValue, int day)
   {
        //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
	   date = LocalDateTime.now();
       Label lblTitle = new Label("Title: ");
       TextField txtTitle = new TextField();
       Label lblTime= new Label("Time: ");
       
       ComboBox<String> cbHour = new ComboBox<>();
       for(int i = 0; i < 24; i++) {
    	   String hour = i+"";
    	   if(i < 10)
    		   hour = "0"+i;
    	   cbHour.getItems().add(hour);
       }
       int hourNow = date.getHour();
       String hourNowStr = (hourNow < 10) ? "0"+hourNow : hourNow+"";
       cbHour.setValue(hourNowStr);
       
       ComboBox<String> cbMin = new ComboBox<>();
       for(int i = 0; i < 60; i++) {
    	   String hour = i+"";
    	   if(i < 10)
    		   hour = "0"+i;
    	   cbMin.getItems().add(hour);
       }
       int minNow = date.getMinute();
       String minNowStr = (minNow < 10) ? "0"+minNow : minNow+"";
       cbMin.setValue(minNowStr);

       Button clear = new Button("Clear");
       clear.setOnAction(e->{
    	   clear(txtTitle,cbHour,cbMin);
       });
       
       Button submit = new Button("Submit");
       submit.setOnAction(e ->{
    	   storeAppointment(txtTitle, yearValue, monthValue, Integer.parseInt(cbHour.getValue()), Integer.parseInt(cbMin.getValue()),day);
       });
       
	   GridPane gridPane = new GridPane();
       gridPane.add(lblTitle, 0, 0);
       gridPane.add(txtTitle, 1, 0);

       HBox hbox = new HBox(10);
       hbox.getChildren().addAll(cbHour,cbMin);
       
       gridPane.add(lblTime, 0, 1);
       gridPane.add(hbox, 1, 1);
       
       HBox hbox1 = new HBox(30);
       hbox1.getChildren().addAll(clear,submit);
       gridPane.add(hbox1, 1, 2);
       
       gridPane.setAlignment(Pos.CENTER);
       gridPane.setPadding(new Insets(20,20,20,20));
       gridPane.setHgap(30);
       gridPane.setVgap(30);
	   setUp.setCenter(gridPane);
        
   }
    

   public void displayAppointments(int yearValue, int monthValue, int dayValue, StackPane stack, VBox vbox)
   {
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS

      File file = new File(appointmentFile);
      try {
		Scanner scan = new Scanner(file);
		while(scan.hasNext()) {
			apointment.add(scan.nextLine()+"");
		}
	  } catch (FileNotFoundException e) {
		e.printStackTrace();
	  }
      
      VBox vbox1 = new VBox(100);
      if(!yearClicked) {
	      for(String data : apointment)
	      {
	    	  String[] arr = data.split(",");
	    	  String titleApoint = arr[0];
	    	  int yearApoint = Integer.parseInt(arr[1]);
	    	  int monthApoint = Integer.parseInt(arr[2]);
	    	  int dateApoint = Integer.parseInt(arr[3]);
	    	  int hourApoint = Integer.parseInt(arr[4]);
	    	  int minuApoint = Integer.parseInt(arr[5]);
		   	  String hourStr = (hourApoint < 10) ? "0"+hourApoint : hourApoint+"";
			  String minStr = (minuApoint < 10) ? "0"+minuApoint : minuApoint+"";
	    	  if(yearApoint == yearValue && monthValue == monthApoint && dayValue == dateApoint)
	    	  {
	    		  Text text = new Text(hourStr + ":" + minStr + " " + titleApoint);
	    		  text.setFill(Color.GREEN);
	    		  vbox1.getChildren().add(text);
	    		  vbox1.setAlignment(Pos.BOTTOM_CENTER);
	    		  vbox1.setPadding(new Insets(20,20,30,20));
	    		  break;
	    	  }
	      }
      }
      
      vbox.setAlignment(Pos.CENTER);
	  stack.getChildren().addAll(vbox, vbox1);
	   

   }

   public void clear(TextField title, ComboBox<String> hour, ComboBox<String> min)
   {
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
	   date = LocalDateTime.now();
       int hourNow = date.getHour();
       String hourNowStr = (hourNow < 10) ? "0"+hourNow : hourNow+"";
       hour.setValue(hourNowStr);
       
       int minNow = date.getMinute();
       String minNowStr = (minNow < 10) ? "0"+minNow : minNow+"";
       min.setValue(minNowStr);
      
       title.setText("");
   }

    
   public void storeAppointment(TextField txtTitle, int yearValue, int monthValue, int hour, int min, int day)
   {   
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
	   try {
		   FileWriter write = new FileWriter(new File(appointmentFile),true);
		   StringBuilder sb = new StringBuilder();
		   
		   sb.append(txtTitle.getText());
		   sb.append(",");
		   sb.append(yearValue);
		   sb.append(",");
		   sb.append(monthValue);
		   sb.append(",");
		   sb.append(day);
		   sb.append(",");
		   sb.append(hour);
		   sb.append(",");
		   sb.append(min);
		   sb.append("\n");
		   
		   write.write(sb.toString());
		   
		   write.close();
	   }catch(IOException e) {
		   System.out.println("File Not Found");
	   }

	   appointmentStage.close();
	   setupMonthPane(yearValue, monthValue);
   }

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      launch(args);
   }
}
