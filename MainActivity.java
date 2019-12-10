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
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    public Board b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        b = new Board();


        final Button Resign = findViewById(R.id.Resign);

        final Button[][] boardClick =
                {{findViewById(R.id.a8),findViewById(R.id.b8),findViewById(R.id.c8),findViewById(R.id.d8),findViewById(R.id.e8),findViewById(R.id.f8),findViewById(R.id.g8),findViewById(R.id.h8)},{findViewById(R.id.a7),findViewById(R.id.b7),findViewById(R.id.c7),findViewById(R.id.d7),findViewById(R.id.e7),findViewById(R.id.f7),findViewById(R.id.g7),findViewById(R.id.h7)},
                        {findViewById(R.id.a6),findViewById(R.id.b6),findViewById(R.id.c6),findViewById(R.id.d6),findViewById(R.id.e6),findViewById(R.id.f6),findViewById(R.id.g6),findViewById(R.id.h6)},{findViewById(R.id.a5),findViewById(R.id.b5),findViewById(R.id.c5),findViewById(R.id.d5),findViewById(R.id.e5),findViewById(R.id.f5),findViewById(R.id.g5),findViewById(R.id.h5)},
                        {findViewById(R.id.a4),findViewById(R.id.b4),findViewById(R.id.c4),findViewById(R.id.d4),findViewById(R.id.e4),findViewById(R.id.f4),findViewById(R.id.g4),findViewById(R.id.h4)},{findViewById(R.id.a3),findViewById(R.id.b3),findViewById(R.id.c3),findViewById(R.id.d3),findViewById(R.id.e3),findViewById(R.id.f3),findViewById(R.id.g3),findViewById(R.id.h3)},
                        {findViewById(R.id.a2),findViewById(R.id.b2),findViewById(R.id.c2),findViewById(R.id.d2),findViewById(R.id.e2),findViewById(R.id.f2),findViewById(R.id.g2),findViewById(R.id.h2)},{findViewById(R.id.a1),findViewById(R.id.b1),findViewById(R.id.c1),findViewById(R.id.d1),findViewById(R.id.e1),findViewById(R.id.f1),findViewById(R.id.g1),findViewById(R.id.h1)}};

        final int[] i = new int[1];
        i[0]=0;

        for(i[0]=0;i[0]<200;i[0]++){
            final boolean[] gameOver = new boolean[1];
            final Coord[] move = new Coord[2];
            gameOver[0]=false;
            move[0]=new Coord();
            move[1]=new Coord();

            Resign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameOver[0] = true;
                    if(gameOver[0]){
                        Resign.setText("True");
                    }
                }
            });

            if(gameOver[0]){
                Resign.setText("aa");
                break;
            }

            final int[] m = new int[1];
            m[0] = 0;

            final int[] n = new int[1];
            n[0] = 0;

            for(m[0]=0;m[0]<8;m[0]++){
                for(n[0]=0;n[0]<8;n[0]++){
                    //boardClick[m[0]][n[0]].setText(m[0]+" "+n[0]);
                    boardClick[m[0]][n[0]].setText(b.tile[n[0]][m[0]].tosString(b.tile[n[0]][m[0]]));
                    //this line is giving me trouble
                }
            }

            if((move[0].getX()==-1)||(move[1].getX()==-1)){

                final int[] j = new int[1];
                j[0]=0;
                final int[] k = new int[1];
                k[0]=0;

                for(j[0]=0;j[0]<8;j[0]++) {
                  for (k[0] = 0; k[0] < 8; k[0]++) {
                    boardClick[j[0]][k[0]].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (move[0].getX() == -1) {
                                move[0] = new Coord(k[0], j[0]);
                            } else if (move[1].getX() == -1) {
                                move[1] = new Coord(k[0], j[0]);
                            }
                        }
                    });
                  }
                 }
            }





        }
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




    public String[] move(){
        final String[] m = new String[2];
        m[0] = "";
        m[1] = "";

        final Button a1 = findViewById(R.id.a1);

        while((m[0].isEmpty()) || (m[1].isEmpty())) {

            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!m[0].isEmpty()) {
                        m[0]="a1";
                    } else {
                        m[1]="a1";
                    }
                    a1.setText(m[0]+" "+m[1]);
                }
            });



            //if these 5 buttons work I'll add all 64 other buttons
        }

        return m;



    }


    private static boolean movePiece(String s, boolean player, Board board) {


        if(!(s.length() == 5 || s.length() == 11))
            return false;

        if(s.charAt(0) > 'h' || s.charAt(0) < 'a')
            return false;
        if(s.charAt(1) > '8' || s.charAt(1) < '1')
            return false;

        if(s.charAt(2) != ' ')
            return false;
        if(s.charAt(3) > 'h' || s.charAt(3) < 'a')
            return false;
        if(s.charAt(4) > '8' || s.charAt(4) < '1')
            return false;

        Coord start = getPosition(s.substring(0,2));
        Coord end = getPosition(s.substring(3,5));


        //Check to make sure square is not blank
        if(board.tile[start.getY()][start.getX()] == null)
            return false;

        Tile startSquare = board.tile[start.getY()][start.getX()];

        //Check to make sure player is moving their own piece
        if((startSquare.player == false && player == true) ||
                (startSquare.player == true && player == false)) {

            return false;
        }

        //Check input included a promotion character
        if(s.length() >= 7) {
            startSquare.piece.promotion = s.charAt(6);
        }

        //Check to make sure the specific piece (pawn, bishop, etc) is allowed to move in that direction
        if(!startSquare.piece.legalMove(start, end, board))
            return false;

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
    private static boolean stalemate(boolean player, Board board) {
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




}
