package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import minesweeper.bean.Square;
import minesweeper.ProblemManager;

public class MineSweeperUIFrame extends JFrame {

	private JPanel contentPane;
	private Timer objTimer;
	private MineFieldButton mines[][];
	private ProblemManager problemManager;
	private JLabel noOfMinesLabel;
	/**
	 * Create the frame.
	 */
	public MineSweeperUIFrame(ProblemManager problemManager) {
		this.problemManager =problemManager;
		Square[][] arrSquares =problemManager.getSquares();
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int rows = arrSquares.length;
		int cols = arrSquares[0].length;
		mines = new MineFieldButton[rows][cols];

		setBounds(100, 100, 100 + rows * 50, cols * 25 + 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel topPanel = new JPanel();
		topPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		contentPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1, 3, 4, 0));

		noOfMinesLabel = new JLabel("No Of Mines : " +problemManager.getNoOfMines());
		topPanel.add(noOfMinesLabel);

		JPanel mineFieldPanel = new JPanel();
		contentPane.add(mineFieldPanel, BorderLayout.CENTER);
		mineFieldPanel.setLayout(new GridLayout(arrSquares.length, arrSquares[0].length));

		for(int i=0; i< arrSquares.length; i++){
			for(int j=0; j<arrSquares[i].length; j++){
				MineFieldButton btnNewButton = new MineFieldButton();
				mineFieldPanel.add(btnNewButton);
				if(problemManager.containsMine(i, j)){
					btnNewButton.setBackground(Color.RED);
				}
				mines[i][j] = btnNewButton;
				mines[i][j].setAssociatedSquare(arrSquares[i][j]);

				btnNewButton.addActionListener(new MineActionListener());
			}
		}

		final JLabel timer = new JLabel("");
		timer.setHorizontalAlignment(SwingConstants.RIGHT);
		topPanel.add(timer);

		objTimer = new Timer(1000, new ActionListener() {
			int count = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = "Time : ";
				if(count  > 3600 ){
					text += new Integer(count / 3600 ).toString() +"h : " +new Integer((count % 3600   ) / 60 ).toString()
							+"m : " +new Integer( (count % 3600) % 60).toString() +"s";
				}else if (count > 60 ){
					text += new Integer(count  / 60 ).toString()
							+"m : " +new Integer( count % 60).toString() +"s";
				}else {
					text += new Integer(count) +"s";
				}
				timer.setText(text);
				count++;
			}
		});
		objTimer.start();
	}

	public MineFieldButton[][] getMines(){
		return mines;
	}

	public void disableSquare(int x, int y){
		mines[x][y].setEnabled(false);
		mines[x][y].getAssociatedSquare().setEnabled(false);
	}
	
	public void gameEnded(boolean won){
		objTimer.stop();
		if (won){
			new JDialog(this, "YOU WON!!!").setVisible(true); 
		}else{
			new JDialog(this, "U R DEAD BUDDY!!! SORRY !!! ").setVisible(true);
		}
	}
	
	public void startTimer(){
		objTimer.start();
	}

	@Override
	public void repaint(){		
		noOfMinesLabel.setText("No of Mines : " +ProblemManager.noOfMines);
		for(int i = 0 ; i < mines.length; i++){
			for(int j = 0 ; j < mines[0].length; j++){
				Square associatedSquare = mines[i][j].getAssociatedSquare();
				if(associatedSquare.getNoOfMinesAround() > 0 && 
						!problemManager.containsMine(associatedSquare.getLocX(),associatedSquare.getLocY())){
					mines[i][j].setText(new Integer(associatedSquare.getNoOfMinesAround()).toString());
					mines[i][j].getAssociatedSquare().setEnabled(false);
					mines[i][j].setEnabled(false);
				}else{
					//do nothing.
				}
				if(associatedSquare.isMarked()){
					mines[i][j].setBackground(Color.GREEN);
				}else if (!associatedSquare.isEnabled()){
					mines[i][j].setBackground(Color.BLUE);
				}
				mines[i][j].setEnabled(associatedSquare.isEnabled());
			}
		}
		super.repaint();
	}

}
