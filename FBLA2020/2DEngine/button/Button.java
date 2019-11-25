package button;

import java.awt.Color;

import javax.swing.JButton;

public class Button {
	
	//class is used to simplify button handling
	//button mechanics are the same a Jbutton
	//but interface is cleaned up for easy use
	
	//stores button as the Jbutton
	protected JButton button;
	private boolean buttonState;//if button is clicked or not
	private String title, tag;//Characteristics of button
	
	 Color textColor = Color.decode("#000000");
	 Color backgroundColor = Color.decode("#ffffff");
	 Color hoverColor = Color.decode("#ffffff");
	
	
	/**
	 * creates a button at (x, y) -> (canvas orientation) -> center of the button
	 * and with dimensions of (width, height)
	 * the button will have title on it
	 * set the set of button with specific tag
	 * Visibility initially
	 */
	public Button(int x, int y, int width, int height, String title, boolean visablity, String tag){	
		this.button = new JButton();
		this.title = title;
		this.tag = tag;
		x -= width/2;
		y += height/2;
		button.setBounds(x, y, width, height);
		button.setText(title);
		button.setVisible(visablity);
		buttonState = false;
		
		button.setFocusPainted(false);
		button.setBorder(null);
		button.setBorderPainted(true);
		button.setForeground(textColor);
	    button.setBackground(backgroundColor);
	}
	
	//Updates the visibility of button
	public void visibilityState(boolean b){
		button.setVisible(b);//visibility is change to b
	}
	
	/*
	 * returns the visibility state of button
	 */
	public boolean isVisible() {
		return button.isVisible();//visibility is stored in j-button
	}
	
	//returns the tag of the button
	public String getTag() {
		//the tag can be used to clasify the button
		return tag;
	}
	
	//gets the state of the button
	public boolean getButtonState() {
		//tells weather the button is clicked or not at that instant
		return buttonState;
	}
	
	//update the button's state
	public void setButtonState(boolean buttonState) {
		//if button was clicked method can be used to turn state to false
		this.buttonState = buttonState;
	}
	
	/**
	 * return@ the j button
	 */
	public JButton getButton() {
		return button;
	}
	
	//gets the title of button
	public String getTitle() {
		//can be used to clasify button
		return title;
	}
	
	//updates button to check weather is has been clicked or not
	public void update(Object src){
		if(src == button){//if this button is clicked
			//the state is flipped
			buttonState = !buttonState;			
		}
	}
}