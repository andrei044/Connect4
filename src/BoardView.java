import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.http.HttpResponse;

public class BoardView extends JFrame{
    private JLabel[] cell=new JLabel[42];
    private JButton resetBtn=new JButton("Reset");
    private JButton exitBtn=new JButton("Exit");
    private JLabel turnLabel=new JLabel("Who's turn?");
    private JLabel turnImgLabel=new JLabel();
    private JLabel statusLabel=new JLabel();
    private JLabel animatedPiece=new JLabel();
    private Board boardModel;
    Timer t=new Timer(10,null);
    private ImageIcon board_cell;
    private ImageIcon board_cell_yellow;
    private ImageIcon board_cell_red;
    private ImageIcon yellow_chip;
    private ImageIcon red_chip;
    public BoardView(Board newBoard) {
        t.start();
        resetBtn.setPreferredSize(new Dimension(100,50));
        exitBtn.setPreferredSize(new Dimension(100,50));
        statusLabel.setPreferredSize(new Dimension(300,50));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Dialog", Font.PLAIN, 30));
        boardModel=newBoard;
        this.setPreferredSize(new Dimension(722,760));
        this.setResizable(false);
        JPanel board_grid=new JPanel();
        JPanel content=new JPanel();
        JPanel menu=new JPanel();
        JPanel menu_left=new JPanel();
        JPanel menu_right=new JPanel();
        JPanel menu_middle=new JPanel();
        board_grid.setLayout(new GridLayout(6,7));
        //ImageIcon yellow_chip=new ImageIcon("resources/board_cell.png");
        //Image img= yellow_chip.getImage();
        //Image imgScaled=img.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        //ImageIcon iconScaled=new ImageIcon(imgScaled);
        statusLabel.setText("Game In Progress");
        board_cell=new ImageIcon("resources/board_cell.png");
        board_cell=scaleImage(board_cell,100,100);
        board_cell_yellow=new ImageIcon("resources/board_cell_yellow.png");
        board_cell_yellow=scaleImage(board_cell_yellow,100,100);
        board_cell_red=new ImageIcon("resources/board_cell_red.png");
        board_cell_red=scaleImage(board_cell_red,100,100);
        red_chip=new ImageIcon("resources/ChipRed.png");
        red_chip=scaleImage(red_chip,100,100);
        yellow_chip=new ImageIcon("resources/ChipYellow.png");
        yellow_chip=scaleImage(yellow_chip,100,100);
        for(int i=0;i<42;i++){
            cell[i]=new JLabel("",SwingConstants.CENTER);
            cell[i].setIcon(board_cell);
            board_grid.add(cell[i]);
            //cell[i].setBorder(BorderFactory.createLineBorder(Color.black));
        }
        turnImgLabel.setIcon(yellow_chip);
//        menu.add(turnLabel);
//        menu.add(turnImgLabel);
//        menu.add(statusLabel);
//        menu.add(resetBtn);
//        menu.add(exitBtn);
        //Border etched = BorderFactory.createEtchedBorder();
        menu_left.add(turnLabel);
        menu_left.add(turnImgLabel);
        menu_left.setLayout(new FlowLayout(FlowLayout.LEFT));
        //menu_left.setBorder(BorderFactory.createTitledBorder(etched));
        JPanel buttons=new JPanel();
        //exitBtn.setPreferredSize(new Dimension(200,90));
        //resetBtn.setPreferredSize(new Dimension(200,90));

        buttons.add(resetBtn);
        buttons.add(exitBtn);

        buttons.setLayout(new GridLayout(2,1));

        //buttons.setBorder(BorderFactory.createTitledBorder(etched));
        //menu_right.add(resetBtn);
        //menu_right.add(exitBtn);
        menu_right.add(buttons);
        menu_right.setLayout(new FlowLayout(FlowLayout.RIGHT));


        statusLabel.setSize(100,100);
        menu_middle.add(statusLabel);
        menu_middle.setLayout(new FlowLayout(FlowLayout.CENTER));
        //menu_middle.setBorder(BorderFactory.createTitledBorder(etched));


        //menu.setBorder(BorderFactory.createTitledBorder(etched));
        menu.add(menu_left);
        menu.add(menu_middle);
        menu.add(menu_right);




        content.add(board_grid);
        content.add(menu);
        //content.setBorder(BorderFactory.createLineBorder(Color.green));
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));


        JPanel content1=new JPanel();
        content1.add(animatedPiece);
        animatedPiece.setVisible(false);
        content1.add(content);
        SpringLayout springLayout=new SpringLayout();
        content1.setLayout(springLayout);
        springLayout.putConstraint(SpringLayout.WEST,animatedPiece,-1000,SpringLayout.WEST,content1);

        this.setContentPane(content1);
        this.pack();
        this.setTitle("Connect4-by Andrei Constantin");


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void printCellCoord(){
        for(int i=0;i<6;i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print("(" + cell[((6*i)+j+i)].getX() + "," + cell[((6*i)+j+i)].getY() + ") ");
            }
            System.out.print("\n");
        }
    }
    public Point getCell(int x,int y){
        //printCellCoord();
        int xAux=cell[0].getWidth();
        int yAux=cell[0].getHeight();
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                int cx=cell[((6*i)+j+i)].getX();
                int cy=cell[((6*i)+j+i)].getY();
                if(x>=cx && x<cx+xAux && y>=cy && y<cy+yAux){
                    System.out.println(cx+" "+cy+' '+(cx+xAux)+' '+(cy+yAux));
                    return new Point(i,j);
                }

            }
        }
        return new Point(-1,-1);
    }
    public void setCell(int i,int j,boolean color){
        turnImgLabel.setIcon(board_cell_red);
        if(color==false){
            cell[((6*i)+j+i)].setIcon(board_cell_yellow);
            turnImgLabel.setIcon(red_chip);
        }

        else{
            cell[((6*i)+j+i)].setIcon(board_cell_red);
            turnImgLabel.setIcon(yellow_chip);
        }

    }

    private ImageIcon scaleImage(ImageIcon imageIcon,int x,int y){
        Image img= imageIcon.getImage();
        Image imgScaled=img.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        ImageIcon iconScaled=new ImageIcon(imgScaled);
        return iconScaled;
    }

    public void addExitListener(ActionListener actionListener){
        exitBtn.addActionListener(actionListener);
    }

    public void addResetListener(ActionListener actionListener){
        resetBtn.addActionListener(actionListener);
    }

    public void addTimerListener(ActionListener actionListener){
        t.addActionListener(actionListener);
    }
    public void setStatus(int newStatus){
        if(newStatus==0)
            statusLabel.setText("Game In Progress");
        else if(newStatus==1){
            statusLabel.setText("Draw");
            JOptionPane.showMessageDialog(this,
                    "Draw.",
                    "Game status",
                    JOptionPane.PLAIN_MESSAGE);
        }

        else if(newStatus==2){
            statusLabel.setText("Yellow is the Winner");
            JOptionPane.showMessageDialog(this,
                    "Yellow is the Winner.",
                    "Game status",
                    JOptionPane.PLAIN_MESSAGE);
        }

        else{
            statusLabel.setText("Red is the Winner");
            JOptionPane.showMessageDialog(this,
                    "Red is the Winner.",
                    "Game status",
                    JOptionPane.PLAIN_MESSAGE);
        }

    }
    public void reset(){
        turnImgLabel.setIcon(yellow_chip);
        statusLabel.setText("Game In Progress");
        for(int i=0;i<6;i++) {
            for (int j = 0; j < 7; j++) {
                cell[((6*i)+j+i)].setIcon(board_cell);
            }
        }
    }
    public void setAnimated(int i,int j,boolean turn){
        if(!turn) animatedPiece.setIcon(yellow_chip);
        else animatedPiece.setIcon(red_chip);
        animatedPiece.setVisible(true);
        System.out.println("start cell "+cell[((6*i)+j+i)].getX()+" "+-100);
        //animatedPiece.setLocation(cell[((6*i)+j+i)].getX(),-100);

        //System.out.println("start cell animated "+animatedPiece.getX()+" "+animatedPiece.getY());
    }
    public void hideAnimated(){
        animatedPiece.setVisible(false);
    }
    public int playAnimation(int i,int j){

        animatedPiece.setLocation(cell[((6*i)+j+i)].getX(),animatedPiece.getY()+10);

        //System.out.println("animate cell "+animatedPiece.getX()+" "+animatedPiece.getY());
        if(animatedPiece.getY()>=cell[((6*(5-i))+j+5-i)].getY()){
            System.out.println("stop cell "+animatedPiece.getX()+" "+animatedPiece.getY());
            return 1;
        }

        return 0;
    }
}
