package com.example.npuzzleapp;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tmov;
    TextView cols;
    TextView rows;
    GridLayout gl;
    LinearLayout lly;
    char last_move = 'o';
    int row = 3;
    String s;
    int column = 3;
    int moves = 0;
    int space = row*column-1;
    int [] bord = new int[row*column];
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lly = (LinearLayout)findViewById(R.id.llyt);
        gl = new GridLayout(MainActivity.this);
        tmov = (TextView) findViewById(R.id.tmoves);
        cols = (TextView) findViewById(R.id.cols);
        rows = (TextView) findViewById(R.id.rows);
        gl.setColumnCount(column);
        gl.setRowCount(row);
        for(int i = 0; i < bord.length; i++){
            if(i != space)
                bord[i] = i+1;
            else
                bord[i] = -1;
        }
        setGrid();
        lly.addView(gl);
        lly.setGravity(Gravity.CENTER);
    }

    public void shuffle(android.view.View v){
        int i = 0;
        char current_move = 'e';
        while( i < row*column){
            while(true){
                int j = rand.nextInt(4);
                if(j == 0)
                    current_move = 'u';
                else if(j == 1)
                    current_move = 'd';
                else if( j== 2)
                    current_move = 'r';
                else if(j == 3)
                    current_move = 'l';

                if(current_move != last_move)
                    break;
            }
            if(move(current_move))
                ++i;
        }
    }

    public void reset(android.view.View v){
        gl.removeAllViewsInLayout();
        moves = 0;
        tmov.setText(String.valueOf(moves));
        space = row*column-1;
        last_move = 'o';
        for(int i = 0; i < bord.length; i++){
            if(i != space)
                bord[i] = i+1;
            else
                bord[i] = -1;
        }
        setGrid();
    }

    public void increaseRow(android.view.View v){
        if(row < 9){
            ++row;
            s  = String.valueOf(row);
            rows.setText(s);
            gl.removeAllViewsInLayout();
            gl.setRowCount(row);
            space = row*column-1;
            bord = new int[row*column];
            for(int i = 0; i < bord.length; i++){
                if(i != space)
                    bord[i] = i+1;
                else
                    bord[i] = -1;
            }
            setGrid();
        }
    }

    public void increaseColumn(android.view.View v){
        if(column < 9){
            ++column;
            s  = String.valueOf(column);
            cols.setText(s);
            gl.removeAllViewsInLayout();
            gl.setColumnCount(column);
            space = row*column-1;
            bord = new int[row*column];
            for(int i = 0; i < bord.length; i++){
                if(i != space)
                    bord[i] = i+1;
                else
                    bord[i] = -1;
            }
            setGrid();
        }
    }

    public void decreaseRow(android.view.View v){
        if(row > 3){
            --row;
            s  = String.valueOf(row);
            rows.setText(s);
            gl.removeAllViewsInLayout();
            gl.setRowCount(row);
            space = row*column-1;
            bord = new int[row*column];
            for(int i = 0; i < bord.length; i++){
                if(i != space)
                    bord[i] = i+1;
                else
                    bord[i] = -1;
            }
            setGrid();
        }
    }

    public void decreaseColumn(android.view.View v){
        if(column > 3){
            --column;
            s  = String.valueOf(column);
            cols.setText(s);
            gl.removeAllViewsInLayout();
            gl.setColumnCount(column);
            space = row*column-1;
            bord = new int[row*column];
            for(int i = 0; i < bord.length; i++){
                if(i != space)
                    bord[i] = i+1;
                else
                    bord[i] = -1;
            }
            setGrid();
        }
    }

    public void setGrid(){

        for(int i = 0; i < row*column; i++){
            Button bttn = new Button(MainActivity.this);
            if(bord[i] != -1){
                bttn.setText(""+(bord[i]));
                bttn.setTextSize(10);
            }
            else if(bord[i] == -1){
                bttn.setTextSize(8);
                bttn.setText("space");
            }
            GridLayout.LayoutParams param =new GridLayout.LayoutParams();
            param.height = lly.getHeight()/row;
            param.width = lly.getWidth()/column;
            bttn.setLayoutParams(param);
            gl.addView(bttn, i);
        }
    }

    public void Up(android.view.View v){
        move('u');
    }

    public void Down(android.view.View v){
        move('d');
    }

    public void Right(android.view.View v){
        move('r');
    }

    public void Left(android.view.View v){
        move('l');
    }

    public boolean move(char move){

        int change = 0;
        if(move == 'u'){
            change = -column;
        }
        else if( move == 'd'){
            change = column;
        }
        else if( move == 'l'){
            change = -1;
        }
        else if( move == 'r'){
            change = 1;
        }

        if(checkBorder(change) == false)
            return false;
        tmov.setText(String.valueOf(++moves));
        bord[space] = bord[space+change];
        bord[space+change] = -1;
        space += change;
        last_move = move;
        gl.removeAllViewsInLayout();
        setGrid();
        return true;
    }

    public boolean checkBorder(int change){

        if(space + change > row*column-1){
            return false;
        }else if(space + change < 0){
            return false;
        }
        else if((space%column == 0) && change == -1)
            return false;
        else if(((space+1)%column == 0) && change == 1)
            return false;
        return true;
    }

    public void hint(android.view.View v){
        int difl = 99;
        int difr = 99;
        int difu = 99;
        int difd = 99;

        if(space -1 >= 0 && space % column != 0 && last_move != 'r'){
            if(absu(space - bord[space-1]) <= absu(space - bord[space]))
            difl = absu(space - bord[space-1]);
        }
        if(space + 1 <= row*column-1 && (space+1)%column != 0 && last_move != 'l'){
            if(absu(space - bord[space+1]) <= absu(space - bord[space]))
            difr = absu(space - bord[space+1]);
        }
        if(space + column <= row*column-1 && last_move != 'u'){
            if(absu(space - bord[space+column]) <= absu(space - bord[space]))
            difd = absu(space - bord[space+column]);
        }
        if(space - column >= 0 && last_move != 'd'){
            if(absu(space - bord[space-column]) <= absu(space - bord[space]))
            difu = absu(space - bord[space-column]);
        }
        char mov = 'o';
        int min = 99;
        if(min  > difl){
            mov = 'l';
            min = difl;
        }
        if(min > difr){
            mov = 'r';
            min = difr;
        }
        if(min > difu){
            mov = 'u';
            min = difu;
        }
        if(min > difd){
            mov = 'd';
        }
        if(mov == 'o'){
            while(true){
                int j = rand.nextInt(4);
                if(j == 0)
                    mov = 'u';
                else if(j == 1)
                    mov = 'd';
                else if( j== 2)
                    mov = 'r';
                else if(j == 3)
                    mov = 'l';

                if(mov != last_move)
                    if(move(mov))
                        break;
            }
        }
        else
            move(mov);

    }

    public int absu(int x){
        if(x >= 0)
            return x;
        else
            return -x;
    }

    public void start(android.view.View v){
        int i = 0;
        char current_move = 'e';
        while( i < row*column){
            while(true){
                int j = rand.nextInt(4);
                if(j == 0)
                    current_move = 'u';
                else if(j == 1)
                    current_move = 'd';
                else if( j== 2)
                    current_move = 'r';
                else if(j == 3)
                    current_move = 'l';

                if(current_move != last_move)
                    break;
            }
            if(move(current_move))
                ++i;
        }
    }
}
