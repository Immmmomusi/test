package college.practice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OthelloModelTest {

	@Test
	void testGetStone() {
		OthelloModel om = new OthelloModel();
		assertEquals(STONE.NONE, om.getStone(0, 0));
		assertEquals(STONE.WHITE, om.getStone(3, 3));
		assertEquals(STONE.WHITE, om.getStone(4, 4));
		assertEquals(STONE.BLACK, om.getStone(3, 4));
		assertEquals(STONE.BLACK, om.getStone(4, 3));

	}

	@Test
	void testGetAttacker() {
		OthelloModel om = new OthelloModel();
		assertEquals(STONE.BLACK, om.getAttacker());

	}

	@Test
	void testIsGameEnd() {
		OthelloModel om = new OthelloModel();
		//起動直後
		assertEquals(false, om.isGameEnd());
	}

	@Test
	void testIsGameEnd_Full() {
		OthelloModel om = new OthelloModel();
		//全部埋まっている
		setBoardPtn(ptnIsGameEndTest_Full, om);
		om.viewBoardTest(om.getBoard());
		om.setAttackerForTest(STONE.WHITE);
		assertEquals(true, om.isGameEnd());
		om.setAttackerForTest(STONE.BLACK);
		assertEquals(true, om.isGameEnd());

	}

	@Test
	void testIsGameEnd_DoubleOk() {
		OthelloModel om = new OthelloModel();
		//どちらもおける
		setBoardPtn(ptnIsGameEndTest_AllOk, om);
		om.viewBoardTest(om.getBoard());
		om.setAttackerForTest(STONE.WHITE);
		assertEquals(false, om.isGameEnd());
		om.setAttackerForTest(STONE.BLACK);
		assertEquals(false, om.isGameEnd());
	}

	@Test
	void testIsGameEnd_WHITEOnly() {
		OthelloModel om = new OthelloModel();
		//白しかおけない
		setBoardPtn(ptnIsGameEndTest_WHITEOk, om);
		om.viewBoardTest(om.getBoard());
		om.setAttackerForTest(STONE.BLACK);
		assertEquals(false, om.isGameEnd());//白に交代
		assertEquals(STONE.WHITE, om.getAttacker());
		om.setAttackerForTest(STONE.WHITE);
		assertEquals(false, om.isGameEnd());
		assertEquals(STONE.WHITE, om.getAttacker());
	}

	@Test
	void testIsGameEnd_BLACKOnly() {
		OthelloModel om = new OthelloModel();
		//黒しかおけない
		setBoardPtn(ptnIsGameEndTest_BLACKOk, om);
		om.setAttackerForTest(STONE.WHITE);
		om.viewBoardTest(om.getBoard());
		assertEquals(false, om.isGameEnd());//黒に交代
		assertEquals(STONE.BLACK, om.getAttacker());
		om.setAttackerForTest(STONE.BLACK);
		assertEquals(false, om.isGameEnd());
		assertEquals(STONE.BLACK, om.getAttacker());
	}

	@Test
	void testIsGameEnd_DoubleOut() {
		OthelloModel om = new OthelloModel();
		//空きはあるが白も黒もおけない
		setBoardPtn(ptnIsGameEndTest_EmptyBLACK, om);
		om.viewBoardTest(om.getBoard());
		om.setAttackerForTest(STONE.BLACK);
		assertEquals(true, om.isGameEnd());
		setBoardPtn(ptnIsGameEndTest_EmptyWHITE, om);
		om.viewBoardTest(om.getBoard());
		om.setAttackerForTest(STONE.WHITE);
		assertEquals(true, om.isGameEnd());
	}

	int[][] ptnIsGameEndTest_Full = {
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1 },
	};
	int[][] ptnIsGameEndTest_AllOk = {
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 2, 1, 0, 0, 0 },
			{ 0, 0, 0, 1, 2, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
	};
	int[][] ptnIsGameEndTest_WHITEOk = {
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 1, 0, 0, 0, 0, 0, 1 },
			{ 0, 0, 1, 0, 0, 1, 1, 2 },
			{ 0, 0, 1, 1, 1, 1, 2, 2 },
			{ 0, 0, 1, 1, 1, 2, 2, 2 },
			{ 0, 0, 0, 1, 2, 2, 2, 2 },
			{ 0, 0, 0, 2, 2, 2, 2, 2 },
			{ 0, 0, 0, 1, 2, 2, 2, 2 },
	};
	int[][] ptnIsGameEndTest_BLACKOk = {
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 2, 2, 0, 0, 0, 0, 0 },
			{ 1, 1, 2, 0, 0, 0, 0, 0 },
			{ 1, 1, 2, 0, 0, 0, 0, 0 },
			{ 1, 1, 2, 0, 0, 0, 0, 0 },
	};
	int[][] ptnIsGameEndTest_EmptyBLACK = {
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 0, 0, 0, 1, 1, 0, 0, 0 },
			{ 0, 1, 1, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
	};
	int[][] ptnIsGameEndTest_EmptyWHITE = {
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 2, 0, 0, 0 },
			{ 0, 0, 0, 2, 2, 2, 0, 0 },
			{ 0, 0, 2, 2, 2, 2, 2, 0 },
			{ 0, 0, 0, 2, 2, 2, 0, 0 },
			{ 0, 0, 0, 0, 2, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
	};

	@Test
	void testIsEnable_Equal() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnEnableTestX11T, om);
		//同値テスト
		assertEquals(true, om.isEnable(3, 2));
		assertEquals(false, om.isEnable(-10, 2));
		assertEquals(false, om.isEnable(10, 2));
	}

	@Test
	void testIsEnable_Border() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnEnableTestX11T, om);
		//境界値テスト
		assertEquals(true, om.isEnable(0, 0));
		assertEquals(true, om.isEnable(7, 7));
		assertEquals(false, om.isEnable(-1, 0));
		assertEquals(false, om.isEnable(7, 8));
	}

	@Test
	void testIsEnable_Xdirection() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnEnableTestX11T, om);
		assertEquals(false, om.isEnable(0x7FFFFFFF, 0x80000000));
		//X方向テスト
		assertEquals(true, om.isEnable(3, 2));//X方向の反転可否
		assertEquals(true, om.isEnable(0, 0));//X方向の最大
		assertEquals(true, om.isEnable(1, 0));//X方向の最小
		setBoardPtn(ptnEnableTestX11F, om);
		assertEquals(false, om.isEnable(0, 7));//X方向の隣が盤の外
		assertEquals(false, om.isEnable(1, 6));//X方向の隣がNONE
		assertEquals(false, om.isEnable(6, 2));//X方向に相手の石が続いた先にNONE
		assertEquals(false, om.isEnable(5, 2));//X方向に相手の石が続いた先が盤の外
		assertEquals(false, om.isEnable(7, 0));
		setBoardPtn(ptnEnableTestY11T, om);
	}

	@Test
	void testIsEnable_Ydirection() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnEnableTestY11T, om);
		om.setAttackerForTest(STONE.BLACK);
		//Y方向テスト
		assertEquals(true, om.isEnable(6, 5));//Y方向の反転の可否
		assertEquals(true, om.isEnable(7, 0));//Y方向の最大
		assertEquals(true, om.isEnable(3, 3));//Y方向の最小
		setBoardPtn(ptnEnableTestY11F, om);
		assertEquals(false, om.isEnable(0, 4));//Y方向の隣が盤の外
		assertEquals(false, om.isEnable(1, 5));//Y方向の隣がNONE
		assertEquals(false, om.isEnable(7, 0));//Y方向に相手の石が続いた先にNONE
		assertEquals(false, om.isEnable(3, 2));//Y方向に相手の石が続いた先が盤の外
		assertEquals(false, om.isEnable(7,7));
		setBoardPtn(ptnEnableTestAllDirection11T, om);
	}

	@Test
	void testIsEnable_Alldirection() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnEnableTestAllDirection11T, om);
		//時計回りに反転可否をテストしていく
		assertEquals(true, om.isEnable(3, 4));
		assertEquals(true, om.isEnable(7, 0));
		assertEquals(true, om.isEnable(4, 0));
		assertEquals(true, om.isEnable(0, 0));
		assertEquals(true, om.isEnable(3, 3));
		assertEquals(true, om.isEnable(0, 7));
		assertEquals(true, om.isEnable(4, 7));
		assertEquals(true, om.isEnable(7, 7));
	}

	//X方向trueテスト　1:BLACK, 2:WHITE, 3:attacker
	int[][] ptnEnableTestX11T = {
			{ 3, 2, 2, 2, 2, 2, 2, 1 },
			{ 3, 2, 2, 1, 0, 0, 0, 0 },
			{ 3, 2, 2, 2, 2, 2, 2, 2 },
			{ 0, 0, 3, 2, 1, 0, 0, 0 },
			{ 0, 0, 0, 1, 2, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 2 },
			{ 0, 0, 0, 0, 0, 0, 0, 3 },
	};
	//X方向falseテスト　1:BLACK, 2:WHITE, 3:attacker
	int[][] ptnEnableTestX11F = {
			{ 0, 0, 0, 0, 0, 0, 0, 3 },
			{ 0, 0, 0, 0, 0, 0, 3, 0 },
			{ 0, 0, 0, 1, 1, 0, 0, 0 },
			{ 0, 0, 0, 2, 2, 0, 0, 0 },
			{ 0, 0, 0, 2, 2, 0, 0, 0 },
			{ 0, 0, 3, 2, 2, 2, 2, 2 },
			{ 0, 0, 3, 2, 2, 0, 1, 0 },
			{ 3, 2, 2, 2, 2, 2, 2, 2 },
	};
	//Y方向trueテスト　1:BLACK, 2:WHITE, 3:attacker
	int[][] ptnEnableTestY11T = {
			{ 1, 0, 0, 1, 0, 0, 0, 0 },
			{ 2, 0, 0, 2, 0, 0, 0, 0 },
			{ 2, 0, 0, 2, 0, 0, 0, 0 },
			{ 2, 0, 0, 3, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 1, 0, 0 },
			{ 2, 0, 0, 0, 0, 2, 0, 0 },
			{ 2, 0, 0, 0, 0, 3, 0, 0 },
			{ 3, 0, 0, 0, 0, 0, 0, 0 },

	};
	//Y方向falseテスト　1:BLACK, 2:WHITE, 3:attacker
	int[][] ptnEnableTestY11F = {
			{ 1, 0, 2, 0, 3, 0, 0, 2 },
			{ 0, 0, 2, 0, 0, 3, 0, 2 },
			{ 2, 0, 2, 0, 0, 0, 0, 2 },
			{ 2, 0, 2, 0, 0, 0, 0, 2 },
			{ 2, 0, 3, 0, 0, 0, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 2 },
			{ 2, 0, 0, 0, 0, 0, 0, 2 },
			{ 3, 0, 0, 0, 0, 0, 0, 3 },
	};
	//全方向trueテスト　1:BLACK, 2:WHITE, 3:attacker
	int[][] ptnEnableTestAllDirection11T = {
			{ 3, 0, 0, 0, 0, 0, 0, 3 },
			{ 0, 2, 0, 0, 1, 0, 2, 0 },
			{ 0, 0, 1, 0, 2, 1, 0, 0 },
			{ 0, 0, 0, 3, 3, 0, 0, 0 },
			{ 3, 2, 1, 2, 0, 1, 2, 3 },
			{ 0, 0, 1, 1, 0, 1, 0, 0 },
			{ 0, 2, 0, 0, 0, 0, 2, 0 },
			{ 3, 0, 0, 0, 0, 0, 0, 3 },
	};

	@Test
	void testReverseStone_ForTest() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnReverseStoneTestX11, om);
		om.reverseStone(2, 0);//forの最小ループと入力文字判定のif文true
		om.reverseStone(7, 7);//forの最大ループ
		assertEquals(true, isEqualPtn(ptnReverseStoneTestX11Ans, om));
	}

	//ループの最大,最小のテスト　1:BLACK, 2:WHITE, 3:attacker
	int[][] ptnReverseStoneTestX11 = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 3, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 2, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 3 },
	};
	//反転後のボード
	int[][] ptnReverseStoneTestX11Ans = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 2, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 2, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 2 },
	};

	void testReverseStone_If_thenTest() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnReverseStoneTestX13, om);
		om.setAttackerForTest(STONE.BLACK);
		om.reverseStone(2, 0);//全てのif文true
		assertEquals(true, isEqualPtn(ptnReverseStoneTestX13Ans, om));
		assertEquals(STONE.WHITE, om.getAttacker());//攻撃側も交代できている
	}

	//ループのif文のthenテスト　1:BLACK, 2:WHITE, 3:attacker
	int[][] ptnReverseStoneTestX13 = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 3, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 2, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 3 },
	};
	//反転後のボード
	int[][] ptnReverseStoneTestX13Ans = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 2, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 3 },
	};

	@Test
	void testReverseStone_If_elseTest() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnReverseStoneTestX12, om);
		om.setAttackerForTest(STONE.WHITE);
		om.reverseStone(-1, -1);//入力文字判定のif文で0未満の値false
		om.reverseStone(20, 20);//入力文字判定のif文で8を超える値false
		assertEquals(false, isEqualPtn(ptnReverseStoneTestX12Ans, om));
		om.reverseStone(5, 5);//反転可否判定のif文と反転成否判定のif文false
		assertEquals(false, isEqualPtn(ptnReverseStoneTestX12Ans, om));
		assertEquals(STONE.WHITE, om.getAttacker());//攻撃側の色が入れ替わっていない
		om.reverseStone(7, 7);//反転を成功させ攻撃側の交代判定まで処理させる
		assertEquals(true, isEqualPtn(ptnReverseStoneTestX12Ans, om));
		assertEquals(STONE.BLACK, om.getAttacker());//交代判定でelseを処理させる
	}

	//ループのif文のelseテスト　1:BLACK, 2:WHITE, 3:attacker
	int[][] ptnReverseStoneTestX12 = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 3, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 2, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 3 },
	};
	//反転後のボード
	int[][] ptnReverseStoneTestX12Ans = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 3, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 2, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 2, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 2 },
	};

	@Test
	void reverseStoneOne_ForTest() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnReverseStoneOneTestX11, om);
		om.setAttackerForTest(STONE.BLACK);
		om.reverseStone(7, 0);//ループの最大
		om.reverseStone(7, 7);//ループの最小
		assertEquals(true, isEqualPtn(ptnReverseStoneOneTestX11Ans, om));
	}

	@Test
	void reverseStoneOne_IfTest() {
		OthelloModel om = new OthelloModel();
		setBoardPtn(ptnReverseStoneOneTestX11if, om);
		om.setAttackerForTest(STONE.BLACK);
		om.reverseStone(7, 0);//値の判定else,同じ色の石else,調べた位置に何もないelse
		assertEquals(true, isEqualPtn(ptnReverseStoneOneTestX11ifAns, om));//同じ色の石trueでループをぬけて検索を終わる
	}

	//ReverseStoneOneのテスト
	int[][] ptnReverseStoneOneTestX11 = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 2, 0, 0, 0, 2, 0, 0 },
			{ 2, 2, 0, 0, 0, 0, 1, 0 },
			{ 3, 3, 0, 0, 0, 0, 0, 3 },
	};
	//反転後のボード
	int[][] ptnReverseStoneOneTestX11Ans = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 2, 0, 0, 0, 2, 0, 0 },
			{ 1, 2, 0, 0, 0, 0, 2, 0 },
			{ 1, 3, 0, 0, 0, 0, 0, 2 },
	};
	//ReverseStoneOneのifのテスト
	int[][] ptnReverseStoneOneTestX11if = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 2, 2, 0, 0, 0, 2, 0, 0 },
			{ 2, 2, 0, 0, 0, 0, 1, 0 },
			{ 3, 3, 0, 0, 0, 0, 0, 3 },
	};
	//反転後
	int[][] ptnReverseStoneOneTestX11ifAns = {
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 2, 0, 0, 0, 2, 0, 0 },
			{ 1, 2, 0, 0, 0, 0, 1, 0 },
			{ 1, 3, 0, 0, 0, 0, 0, 3 },
	};

	void setBoardPtn(int[][] ptn, OthelloModel om) {
		STONE stone = STONE.NONE;
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				switch (ptn[y][x]) {
				case 0:
					stone = STONE.NONE;
					break;
				case 1:
					stone = STONE.BLACK;
					break;
				case 2:
					stone = STONE.WHITE;
					break;
				case 3: //起点指定
					stone = STONE.NONE;
					break;
				}
				om.setStoneForTest(y, x, stone);
			}
		}
	}

	boolean isEqualPtn(int[][] ptn, OthelloModel om) {
		boolean equal = true;
		STONE stone = STONE.NONE;
		for (int y = 0; y < 8 && equal; y++) {
			for (int x = 0; x < 8; x++) {
				switch (ptn[y][x]) {
				case 0:
					stone = STONE.NONE;
					break;
				case 1:
					stone = STONE.BLACK;
					break;
				case 2:
					stone = STONE.WHITE;
					break;
				default:// 起点指定
					stone = STONE.NONE;
					break;
				}
				if (stone != om.getStone(y, x)) {
					equal = false;
					break;
				}
			}
		}
		return equal;
	}
}
