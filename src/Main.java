import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board x=new Board();
        BoardView view=new BoardView(x);
        BoardController controller=new BoardController(x,view);
        view.setVisible(true);
    }
}