import javax.lang.model.type.MirroredTypeException;
import java.util.concurrent.ThreadLocalRandom;

public class Model {
    public static Tile grid[][] = new Tile[3][3];
    public boolean player1Turn = true;
    public static  int gameCount = 0;

    //all additional attributes for level 1
    public static char pastMove = '.';
    public static char config = '.';
    public static char config1 = '.';
    public static int configInt1 = -1;
    public static int configInt2 = -1;
    public static boolean end = false;

    public void level0(){
        int tileCode = 0;
        int min = 1;
        int max = 8;
        boolean found = false;

        while (!found){
            tileCode = ThreadLocalRandom.current().nextInt(min, max + 1); // generate number from 1-8
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i][j].getCode() == tileCode){
                        if (grid[i][j].getType().equalsIgnoreCase("blank")){
                            if (gameCount%2 != 0){
                                grid[i][j].drawO();
                            }
                            else {
                                grid[i][j].drawX();
                            }
                            found = true;
                        }
                    }
                }
            }
        }
    }

    public void setPastMove(int x, int y){
       switch(x){
           case 0:
               switch (y){
                   case 0:
                       pastMove = 'a';
                       break;
                   case 1:
                       pastMove = 'b';
                       break;
                   case 2:
                       pastMove = 'c';
                       break;
               }
               break;
           case 1:
               switch (y){
                   case 0:
                       pastMove = 'd';
                       break;
                   case 1:
                       pastMove = 'e';
                       break;
                   case 2:
                       pastMove = 'f';
                       break;
               }
               break;
           case 2:
               switch (y){
                   case 0:
                       pastMove = 'g';
                       break;
                   case 1:
                       pastMove = 'h';
                       break;
                   case 2:
                       pastMove = 'i';
                       break;
               }
               break;
       }
    }

    public void level1XAi(int moveCount){
        if(!end) {
            switch (moveCount) {
                case 0:
                    grid[0][0].drawX();
                    break;
                case 2:
                    //Player moves : a
                    switch (pastMove) {//CHECKS WHAT SET OF MOVES WILL DO BASED ON PLAYER'S FIRST MOVE
                        case 'b':
                        case 'f'://SURE WIN
                            grid[2][2].drawX();
                            config = 'b';
                            break;
                        case 'd':
                        case 'h'://SURE WIN
                            grid[1][1].drawX();
                            config = 'd';
                            break;
                    }
                    break;
                case 4:
                    switch (config) {
                        case 'b':
                        case 'f'://SURE WIN
                            if (grid[1][1].getType() == "blank") {//FINISHING MOVE
                                grid[1][1].drawX();
                                end = true;
                            }
                            else
                                grid[2][2].drawX();
                            break;
                        case 'd':
                        case 'h'://SURE WIN
                            if(grid[2][2].getType() == "blank"){
                                grid[2][2].drawX();
                                end = true;
                            }
                            else
                                grid[0][2].drawX();
                            break;

                    }
                    break;
            }
        }
    }

    public void level1OAI(int moveCount){
        System.out.println("LEVEL10AI FUNCTION ENTERED");
        switch (moveCount){
            case 1:
                switch (pastMove){
                    case 'e'://DRAW, PLAYER TAKES CENTER, GET CORNER
                        grid[0][0].drawO();
                        config = pastMove;
                        break;
                    case 'a' : case 'c': case 'g': case 'i'://DRAW OR POTENTIAL LOSS, PLAYER TAKES CORNER, GET CENTER
                        grid[1][1].drawO();
                        config = pastMove;
                        break;
                    default://POTENTIAL WIN, PLAYER TAKES SIDE, GET CENTER
                        grid[1][1].drawO();
                        config = pastMove;
                }
                break;
            case 3:
                switch(config){
                    case 'e':
                        if (grid[0][1].getType().equalsIgnoreCase("X")){// UP TILE
                            configInt1 = 1;
                            grid[2][1].drawO();
                        }
                        else if (grid[1][0].getType().equalsIgnoreCase("X")){ // LEFT TILE
                            configInt1 = 2;
                            grid[1][2].drawO();
                        }
                        else if (grid[1][2].getType().equalsIgnoreCase("X")){ //RIGHT TILE
                            configInt1 = 3;
                            grid[1][0].drawO();
                        }
                        else if (grid[2][1].getType().equalsIgnoreCase("X")){//DOWN TILE
                            configInt1 = 4;
                            grid[0][1].drawO();
                        }
                        else if (grid[0][2].getType().equalsIgnoreCase("X")){//TOP RIGHT
                            configInt1 = 5;
                            grid[2][0].drawO();
                        }
                        else if (grid[2][0].getType().equalsIgnoreCase("X")){//BOTTOM LEFT
                            configInt1 = 6;
                            grid[0][2].drawO();
                        }
                        else if (grid[2][2].getType().equalsIgnoreCase("X")){//BOTTOM RIGHT
                            configInt1 = 7;
                            grid[0][2].drawO();
                        }

                        break;
                    case 'a': case 'c': case 'g': case 'i':
                        if (config == 'c'){
                            rotateMatrix(3);
                        }
                        else if(config == 'i'){
                            rotateMatrix(2);
                        }
                        else if(config == 'g') {
                            rotateMatrix(1);
                        }
                        if (grid[0][1].getType().equalsIgnoreCase("X")){//UP TILE PLAYER MOVE
                            configInt1 = 1;
                            grid[0][2].drawO();
                        }
                        else if(grid[2][1].getType().equalsIgnoreCase("X")){//BOTTOM TILE PLAYER MOVE
                            configInt1 = 2;
                            grid[2][0].drawO();
                        }
                        else if(grid[0][2].getType().equalsIgnoreCase("X")){//UPPER RIGHT CORNER MOVE
                            configInt1 = 3;
                            grid[0][1].drawO();
                        }
                        else if(grid[1][0].getType().equalsIgnoreCase("X")){//ROTATED MIRRORED VERSION OF configInt 1 (LEFT TILE)
                            configInt1 = 4;
                            grid[2][0].drawO();
                        }
                        else if(grid[1][2].getType().equalsIgnoreCase("X")){//ROTATED MIRRORED VERSION OF configInt 2 (RIGHT TILE)
                            configInt1 = 5;
                            grid[0][2].drawO();
                        }
                        else if(grid[2][0].getType().equalsIgnoreCase("X")){//ROTATED MIRRORED VERSION OF config 3 (BOTTOM LEFT CORNER)
                            configInt1 = 6;
                            grid[1][0].drawO();
                        }
                        else if(grid[2][2].getType().equalsIgnoreCase("X")) {//
                            configInt1 = 7;
                            grid[1][0].drawO();
                        }

                        if (config == 'c'){
                            rotateMatrix(1);
                        }
                        else if(config == 'i'){
                            rotateMatrix(2);
                        }
                        else if(config == 'g')
                            rotateMatrix(3);
                        break;
                    case 'b': case 'd': case 'f': case 'h':
                        //ROTATE MATRIX
                        if (config == 'd')
                            rotateMatrix(1);
                        else if (config == 'h')
                            rotateMatrix(2);
                        else if (config == 'f')
                            rotateMatrix(3);

                        if(grid[0][0].getType().equalsIgnoreCase("X")) {//UPPER LEFT CORNER CORNER MOVE
                            grid[0][2].drawO();
                            configInt1 = 1;
                        }
                        else if (grid[0][2].getType().equalsIgnoreCase("X")) {//UPPER RIGHT CORNER MOVE
                            grid[0][0].drawO();
                            configInt1 = 2;
                        }
                        else if(grid[2][0].getType().equalsIgnoreCase("X")) {//BOTTOM LEFT CORNER MOVE
                            grid[0][0].drawO();
                            configInt1 = 3;
                        }
                        else if (grid[2][2].getType().equalsIgnoreCase("X")) {//BOTTOM RIGHT CORNER MOVE
                            grid[0][2].drawO();
                            configInt1 = 4;
                        }
                        else if (grid[1][0].getType().equalsIgnoreCase("X")){//LEFT SIDE MOVE
                            grid[0][0].drawO();
                            configInt1 = 5;
                        }
                        else if (grid[1][2].getType().equalsIgnoreCase("X")){//RIGHT SIDE MOVE
                            grid[0][2].drawO();
                            configInt1 = 6;
                        }
                        else if (grid[2][1].getType().equalsIgnoreCase("X")){//BOTTOM SIDE MOVE
                            grid[0][0].drawO();
                            configInt1 = 7;
                        }
                        if (config == 'd')
                            rotateMatrix(3);
                        else if (config == 'h')
                            rotateMatrix(2);
                        else if (config == 'f')
                            rotateMatrix(1);
                        break;
                }
                break;
            case 5:
                switch (config) {
                    case 'e':
                        switch (configInt1) {
                            case 2:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 1:
                                System.out.println("CASE 1 2 EMTERED");
                                if (grid[1][0].getType().equalsIgnoreCase("X")) {//LEFT TILE
                                    configInt2 = 1;
                                    grid[1][2].drawO();
                                } else if (grid[1][2].getType().equalsIgnoreCase("X")) {//RIGHT TILE
                                    configInt2 = 2;
                                    grid[1][0].drawO();
                                } else if (grid[0][2].getType().equalsIgnoreCase("X")) {//UPPER RIGHT CORNER
                                    configInt2 = 3;
                                    grid[2][0].drawO();
                                } else if (grid[2][0].getType().equalsIgnoreCase(("X"))) {//BOTTOM LEFT CORNER
                                    configInt2 = 4;
                                    grid[0][2].drawO();
                                } else if (grid[2][2].getType().equalsIgnoreCase("X")) {
                                    configInt2 = 5;
                                    grid[2][0].drawO();
                                }
                                if (configInt1 == 2) {
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 3:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 4:
                                System.out.println("CASE 3 4 EMTERED");
                                if (grid[0][2].getType().equalsIgnoreCase("blank")) {
                                    grid[0][2].drawO();
                                } else {
                                    grid[2][0].drawO();
                                }

                                if (configInt1 == 3) {
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                System.out.println("CURRENT BOARD ______");
                                printBoard();
                                break;
                            case 6:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 5:
                                System.out.println("CASE 5 6 EMTERED");
                                if (grid[1][0].getType().equalsIgnoreCase("blank")) {
                                    grid[1][0].drawO();
                                } else {
                                    grid[1][2].drawO();
                                }

                                if (configInt1 == 6) {
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 7:
                                if (grid[0][1].getType().equalsIgnoreCase("blank")) {
                                    grid[0][1].drawO();
                                } else {
                                    grid[2][1].drawO();
                                }
                                break;
                        }

                        break;
                    case 'a':
                    case 'c':
                    case 'g':
                    case 'i':
                        if (config == 'c')
                            rotateMatrix(3);
                        else if (config == 'i')
                            rotateMatrix(2);
                        else if (config == 'g')
                            rotateMatrix(1);

                        switch (configInt1) {
                            case 4:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 1:
                                if (grid[2][0].getType().equalsIgnoreCase("blank"))
                                    grid[2][0].drawO();
                                else
                                    grid[1][0].drawO();

                                if (configInt1 == 4) {
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 5:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 2:
                                if (grid[0][2].getType().equalsIgnoreCase("blank"))
                                    grid[0][2].drawO();
                                else
                                    grid[0][1].drawO();

                                if (configInt1 == 5) {
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 6:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 3:
                                if (grid[2][1].getType().equalsIgnoreCase("blank")) {
                                    grid[2][1].drawO();
                                } else {
                                    grid[1][0].drawO();
                                }

                                if (configInt1 == 6) {
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 7:
                                if (grid[1][2].getType().equalsIgnoreCase("blank")) {
                                    grid[1][2].drawO();
                                } else {
                                    grid[0][2].drawO();
                                }
                                break;
                        }
                        if (config == 'c')
                            rotateMatrix(1);
                        else if (config == 'i')
                            rotateMatrix(2);
                        else if (config == 'g')
                            rotateMatrix(3);
                        break;
                    case 'b':
                    case 'd':
                    case 'f':
                    case 'h':
                        if (config == 'd')
                            rotateMatrix(1);
                        else if (config == 'h')
                            rotateMatrix(2);
                        else if (config == 'f')
                            rotateMatrix(3);

                        switch (configInt1) {
                            case 2:
                                mirrorHorizontally();
                            case 1:
                                if (grid[2][0].getType().equalsIgnoreCase("blank")) {
                                    grid[2][0].drawO();
                                } else {
                                    grid[1][0].drawO();
                                }

                                if (configInt1 == 2)
                                    mirrorHorizontally();

                                break;
                            case 4:
                                mirrorHorizontally();
                            case 3:
                                if (grid[2][2].getType().equalsIgnoreCase("blank")) {
                                    grid[2][2].drawO();
                                } else {
                                    grid[2][1].drawO();
                                }

                                if (configInt1 == 4)
                                    mirrorHorizontally();

                                break;
                            case 6:
                                mirrorHorizontally();
                            case 5:
                                if (grid[2][2].getType().equalsIgnoreCase("blank")) {
                                    grid[2][2].drawO();
                                } else {
                                    grid[2][0].drawO();
                                }

                                if (configInt1 == 6)
                                    mirrorHorizontally();
                                break;
                            case 7:
                                if (grid[2][2].getType().equalsIgnoreCase("blank")) {
                                    grid[2][2].drawO();
                                } else {
                                    grid[2][0].drawO();
                                }
                                break;
                        }

                        if (config == 'd')
                            rotateMatrix(3);
                        else if (config == 'h')
                            rotateMatrix(2);
                        else if (config == 'f')
                            rotateMatrix(1);
                        break;
                }
                break;
            case 7:
                switch(config) {
                    case 'e':
                        switch(configInt1){
                            case 2:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 1:
                                switch(configInt2){
                                    case 1:
                                        if(grid[0][2].getType().equalsIgnoreCase("blank"))
                                            grid[0][2].drawO();
                                        else
                                            grid[2][0].drawO();
                                        break;
                                    case 2:
                                        if(grid[2][0].getType().equalsIgnoreCase("blank")){
                                            grid[2][0].drawO();
                                        }
                                        else
                                            grid[0][2].drawO();
                                        break;
                                    case 3:
                                        if(grid[1][0].getType().equalsIgnoreCase("blank")){
                                            grid[1][0].drawO();
                                        }
                                        else{
                                            grid[2][2].drawO();
                                        }
                                        break;
                                    case 4:
                                        if(grid[1][0].getType().equalsIgnoreCase("blank")){
                                            grid[1][0].drawO();
                                        }
                                        else
                                            grid[1][2].drawO();
                                        break;
                                    case 5:
                                        if(grid[1][0].getType().equalsIgnoreCase("blank"))
                                            grid[1][0].drawO();
                                        else
                                            grid[1][2].drawO();
                                        break;
                                }
                                if(configInt1 == 2){
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 3:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 4:
                                if(grid[1][0].getType().equalsIgnoreCase("blank"))
                                    grid[1][0].drawO();
                                else
                                    grid[1][2].drawO();

                                if(configInt1 == 3) {
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 6:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 5:

                                if(grid[2][1].getType().equalsIgnoreCase("blank"))
                                    grid[2][1].drawO();
                                else
                                    grid[0][1].drawO();

                                if(configInt1 == 6){
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 7:
                                if(grid[1][0].getType().equalsIgnoreCase("blank"))
                                    grid[1][0].drawO();
                                else
                                    grid[1][2].drawO();
                                break;
                        }
                        break;
                    case 'a': case 'c': case 'g': case 'i':
                        if (config == 'c')
                            rotateMatrix(3);
                        else if (config == 'i')
                            rotateMatrix(2);
                        else if (config == 'g')
                            rotateMatrix(1);


                        switch(configInt1){
                            case 4:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 1:

                                if(grid[1][2].getType().equalsIgnoreCase("blank"))
                                    grid[1][2].drawO();
                                else
                                    grid[2][1].drawO();

                                if(configInt1 == 4){
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }

                                break;
                            case 5:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 2:

                                if(grid[1][2].getType().equalsIgnoreCase("blank"))
                                    grid[1][2].drawO();
                                else
                                    grid[2][2].drawO();


                                if(configInt1 == 5){
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 6:
                                mirrorHorizontally();
                                rotateMatrix(3);
                            case 3:

                                if(grid[1][2].getType().equalsIgnoreCase("blank"))
                                    grid[1][2].drawO();
                                else
                                    grid[2][2].drawO();

                                if (configInt1 == 6){
                                    rotateMatrix(1);
                                    mirrorHorizontally();
                                }
                                break;
                            case 7:
                                if(grid[0][2].getType().equalsIgnoreCase("blank")){
                                    grid[0][2].drawO();
                                }
                                else
                                    grid[2][1].drawO();
                                break;
                        }
                        if (config == 'c')
                            rotateMatrix(1);
                        else if (config == 'i')
                            rotateMatrix(2);
                        else if (config == 'g')
                            rotateMatrix(3);
                        break;
                    case 'b':
                    case 'd':
                    case 'f':
                    case 'h':

                        if (config == 'd')
                            rotateMatrix(1);
                        else if (config == 'h')
                            rotateMatrix(2);
                        else if (config == 'f')
                            rotateMatrix(3);

                        switch (configInt1) {
                            case 2:
                                mirrorHorizontally();
                            case 1:
                                if(grid[1][2].getType().equalsIgnoreCase("blank")){
                                    grid[1][2].drawO();
                                }
                                else{
                                    grid[2][2].drawO();
                                }
                                if (configInt1 == 2)
                                    mirrorHorizontally();
                                break;
                            case 4:
                                mirrorHorizontally();
                            case 3:
                                if (grid[1][2].getType().equalsIgnoreCase("blank")){
                                    grid[1][2].drawO();
                                }
                                else{
                                    grid[0][2].drawO();
                                }
                                if (configInt1 == 4)
                                    mirrorHorizontally();
                                break;
                            case 6:
                                mirrorHorizontally();
                            case 5:
                                if(grid[0][2].getType().equalsIgnoreCase("blank")){
                                    grid[0][2].drawO();
                                }
                                else
                                    grid[1][2].drawO();
                                if (configInt1 == 6)
                                    mirrorHorizontally();
                                break;
                            case 7:
                                if(grid[1][0].getType().equalsIgnoreCase("blank")){
                                    grid[1][0].drawO();
                                }
                                else
                                    grid[0][2].drawO();
                                break;
                        }
                        if (config == 'd')
                            rotateMatrix(3);
                        else if (config == 'h')
                            rotateMatrix(2);
                        else if (config == 'f')
                            rotateMatrix(1);
                        break;
                }
                break;
        }
    }

    public void printBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                System.out.print(grid[i][j].getType());
            }
            System.out.println();
        }
        System.out.println();
    }

    public void rotateMatrix(int noOfRotates){

        for (int i = 0 ; i < noOfRotates; i++){
            printBoard();
            //Transpose Matrix
            for (int a = 0; a < 3; a++){
                for (int b = a; b < 3; b++){
                    Tile temp = grid[a][b];

                    //grid[a][b].drawType(grid[b][a].getType());
                    grid[a][b] = grid[b][a];

                    //grid[b][a].drawType(temp.getType());
                    grid[b][a] = temp;


                }
            }
            //Reverse Rows
            for (int a = 0; a < 3; a++){
                for (int b = 0; b < 3/2; b++){
                    Tile temp = grid[a][b];

                    //grid[a][b].drawType(grid[a][3 - 1 - b].getType());
                    grid[a][b] = grid[a][3 - 1 - b];

                    //grid[a][3 - 1 - b].drawType(temp.getType());
                    grid[a][3 - 1 - b] = temp;
                }
            }
        }
        System.out.println("FINAL BOARD");
        printBoard();
    }

    public void mirrorHorizontally(){
        Tile[][] temp = new Tile[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp[i][2 - j] = grid[i][j];
            }
        }

        grid = temp;//COPY TEMP GRID TO ORIG GRID
        printBoard();
    }

    public void level2(){
        int move[] = bestMove();

    }

    public int checkWinner(){
        // X wins = 1, used 1 and 0 for it to bre reusable in all algo
        // O wins = -1
        // tie = 0
        for (int i = 0; i < 3; i++) {
            //check row
            if (grid[i][0].getType().equalsIgnoreCase(grid[i][1].getType()) && grid[i][0].getType().equalsIgnoreCase(grid[i][2].getType()) && grid[i][0].getType().equalsIgnoreCase("X")){
                return 1;
            } else if (grid[i][0].getType().equalsIgnoreCase(grid[i][1].getType()) && grid[i][0].getType().equalsIgnoreCase(grid[i][2].getType()) && grid[i][0].getType().equalsIgnoreCase("O")) {
                return -1;
            }
            //check col
            if (grid[0][i].getType().equalsIgnoreCase(grid[1][i].getType()) && grid[0][i].getType().equalsIgnoreCase(grid[2][i].getType()) && grid[0][i].getType().equalsIgnoreCase("X")){
                return 1; //check col
            } else if (grid[0][i].getType().equalsIgnoreCase(grid[1][i].getType()) && grid[0][i].getType().equalsIgnoreCase(grid[2][i].getType()) && grid[0][i].getType().equalsIgnoreCase("O")) {
                return -1; //check col
            }
        }

        // check
        if (grid[0][0].getType().equalsIgnoreCase(grid[1][1].getType()) && grid[0][0].getType().equalsIgnoreCase(grid[2][2].getType()) && grid[0][0].getType().equalsIgnoreCase("X")){
            return 1;
        } else if (grid[0][0].getType().equalsIgnoreCase(grid[1][1].getType()) && grid[0][0].getType().equalsIgnoreCase(grid[2][2].getType()) && grid[0][0].getType().equalsIgnoreCase("O")){
            return -1;
        }
        // check /
        if(grid[2][0].getType().equalsIgnoreCase(grid[1][1].getType()) && grid[2][0].getType().equalsIgnoreCase(grid[0][2].getType()) && grid[2][0].getType().equalsIgnoreCase("X")){
            return 1;
        } else if(grid[2][0].getType().equalsIgnoreCase(grid[1][1].getType()) && grid[2][0].getType().equalsIgnoreCase(grid[0][2].getType()) && grid[2][0].getType().equalsIgnoreCase("O")){
            return -1;
        }
        return 0;
    }

    public int[] bestMove(){
        int bestVal = -1000;
        int moveVal;
        int[] move = new int[2];
        move[0] = -1; // row
        move[1] = -1;  // col
        System.out.println(gameCount);
        // check each tile and evaluate minimax value for each.
        // return optimal value
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(grid[i][j].getType().equalsIgnoreCase("blank")){ //check if empty tile

                    if(gameCount%2 != 0){
                        grid[i][j].drawO(); // make move
                    } else {
                        grid[i][j].drawX();
                    }

                    //compute value
                    moveVal = minimax(0, false);
                    grid[i][j].setInitial(); //undo the move bc ur just checking but not really setting

                    if (moveVal > bestVal){ // update bestVal
                        bestVal = moveVal;
                        move[0] = i;
                        move[1] = j;

                    }
                }
            }
        }
        if(gameCount%2 != 0){
            grid[move[0]][move[1]].drawO(); // make move
        } else {
            grid[move[0]][move[1]].drawX();
        }
        player1Turn = true;
        return move;
    }



    public int minimax(int depth, boolean isMaximizing){
        int score = checkWinner();

        if (score == 1) // if max won
            return score;
        if (score == -1) // if min won
            return score;

        int bestScore;

        if (isMaximizing){
            bestScore = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(grid[i][j].getType().equalsIgnoreCase("blank")){
                        if(gameCount%2 != 0){
                            grid[i][j].drawO(); // make move
                        } else {
                            grid[i][j].drawX();
                        }
                        int tempScore =  minimax(depth+1, false);
                        bestScore = Math.max(bestScore, tempScore);
                        grid[i][j].setInitial(); //undo the move
                    }
                }
            }
            return bestScore;
        } else {
            bestScore = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(grid[i][j].getType().equalsIgnoreCase("blank")){
                        if(gameCount%2 != 0){
                            grid[i][j].drawO(); // make move
                        } else {
                            grid[i][j].drawX();
                        }
                        int tempScore = minimax(depth+1, true);
                        bestScore = Math.min(bestScore, tempScore);
                        grid[i][j].setInitial(); //undo
                    }
                }
            }
            return bestScore;
        }
    }

    public static Tile[][] getGrid() {
        return grid;
    }

    public static void setGrid(Tile[][] grid) {
        Model.grid = grid;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Turn(boolean bool) {
       player1Turn = bool;
    }
}
