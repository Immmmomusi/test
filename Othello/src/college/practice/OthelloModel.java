/************************************************************
 * Othello.java
 * 詳細 : オセロゲームのロジック部
 * date : 2019/06/21
 * @author 小林怜央
 */
//Copyright (C) 2018 JAPANIACE Co.,Ltd. All Right Reserved.
//***********************************************************

package college.practice;

/**
 * 列挙型でオセロの色を宣言/.
 *
 *
 */
enum STONE {
    /**色の初期化.
     * NONE 空白
     * WHITE 白
     * BLACK 黒
     * */
    NONE, WHITE, BLACK,
}

/************************************************
 * オセロゲームのロジック部クラス。
 ************************************************/

/**
 * @author jna
 *
 */
public class OthelloModel {
    /**
     * ボードのサイズを8で初期化.
     */
    static final int BOARD_SIZE = 8;
    /**
     * 方向検索につかう方向を最大8で初期化.
     */
    static final int MAX_DIRECTION = 8;
    /**
     * 最初のプレイヤーを黒色で初期化.
     */
    private STONE attacker = STONE.BLACK;
    /**
     * ボードを8×8で初期化.
     */
    private STONE[][] board = new STONE[OthelloModel.BOARD_SIZE][OthelloModel.BOARD_SIZE];

    /**
     * OthelloModelのコンストラクタ呼び出しでintialGame()を呼ぶ.
     */
    OthelloModel() {
        initialGame();
    }
    public STONE[][] getBoard() {
    	return this.board;
    }

    /**
     *
     *ゲーム開始の準備.
     *ボードを全て石が無い状態にしたあと、ボードの中心に<br>
     *○●<br>
     *●○<br>
     *となるように石を配置する。<br>
     *先攻は黒<br>
     * @param なし
     */
    private void initialGame() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = STONE.NONE;
            }
        }
        board[BOARD_SIZE / 2][BOARD_SIZE / 2] = STONE.WHITE;
        board[BOARD_SIZE / 2 - 1][BOARD_SIZE / 2 - 1] = STONE.WHITE;
        board[BOARD_SIZE / 2 - 1][BOARD_SIZE / 2] = STONE.BLACK;
        board[BOARD_SIZE / 2][BOARD_SIZE / 2 - 1] = STONE.BLACK;
        attacker = STONE.BLACK;
    }

    /**
     *
     * STONEのgetter.
     * 指定された位置がボードの大きさにおさまっていればその位置の石の色を返す。<br>
     * @param ypos Y座標<br>
     * @param xpos X座標<br>
     * @return STONE型の値<br>
     */
    public STONE getStone(final int ypos, final int xpos) {
        //返すためのSTONE型の変数を空白で初期化
        STONE stone = STONE.NONE;
        //Y座標X座標がボードの大きさより小さければその位置に空白を返す
        if (0 <= ypos && ypos < BOARD_SIZE
                && 0 <= xpos && xpos < BOARD_SIZE) {
            stone = board[ypos][xpos];
        }
        return stone;
    }

    /**
     * 攻撃する側の色を返す.
     * @return STONE型の変数
     */
    public STONE getAttacker() {
        return attacker;
    }

    /**
     * ゲームが進行可能かを判別するメソッド.
     * 最初に現在のattackerにひっくり返せる石があるか調べて、なければそのターンをスキップさせてattackerを入れ替える。<br>
     * 入れ替わった方のattackerにもひっくり返せる石がなければisEndにtrueを入れて返す。<br>
     * @param なし<br>
     * @return boolean型の値<br>
     *
     */
    public boolean isGameEnd() {
        boolean isEnd = false;
        boolean isSkip = true;
        /**
         *isEnableを使って石を置く場所があるか調べている.<br>
         *あればisSkipにfalseを代入して終わり<br>
         *
         */
        Loop1: for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (board[y][x] == STONE.NONE) {
                    boolean enable = isEnable(x, y);
                    if (enable) {
                        isSkip = false;
                        break Loop1;
                    }
                }
            }
        }
        /**
         * isSkipがtrueならそのターンをスキップして攻撃側を入れ替える. <br>
         * 入れ替えた攻撃側も置く場所がなければisEndにfalseを入れて返し、ゲームを終わらせる。<br>
         *
         */
        if (isSkip) {
            if (attacker == STONE.BLACK) {
                attacker = STONE.WHITE;
            } else {
                attacker = STONE.BLACK;
            }
            isEnd = true;
            Loop2: for (int y = 0; y < BOARD_SIZE; y++) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    if (board[y][x] == STONE.NONE) {
                        boolean enable = isEnable(x, y);
                        if (enable) {
                            isEnd = false;
                            break Loop2;
                        }
                    }
                }
            }
        }
        return isEnd;
    }

    // @formatter:off
    /** 指定した方向を調べるためのY座標.*/
    private int[] ynotch = {-1, -1, 0, 1, 1, 1, 0, -1 };
    /** 指定した方向を調べるためのX座標.*/
    private int[] xnotch = {0, 1, 1, 1, 0, -1, -1, -1 };
    // @formatter:on

    /**
     *
     * 方向ごとに返せる石があるか調べているメソッド.
     * ynotch,xnotchを使い時計回りに調べていく<br>
     * @param ypos Y座標<br>
     * @param xpos X座標<br>
     * @param direction 調べる方向<br>
     * @return boolean型の変数<br>
     */
    private boolean canReverseOne(final int ypos, final int xpos, final int direction) {
        boolean reverse = false;
        int y = 0;
        int x = 0;
        int count = 0;
        /**
         * 引数から得た座標から引数の方向へ1マスずつ調べていく.
         * 座標が負の値だったりボードの大きさを超えている場合、調べた位置に石が置かれてなかった場合、隣が同じ色の石だった場合にfalseを返す<br>
         * 隣が違う色だった場合、自分と同じ色の石が見つかるまでループさせtrueを返す<br>
         * 隣が違う色だった場合でも石が置かれてなかった場合はfalseを返す
         */
        for (int next = 1; next < BOARD_SIZE+1; next++) {
            y = ypos + ynotch[direction] * next;
            x = xpos + xnotch[direction] * next;
            /**指定した位置が0以下だったりボードのサイズより大きかったりしたらループを抜ける*/
            if (y < 0 || BOARD_SIZE <= y || x < 0 || BOARD_SIZE <= x) {
                count = 0;
                break;
            }
            /**指定した位置に自分と同じ石の色があればループを抜ける*/
            if (board[y][x] == attacker) {
                break;
              /**指定した位置に石がなければループを抜ける*/
            } else if (board[y][x] == STONE.NONE) {
                count = 0;
                break;
            }
            count++;
        }
        if (0 < count) {
            reverse = true;
        }
        return reverse;
    }

    /**
     *
     * ひっくり返せる石があるか調べるメソッド.
     *canReverseOneに位置と、調べる方向の値を渡す<br>
     *@param ypos Y座標<br>
     *@param xpos X座標<br>
     *@return bookean型の変数
     */
    private boolean canReverseAll(final int ypos, final int xpos) {
        boolean canReverse = false;
        /**
         * 引数から得た座標を軸にして8方向にひっくり返せる石があるか調べる.<br>
         * 見つかった時点でループを抜けtrueを返す。<br>
         * 見つからなければfalseを返す
         */

        for (int direction = 0; direction < MAX_DIRECTION; direction++) {
            canReverse = canReverseOne(ypos, xpos, direction);
            if (canReverse) {
                break;
            }
        }
        return canReverse;
    }

    /**
     *
     * このターン石を置く場所があるか調べるメソッド.
     *置く場所があればcanReverseAllに位置を渡す。<br>
     *@param ypos Y座標<br>
     *@param xpos X座標<br>
     *@return boolean型の変数
     */
    public boolean isEnable(final int ypos, final int xpos) {
        boolean canReverse = false;
        /**
         * 引数の座標がボードにおさまっているか調べる.<br>
         * おさまっていれば座標の引数を渡してひっくり返せる石があるか調べてもらう。<br>
         * みつかればtrueを返す
         */
        if (0 <= ypos && ypos < BOARD_SIZE && 0 <= xpos && xpos < BOARD_SIZE) {
            if (board[ypos][xpos] == STONE.NONE) {
                canReverse = canReverseAll(ypos, xpos);
            }
        }
        return canReverse;
    }

    /**
     * 石をひっくり返すメソッド.
     *渡された方向へ自分とおなじ石が見つかるまで石をひっくり返し続ける<br>
     *@param ypos Y座標<br>
     *@param xpos X座標<br>
     *@param direction 調べる方向
     */
    private void reverseStoneOne(final int ypos, final int xpos, final int direction) {
        int y;
        int x;
        /**
         * 引数の座標から引数の方向へ石をひっくり返していく.<br>
         *座標が負の値だったり、ボードの大きさにおさまっていない場合、自分と同じ石が見つかった場合、調べた位置に石が無かった場合にループを抜ける<br>
         *もしひっくり返しながら調べていった先に石が無かったり、ボードに収まりきらない座標が代入されてしまった場合でも、<br>
         *ひっくり返えした石はそのままになっているが、<br>
         *canReverseOneメソッドにより確実に自分の色の石ではさまれていることが保障されているので大丈夫
         *
         */
        for (int next = 1; next < BOARD_SIZE; next++) {
            y = ypos + ynotch[direction] * next;
            x = xpos + xnotch[direction] * next;
            if (y < 0 || BOARD_SIZE <= y
                    || x < 0 || BOARD_SIZE <= x) {
                break;
            }
            if (board[y][x] == attacker) {
                break;
            } else if (board[y][x] == STONE.NONE) {
                break;
            }
            board[y][x] = attacker;
        }
    }

    /**
     * 石をひっくり返せる方向を調べるメソッド.
     *@param ypos Y座標<br>
     *@param xpos X座標<br>
     */
    public void reverseStone(final int ypos, final int xpos) {
        /**入力した位置が不正出ないか調べる.*/
        if (0 <= ypos && ypos <= BOARD_SIZE && 0 <= xpos && xpos <= BOARD_SIZE) {
            boolean canReverse = false;
            for (int direction = 0; direction < MAX_DIRECTION; direction++) {
                /**
                 *canReverseOneを使い渡した方向の石がひっくり返せるかどうか調べる.<br>
                 *ひっくり返せるようならreverseStoneOneに位置と方向をわたす
                 */
                if (canReverseOne(ypos, xpos, direction)) {
                    reverseStoneOne(ypos, xpos, direction);
                    canReverse = true;
                    /**break文を記述してしまうと2方向以上にひっくり返すことができない.*/
                    break;
                }
            }
            /**ひっくり返すことができればattackerを入れ替える.*/
            if (canReverse) {
                board[ypos][xpos] = attacker;
                if (attacker == STONE.BLACK) {
                    attacker = STONE.WHITE;
                } else {
                    attacker = STONE.BLACK;
                }
            }
        }
    }

    /**
     * デバック用？.
     * @param ypos X座標
     * @param xpos Y座標
     * @param stone 石の色
     */
    public void setStoneForTest(final int ypos, final int xpos, final STONE stone) {
        if (0 <= ypos && ypos < BOARD_SIZE
                && 0 <= xpos && xpos < BOARD_SIZE) {
            board[ypos][xpos] = stone;
        }
    }

    /**
     * デバック用？.
     * @param stone 石の色
     */
    public void setAttackerForTest(final STONE stone) {
        attacker = stone;
    }
    public void viewBoardTest(final STONE[][] board) {
        //Y軸に番号を振る
        String num = "１２３４５６７８";
        System.out.println();
        //X軸に番号を振る
        System.out.println("　ＡＢＣＤＥＦＧＨ");
        for (int y = 0; y < OthelloModel.BOARD_SIZE; y++) {
            System.out.print(num.charAt(y));
            for (int x = 0; x < OthelloModel.BOARD_SIZE; x++) {
                if (getStone(y, x) == STONE.BLACK) {
                    System.out.print("●");
                } else if (getStone(y, x) == STONE.WHITE) {
                    System.out.print("〇");
                } else {
                    System.out.print("・");
                }
            }
            System.out.println();
        }
    }
}
