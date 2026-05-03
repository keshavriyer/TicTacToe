package tictactoe;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Controller {

    @FXML
    private Canvas canvas;
    GraphicsContext gc;
    boolean turn;
    int[][] matrix = new int[3][3];
    boolean game;
    
    @FXML
    public void initialize() {

        resetGame();
        
        
    }
    public void resetGame(){
        turn=true;
        game=true;
        
        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.WHITE);
        
        gc.setLineWidth(5);
        gc.strokeLine(canvas.getWidth()/3, 0, canvas.getWidth()/3, canvas.getHeight());
        gc.strokeLine(canvas.getWidth()*2/3, 0, canvas.getWidth()*2/3, canvas.getHeight());
        gc.strokeLine(0, canvas.getHeight()/3, canvas.getWidth(), canvas.getHeight()/3);
        gc.strokeLine(0, canvas.getHeight()*2/3, canvas.getWidth(), canvas.getHeight()*2/3);
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                matrix[i][j]=-1;
            }
        }
        canvas.setOnMouseClicked(event -> {
            if(game){
                
                double mouseX = event.getX();
                double mouseY = event.getY();
                handleMove(mouseX, mouseY);
                
                
            }
            else{
                resetGame();
            }
            
        });
    }


    private void handleMove(double x, double y) {
        int col = (int) (x / (canvas.getWidth() / 3));
        int row = (int) (y / (canvas.getHeight() / 3));

    
        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
            processClick(row, col);
        }
    }

    public void processClick(int row,int col){
        if(matrix[row][col]!=-1){
            return;
        }
        if(turn){
            drawX(row, col);
            turn=false;
            matrix[row][col]=1;
            checkWin();
        }
        else{
            drawO(row, col);
            turn=true;
            matrix[row][col]=0;
            checkWin();
        }
        
    }

    @FXML
    public void drawX(int row,int col){
        double x = col * canvas.getWidth()/3 + canvas.getWidth()/15;
        double y = row * canvas.getHeight()/3 + canvas.getHeight()/15;
        gc.strokeLine(x, y, x + canvas.getWidth()/5, y + canvas.getHeight()/5);
        gc.strokeLine(x + canvas.getWidth()/5, y, x, y + canvas.getHeight()/5);

    }
    @FXML
    private void drawO(int row, int col) {
        gc.strokeOval(col * canvas.getWidth()/3 + canvas.getWidth()/15, row * canvas.getHeight()/3 + canvas.getHeight()/15, canvas.getWidth()/5, canvas.getHeight()/5);
        
    }

    private boolean isFull(){
        boolean full = true;
        for(int i = 0;i<3;i++){
            for(int j = 0;j<3;j++){
                if(matrix[i][j]==-1){
                    full=false;
                }
            }
        }
        return full;
    }
    private boolean oWins(){
        for(int i = 0;i<3;i++){
            if(matrix[i][0]==matrix[i][1]&&matrix[i][1]==matrix[i][2]&&matrix[i][0]==0){
                return true;
            }
            if(matrix[0][i]==matrix[1][i]&&matrix[1][i]==matrix[2][i]&&matrix[1][i]==0){
                return true;
            }
        }
        if(matrix[0][0]==matrix[1][1]&&matrix[1][1]==matrix[2][2]&&matrix[0][0]==0){
            return true;
        }
        if(matrix[0][2]==matrix[1][1]&&matrix[2][0]==matrix[1][1]&&matrix[0][2]==0){
            return true;
        }
        return false;
    }
    private boolean xWins(){
        for(int i = 0;i<3;i++){
            if(matrix[i][0]==matrix[i][1]&&matrix[i][1]==matrix[i][2]&&matrix[i][0]==1){
                return true;
            }
            if(matrix[0][i]==matrix[1][i]&&matrix[1][i]==matrix[2][i]&&matrix[1][i]==1){
                return true;
            }
        }
        if(matrix[0][0]==matrix[1][1]&&matrix[1][1]==matrix[2][2]&&matrix[0][0]==1){
            return true;
        }
        if(matrix[0][2]==matrix[1][1]&&matrix[2][0]==matrix[1][1]&&matrix[0][2]==1){
            return true;
        }
        return false;
    }



    public void checkWin(){
        gc.setFont(Font.font("Verdana", canvas.getHeight()/9));
        gc.setTextAlign(TextAlignment.CENTER); 
        gc.setFill(Color.YELLOW);
        if(oWins()){
            game = false;
            gc.fillText("Player O wins", canvas.getWidth()/2, canvas.getHeight()/2);
            gc.setFont(Font.font("Verdana", canvas.getHeight()/30));
            gc.fillText("Click anywhere for a new game", canvas.getWidth()/2, canvas.getHeight()*5/9);
        }
        else if(xWins()){
            game=false;
            gc.fillText("Player X wins", canvas.getWidth()/2, canvas.getHeight()/2);
            gc.setFont(Font.font("Verdana", canvas.getHeight()/30));
            gc.fillText("Click anywhere for a new game", canvas.getWidth()/2, canvas.getHeight()*5/9);
        }
        else if(isFull()){
            game=false;
            gc.fillText("Game is a Tie", canvas.getWidth()/2, canvas.getHeight()/2);
            gc.setFont(Font.font("Verdana", canvas.getHeight()/30));
            gc.fillText("Click anywhere for a new game", canvas.getWidth()/2, canvas.getHeight()*5/9);
        }
        gc.setStroke(Color.WHITE);

    }
}
