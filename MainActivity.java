package com.example.chessproject;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.io.*;
import java.io.File.*;

public class MainActivity extends AppCompatActivity {

    public static Board board;
    public boolean pl;
    public int j;
    public int k;
    public boolean gameOver;
    public Coord[] move;
    public Coord[] lastMove;
    public boolean record;
    public boolean draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        board = new Board();

        record = false;
        draw = false;

        pl = true;


        final Button Resign = findViewById(R.id.Resign);
        final TextView Move = findViewById(R.id.Move);
        Move.setText("");

        final Button AI = findViewById(R.id.AI);

        final Button Mov = findViewById(R.id.Mov);

        //Resign.setText(b.tile[0][0].piece.toString());

        final Button[][] boardClick =
                {{findViewById(R.id.a8), findViewById(R.id.b8), findViewById(R.id.c8), findViewById(R.id.d8), findViewById(R.id.e8), findViewById(R.id.f8), findViewById(R.id.g8), findViewById(R.id.h8)}, {findViewById(R.id.a7), findViewById(R.id.b7), findViewById(R.id.c7), findViewById(R.id.d7), findViewById(R.id.e7), findViewById(R.id.f7), findViewById(R.id.g7), findViewById(R.id.h7)},
                        {findViewById(R.id.a6), findViewById(R.id.b6), findViewById(R.id.c6), findViewById(R.id.d6), findViewById(R.id.e6), findViewById(R.id.f6), findViewById(R.id.g6), findViewById(R.id.h6)}, {findViewById(R.id.a5), findViewById(R.id.b5), findViewById(R.id.c5), findViewById(R.id.d5), findViewById(R.id.e5), findViewById(R.id.f5), findViewById(R.id.g5), findViewById(R.id.h5)},
                        {findViewById(R.id.a4), findViewById(R.id.b4), findViewById(R.id.c4), findViewById(R.id.d4), findViewById(R.id.e4), findViewById(R.id.f4), findViewById(R.id.g4), findViewById(R.id.h4)}, {findViewById(R.id.a3), findViewById(R.id.b3), findViewById(R.id.c3), findViewById(R.id.d3), findViewById(R.id.e3), findViewById(R.id.f3), findViewById(R.id.g3), findViewById(R.id.h3)},
                        {findViewById(R.id.a2), findViewById(R.id.b2), findViewById(R.id.c2), findViewById(R.id.d2), findViewById(R.id.e2), findViewById(R.id.f2), findViewById(R.id.g2), findViewById(R.id.h2)}, {findViewById(R.id.a1), findViewById(R.id.b1), findViewById(R.id.c1), findViewById(R.id.d1), findViewById(R.id.e1), findViewById(R.id.f1), findViewById(R.id.g1), findViewById(R.id.h1)}};


        for (int y = 0; y < 8; y++) {
            for (int z = 0; z < 8; z++) {
                if (board.tile[y][z] != null) {
                    boardClick[y][z].setText(board.tile[y][z].toString());
                }
                else{
                    boardClick[y][z].setText("");
                }
            }
        }


        final int[] i = new int[1];
        i[0] = 0;

        final Button Record = findViewById(R.id.Record);
        final Button Draw = findViewById(R.id.Draw);

        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(record==false) {
                    record = true;


                }
            }
        });

        final Button Undo = findViewById(R.id.Undo);

        final Button NewGame = findViewById(R.id.NewGame);

        for (i[0] = 0; i[0] < 200; i[0]++) {
            move = new Coord[2];
            gameOver = false;
            move[0] = new Coord();
            move[1] = new Coord();

            NewGame.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    board = new Board();

                    for (int y = 0; y < 8; y++) {
                        for (int z = 0; z < 8; z++) {
                            if (board.tile[y][z] != null) {
                                boardClick[y][z].setText(board.tile[y][z].toString());
                            }
                            else{
                                boardClick[y][z].setText("");
                            }
                        }
                    }

                    move[0] = new Coord();
                    move[1] = new Coord();
                    lastMove = null;
                    draw = false;
                    pl = true;
                    gameOver=false;
                }
            });

            Resign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!gameOver) {
                        gameOver = true;

                        if(pl) {
                            Move.setText("Resign");
                            Move.setText("Black");
                        }
                        else{
                            Move.setText("Resign");
                            Move.setText("White");
                        }

                        board = new Board();

                        for (int y = 0; y < 8; y++) {
                            for (int z = 0; z < 8; z++) {
                                if (board.tile[y][z] != null) {
                                    boardClick[y][z].setText(board.tile[y][z].toString());
                                }
                                else{
                                    boardClick[y][z].setText("");
                                }
                            }
                        }

                        move[0] = new Coord();
                        move[1] = new Coord();
                        lastMove = null;
                        pl = true;
                        draw = false;
                        gameOver=false;
                    }
                    else if (gameOver) {


                        board = new Board();

                        for (int y = 0; y < 8; y++) {
                            for (int z = 0; z < 8; z++) {
                                if (board.tile[y][z] != null) {
                                    boardClick[y][z].setText(board.tile[y][z].toString());
                                }
                                else{
                                    boardClick[y][z].setText("");
                                }
                            }
                        }

                        move[0] = new Coord();
                        move[1] = new Coord();
                        lastMove = null;
                        pl = true;
                        draw = false;
                        gameOver=false;
                    }
                }
            });

            Undo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if ((move[0].getX()!=-1)||(move[1].getX()!=-1)){
                        move[0]= new Coord(-1,-1);
                        move[1]=new Coord(-1,-1);
                        Move.setText("");
                    }
                    /*else{
                        if(lastMove!=null) {
                            undo(lastMove[0],lastMove[1],!pl);
                            Move.setText("");
                        }
                    }*/
                }
            });


            final int[] m = new int[1];
            m[0] = 0;

            final int[] n = new int[1];
            n[0] = 0;

            for (m[0] = 0; m[0] < 8; m[0]++) {
                for (n[0] = 0; n[0] < 8; n[0]++) {
                    if (board.tile[m[0]][n[0]] != null) {
                        if (board.tile[m[0]][n[0]].player) {
                            //boardClick[m[0]][n[0]].setTextColor(0x000000);
                            boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                        } else {
                            //boardClick[m[0]][n[0]].setTextColor(0xffffff);
                            boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                        }
                    }
                }
            }

            if ((move[0].getX() == -1) || (move[1].getX() == -1)) {
                boardClick[0][0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("00");
                            move[0] = new Coord(0, 0);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(0,0);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 00"));
                        }
                    }
                });

                boardClick[0][1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("10");
                            move[0] = new Coord(1, 0);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(1,0);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 10"));
                        }
                    }
                });

                boardClick[0][2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("20");
                            move[0] = new Coord(2, 0);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(2,0);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 20"));
                        }
                    }
                });


                boardClick[0][3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("30");
                            move[0] = new Coord(3, 0);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(3,0);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 30"));
                        }
                    }
                });


                boardClick[0][4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("40");
                            move[0] = new Coord(4, 0);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(4,0);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 40"));
                        }
                    }
                });


                boardClick[0][5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("50");
                            move[0] = new Coord(5, 0);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(5,0);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 50"));
                        }
                    }
                });


                boardClick[0][6].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("60");
                            move[0] = new Coord(6, 0);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(6,0);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 60"));
                        }
                    }
                });

                boardClick[0][7].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("70");
                            move[0] = new Coord(7, 0);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(7,0);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 70"));
                        }
                    }
                });
                boardClick[3][0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("03");
                            move[0] = new Coord(0, 3);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(0,3);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 03"));
                        }
                    }
                });

                boardClick[3][1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("13");
                            move[0] = new Coord(1, 3);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(1,3);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 13"));
                        }
                    }
                });

                boardClick[3][2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("23");
                            move[0] = new Coord(2, 3);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(2,3);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 23"));
                        }
                    }
                });


                boardClick[3][3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("33");
                            move[0] = new Coord(3, 3);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(3,3);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 33"));
                        }
                    }
                });


                boardClick[3][4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("43");
                            move[0] = new Coord(4, 3);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(4,3);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 43"));
                        }
                    }
                });


                boardClick[3][5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("53");
                            move[0] = new Coord(5, 3);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(5,3);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 53"));
                        }
                    }
                });


                boardClick[3][6].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("63");
                            move[0] = new Coord(6, 3);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(6,3);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 63"));
                        }
                    }
                });

                boardClick[3][7].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("73");
                            move[0] = new Coord(7, 3);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(7,3);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 73"));
                        }
                    }
                });

                boardClick[2][0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("02");
                            move[0] = new Coord(0, 2);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(0,2);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 02"));
                        }
                    }
                });

                boardClick[2][1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("12");
                            move[0] = new Coord(1, 2);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(1,2);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 12"));
                        }
                    }
                });

                boardClick[2][2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("22");
                            move[0] = new Coord(2, 2);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(2,2);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 22"));
                        }
                    }
                });


                boardClick[2][3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("32");
                            move[0] = new Coord(3, 2);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(3,2);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 32"));
                        }
                    }
                });


                boardClick[2][4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("42");
                            move[0] = new Coord(4, 2);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(4,2);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 42"));
                        }
                    }
                });


                boardClick[2][5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("52");
                            move[0] = new Coord(5, 2);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(5,2);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 52"));
                        }
                    }
                });


                boardClick[2][6].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("62");
                            move[0] = new Coord(6, 2);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(6,2);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 62"));
                        }
                    }
                });

                boardClick[2][7].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("72");
                            move[0] = new Coord(7, 2);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(7,2);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 72"));
                        }
                    }
                });

                boardClick[1][0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("01");
                            move[0] = new Coord(0, 1);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(0,1);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 01"));
                        }
                    }
                });

                boardClick[1][1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("11");
                            move[0] = new Coord(1, 1);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(1,1);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 11"));
                        }
                    }
                });

                boardClick[1][2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("21");
                            move[0] = new Coord(2, 1);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(2,1);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 21"));
                        }
                    }
                });


                boardClick[1][3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("31");
                            move[0] = new Coord(3, 1);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(3,1);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 31"));
                        }
                    }
                });


                boardClick[1][4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("41");
                            move[0] = new Coord(4, 1);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(4,1);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 41"));
                        }
                    }
                });


                boardClick[1][5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("51");
                            move[0] = new Coord(5, 1);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(5,1);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 51"));
                        }
                    }
                });


                boardClick[1][6].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("61");
                            move[0] = new Coord(6, 1);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(6,1);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 61"));
                        }
                    }
                });

                boardClick[1][7].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("71");
                            move[0] = new Coord(7, 1);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(7,1);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 71"));
                        }
                    }
                });

                boardClick[4][0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("04");
                            move[0] = new Coord(0, 4);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(0,4);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 04"));
                        }
                    }
                });

                boardClick[4][1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("14");
                            move[0] = new Coord(1, 4);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(1,4);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 14"));
                        }
                    }
                });

                boardClick[4][2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("24");
                            move[0] = new Coord(2, 4);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(2,4);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 24"));
                        }
                    }
                });


                boardClick[4][3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("34");
                            move[0] = new Coord(3, 4);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(3,4);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 34"));
                        }
                    }
                });


                boardClick[4][4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("44");
                            move[0] = new Coord(4, 4);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(4,4);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 44"));
                        }
                    }
                });


                boardClick[4][5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("54");
                            move[0] = new Coord(5, 4);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(5,4);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 54"));
                        }
                    }
                });


                boardClick[4][6].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("64");
                            move[0] = new Coord(6, 4);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(6,4);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 64"));
                        }
                    }
                });

                boardClick[4][7].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("74");
                            move[0] = new Coord(7, 4);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(7,4);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 74"));
                        }
                    }
                });

                boardClick[5][0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("05");
                            move[0] = new Coord(0, 5);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(0,5);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 05"));
                        }
                    }
                });

                boardClick[5][1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("15");
                            move[0] = new Coord(1, 5);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(1,5);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 15"));
                        }
                    }
                });

                boardClick[5][2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("25");
                            move[0] = new Coord(2, 5);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(2,5);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 25"));
                        }
                    }
                });


                boardClick[5][3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("35");
                            move[0] = new Coord(3, 5);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(3,5);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 35"));
                        }
                    }
                });


                boardClick[5][4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("45");
                            move[0] = new Coord(4, 5);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(4,5);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 45"));
                        }
                    }
                });


                boardClick[5][5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("55");
                            move[0] = new Coord(5, 5);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(5,5);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 55"));
                        }
                    }
                });


                boardClick[5][6].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("65");
                            move[0] = new Coord(6, 5);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(6,5);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 65"));
                        }
                    }
                });

                boardClick[5][7].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("75");
                            move[0] = new Coord(7, 5);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(7,5);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 75"));
                        }
                    }
                });

                boardClick[6][0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("06");
                            move[0] = new Coord(0, 6);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(0,6);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 06"));
                        }
                    }
                });

                boardClick[6][1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("16");
                            move[0] = new Coord(1, 6);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(1,6);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 16"));
                        }
                    }
                });

                boardClick[6][2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("26");
                            move[0] = new Coord(2, 6);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(2,6);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 26"));
                        }
                    }
                });


                boardClick[6][3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("36");
                            move[0] = new Coord(3, 6);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(3,6);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 36"));
                        }
                    }
                });


                boardClick[6][4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("46");
                            move[0] = new Coord(4, 6);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(4,6);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 46"));
                        }
                    }
                });


                boardClick[6][5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("56");
                            move[0] = new Coord(5, 6);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(5,6);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 56"));
                        }
                    }
                });


                boardClick[6][6].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("66");
                            move[0] = new Coord(6, 6);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(6,6);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 66"));
                        }
                    }
                });

                boardClick[6][7].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("76");
                            move[0] = new Coord(7, 6);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(7,6);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 76"));
                        }
                    }
                });

                boardClick[7][0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("07");
                            move[0] = new Coord(0, 7);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(0,7);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 07"));
                        }
                    }
                });

                boardClick[7][1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("17");
                            move[0] = new Coord(1, 7);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(1,7);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 17"));
                        }
                    }
                });

                boardClick[7][2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("27");
                            move[0] = new Coord(2, 7);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(2,7);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 27"));
                        }
                    }
                });


                boardClick[7][3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("37");
                            move[0] = new Coord(3, 7);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(3,7);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 37"));
                        }
                    }
                });


                boardClick[7][4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("47");
                            move[0] = new Coord(4, 7);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(4,7);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 47"));
                        }
                    }
                });


                boardClick[7][5].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("57");
                            move[0] = new Coord(5, 7);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(5,7);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 57"));
                        }
                    }
                });


                boardClick[7][6].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("67");
                            move[0] = new Coord(6, 7);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(6,7);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 67"));
                        }
                    }
                });

                boardClick[7][7].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (move[0].getX() == -1) {
                            Move.setText("77");
                            move[0] = new Coord(7, 7);
                        } else if (move[1].getX() == -1){
                            move[1] = new Coord(7,7);
                            String a = (String)Move.getText();
                            Move.setText(a.concat(" 77"));
                        }
                    }
                });



            }
            //if((move[0].getX()!=-1)&&(move[1].getX()!=-1)) {
                Draw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(draw){
                            Move.setText("Draw agreed");
                            Move.setText("Draw");
                            board = new Board();

                            for (int y = 0; y < 8; y++) {
                                for (int z = 0; z < 8; z++) {
                                    if (board.tile[y][z] != null) {
                                        boardClick[y][z].setText(board.tile[y][z].toString());
                                    }
                                    else{
                                        boardClick[y][z].setText("");
                                    }
                                }
                            }

                            move[0] = new Coord();
                            move[1] = new Coord();
                            lastMove = null;
                            draw = false;
                            pl = true;
                            gameOver=false;

                        }
                        else if(pl && whiteCheck()){
                            Board backup = board;
                            if(movePiece(move[0],move[1],pl,backup)){
                                movePiece(move[0],move[1],pl,backup);
                                if(whiteCheck(backup)){

                                    move[0]= new Coord();
                                    move[1]=new Coord();
                                    Move.setText("");
                                }
                                else{
                                    draw = true;
                                    movePiece(move[0], move[1], pl);
                                    lastMove = move;
                                    move[0] = new Coord(-1, -1);
                                    move[1] = new Coord(-1, -1);
                                    Move.setText("");

                                    for (m[0] = 0; m[0] < 8; m[0]++) {
                                        for (n[0] = 0; n[0] < 8; n[0]++) {
                                            if (board.tile[m[0]][n[0]] != null) {
                                                if (board.tile[m[0]][n[0]].player) {
                                                    //boardClick[m[0]][n[0]].setTextColor(0x000000);
                                                    boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                                } else {
                                                    //boardClick[m[0]][n[0]].setTextColor(0xffffff);
                                                    boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                                }
                                            }
                                            else if(board.tile[m[0]][n[0]]==null){
                                                boardClick[m[0]][n[0]].setText("");
                                            }
                                        }
                                    }



                                    if (pl) {
                                        pl = false;
                                    } else {
                                        pl = true;
                                    }

                                    if(stalemate(pl)){
                                        Move.setText("Stalemate");
                                        Move.setText("Draw");
                                        board = new Board();

                                        for (int y = 0; y < 8; y++) {
                                            for (int z = 0; z < 8; z++) {
                                                if (board.tile[y][z] != null) {
                                                    boardClick[y][z].setText(board.tile[y][z].toString());
                                                }
                                                else{
                                                    boardClick[y][z].setText("");
                                                }
                                            }
                                        }

                                        move[0] = new Coord();
                                        move[1] = new Coord();
                                        lastMove = null;
                                        draw = false;
                                        pl = true;
                                        gameOver=false;
                                    }

                                    if(detectMate(pl)) {
                                        Move.setText("Checkmate");
                                        if (pl) {
                                            Move.setText("Black");
                                        } else {
                                            Move.setText("White");
                                        }
                                        board = new Board();

                                        for (int y = 0; y < 8; y++) {
                                            for (int z = 0; z < 8; z++) {
                                                if (board.tile[y][z] != null) {
                                                    boardClick[y][z].setText(board.tile[y][z].toString());
                                                } else {
                                                    boardClick[y][z].setText("");
                                                }
                                            }
                                        }

                                        move[0] = new Coord();
                                        move[1] = new Coord();
                                        lastMove = null;
                                        draw = false;
                                        pl = true;
                                        gameOver = false;
                                    }
                                }
                            }
                        }
                        else if(!pl && blackCheck()){
                            Board backup = board;
                            if(movePiece(move[0],move[1],pl,backup)){
                                movePiece(move[0],move[1],pl,backup);
                                if(blackCheck(backup)){
                                    move[0]= new Coord(-1,-1);
                                    move[1]=new Coord(-1,-1);
                                    Move.setText("");
                                }
                                else{
                                    draw = true;
                                    movePiece(move[0], move[1], pl);
                                    lastMove = move;
                                    move[0] = new Coord(-1, -1);
                                    move[1] = new Coord(-1, -1);
                                    Move.setText("");

                                    for (m[0] = 0; m[0] < 8; m[0]++) {
                                        for (n[0] = 0; n[0] < 8; n[0]++) {
                                            if (board.tile[m[0]][n[0]] != null) {
                                                if (board.tile[m[0]][n[0]].player) {
                                                    //boardClick[m[0]][n[0]].setTextColor(0x000000);
                                                    boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                                } else {
                                                    //boardClick[m[0]][n[0]].setTextColor(0xffffff);
                                                    boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                                }
                                            }
                                            else if(board.tile[m[0]][n[0]]==null){
                                                boardClick[m[0]][n[0]].setText("");
                                            }
                                        }
                                    }



                                    if (pl) {
                                        pl = false;
                                    } else {
                                        pl = true;
                                    }

                                    if(stalemate(pl)){
                                        Move.setText("Stalemate");
                                        Move.setText("Draw");
                                        board = new Board();

                                        for (int y = 0; y < 8; y++) {
                                            for (int z = 0; z < 8; z++) {
                                                if (board.tile[y][z] != null) {
                                                    boardClick[y][z].setText(board.tile[y][z].toString());
                                                }
                                                else{
                                                    boardClick[y][z].setText("");
                                                }
                                            }
                                        }

                                        move[0] = new Coord();
                                        move[1] = new Coord();
                                        lastMove = null;
                                        draw = false;
                                        pl = true;
                                        gameOver=false;
                                    }

                                    if(detectMate(pl)) {
                                        Move.setText("Checkmate");
                                        if (pl) {
                                            Move.setText("Black");
                                        } else {
                                            Move.setText("White");
                                        }
                                        board = new Board();

                                        for (int y = 0; y < 8; y++) {
                                            for (int z = 0; z < 8; z++) {
                                                if (board.tile[y][z] != null) {
                                                    boardClick[y][z].setText(board.tile[y][z].toString());
                                                } else {
                                                    boardClick[y][z].setText("");
                                                }
                                            }
                                        }

                                        move[0] = new Coord();
                                        move[1] = new Coord();
                                        lastMove = null;
                                        draw = false;
                                        pl = true;
                                        gameOver = false;
                                    }
                                }
                            }
                        }
                        else if (movePiece(move[0], move[1], pl)) {



                            draw = true;
                            movePiece(move[0], move[1], pl);
                            lastMove = move;
                            move[0] = new Coord(-1, -1);
                            move[1] = new Coord(-1, -1);
                            Move.setText("");

                            for (m[0] = 0; m[0] < 8; m[0]++) {
                                for (n[0] = 0; n[0] < 8; n[0]++) {
                                    if (board.tile[m[0]][n[0]] != null) {
                                        if (board.tile[m[0]][n[0]].player) {
                                            //boardClick[m[0]][n[0]].setTextColor(0x000000);
                                            boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                        } else {
                                            //boardClick[m[0]][n[0]].setTextColor(0xffffff);
                                            boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                        }
                                    }
                                    else if(board.tile[m[0]][n[0]]==null){
                                        boardClick[m[0]][n[0]].setText("");
                                    }
                                }
                            }



                            if (pl) {
                                pl = false;
                            } else {
                                pl = true;
                            }

                            if(stalemate(pl)){
                                Move.setText("Stalemate");
                                Move.setText("Draw");
                                board = new Board();

                                for (int y = 0; y < 8; y++) {
                                    for (int z = 0; z < 8; z++) {
                                        if (board.tile[y][z] != null) {
                                            boardClick[y][z].setText(board.tile[y][z].toString());
                                        }
                                        else{
                                            boardClick[y][z].setText("");
                                        }
                                    }
                                }

                                move[0] = new Coord();
                                move[1] = new Coord();
                                lastMove = null;
                                draw = false;
                                pl = true;
                                gameOver=false;
                            }

                            if(detectMate(pl)){
                                Move.setText("Checkmate");
                                if(pl) {
                                    Move.setText("Black");
                                }
                                else{
                                    Move.setText("White");
                                }
                                board = new Board();

                                for (int y = 0; y < 8; y++) {
                                    for (int z = 0; z < 8; z++) {
                                        if (board.tile[y][z] != null) {
                                            boardClick[y][z].setText(board.tile[y][z].toString());
                                        }
                                        else{
                                            boardClick[y][z].setText("");
                                        }
                                    }
                                }

                                move[0] = new Coord();
                                move[1] = new Coord();
                                lastMove = null;
                                draw = false;
                                pl = true;
                                gameOver=false;
                            }



                        } else {
                            move[0] = new Coord(-1, -1);
                            move[1] = new Coord(-1, -1);
                            Move.setText("");
                        }
                    }
                });

            Mov.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pl && whiteCheck()){
                        Board backup = board;
                        if(movePiece(move[0],move[1],pl,backup)){
                            movePiece(move[0],move[1],pl,backup);
                            if(whiteCheck(backup)){
                                move[0]= new Coord();
                                move[1]=new Coord();
                                Move.setText("");
                            }
                            else{
                                draw = false;
                                movePiece(move[0], move[1], pl);
                                lastMove = move;
                                move[0] = new Coord(-1, -1);
                                move[1] = new Coord(-1, -1);
                                Move.setText("");

                                for (m[0] = 0; m[0] < 8; m[0]++) {
                                    for (n[0] = 0; n[0] < 8; n[0]++) {
                                        if (board.tile[m[0]][n[0]] != null) {
                                            if (board.tile[m[0]][n[0]].player) {
                                                //boardClick[m[0]][n[0]].setTextColor(0x000000);
                                                boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                            } else {
                                                //boardClick[m[0]][n[0]].setTextColor(0xffffff);
                                                boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                            }
                                        }
                                        else if(board.tile[m[0]][n[0]]==null){
                                            boardClick[m[0]][n[0]].setText("");
                                        }
                                    }
                                }



                                if (pl) {
                                    pl = false;
                                } else {
                                    pl = true;
                                }

                                if(stalemate(pl)){
                                    Move.setText("Stalemate");
                                    Move.setText("Draw");
                                    board = new Board();

                                    for (int y = 0; y < 8; y++) {
                                        for (int z = 0; z < 8; z++) {
                                            if (board.tile[y][z] != null) {
                                                boardClick[y][z].setText(board.tile[y][z].toString());
                                            }
                                            else{
                                                boardClick[y][z].setText("");
                                            }
                                        }
                                    }

                                    move[0] = new Coord();
                                    move[1] = new Coord();
                                    lastMove = null;
                                    draw = false;
                                    pl = true;
                                    gameOver=false;
                                }

                                if(detectMate(pl)) {
                                    Move.setText("Checkmate");
                                    if (pl) {
                                        Move.setText("Black");
                                    } else {
                                        Move.setText("White");
                                    }
                                    board = new Board();

                                    for (int y = 0; y < 8; y++) {
                                        for (int z = 0; z < 8; z++) {
                                            if (board.tile[y][z] != null) {
                                                boardClick[y][z].setText(board.tile[y][z].toString());
                                            } else {
                                                boardClick[y][z].setText("");
                                            }
                                        }
                                    }

                                    move[0] = new Coord();
                                    move[1] = new Coord();
                                    lastMove = null;
                                    draw = false;
                                    pl = true;
                                    gameOver = false;
                                }
                            }
                        }
                    }
                    else if(!pl && blackCheck()){
                        Board backup = board;
                        if(movePiece(move[0],move[1],pl,backup)){
                            movePiece(move[0],move[1],pl,backup);
                            if(blackCheck(backup)){
                                move[0]= new Coord();
                                move[1]=new Coord();
                                Move.setText("");
                            }
                            else{
                                draw = false;
                                movePiece(move[0], move[1], pl);
                                lastMove = move;
                                move[0] = new Coord(-1, -1);
                                move[1] = new Coord(-1, -1);
                                Move.setText("");

                                for (m[0] = 0; m[0] < 8; m[0]++) {
                                    for (n[0] = 0; n[0] < 8; n[0]++) {
                                        if (board.tile[m[0]][n[0]] != null) {
                                            if (board.tile[m[0]][n[0]].player) {
                                                //boardClick[m[0]][n[0]].setTextColor(0x000000);
                                                boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                            } else {
                                                //boardClick[m[0]][n[0]].setTextColor(0xffffff);
                                                boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                            }
                                        }
                                        else if(board.tile[m[0]][n[0]]==null){
                                            boardClick[m[0]][n[0]].setText("");
                                        }
                                    }
                                }



                                if (pl) {
                                    pl = false;
                                } else {
                                    pl = true;
                                }

                                if(stalemate(pl)){
                                    Move.setText("Stalemate");
                                    Move.setText("Draw");
                                    board = new Board();

                                    for (int y = 0; y < 8; y++) {
                                        for (int z = 0; z < 8; z++) {
                                            if (board.tile[y][z] != null) {
                                                boardClick[y][z].setText(board.tile[y][z].toString());
                                            }
                                            else{
                                                boardClick[y][z].setText("");
                                            }
                                        }
                                    }

                                    move[0] = new Coord();
                                    move[1] = new Coord();
                                    lastMove = null;
                                    draw = false;
                                    pl = true;
                                    gameOver=false;
                                }

                                if(detectMate(pl)) {
                                    Move.setText("Checkmate");
                                    if (pl) {
                                        Move.setText("Black");
                                    } else {
                                        Move.setText("White");
                                    }
                                    board = new Board();

                                    for (int y = 0; y < 8; y++) {
                                        for (int z = 0; z < 8; z++) {
                                            if (board.tile[y][z] != null) {
                                                boardClick[y][z].setText(board.tile[y][z].toString());
                                            } else {
                                                boardClick[y][z].setText("");
                                            }
                                        }
                                    }

                                    move[0] = new Coord();
                                    move[1] = new Coord();
                                    lastMove = null;
                                    draw = false;
                                    pl = true;
                                    gameOver = false;
                                }
                            }
                        }
                    }
                    else if (movePiece(move[0], move[1], pl)) {



                        draw = false;
                        movePiece(move[0], move[1], pl);
                        lastMove = move;
                        move[0] = new Coord(-1, -1);
                        move[1] = new Coord(-1, -1);
                        Move.setText("");

                        for (m[0] = 0; m[0] < 8; m[0]++) {
                            for (n[0] = 0; n[0] < 8; n[0]++) {
                                if (board.tile[m[0]][n[0]] != null) {
                                    if (board.tile[m[0]][n[0]].player) {
                                        //boardClick[m[0]][n[0]].setTextColor(0x000000);
                                        boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                    } else {
                                        //boardClick[m[0]][n[0]].setTextColor(0xffffff);
                                        boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                    }
                                }
                                else if(board.tile[m[0]][n[0]]==null){
                                    boardClick[m[0]][n[0]].setText("");
                                }
                            }
                        }



                        if (pl) {
                            pl = false;
                        } else {
                            pl = true;
                        }

                        if(stalemate(pl)){
                            Move.setText("Stalemate");
                            Move.setText("Draw");
                            board = new Board();

                            for (int y = 0; y < 8; y++) {
                                for (int z = 0; z < 8; z++) {
                                    if (board.tile[y][z] != null) {
                                        boardClick[y][z].setText(board.tile[y][z].toString());
                                    }
                                    else{
                                        boardClick[y][z].setText("");
                                    }
                                }
                            }

                            move[0] = new Coord();
                            move[1] = new Coord();
                            lastMove = null;
                            draw = false;
                            pl = true;
                            gameOver=false;
                        }

                        if(detectMate(pl)){
                            Move.setText("Checkmate");
                            if(pl) {
                                Move.setText("Black");
                            }
                            else{
                                Move.setText("White");
                            }
                            board = new Board();

                            for (int y = 0; y < 8; y++) {
                                for (int z = 0; z < 8; z++) {
                                    if (board.tile[y][z] != null) {
                                        boardClick[y][z].setText(board.tile[y][z].toString());
                                    }
                                    else{
                                        boardClick[y][z].setText("");
                                    }
                                }
                            }

                            move[0] = new Coord();
                            move[1] = new Coord();
                            lastMove = null;
                            draw = false;
                            pl = true;
                            gameOver=false;
                        }



                    } else {
                        move[0] = new Coord(-1, -1);
                        move[1] = new Coord(-1, -1);
                        Move.setText("");
                    }
                }
            });

            AI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    move[0]=new Coord();
                    move[1]=new Coord();

                    for(int i=0;i<8;i++){
                        for(int j=0;j<8;j++){
                            if(board.tile[i][j]!=null){
                                move[0]=new Coord(j,i);
                                for(int k=0;k<8;k++){
                                    for(int l=0;l<8;l++){
                                        Coord c = new Coord(l,k);
                                        if(movePiece(move[0],c,pl)){
                                            movePiece(move[0],c,pl);
                                            draw = false;
                                            lastMove = move;
                                            move[0] = new Coord(-1, -1);
                                            move[1] = new Coord(-1, -1);
                                            Move.setText("");

                                            for (m[0] = 0; m[0] < 8; m[0]++) {
                                                for (n[0] = 0; n[0] < 8; n[0]++) {
                                                    if (board.tile[m[0]][n[0]] != null) {
                                                        if (board.tile[m[0]][n[0]].player) {
                                                            //boardClick[m[0]][n[0]].setTextColor(0x000000);
                                                            boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                                        } else {
                                                            //boardClick[m[0]][n[0]].setTextColor(0xffffff);
                                                            boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                                        }
                                                    }
                                                    else if(board.tile[m[0]][n[0]]==null){
                                                        boardClick[m[0]][n[0]].setText("");
                                                    }
                                                }
                                            }



                                            if (pl) {
                                                pl = false;
                                            } else {
                                                pl = true;
                                            }

                                            if(stalemate(pl)){
                                                Move.setText("Stalemate");
                                                Move.setText("Draw");
                                                board = new Board();

                                                for (int y = 0; y < 8; y++) {
                                                    for (int z = 0; z < 8; z++) {
                                                        if (board.tile[y][z] != null) {
                                                            boardClick[y][z].setText(board.tile[y][z].toString());
                                                        }
                                                        else{
                                                            boardClick[y][z].setText("");
                                                        }
                                                    }
                                                }

                                                move[0] = new Coord();
                                                move[1] = new Coord();
                                                lastMove = null;
                                                draw = false;
                                                pl = true;
                                                gameOver=false;
                                            }

                                            if(detectMate(pl)) {
                                                Move.setText("Checkmate");
                                                if (pl) {
                                                    Move.setText("Black");
                                                } else {
                                                    Move.setText("White");
                                                }
                                                board = new Board();

                                                for (int y = 0; y < 8; y++) {
                                                    for (int z = 0; z < 8; z++) {
                                                        if (board.tile[y][z] != null) {
                                                            boardClick[y][z].setText(board.tile[y][z].toString());
                                                        } else {
                                                            boardClick[y][z].setText("");
                                                        }
                                                    }
                                                }

                                                move[0] = new Coord();
                                                move[1] = new Coord();
                                                lastMove = null;
                                                draw = false;
                                                pl = true;
                                                gameOver = false;
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });


            //}

            /*if((move[0].getX()!=-1)&&(move[1].getX()!=-1)){
                if(movePiece(move[0],move[1],pl)){
                    movePiece(move[0],move[1],pl);
                    move[0]=new Coord(-1,-1);
                    move[1]=new Coord(-1,-1);
                    Move.setText("");

                    for (m[0] = 0; m[0] < 8; m[0]++) {
                        for (n[0] = 0; n[0] < 8; n[0]++) {
                            if (board.tile[m[0]][n[0]] != null) {
                                if (board.tile[m[0]][n[0]].player) {
                                    //boardClick[m[0]][n[0]].setTextColor(0x000000);
                                    boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                } else {
                                    //boardClick[m[0]][n[0]].setTextColor(0xffffff);
                                    boardClick[m[0]][n[0]].setText(board.tile[m[0]][n[0]].toString());
                                }
                            }
                        }
                    }

                    if(pl){
                        pl=false;
                    }
                    else{
                        pl = true;
                    }
                }
                else{
                    move[0]=new Coord(-1,-1);
                    move[1]=new Coord(-1,-1);
                    Move.setText("");
                }
            }
*/


        }
    }


    public int getButtonPositionX(Button b, Button[][] b1){
        int x = -1;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if (b1[i][j].equals(b)){
                    x=i;
                }
            }
        }
        return x;
    }

    public int getButtonPositionY(Button b, Button[][] b1){
        int y = -1;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if (b1[i][j].equals(b)){
                    y=j;
                }
            }
        }
        return y;
    }
    private static void undo(Coord start, Coord end, boolean player) {
        board.tile[end.getY()][end.getX()] = board.tile[start.getY()][start.getX()];
        board.tile[start.getY()][start.getX()] = null;
    }


/*

        while (true) {
            final String g = "";

            Resign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    g.concat(".");
                }
            });

            if (!g.isEmpty()) {
                Resign.setText("...");
                break;
            }

            String m = "";
            while(!movePiece(m,true, b)){
                m = move(b);
                if(movePiece(m,true, b)){
                 movePiece(m,true,b);
                 break;
                }
            }
            while(!movePiece(m,false,b)){
                m = move(b);
                if(movePiece(m,false,b)){
                    movePiece(m,false,b);
                    break;
                }
            }

            //need to implement gameover check
        }

*/









    private static boolean movePiece(Coord start, Coord end, boolean player) {




        //Check to make sure square is not blank
        if(board.tile[start.getY()][start.getX()] == null)
            return false;

        Tile startSquare = board.tile[start.getY()][start.getX()];

        //Check to make sure player is moving their own piece
        if((startSquare.player == false && player == true) ||
                (startSquare.player == true && player == false)) {

            return false;
        }

        if(!board.tile[start.getY()][start.getX()].piece.legalMove(start,end,board)){
            return false;
        }

        if(player&& whiteCheck()){

        }
        if(!player&& blackCheck()){

        }

        //Check input included a promotion character
        /*if(s.length() >= 7) {
            startSquare.piece.promotion = s.charAt(6);
        }

        //Check to make sure the specific piece (pawn, bishop, etc) is allowed to move in that direction
        if(!startSquare.piece.legalMove(start, end, board))
            return false;
            */

        //Player executed en passant
        if(startSquare.piece.enpassant) {
            board.tile[start.getY()][end.getX()] = null;
        }

        //Move Rook piece according to direction
        if(startSquare.piece.castling == '1') {
            board.tile[0][3] = board.tile[0][0];
            board.tile[0][0] = null;
        }
        if(startSquare.piece.castling == '2') {
            board.tile[0][5] = board.tile[0][7];
            board.tile[0][7] = null;
        }
        if(startSquare.piece.castling == '3') {
            board.tile[7][3] = board.tile[7][0];
            board.tile[7][0] = null;
        }
        if(startSquare.piece.castling == '4') {
            board.tile[7][5] = board.tile[7][7];
            board.tile[7][7] = null;
        }


        //Move the piece
        board.tile[end.getY()][end.getX()] = board.tile[start.getY()][start.getX()];
        board.tile[start.getY()][start.getX()] = null;

        return true;

    }

    private static boolean movePiece(Coord start, Coord end, boolean player, Board b) {




        //Check to make sure square is not blank
        if(b.tile[start.getY()][start.getX()] == null)
            return false;

        Tile startSquare = b.tile[start.getY()][start.getX()];

        //Check to make sure player is moving their own piece
        if((startSquare.player == false && player == true) ||
                (startSquare.player == true && player == false)) {

            return false;
        }

        //Check input included a promotion character
        /*if(s.length() >= 7) {
            startSquare.piece.promotion = s.charAt(6);
        }

        //Check to make sure the specific piece (pawn, bishop, etc) is allowed to move in that direction
        if(!startSquare.piece.legalMove(start, end, board))
            return false;
            */

        //Player executed en passant
        if(startSquare.piece.enpassant) {
            b.tile[start.getY()][end.getX()] = null;
        }

        //Move Rook piece according to direction
        if(startSquare.piece.castling == '1') {
            b.tile[0][3] = b.tile[0][0];
            b.tile[0][0] = null;
        }
        if(startSquare.piece.castling == '2') {
            b.tile[0][5] = b.tile[0][7];
            b.tile[0][7] = null;
        }
        if(startSquare.piece.castling == '3') {
            b.tile[7][3] = b.tile[7][0];
            b.tile[7][0] = null;
        }
        if(startSquare.piece.castling == '4') {
            b.tile[7][5] = b.tile[7][7];
            b.tile[7][7] = null;
        }


        //Move the piece
        b.tile[end.getY()][end.getX()] = b.tile[start.getY()][start.getX()];
        b.tile[start.getY()][start.getX()] = null;

        return true;

    }


    private static void resetEPvalue(boolean player, Board board) {

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {

                }
                else if(s.piece instanceof Pawn && s.player == player)
                    ((Pawn) board.tile[i][j].piece).doubleJump = false;
            }
        }
    }


    public static Coord getPosition(String s) {

        char c1 = s.charAt(0);
        char c2 = s.charAt(1);

        int x = c1 - 97;
        if(x < 0 || x > 7) // Out of bound
            return null;

        int y = 56 - c2;
        if(y < 0 || y > 7) // Out of bound
            return null;

        return new Coord(x, y);
    }
    private static boolean stalemate(boolean player) {
        Coord wK_pos = null, bK_pos = null, test;
        if (!player) {
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    Tile s = board.tile[i][j];
                    if(s == null) {
                        //Skip null spot
                    }
                    else if((s.piece instanceof King) && s.player == true)
                        wK_pos = new Coord(j , i);

                }

            }

            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    Tile s = board.tile[i][j];
                    if(s == null) {
                        //Skip null spot
                    }  else if(s.player == false) {
                        test = new Coord(j , i);
                        if(s.piece.legalMove(test, wK_pos, board))
                            return true; //White in check
                    }
                }
            }
        }
        else if(player) {
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    Tile s = board.tile[i][j];
                    if(s == null) {
                        //Skip null spot
                    }
                    else if((s.piece instanceof King) && s.player == false)
                        bK_pos = new Coord(j , i);

                }

            }

            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    Tile s = board.tile[i][j];
                    if(s == null) {
                        //Skip null spot
                    }  else if(s.player == true) {
                        test = new Coord(j , i);
                        if(s.piece.legalMove(test, bK_pos, board))
                            return true; //Black in check
                    }
                }
            }
        }
        return false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private static boolean detectCheck() {
        /**
         * Step 1) Find the position of Kings (This way it
         * Step 2) Traverse the board to find all pieces
         * Step 3) Find distance to current piece to enemy's King piece
         * Step 4) Test if piece can reach ememy's King piece
         */
        //Holds the location of each player's king, and the piece to compare
        Coord wK_pos = null, bK_pos = null, test;

        //Step 1)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }
                else if((s.piece instanceof King) && s.player == true)
                    wK_pos = new Coord(j , i);
                else if((s.piece instanceof  King) && s.player == false)
                    bK_pos = new Coord(j , i);

            }

        }

        //Step 2 and 3)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                } else if(s.player == true) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, bK_pos, board)) {
                        return true;    //Black in check

                    }
                } else if(s.player == false) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, wK_pos, board)) {
                        return true;    //White in check

                    }
                }
            }
        }

        return false;
    }

    /**
     * Detects if white's king is in check
     *
     * @return True if a player is in check, false otherwise
     */
    private static boolean whiteCheck() {
        /**
         * Step 1) Find the position of white's king
         * Step 2) Traverse the board to find all black pieces
         * Step 3) Find distance to current piece to white's King piece
         * Step 4) Test if piece can reach white's King piece
         */
        //Holds the location of each player's king, and the piece to compare
        Coord wK_pos = null, test;

        //Step 1)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }
                else if((s.piece instanceof King) && s.player == true)
                    wK_pos = new Coord(j , i);

            }

        }

        //Step 2 and 3)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }  else if(s.player == false) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, wK_pos, board))
                        return true; //White in check
                }
            }
        }

        return false;
    }

    private static boolean whiteCheck(Board b) {
        /**
         * Step 1) Find the position of white's king
         * Step 2) Traverse the board to find all black pieces
         * Step 3) Find distance to current piece to white's King piece
         * Step 4) Test if piece can reach white's King piece
         */
        //Holds the location of each player's king, and the piece to compare
        Coord wK_pos = null, test;

        //Step 1)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = b.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }
                else if((s.piece instanceof King) && s.player == true)
                    wK_pos = new Coord(j , i);

            }

        }

        //Step 2 and 3)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = b.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }  else if(s.player == false) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, wK_pos, b))
                        return true; //White in check
                }
            }
        }

        return false;
    }

    /**
     * Detects if white's king is in check
     *
     * @return True if a player is in check, false otherwise
     */
    private static boolean blackCheck() {
        /**
         * Step 1) Find the position of white's king
         * Step 2) Traverse the board to find all black pieces
         * Step 3) Find distance to current piece to white's King piece
         * Step 4) Test if piece can reach white's King piece
         */
        //Holds the location of each player's king, and the piece to compare
        Coord bK_pos = null, test;

        //Step 1)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }
                else if((s.piece instanceof King) && s.player == false)
                    bK_pos = new Coord(j , i);

            }

        }

        //Step 2 and 3)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = board.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }  else if(s.player == true) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, bK_pos, board))
                        return true; //Black in check
                }
            }
        }

        return false;
    }

    private static boolean blackCheck(Board b) {
        /**
         * Step 1) Find the position of white's king
         * Step 2) Traverse the board to find all black pieces
         * Step 3) Find distance to current piece to white's King piece
         * Step 4) Test if piece can reach white's King piece
         */
        //Holds the location of each player's king, and the piece to compare
        Coord bK_pos = null, test;

        //Step 1)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = b.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }
                else if((s.piece instanceof King) && s.player == false)
                    bK_pos = new Coord(j , i);

            }

        }

        //Step 2 and 3)
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                Tile s = b.tile[i][j];
                if(s == null) {
                    //Skip null spot
                }  else if(s.player == true) {
                    test = new Coord(j , i);
                    if(s.piece.legalMove(test, bK_pos, b))
                        return true; //Black in check
                }
            }
        }

        return false;
    }

    /**
     * Detects if a player is in Checkmate
     *
     * @param p The player to check for checkmate (w or b)
     * @return True if player is in checkmate, false otherwise
     */
    private static boolean detectMate(boolean p) {

        //Saves the firstMove variable
        boolean firstMove;

        //Loop through board and find white's pieces
        for(int a = 0; a < 8; a++) {
            for(int b = 0; b < 8; b++) {
                if(board.tile[a][b] != null && board.tile[a][b].player == p) {
                    Coord start = new Coord(b , a);

                    //Check every piece on the board to see if it can move there
                    for(int c = 0; c < 8; c++) {
                        for(int d = 0; d < 8; d++) {
                            Coord end = new Coord(d, c);

                            //Save firstMove variable
                            firstMove = board.tile[a][b].piece.firstMove;

                            //It can move to given piece
                            if(board.tile[a][b].piece.legalMove(start, end, board)) {

                                //Move the piece
                                Tile startSquare = board.tile[a][b];
                                Tile endSquare = board.tile[end.getY()][end.getX()];
                                board.tile[end.getY()][end.getX()] = board.tile[a][b];
                                board.tile[a][b] = null;

                                //Found a situation where player can move piece and not be in check anymore
                                if(!detectCheck()) {
                                    board.tile[end.getY()][end.getX()] = endSquare;
                                    board.tile[a][b] = startSquare;
                                    return false;
                                }

                                //Put the board back
                                board.tile[end.getY()][end.getX()] = endSquare;
                                board.tile[a][b] = startSquare;

                                //Reset the firstMove variable
                                board.tile[a][b].piece.firstMove = firstMove;

                            }
                        }
                    }
                }
            }
        }


        return true;
    }




}