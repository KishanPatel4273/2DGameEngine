package button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ButtonHandler implements ActionListener{

	
	public static ArrayList<Button> buttonList = new ArrayList<Button>();
	
	public ButtonHandler() {
		//add button below
		
		//buttonList.add(new Button(400, 400, 200, 200, "test", true, "temp"));
		

	}

	/**
	 * call method after all buttons have been loaded to list
	 */
	public void addListener() {
		//adds action listener to button
		for(Button b: buttonList){//applies to all buttons
			b.button.addActionListener(this);//this- implemented listener
		}		
	}
	
	/**
	 * updates button state
	 */
	public void tick() {
		//add what each button does below
		if(false && buttonList.get(0).getButtonState()) {
			System.out.println("clicked");
		}
	}
	
	/**
	 * updates all the buttons to check if they have be clicked or not
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();//stores the button that has been clicked
		for(Button b: buttonList) {//runs through all buttons
			if(b.isVisible()) {//only visible buttons can be pressed
				//will check if its the src if it is it will flip state(on or off)
				b.update(src);//sends the src to button b
			}
		}
	}

	/**
	 * clears all buttons states to false
	 */
	public static void clear() {
		for(Button b: buttonList) {//runs through all buttons
			b.setButtonState(false);//sets state to false
		}
	}
	
	/**
	 * if a button with tag tag is clicked it will return true
	 * else false
	 */
	public static boolean isTypeTagActive(String tag) {
		for(Button b: buttonList) {//runs through all buttons
			if(b.getTag().equals(tag)) {//button has same tag
				//if button is on return true
				if(b.getButtonState()) {
					return true;
				}
			}
		}
		return false;//if no button with tag tag is on
	}
	
	/**
	 * returns the button with title tile and tag tag
	 */
	public static Button getButton(String title, String tag) {
		for(Button b: buttonList) {//runs through all buttons
			if(b.getTitle().equals(title) && b.getTag().equals(tag)) {//button has same tag and title
				return b;
			}
		}
		return null;//returns null if no button with title title and tag tag exist
	}
	
}
