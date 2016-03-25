/* Created on : 19-02-2016
 * Author     : Karlo Kraljic
 *
 *-----------------------------------------------------------------------------
 * Revision History (Release 1.1.0.0)
 *-----------------------------------------------------------------------------
 * VERSION     AUTHOR/      DESCRIPTION OF CHANGE
 * OLD/NEW     DATE                RFC NO
 *-----------------------------------------------------------------------------
 * --/1.0  | Karlo Kraljic | Initial Create.
 *         | 19-02-2016    |
 *---------|---------------|---------------------------------------------------
 * 1.0/1.1 | Karlo Kraljic | Code improvement. No changes in terms of functionality.
 *         | 25-03-2016    | 
 *---------|---------------|---------------------------------------------------
 */

package HybridIT;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import HybridIT.com.fourspaces.couchdb.*;

@SuppressWarnings("serial")
public class DemoUI extends JFrame {
	
		private static final String PRESENTER_KEY_NAME = "name";
		static Session dockerSession;
		static Database presenterDb;

		private JLabel nameFieldLable;
		private JLabel successFieldLable;
		private JLabel retrieveAllEntriesLable;
		private JLabel returnLable;
		private JTextField nameField;
		private JButton submitButton;
		private JButton retrieveButton;
		private JScrollPane rightScrollbar;
		
		public static void main(String[] args) {
			new DemoUI("Simple Demo Application");
		}
		
		public DemoUI(String title){
			super(title);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(500, 500);
			this.setResizable(false);
			
			GridLayout layout = new GridLayout(7, 1);
			this.setLayout(layout);
			
			nameFieldLable = new JLabel("Your name:");
			nameField = new JTextField();
			submitButton = new JButton("Submit to database!");
			successFieldLable = new JLabel("");
			submitButton.setName("submit");
			submitButton.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) {
					fillUI(1);
				}
			});
			
			retrieveAllEntriesLable = new JLabel("Wanna retrieve all entries of your DB?");
			retrieveButton = new JButton("Retrieve your data!");
			retrieveButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					fillUI(2);
				}
				
			});
			returnLable = new JLabel();
			rightScrollbar = new JScrollPane(returnLable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			fillUI(0);
			
}
		
		private void fillUI(int mode){
			
			switch(mode){
			case 1: setDataInDb();
					returnLable.setText("");
					break;
			case 2: successFieldLable.setText(""); 
					getDataFromDb(); 
					break;
			default:successFieldLable.setText(""); 
					break;
			}
				getContentPane().add(nameFieldLable);
				getContentPane().add(nameField);
				getContentPane().add(submitButton);
				getContentPane().add(successFieldLable);
				getContentPane().add(retrieveAllEntriesLable);
				getContentPane().add(retrieveButton);
				getContentPane().add(rightScrollbar);
				getContentPane().repaint();
				this.setVisible(true);
		}
		
		private static void openDbSession(){
			System.out.println("\n Trying to connect to the DB...");
			dockerSession = new Session ("172.17.0.2",5984);
			System.out.println("\n Success.");			
			
			System.out.println("\n Trying to catch the presenter DB:");
			
			if(dockerSession.getDatabase("presenter") != null){
			presenterDb = dockerSession.getDatabase("presenter");
			System.out.println("\n Success.");
			}else{
				System.out.println("\n Failed. Reason: No 'presenter' DB available.");
			}
			
		}
		private void setDataInDb(){			
			Document newPresenterEntry;
			Map<String, String> propertiesOfPresenter;
			
			openDbSession();
			
			newPresenterEntry = new Document();
			propertiesOfPresenter = new HashMap<String, String>();
			propertiesOfPresenter.put(PRESENTER_KEY_NAME, nameField.getText());
			
			newPresenterEntry.putAll(propertiesOfPresenter);
			try {
				presenterDb.saveDocument(newPresenterEntry);
			} catch (IOException e) {
				System.out.println("\n Saving of document went wrong. Check everything.");
			}
			successFieldLable.setText("\n Succesfully submitted!");
		}
		
		private void getDataFromDb(){

			ViewResults presenterViewResults;
			List<Document> presenterDocuments;
			String id;
			Document presenterEntries;
			String returnSet;
			
			openDbSession();
			
			getContentPane().remove(returnLable);
			getContentPane().remove(rightScrollbar);
			
			returnSet = "<html><body>The following entries are available in the database:<br>";
			
			presenterViewResults = presenterDb.getAllDocuments();
			presenterDocuments = presenterViewResults.getResults();
			
			for(Document couchDocument : presenterDocuments){
				id = couchDocument.getJSONObject().getString("id");
				returnSet = returnSet + "<br> Pres. no.:&#09"+ " " + id; //&#09 is a tab
			
			
				try{
					presenterEntries = presenterDb.getDocument(id);
					
					if(presenterEntries.containsKey(PRESENTER_KEY_NAME)){
						returnSet = returnSet +  "<br> NAME:&#09"+ " " + presenterEntries.getString(PRESENTER_KEY_NAME) ;
					}
					
				}			
				catch(IOException e){
					System.out.println("No connection possible. Whyever.");
				}
				returnSet = returnSet + "<br>";
			}
			
			returnSet = returnSet + "</body></html>";
			getContentPane().add(returnLable);
			returnLable.setText(returnSet);	
			getContentPane().repaint();
			rightScrollbar = new JScrollPane(returnLable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			getContentPane().add(rightScrollbar);
			this.setVisible(true);
		}
}
