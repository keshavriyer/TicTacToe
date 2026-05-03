package tictactoe;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
        gc.clearRect(0, 0, 900, 900);
        gc.setLineWidth(2);
        gc.strokeLine(300, 0, 300, 900);
        gc.strokeLine(600, 0, 600, 900);
        gc.strokeLine(0, 300, 900, 300);
        gc.strokeLine(0, 600, 900, 600);
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
        double x = col * 300 + 60;
        double y = row * 300 + 60;
        gc.strokeLine(x, y, x + 180, y + 180);
        gc.strokeLine(x + 180, y, x, y + 180);

    }
    @FXML
    private void drawO(int row, int col) {
        gc.strokeOval(col * 300 + 60, row * 300 + 60, 180, 180);
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
        gc.setFont(Font.font("Verdana", 100)); 
        gc.setStroke(Color.BLUE);
        if(oWins()){
            game = false;
            gc.strokeText("Player O wins", 100, 450);
            gc.setFont(Font.font("Verdana", 30));
            gc.strokeText("Click anywhere for a new game", 200, 500);
        }
        else if(xWins()){
            game=false;
            gc.strokeText("Player X wins", 100, 450);
            gc.setFont(Font.font("Verdana", 30));
            gc.strokeText("Click anywhere for a new game", 200, 500);
        }
        else if(isFull()){
            game=false;
            gc.strokeText("Game is a Tie", 100, 450);
            gc.setFont(Font.font("Verdana", 30));
            gc.strokeText("Click anywhere for a new game", 200, 500);
        }
        gc.setStroke(Color.BLACK);

    }
}