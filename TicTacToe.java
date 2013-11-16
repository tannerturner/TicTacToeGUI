
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
	private JLabel message;
	private JButton[][] squares;
	private MouseListener msListener=new MouseListener();
	private char player;
	private static int turns=0;
	
	public TicTacToe() {
		super("TTT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel north=new JPanel();
		message=new JLabel("X GOES FIRST!");
		player=message.getText().charAt(0);
		message.addMouseListener(msListener);	//let user click text to change which player starts
		north.add(message);
		add(north, BorderLayout.NORTH);
		
		JPanel board=new JPanel(new GridLayout(3,3));
		squares=new JButton[3][3];
		for(int i=0; i<squares.length; i++) {
			for(int j=0; j<squares[i].length; j++) {
				squares[i][j]=new JButton();
				squares[i][j].setPreferredSize(new Dimension(50,50));
				squares[i][j].addActionListener(this);
				squares[i][j].setActionCommand(i+""+j);
				board.add(squares[i][j]);
			}
		}
		add(board, BorderLayout.CENTER);
		
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(turns==0)
			message.removeMouseListener(msListener);
			//disallows player to change whose turn it is after game starts
		turns++;
		
		String source=e.getActionCommand();
		int x=Integer.parseInt(source.substring(0,1)),
			y=Integer.parseInt(source.substring(1,2));
		//finds and logs what position button is in
		squares[x][y].removeActionListener(this);
		//disallows button just pressed from firing another event

		squares[x][y].setText(String.valueOf(player));
		if(wins(player, x, y)) {						//If winner, stop
			message.setText(player+" WINS!");
			for(int i=0; i<squares.length; i++) {
				for(int j=0; j<squares[i].length; j++) {
					squares[i][j].removeActionListener(this);
					//disallows any unpressed buttons from firing events
				}
			} return;
		} else {						//else if no winner and draw, stop
			if(turns==9) {
				message.setText("CAT'S GAME!");
				return;
			}
		}
		
		if(player=='X') {
			message.setText("O'S TURN!");
			player='O';
		} else {
			message.setText("X'S TURN!");
			player='X';
		}
		//changes player
	}
	
	public boolean wins(char who, int row, int col) {
		if(turns<5)	//can't have a winner with only 4 turns!
			return false;
		else {
			char[][] choices=new char[3][3];
			for(int i=0; i<squares.length; i++) {
				for(int j=0; j<squares[i].length; j++) {
					if(!squares[i][j].getText().equals(""))
						choices[i][j]=squares[i][j].getText().charAt(0);
						//see and store characters from buttons (if pressed)
				}
			}
			
			return (  (choices[row][0] == who	// 3-in-the-row
	                && choices[row][1] == who
	                && choices[row][2] == who)
	                ||(choices[0][col] == who	// 3-in-the-column
	                && choices[1][col] == who
	                && choices[2][col] == who)
	                ||(row == col            	// 3-in-the-diagonal
	                && choices[0][0] == who
	                && choices[1][1] == who
	                && choices[2][2] == who)
	                ||(row + col == 2  			// 3-in-the-opposite-diagonal
	                && choices[0][2] == who
	                && choices[1][1] == who
	                && choices[2][0] == who));
		}
	}
	
	private class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if(player=='X') {
				message.setText("O GOES FIRST!");
				player='O';
			} else if(player=='O'){
				message.setText("X GOES FIRST!");
				player='X';
			}
			//changes first player if text is clicked
		}
	}

	public static void main(String[] args) {
		new TicTacToe();
	}
}
