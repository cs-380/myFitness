
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
public class MainController implements Initializable
{
	@FXML
	private Button AddFoodButton;
	@FXML
	private Button AddMealButton;
	@FXML
	private Button GenerateMealButton;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnEditProfile;
    @FXML
    private Button btnContinue;
	@FXML
	private TextArea tArea;
	@FXML
	private TextField foodSearchField;
	@FXML
	private Label lblName;
	@FXML
	private Label lblAge;
	@FXML
	private Label lblHeight;
	@FXML
	private Label lblStartWei;
	@FXML
	private Label lblCrtWei;
	@FXML
	private Label lblGoalWei;
	@FXML
	private LineChart<Number, Number> CalorieChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;


    private int marker = 0;
    private Food[] test_search = new Food[0];
	
	public TextArea gettArea()
	{
		return tArea;
	}
	public void settArea(String str)
	{
	
		tArea.appendText("\n" + str + "\n");
	}
	
	public TextField getfoodSearchField()
	{
		return getFoodSearchField();
	}
	
	
	//Meal list tab
	public void GenerateMeal(ActionEvent event) throws IOException // what happens when click generate meal
	{
		tArea.setText("ok we did it");	// place holder
	}
	
	public void AddFood() throws IOException    // insert a specific food to DB ( maybe a meal? )
	{
		
	}
	
	public void createCalorieAlert() 
	{
		AlertBox.display("Set your calories", "Set your calories");
	}

    public void search_for_food() {
    	TextField inputs = getFoodSearchField();
       // scan = new Scanner(System.in);
        //System.out.println("Type in search: ");
        String input = inputs.getText();
        
        
        if (!input.equals(""))
        {
    		test_search = FOOD_DATA.searching(input);
    		//if(tableName.equals("MEAL_DATA"))test_search = MEAL_DATA.searching(input);
    		if(marker + 10 > test_search.length)
    		{
    			String str = "";
    			int i = marker;
        		for(; i < test_search.length; i++) {
        			str = test_search[i].toString();
        			settArea(str);
        		}
        		marker = 0;
    		}
    		else
    		{
    			String str = "";
    			int i = marker;
        		for(; i < (marker + 10); i++) {
        			str = test_search[i].toString();
        			settArea(str);
        		}
    			marker = i;
    		}
    	}
		
    }
	public void FoodSearch(ActionEvent event) throws IOException
	{
		marker = 0;
		tArea.clear();
		search_for_food();
	}
	public TextField getFoodSearchField() {
		return foodSearchField;
	}
	public void setFoodSearchField(TextField foodSearchField) {
		this.foodSearchField = foodSearchField;
	}
	
	public void editProfile(ActionEvent event)
	{
		
		try 
		{
			//Close main scene 
			Stage stage1 = (Stage) btnEditProfile.getScene().getWindow();
			stage1.close();
    	
			//open login ui 
			Parent root = FXMLLoader.load(getClass().getResource("EditAccount.fxml"));
			stage1.setTitle("Edit Account");
			stage1.setScene(new Scene(root));
			stage1.show();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void unPause(ActionEvent event)
	{
		tArea.clear();
		if(marker + 10 > test_search.length)
		{
			String str = "";
			int i = marker;
    		for(; i < test_search.length; i++) {
    			str = test_search[i].toString();
    			settArea(str);
    		}
    		marker = 0;
		}
		else
		{
			String str = "";
			int i = marker;
    		for(; i < (marker + 10); i++) {
    			str = test_search[i].toString();
    			settArea(str);
    		}
			marker = i;
		}
	}
	
	public void displaySearch()
	{
		
	}
	
	public void logout(ActionEvent event)
	{
		
		try 
		{
			//Close main scene 
			Stage stage1 = (Stage) btnLogout.getScene().getWindow();
			stage1.close();
    	
			//open login ui 
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			stage1.setTitle("Create Account");
			stage1.setScene(new Scene(root));
			stage1.show();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//TODO 
	private void init()
	{
		//Connect to db
		try(Connection conn = DriverManager.getConnection("jdbc:sqlite:USER.db");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM USER_DATA");
	    )
		{
			
		while(rs.next())
		{
			
			if( rs.getString("username").equals(LoginController.global))
			{
			
			int units = rs.getInt("metric_check");
			String weiUnit;
			String heightUnit;
			
			if(units == 0)
			{
				//US
				weiUnit = "Lbs";
			
			}
			else
			{
				//Metric
				weiUnit = "Kgs";
			}
			
			
			if(units == 0)
			{
				//Height cm or ft conversion for #'#"
				float val = rs.getFloat("height");
				int ft = (int)val/12;
				float in = val%12;
				heightUnit = Integer.toString(ft) + "'" + Float.toString(in) + "''";
			}
			else
			{
				float val = rs.getFloat("height");
				heightUnit = Float.toString(val) + "cm";
			}
			
			
			//rs get everything and set labels
			lblName.setText("Name : " + rs.getString("profile_name"));
			lblAge.setText("Age : " + Integer.toString(rs.getInt("age")));
			lblHeight.setText("Height : " + " " + heightUnit);
			lblStartWei.setText("Starting Weight : " + Integer.toString(rs.getInt("start_weight")) + " " +  weiUnit);
			lblCrtWei.setText("Current Weight : " + Integer.toString(rs.getInt("goal_weight")) + " " + weiUnit);
		    lblGoalWei.setText("Goal Weight : " + Integer.toString(rs.getInt("current_weight")) + " " + weiUnit);
		    
			}//close if 
			}//close while 	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Auto-generated method stub
		init();
		XYChart.Series series1 = new XYChart.Series();
	    series1.setName("Series 1");
	    series1.getData().add(new XYChart.Data<>("1", 2000));
	    series1.getData().add(new XYChart.Data<>("2", 1000));
	    series1.getData().add(new XYChart.Data<>("3", 800));
	    series1.getData().add(new XYChart.Data<>("4", 1800));
	    series1.getData().add(new XYChart.Data<>("5", 2000));
	    series1.getData().add(new XYChart.Data<>("6", 1000));
	    CalorieChart.getData().add(series1);
	}
	
}
