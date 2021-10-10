/************************************************************
 * Othello.java
 * 詳細 : オセロ板表示、キー入力管理
 * date : 2019/06/24
 * @author 小林怜央
 *
 *Copyright (C) 2018 JAPANIACE Co.,Ltd. All Right Reserved.
 **********************************************************
 */

package college.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author 小林怜央
 *
 */
/**
 * @author jna
 *
 */
public class OthelloViewControl {
    /** 入力できるようにする. */
    private static InputStreamReader is = new InputStreamReader(System.in);
    /** 入力できるようにする. */
    private static BufferedReader br = new BufferedReader(is);
    /**OthelloModelのインスタンスを作成.*/
    private OthelloModel om = new OthelloModel();

    /**
     *
     * ボードに石を置いている.
     * getStoneで戻ってきた値がBLACKなら●、WHITEなら○,NONEなら.を表示<br>
     *
     */
    private void viewBoard() {
        //Y軸に番号を振る
        String num = "１２３４５６７８";
        System.out.println();
        //X軸に番号を振る
        System.out.println("　ＡＢＣＤＥＦＧＨ");
        for (int y = 0; y < OthelloModel.BOARD_SIZE; y++) {
            System.out.print(num.charAt(y));
            for (int x = 0; x < OthelloModel.BOARD_SIZE; x++) {
                if (om.getStone(y, x) == STONE.BLACK) {
                    System.out.print("●");
                } else if (om.getStone(y, x) == STONE.WHITE) {
                    System.out.print("〇");
                } else {
                    System.out.print("・");
                }
            }
            System.out.println();
        }
    }

    /**
     * 勝者を判定するメソッド.
     * BLACKとWHITEの枚数を数えて多かった方を勝者とする
     */
    private void judgeWinner() {
        /**勝利者をNONEで初期化.*/
        STONE winner = STONE.NONE;
        /**黒の枚数と白の枚数を0で初期化.*/
        int black = 0;
        int white = 0;
        /**石の枚数を調べる.*/
        for (int y = 0; y < OthelloModel.BOARD_SIZE; y++) {
            for (int x = 0; x < OthelloModel.BOARD_SIZE; x++) {
                if (om.getStone(y, x) == STONE.BLACK) {
                    black++;
                }
                if (om.getStone(y, x) == STONE.WHITE) {
                    white++;
                }
            }
        }
        /**枚数の多かった方を勝者にする.*/
        if (black > white) {
            winner = STONE.BLACK;
        } else if (white > black) {
            winner = STONE.WHITE;
        }
        /**勝敗表示.*/
        if (winner == STONE.BLACK) {
            System.out.println("●：" + black + "対〇：" + white + "で黒の勝です。");
        } else if (winner == STONE.WHITE) {
            System.out.println("●：" + black + "対〇：" + white + "で白の勝です。");
        } else {
            System.out.println("●：" + black + "対〇：" + white + "で引き分けです。");
        }
    }

    /**
     * オセロをスタートさせるメソッド.
     *お互いにひっくり返せる石がなくなるまでループさせる。<br>
     *入力させた値が空白、英数字以外、負の値なら弾く<br>
     */
    public void startOthello() {
        STONE attacker;
        String str;
        char[] alpha = new char[2];
        boolean isEnd = false;
        while (!isEnd) {
            viewBoard();
            isEnd = om.isGameEnd();
            attacker = om.getAttacker();
            int xpos = -1;
            int ypos = -1;
            boolean canReverse = false;
            while (!canReverse) {
                /**入力した値が不正でないか調べる.*/
                if (attacker == STONE.BLACK) {
                    System.out.print("●の位置を入力してください：");
                } else if (attacker == STONE.WHITE) {
                    System.out.print("○の位置を入力してください：");
                }
                try {
                    str = br.readLine();
                    if(str != null) {
                    str = str.trim();
                    }
                } catch (IOException e) {
                    str = "";
                    System.out.println("入力エラー！");
                }
                alpha[0] = str.charAt(0);
                alpha[1] = str.charAt(1);
                xpos = -1;
                ypos = -1;
                /**アルファベットであるか判定、数字であるか判定する.*/
                for (int i = 0; i < 2; i++) {
                    if ('A' <= alpha[i] && alpha[i] <= 'Z') {
                        xpos = alpha[i] - 'A';
                    } else if ('a' <= alpha[i] && alpha[i] <= 'z') {
                        xpos = alpha[i] - 'a';
                    } else if ('1' <= alpha[i] && alpha[i] <= '8') {
                        ypos = alpha[i] - '1';
                    }
                }
                /**正常な値ならcanReverseにY座標とX座標を渡す.*/
                if (0 <= ypos && 0 <= xpos) {
                    canReverse = om.isEnable(ypos, xpos);
                } else {
                    System.out.println("入力エラー！");
                }
            }
            om.reverseStone(ypos, xpos);
        }
        /**どちらも石を置く場所がなくなりループを抜けたら勝者判定.*/
        judgeWinner();
    }
}
