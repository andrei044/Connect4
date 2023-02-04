import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardController {
    private Board boardModel;
    private BoardView boardView;
    private boolean stop;
    private boolean animate;
    private int lastClickedX=0;
    private int lastClickedY=0;
    private int lastColumn=-1;
    private int lastRow=-1;

    public BoardController(Board boardModel, BoardView boardView) {
        stop=false;
        animate=false;
        this.boardModel = boardModel;
        this.boardView = boardView;
        boardView.addExitListener(new exitListener());
        boardView.addResetListener(new resetListener());
        boardView.addTimerListener(new timerListener());
        boardView.addMouseListener(new BoardViewMouserListener());
    }



    class exitListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    class resetListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            stop=false;
            boardModel.resetBoard();
            boardView.reset();
        }
    }

    class timerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(animate){
                if(boardView.playAnimation(lastRow,lastColumn)==1){
                    animate=false;
                    boardView.hideAnimated();
                    boardView.setCell(5-lastRow,lastColumn,!boardModel.isTurn());
                    System.out.println("Animation stop");
                }
            }
        }
    }

    class BoardViewMouserListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(!stop && !animate){
                lastClickedX = e.getX();
                lastClickedY = e.getY();
                Point poz=boardView.getCell(lastClickedX,lastClickedY);
                System.out.println(poz.x+" "+poz.y);
                System.out.println(lastClickedX+" "+lastClickedY+'\n');
                if(poz.y!=-1){
                    int R=boardModel.getFirstFreeCell(poz.y);
                    if(R!=-1){
                        boolean turn=boardModel.isTurn();
                        boardView.setAnimated(R,poz.y,turn);
                        animate=true;
                        System.out.println("Animation start");
                        lastColumn=poz.y;
                        lastRow=R;
                        boardModel.putPiece(R,poz.y);
                        //boardView.setCell(5-R,poz.y,turn);
                        if( boardModel.checkWin(R,poz.y)==0){
                            stop=true;
                            boardView.setStatus(1);
                        }else if(boardModel.checkWin(R,poz.y)==1){
                            stop=true;
                            if(turn)
                                boardView.setStatus(3);
                            else
                                boardView.setStatus(2);
                        }
                    }
                }
                System.out.println(boardModel.toString());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
