package ui;

import ui.MineFieldButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineActionListener implements ActionListener {

	public MineActionListener() {
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object srcObj = evt.getSource();
		if(srcObj instanceof MineFieldButton){
			MineFieldButton srcButton = (MineFieldButton)srcObj;
			System.out.println("Button Clicked!!! ");
			/*if(srcButton.getAssociatedSquare().isContainsMine()){
				System.out.println("KABOOOM!!!! KABOOOM!!!! KABOOOM!!!! U R KILLED...");
			}*/
		}else{
			System.err.println("MineActionListener Action Listener works only for Buttons of type MineFieldButton");
		}
		
	}

}
