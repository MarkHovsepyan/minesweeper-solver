package ui;

import java.awt.BorderLayout;

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

import minesweeper.Tile;
import minesweeper.levelCreator;

public class GameUI extends JFrame {

	private JPanel contentPane;
	private Timer objTimer;
	private TileButton mines[][];
	private levelCreator levelCreator;
	private JLabel noOfMinesLabel;


	public GameUI(levelCreator levelCreator) {
		this.levelCreator = levelCreator;
		Tile[][] tileArray = levelCreator.getTiles();
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int rows = tileArray.length;
		int cols = tileArray[0].length;
		mines = new TileButton[rows][cols];

		setBounds(100, 100, 100 + rows * 50, cols * 25 + 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel topPanel = new JPanel();
		topPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		contentPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new GridLayout(1, 3, 4, 0));

		noOfMinesLabel = new JLabel("No Of Mines : " + levelCreator.getNoOfMines());
		topPanel.add(noOfMinesLabel);

		JPanel mineFieldPanel = new JPanel();
		contentPane.add(mineFieldPanel, BorderLayout.CENTER);
		mineFieldPanel.setLayout(new GridLayout(tileArray.length, tileArray[0].length));

		for(int i=0; i< tileArray.length; i++){
			for(int j=0; j<tileArray[i].length; j++){
				TileButton btnNewButton = new TileButton();
				mineFieldPanel.add(btnNewButton);
				if(levelCreator.containsMine(i, j)){
					btnNewButton.setBackground(Color.RED);
				}
				mines[i][j] = btnNewButton;
				mines[i][j].setAssociatedTile(tileArray[i][j]);
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
					text += Integer.toString(count / 3600) +"h : " + Integer.toString((count % 3600) / 60)
							+"m : " + Integer.toString((count % 3600) % 60) +"s";
				}else if (count > 60 ){
					text += Integer.toString(count / 60)
							+"m : " + Integer.toString(count % 60) +"s";
				}else {
					text += count +"s";
				}
				timer.setText(text);
				count++;
			}
		});
		objTimer.start();
	}

	public TileButton[][] getMines(){
		return mines;
	}

	public void disableSquare(int x, int y){
		mines[x][y].setEnabled(false);
		mines[x][y].getAssociatedTile().setEnabled(false);
	}

	public void gameEnded(boolean won){
		objTimer.stop();
		if (won){
			new JDialog(this, "Success!!!").setVisible(true);
		}else{
			new JDialog(this, "Fail!!!").setVisible(true);
		}
	}
	
	public void startTimer(){
		objTimer.start();
	}

	@Override
	public void repaint(){		
		noOfMinesLabel.setText("No of Mines : " + levelCreator.noOfMines);
		for(int i = 0 ; i < mines.length; i++){
			for(int j = 0 ; j < mines[0].length; j++){
				Tile associatedTile = mines[i][j].getAssociatedTile();
				if(associatedTile.getNoOfMinesAround() > 0 &&
						!levelCreator.containsMine(associatedTile.getLocX(),associatedTile.getLocY())){
					mines[i][j].setText(Integer.toString(associatedTile.getNoOfMinesAround()));
					mines[i][j].getAssociatedTile().setEnabled(false);
					mines[i][j].setEnabled(false);
				}

				if(associatedTile.isMarked()){
					mines[i][j].setBackground(Color.GREEN);
				}else if (!associatedTile.isEnabled()){
					mines[i][j].setBackground(Color.GRAY);
				}
				mines[i][j].setEnabled(associatedTile.isEnabled());
			}
		}
		super.repaint();
	}
}
