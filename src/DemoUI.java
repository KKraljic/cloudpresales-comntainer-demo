
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import com.fourspaces.couchdb.*;

@SuppressWarnings("serial")
public class DemoUI extends JFrame {
		private JLabel nameFieldLable;
		private JLabel successFieldLable;
		private JLabel retrieveAllEntriesLable;
		private JLabel returnLable;
		
		private JTextField nameField;
		
		private JButton submitButton;
		private JButton retrieveButton;
		
		public static void main(String[] args) {
			new DemoUI("Simple Demo Application");
		}
		
		public DemoUI(String title){
			super(title);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(500, 500);
			this.setResizable(false);
			
			GridLayout layout = new GridLayout(8, 1);
			this.setLayout(layout);
			
			nameFieldLable = new JLabel("Your name:");
			nameField = new JTextField();
			submitButton = new JButton("Submit to database!");
			successFieldLable = new JLabel("");
			
			
			
			submitButton.setName("submit");
			submitButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					fillUI(1);
					insertDataIntoDb(nameField.getText());
				}
			});
			
			retrieveAllEntriesLable = new JLabel("Wanna retrieve all entries of your DB?");
			retrieveButton = new JButton("Retrieve your data!");
			retrieveButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					fillUI(2);
					getDataFromDb();
				}
				
			});
			returnLable = new JLabel("");
			fillUI(0);
		}
		
		private void fillUI(int mode){
			
			switch(mode){
			case 1: successFieldLable.setText("Succesfully submitted!");
					returnLable.setText("");
					
					break;
			case 2: successFieldLable.setText(""); 
					returnLable.setText("The information inside the DB is:");
					getDataFromDb();
					break;
			default:successFieldLable.setText(""); 
					returnLable.setText("");
					break;
			}
			
			getContentPane().add(nameFieldLable);
			getContentPane().add(nameField);
			getContentPane().add(submitButton);
			getContentPane().add(successFieldLable);
			getContentPane().add(retrieveAllEntriesLable);
			getContentPane().add(retrieveButton);
			getContentPane().add(returnLable);
			
			getContentPane().repaint();
			this.setVisible(true);
			
		}
		
		private void insertDataIntoDb(String name){
			Session dockerSession = new Session ("localhost",5984, "test", "test", false, false);
			dockerSession.createDatabase("names");
			
			
			
			
		}
		
		private void getDataFromDb(){
			Session dockerSession = new Session ("localhost",5984, "test", "test", false, false);
			List<String> availableDbs = dockerSession.getDatabaseNames();
			String resultingEntriesFromDb = "";
			
			for(String dbEntry : availableDbs){
				resultingEntriesFromDb = resultingEntriesFromDb + "\n \t" + dbEntry ;
			};
			
			returnLable.setText(resultingEntriesFromDb);
			getContentPane().add(returnLable);
			getContentPane().repaint();
			this.setVisible(true);
		}
}
