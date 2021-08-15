package pers.gobang.method;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;


public class ChessBoard {
	private final Pane target_Pane; //放置棋盘的布局
	private final String chessBoardLineColor; //棋盘线的颜色
	private final double chessBoardLineWidth; //棋盘线的宽度
	private final double chessBoardSize; //棋盘尺寸大小
	private final int chessBoardUnitNum; //定义棋盘几乘几
	private final double chessPaneUnitSize; //棋盘每一个单元格大小
	public Button last_white; //最后一次放置的白棋棋子对象
	public Button last_black; //最后放置的黑棋棋子对象
	private int[][] chess_data; //棋盘落子数据
	private Canvas canvas; //棋盘画布

	public ChessBoard(Pane target_Pane, String chessBoardLineColor, double chessBoardLineWidth, double chessBoardSize, int chessBoardUnitNum, double chessPaneUnitSize) {
		this.target_Pane = target_Pane;
		this.chessBoardLineColor = chessBoardLineColor;
		this.chessBoardLineWidth = chessBoardLineWidth;
		this.chessBoardSize = chessBoardSize;
		this.chessBoardUnitNum = chessBoardUnitNum;
		this.chessPaneUnitSize = chessPaneUnitSize;
		initData();
		drawChessBoard();
	}

	/**
	 * 画出棋盘
	 */
	private void drawChessBoard() {
		//画布
		canvas = new Canvas(chessBoardSize, chessBoardSize);
		target_Pane.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		for (int i = 0; i < chessBoardUnitNum; i++) {
			gc.setStroke(Paint.valueOf(chessBoardLineColor));
			gc.setLineWidth(chessBoardLineWidth);
			gc.strokeLine(chessPaneUnitSize, (i + 1) * chessPaneUnitSize, chessBoardSize, (i + 1) * chessPaneUnitSize);
			gc.strokeLine((i + 1) * chessPaneUnitSize, chessPaneUnitSize, (i + 1) * chessPaneUnitSize, chessBoardSize);
		}
	}

	/**
	 * 初始化棋盘落子数据
	 */
	private void initData() {
		chess_data = new int[chessBoardUnitNum][chessBoardUnitNum];
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				chess_data[i][j] = 0;
			}
		}
	}

	public void deleteData(int i, int j) {
		chess_data[i][j] = 0;
	}

	/**
	 * 存储棋盘落子数据
	 *
	 * @param i       坐标X轴
	 * @param j       坐标Y轴
	 * @param isBlack 是否为黑棋，如果是黑棋则存为-1，白棋存为1
	 */
	private void saveData(int i, int j, boolean isBlack) {
		if (isBlack) {
			chess_data[i][j] = -1;
		} else {
			chess_data[i][j] = 1;
		}
	}

	/**
	 * 清除棋子
	 */
	public void resetChessBoard() {
		initData();
		target_Pane.getChildren().removeAll(target_Pane.getChildren());
		target_Pane.getChildren().add(canvas);
	}

	/**
	 * 获得棋盘数据
	 *
	 * @return 棋盘数组
	 */
	public int[][] getChess_data() {
		return chess_data;
	}

	/**
	 * 获得棋盘大小
	 *
	 * @return 棋盘大小
	 */
	public int getChessBoardUnitNum() {
		return chessBoardUnitNum;
	}

	/**
	 * 获得棋盘格子大小
	 *
	 * @return 棋盘格子大小
	 */
	public double getChessPaneUnitSize() {
		return chessPaneUnitSize;
	}

	/**
	 * 输出棋盘数据
	 */
	public void printChessData() {
		for (int i = 0; i < chessBoardUnitNum; i++) {
			for (int j = 0; j < chessBoardUnitNum; j++) {
				System.out.print(chess_data[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 根据坐标获得棋盘坐标
	 *
	 * @param mouseX 鼠标点击X轴坐标
	 * @param mouseY 鼠标点击Y轴坐标
	 * @return 返回棋子在棋盘的坐标 null则为该位置非法
	 */
	public double[] getChessPutCoordinate(double mouseX, double mouseY) {
		double[] chess_Coordinate = new double[2];
		double x = (mouseX - target_Pane.getLayoutX() - chessPaneUnitSize) / chessPaneUnitSize;
		double y = (mouseY - target_Pane.getLayoutY() - chessPaneUnitSize) / chessPaneUnitSize;
		System.out.println("进行精确前的坐标为:(" + x + "," + y + ")");
		double i = (int) Math.round(x);
		double j = (int) Math.round(y);
		System.out.println("精确后的棋盘坐标为:(" + i + "," + j + ")");
		/*
		 *判断落子位置是否合法
		 */

		//判断该位置是否有棋子
		if (chess_data[(int) i][(int) j] == 1 || chess_data[(int) i][(int) j] == -1) {
			return null;
		} else if (i >= 0 && i < chessBoardUnitNum && j >= 0 && j < chessBoardUnitNum) {
			chess_Coordinate[0] = i;
			chess_Coordinate[1] = j;
			return chess_Coordinate;
		} else {
			return null;
		}
	}

	/**
	 * 生成一个白棋
	 *
	 * @param i        白棋要放置的X轴坐标
	 * @param j        白棋要放置的Y轴坐标
	 * @param filePath 棋子背景图片路径 null则为纯色棋子
	 * @return 返回是否胜利 1为胜利 0为平局 -1为未胜利
	 */
	public int drawWhiteChessUnit(double i, double j, String filePath) {
		Button white_chess = new Button();
		target_Pane.getChildren().add(white_chess);
		if (filePath == null) {
			white_chess.setStyle("-fx-background-radius: 30; -fx-effect:dropshadow(three-pass-box, #72b9da, 8.0,0, 0, 0); ");
		} else {
			String path = "file:" + filePath;
			white_chess.setStyle("-fx-background-image: url(" + path + "); -fx-background-position:center center; -fx-background-repeat:stretch;-fx-background-color:transparent;");
		}
		drawChessUnit(white_chess, i, j, false);
		/*
		 * 保存落子数据
		 */
		saveData((int) i, (int) j, false);
		printChessData();
		return isSuccess((int) i, (int) j, false);
	}

	/**
	 * 生成一个黑棋
	 *
	 * @param i        黑棋要放置的X轴坐标
	 * @param j        黑棋要放置的Y轴坐标
	 * @param filePath 棋子背景图片路径 null则为纯色棋子
	 * @return 返回是否胜利 1为胜利 0为平局 -1为未胜利
	 */
	public int drawBlackChessUnit(double i, double j, String filePath) {
		Button black_chess = new Button();
		target_Pane.getChildren().add(black_chess);
		if (filePath == null) {
			black_chess.setStyle("-fx-background-color: #000;-fx-background-radius: 30; -fx-effect:dropshadow(three-pass-box, #72b9da, 8.0,0, 0, 0);  ");
		} else {
			String path = "file:" + filePath;
			black_chess.setStyle("-fx-background-image: url(" + path + "); -fx-background-position:center center; -fx-background-repeat:stretch;-fx-background-color:transparent;");
		}
		drawChessUnit(black_chess, i, j, true);
		/*
		 * 保存落子数据
		 */
		saveData((int) i, (int) j, true);
		printChessData();
		return isSuccess((int) i, (int) j, true);
	}

	/**
	 * 放置棋子在棋盘
	 *
	 * @param button  棋子按钮对象
	 * @param i       要放置的棋盘X轴坐标
	 * @param j       要放置的棋盘Y轴坐标
	 * @param isBlack 当前棋子颜色
	 */
	private void drawChessUnit(Button button, double i, double j, boolean isBlack) {
		button.setFocusTraversable(false);
		button.setPrefSize(chessPaneUnitSize, chessPaneUnitSize);
		System.out.println("pane坐标:(" + target_Pane.getLayoutX() + "," + target_Pane.getLayoutY() + ")");
		System.out.println("ij坐标为:(" + i + "," + j + ")");
		button.setLayoutX(chessPaneUnitSize / 2 + (i * chessPaneUnitSize));
		button.setLayoutY(chessPaneUnitSize / 2 + (j * chessPaneUnitSize));
		if (isBlack) {
			last_black = button;
		} else {
			last_white = button;
		}
		System.out.println("棋子放置的坐标为:（" + (target_Pane.getLayoutX() + chessPaneUnitSize / 2) + (i * 64) + ":" + (target_Pane.getLayoutY() + chessPaneUnitSize / 2) + (j * 64) + ")");
	}

	/**
	 * 根据当前落子的坐标，从八个方位检验是否胜利
	 *
	 * @param i       当前棋子放置的X轴
	 * @param j       当前棋子放置的Y轴
	 * @param isBlack 是否为黑棋
	 * @return 返回是否胜利 1为胜利 0为平局 -1为未胜利
	 */
	private int isSuccess(int i, int j, boolean isBlack) {
		/*
		 * 如果下的为黑棋则为-1，白棋为1
		 */
		int chess;
		if (isBlack) {
			chess = -1;
		} else {
			chess = 1;
		}

		if (isDraw()) {
			System.out.println("该局平局");
			return 0;
		}

		/*
		 * 检验各个方向是否胜利，胜利则返回true
		 */
		if (check_Vertical(i, j, chess)) {
			System.out.println("竖向胜利");
		} else if (check_Horizontal(i, j, chess)) {
			System.out.println("横向胜利");
		} else if (check_Left_Oblique(i, j, chess)) {
			System.out.println("左上方到斜右下方胜利");
		} else if (check_Right_Oblique(i, j, chess)) {
			System.out.println("斜右上方和斜左下方胜利");
		} else {
			return -1;
		}

		if (isBlack) {
			System.out.println("黑棋赢");
		} else {
			System.out.println("白棋赢");
		}
		return 1;
	}

	/**
	 * 检查是否平局
	 *
	 * @return 返回true则为平局，返回false则为未平局
	 */
	private boolean isDraw() {
		for (int i = 0; i < chessBoardUnitNum; i++) {
			for (int j = 0; j < chessBoardUnitNum; j++) {
				if (chess_data[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 检查横向是否是否五连棋
	 *
	 * @param i     当前放置棋子的X轴
	 * @param j     当前放置棋子的Y轴
	 * @param chess 白棋或者黑棋
	 * @return 若返回true则说明胜利，否则没有胜利
	 */
	private boolean check_Horizontal(int i, int j, int chess) {
		//暂时储存坐标
		int a = i;
		int total_Num = 0;

		if (i == 0) {
			System.out.println("当前横左方向无需检测");
		} else {
			//向左检测
			for (i = i - 1; i >= 0; i--) {
				if (chess_data[i][j] == chess) {
					total_Num++;
					System.out.println("横向左:" + total_Num);
				} else {
					System.out.println("横向左结束");
					break;
				}
			}
		}
		i = a;
		if (i == 14) {
			System.out.println("当前横右方向无需检测");
		} else {
			//向右检测
			for (i = i + 1; i <= chessBoardUnitNum; i++) {
				if (chess_data[i][j] == chess) {
					total_Num++;
					System.out.println("横向右:" + total_Num);
				} else {
					System.out.println("横向右结束");
					break;
				}
			}
		}
		return total_Num >= 4;
	}

	/**
	 * 检查竖向是否五连棋
	 *
	 * @param i     当前放置棋子的X轴
	 * @param j     当前放置棋子的Y轴
	 * @param chess 白棋或者黑棋
	 * @return 若返回true则说明胜利，否则没有胜利
	 */
	private boolean check_Vertical(int i, int j, int chess) {
		//暂时存储坐标
		int a = j;
		int total_Num = 0;
		if (j == 0) {
			System.out.println("当前竖上方向无需检测");
		} else {
			//向上检测
			for (j = j - 1; j >= 0; j--) {
				if (chess_data[i][j] == chess) {
					total_Num++;
					System.out.println("竖向上:" + total_Num);
				} else {
					System.out.println("竖向上结束");
					break;
				}
			}
		}
		j = a;
		if (j == 14) {
			System.out.println("当前竖下方向无需检测");
		} else {
			//向下检测
			for (j = j + 1; j < chessBoardUnitNum; j++) {
				if (chess_data[i][j] == chess) {
					total_Num++;
					System.out.println("竖向下:" + total_Num);
				} else {
					System.out.println("竖向下结束");
					break;
				}
			}
		}
		return total_Num >= 4;
	}

	/**
	 * 检查斜左斜上方和斜右下方是否五连棋
	 *
	 * @param i     当前放置棋子的X轴
	 * @param j     当前放置棋子的Y轴
	 * @param chess 白棋或者黑棋
	 * @return 若返回true则说明胜利，否则没有胜利
	 */
	private boolean check_Left_Oblique(int i, int j, int chess) {
		int a = i;
		int b = j;
		int total_Num = 0;

		if (i == 0 || j == 0) {
			System.out.println("当前左斜上方向无需检测");
		} else {
			//向左斜上方检测
			for (i = i - 1, j = j - 1; i >= 0 && j >= 0; i--, j--) {
				if (chess_data[i][j] == chess) {
					total_Num++;
					System.out.println("左斜上方:" + total_Num);
				} else {
					System.out.println("左斜上方结束");
					break;
				}
			}
		}

		i = a;
		j = b;
		if (i == 14 || j == 14) {
			System.out.println("当前右斜下方向无需检测");
		} else {
			//向右斜下方检测
			for (i = i + 1, j = j + 1; i < chessBoardUnitNum && j <= chessBoardUnitNum; i++, j++) {
				if (chess_data[i][j] == chess) {
					total_Num++;
					System.out.println("右斜下方:" + total_Num);
				} else {
					System.out.println("右斜下方结束");
					break;
				}
			}
		}
		return total_Num >= 4;
	}

	/**
	 * 检查斜右上方和斜左下方是否五连棋
	 *
	 * @param i     当前放置棋子的X轴
	 * @param j     当前放置棋子的Y轴
	 * @param chess 白棋或者黑棋
	 * @return 若返回true则说明胜利，否则没有胜利
	 */
	private boolean check_Right_Oblique(int i, int j, int chess) {
		int a = i;
		int b = j;
		int total_Num = 0;
		if (i == 14 || j == 0) {
			System.out.println("当前右斜上方向无需检测");
		} else {
			//向右斜上方检测
			for (i = i + 1, j = j - 1; i < chessBoardUnitNum && j >= 0; i++, j--) {
				if (chess_data[i][j] == chess) {
					total_Num++;
					System.out.println("右斜上方:" + total_Num);
				} else {
					System.out.println("右斜上方结束");
					break;
				}
			}
		}

		i = a;
		j = b;
		if (i == 0 || j == 14) {
			System.out.println("当前左斜下方向无需检测");
		} else {
			//向左斜下方检测
			for (i = i - 1, j = j + 1; i >= 0 && j < chessBoardUnitNum; i--, j++) {
				if (chess_data[i][j] == chess) {
					total_Num++;
					System.out.println("左斜下方:" + total_Num);
				} else {
					System.out.println("左斜下方结束");
					break;
				}
			}
		}
		return total_Num >= 4;
	}

}
