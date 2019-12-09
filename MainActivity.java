//this is for testing if the buttons work like they should

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean GameOver = false;
        Board b = new Board();

        while(GameOver==false){
            String m = "";
            while(!movePiece(m,true)){
                m = move(b);
                if(movePiece(m,true)){
                 movePiece(m,true);
                 break;
                }
            }
            while(!movePiece(m,false)){
                m = move(b);
                if(movePiece(m,false)){
                    movePiece(m,false);
                    break;
                }
            }

            //need to implement gameover check
        }




    }

    public String move(final Board b){
        final String m = "";

        Button a1 = findViewById(R.id.a1);

        while(m.length()<6) {

            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!m.isEmpty()) {
                        m.concat("a1");
                    } else {
                        m.concat(" a1");
                    }
                    return;
                }
            });

            Button a2 = findViewById(R.id.a2);

            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!m.isEmpty()) {
                        m.concat("a2");
                    } else {
                        m.concat(" a2");
                    }
                    return;
                }
            });
            Button a3 = findViewById(R.id.a3);

            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!m.isEmpty()) {
                        m.concat("a3");
                    } else {
                        m.concat(" a3");
                    }
                    return;
                }
            });
            Button a4 = findViewById(R.id.a4);

            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!m.isEmpty()) {
                        m.concat("a4");
                    } else {
                        m.concat(" a4");
                    }
                    return;
                }
            });
            Button a5 = findViewById(R.id.a5);

            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!m.isEmpty()) {
                        m.concat("a5");
                    } else {
                        m.concat(" a5");
                    }
                    return;
                }
            });

            //if these 5 buttons work I'll add all 64 other buttons
        }

        return m;



    }


    private static boolean movePiece(String s, boolean player) {

        /**
         * Only accepts regular input (+ draw?)
         * Anything else is considered invalid input (except resign or draw)
         */
        if(!(s.length() == 5 || s.length() == 11))
            return false;

        /**
         * "Spell checking" for regular input
         */
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


    private static void resetEPvalue(boolean player) {

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

    /**
     * Rule for stalemate:
     * 1. No pieces are able to move except for King piece
     * 2. King piece is in a position where if it moves then it is in check
     *
     * @return true if stalemate applies, false otherwise
     */
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
}