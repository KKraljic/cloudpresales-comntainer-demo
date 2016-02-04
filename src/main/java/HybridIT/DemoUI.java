package HybridIT;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import HybridIT.com.fourspaces.couchdb.*;
import HybridIT.com.fourspaces.couchdb.util.*;

@SuppressWarnings("serial")
public class DemoUI extends JFrame {
	
		private static final String PRESENTER_KEY_NAME = "name";
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
				
				public void actionPerformed(ActionEvent e) {
					fillUI(1);
					insertDataIntoDb(nameField.getText());
				}
			});
			
			retrieveAllEntriesLable = new JLabel("Wanna retrieve all entries of your DB?");
			retrieveButton = new JButton("Retrieve your data!");
			retrieveButton.addActionListener(new ActionListener(){
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
			Session dockerSession = new Session ("localhost",5984);
			dockerSession.createDatabase("presenter");
			
			
			
			
		}
		
		private void getDataFromDb(){
			Session dockerSession;
			Database presenterDb;
			ViewResults presenterViewResults;
			List<Document> presenterDocuments;
			String id;
			Document presenterEntries;
			String returnSet = "\n";
			
			
			dockerSession= new Session ("localhost",5984);
			presenterDb = dockerSession.getDatabase("presenter");
			presenterViewResults = presenterDb.getAllDocuments();
			presenterDocuments = presenterViewResults.getResults();
			
			returnLable.setText("The following entries are available in the database:");
			
			for(Document couchDocument : presenterDocuments){
				id = couchDocument.getJSONObject().getString("id");
			
				try{
					presenterEntries = presenterDb.getDocument(id);
					
					if(presenterEntries.containsKey(PRESENTER_KEY_NAME)){
						returnSet = "\n NAME: \t"+presenterEntries.getDouble(PRESENTER_KEY_NAME) ;
					}
					
				}			
				catch(IOException e){
					System.out.println("No conncetion possible. Whyever.");
				}
			}
			
			
			getContentPane().add(returnLable);
			getContentPane().repaint();
			this.setVisible(true);
		}
}
