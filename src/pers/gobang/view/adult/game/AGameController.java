package pers.gobang.view.adult.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pers.gobang.config.StaticResourcesConfig;
import pers.gobang.method.AIChess;
import pers.gobang.method.ChessBoard;
import pers.gobang.method.Circle;
import pers.gobang.method.MessageDialog;
import pers.gobang.viewalter.ViewAlter;

import java.net.URL;
import java.util.ResourceBundle;

public class AGameController extends ViewAlter implements Initializable {
	static SwitchTimer switchTimer = new SwitchTimer(); //实例化定时器线程管理
	@FXML
	public HBox adult_pane;
	public VBox button_pane;
	public Pane chessBoard_pane;
	public VBox game_info_pane;
	public Button startGame;
	public Button pauseGame;
	public Button resetGame;
	public Text timer;
	public HBox timer_pane;
	public AnchorPane score_pane;
	public Text black_score;
	public Text white_score;
	public Button deleteGame;
	public Button gotoChild;


	private ViewAlter viewAlter;
	private ChessBoard chessBoard;
	Stage stage;
	private final boolean first_Chess = true; //设置先手是那个颜色棋子，黑为true，白为false
	private boolean isBlack = true; //黑为true，白为false
	private boolean switch_MouseListener = true; //mouseListener的开关 true为开反之为关
	private boolean game_Mode; //true为单人，反之则为双人
	private int switchGame = -1; //设置游戏当前状态 1为开启 -1为未开始 0为暂停
	private int a = 0; //记录第几次按开始按钮
	private int b = 0; //记录AI第几次放置棋子
	private javafx.scene.shape.Circle last_old_Circle; //用于悔棋时恢复
	private javafx.scene.shape.Circle old_Circle;

	/**
	 * 开始游戏点击事件
	 */
	public void startGameButton() {
		System.out.println("开始游戏");
		/*
		 * 当游戏未开始时
		 */
		if (switchGame == -1) {
			a = a + 1;
			if (a == 1) {
				initTimer();
			}
			switchTimer.setValue(true);
			switch_MouseListener = true;
			game_Mode = viewAlter.getLogged().getGame_mod();
			setMouseListenerClick(chessBoard_pane);
			switchGame = 1;
			/*
			 * 当游戏暂停时
			 */
		} else if (switchGame == 0) {
			switchTimer.setValue(true);
			switch_MouseListener = true;
			switchGame = 1;
		} else {
			System.out.println("当前游戏已开始");
		}

	}

	/**
	 * 暂停游戏点击事件
	 */
	public void pauseGameButton() {
		/*
		 * 暂停计时器
		 */
		switchTimer.setValue(false);
		/*
		 * 暂停鼠标监听
		 */
		switch_MouseListener = false;
		/*
		 * 设置游戏状态为暂停
		 */
		switchGame = 0;
	}

	/**
	 * 重新开始游戏点击事件
	 */
	public void resetGameButton() {
		System.out.println("重新开始");
		/*
		 * 重置计时器
		 */
		switchTimer.setReset(true);
		/*
		 * 停止计时器
		 */
		switchTimer.setValue(false);
		switchTimer.setReset(true);
		chessBoard.resetChessBoard();
		/*
		 * 停止鼠标监听
		 */
		switch_MouseListener = false;
		/*
		 * 设置下一次为黑棋
		 */
		isBlack = first_Chess;
		/*
		 * 设置游戏状态为未开始
		 */
		switchGame = -1;
	}

	/**
	 * 悔棋点击事件
	 */
	public void deleteGameButton() {
		if (game_Mode) {
			System.out.println("delete");
			Button b1 = chessBoard.last_black;
			Button b2 = chessBoard.last_white;
			int b1_i = (int) ((b1.getLayoutX() - chessBoard.getChessPaneUnitSize() / 2) / chessBoard.getChessPaneUnitSize());
			int b1_j = (int) ((b1.getLayoutY() - chessBoard.getChessPaneUnitSize() / 2) / chessBoard.getChessPaneUnitSize());
			int b2_i = (int) ((b2.getLayoutX() - chessBoard.getChessPaneUnitSize() / 2) / chessBoard.getChessPaneUnitSize());
			int b2_j = (int) ((b2.getLayoutY() - chessBoard.getChessPaneUnitSize() / 2) / chessBoard.getChessPaneUnitSize());
			chessBoard_pane.getChildren().remove(b1);
			chessBoard_pane.getChildren().remove(b2);
			chessBoard_pane.getChildren().remove(old_Circle);
			chessBoard_pane.getChildren().add(last_old_Circle);
			chessBoard.deleteData(b1_i, b1_j);
			chessBoard.deleteData(b2_i, b2_j);
		} else {
			if (!isBlack) {
				Button b = chessBoard.last_black;
				int i = (int) ((b.getLayoutX() - chessBoard.getChessPaneUnitSize() / 2) / chessBoard.getChessPaneUnitSize());
				int j = (int) ((b.getLayoutY() - chessBoard.getChessPaneUnitSize() / 2) / chessBoard.getChessPaneUnitSize());
				chessBoard_pane.getChildren().remove(b);
				chessBoard.deleteData(i, j);
				isBlack = true;
			} else {
				Button b = chessBoard.last_white;
				int i = (int) ((b.getLayoutX() - chessBoard.getChessPaneUnitSize() / 2) / chessBoard.getChessPaneUnitSize());
				int j = (int) ((b.getLayoutY() - chessBoard.getChessPaneUnitSize() / 2) / chessBoard.getChessPaneUnitSize());
				chessBoard_pane.getChildren().remove(b);
				chessBoard.deleteData(i, j);
				isBlack = false;
			}
		}
		chessBoard.printChessData();
	}

	/**
	 * 跳转至儿童界面
	 */
	public void gotoChild() {
		stage.setMaximized(false);
		viewAlter.gotoChild_Mode();
	}

	/**
	 * 初始化界面背景
	 */
	private void initView() {
		//设置按钮背景图片大小
		startGame.setStyle("-fx-background-size:" + startGame.getWidth());
		pauseGame.setStyle("-fx-background-size:" + startGame.getWidth());
		resetGame.setStyle("-fx-background-size:" + resetGame.getWidth());
		score_pane.setStyle("-fx-background-size:" + score_pane.getWidth());
		deleteGame.setStyle("-fx-background-size:" + deleteGame.getWidth());
		gotoChild.setStyle("-fx-background-size:" + gotoChild.getWidth());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * 初始化界面
	 *
	 * @param viewAlter viewAlter对象
	 */
	public void setApp(ViewAlter viewAlter) {
		this.viewAlter = viewAlter;
		stage = viewAlter.getStage();
		drawChessBoard(chessBoard_pane);
		stage.setMinWidth(1920);
		stage.setMinHeight(1080);
		stage.setMaximized(true);
		initView();
	}

	/**
	 * 初始化定时器
	 */
	private void initTimer() {
		TimerThread timerThread = new TimerThread(timer, switchTimer);
		timerThread.setName("Timer");
		timerThread.start();
	}

	/**
	 * 画出一个15X15的棋盘
	 *
	 * @param chessBoard_pane 进行绘画的目标pane，必须为绝对布局
	 */
	private void drawChessBoard(Pane chessBoard_pane) {
		chessBoard = new ChessBoard(chessBoard_pane, "#000000", 2.0, 961.0, 15, 64.0);
	}

	/**
	 * 设置鼠标监听
	 * 也是开始游戏的方法
	 *
	 * @param pane 需要设置监听的pane
	 */
	public void setMouseListenerClick(Pane pane) {
		pane.setOnMouseClicked(event -> {
			if (switch_MouseListener) {
				System.out.println("鼠标点击坐标为:(" + event.getSceneX() + "," + event.getSceneY() + ")");
				int temp = drawChess(event.getSceneX(), event.getSceneY());
				showSuccess_info(temp);
				if (game_Mode && temp != -2) {
					/*
					 * 此处为AI放置棋子
					 */
					int[] coordinate = new AIChess(chessBoard.getChess_data(), chessBoard.getChessBoardUnitNum()).searchLocation();
					int temp_2 = chessBoard.drawWhiteChessUnit(coordinate[0], coordinate[1], null);
					if (b == 0) {
						old_Circle = new Circle().drawCircle(coordinate[0], coordinate[1], 2, (int) chessBoard.getChessPaneUnitSize(), Color.RED);
						chessBoard_pane.getChildren().add(old_Circle);
						b++;
					} else {
						chessBoard_pane.getChildren().remove(old_Circle);
						chessBoard_pane.getChildren().remove(last_old_Circle);
						last_old_Circle = old_Circle;
						javafx.scene.shape.Circle circle = new Circle().drawCircle(coordinate[0], coordinate[1], 2, (int) chessBoard.getChessPaneUnitSize(), Color.RED);
						chessBoard_pane.getChildren().add(circle);
						old_Circle = circle;
					}

					isBlack = true;
					showSuccess_info(temp_2);
				}
			}
		});
	}

	/**
	 * 显示是否胜利信息
	 *
	 * @param temp 当前状态信息 1为胜利 0为平局 -1为未胜利不做处理
	 */
	private void showSuccess_info(int temp) {
		if (temp == 1) {
			if (!isBlack) {
				black_score.setText(String.valueOf(Integer.parseInt(black_score.getText()) + 1));
				new MessageDialog().showDialog("提示", "黑棋获胜！！", null, StaticResourcesConfig.ICON_IMAGE_PATH, null);
			} else {
				white_score.setText(String.valueOf(Integer.parseInt(white_score.getText()) + 1));
				new MessageDialog().showDialog("提示", "白棋获胜！！", null, StaticResourcesConfig.ICON_IMAGE_PATH, null);
			}
			resetGameButton();
		} else if (temp == 0) {
			new MessageDialog().showDialog("提示", "该局为平局", null, StaticResourcesConfig.ICON_IMAGE_PATH, null);
			resetGameButton();
		}
	}

	/**
	 * 开始放棋子
	 *
	 * @param i 鼠标点击的X轴
	 * @param j 鼠标点击的Y轴
	 * @return 返回是否胜利 1为胜利 0为平局 -1为未胜利 -2为该棋子落子位置非法
	 */
	private int drawChess(double i, double j) {
		double[] chess_Coordinate = chessBoard.getChessPutCoordinate(i, j);
		if (chess_Coordinate == null) {
			System.out.println("该落子位置非法");
			return -2;
		} else {
			System.out.println("该落子位置合法");
		}
		if (game_Mode) {
			//单人模式
			System.out.println("单人");
			isBlack = false;
			return chessBoard.drawBlackChessUnit(chess_Coordinate[0], chess_Coordinate[1], null);
		} else {
			//双人模式
			System.out.println("双人");
			if (isBlack) {
				isBlack = false;
				return chessBoard.drawBlackChessUnit(chess_Coordinate[0], chess_Coordinate[1], null);
			} else {
				isBlack = true;
				return chessBoard.drawWhiteChessUnit(chess_Coordinate[0], chess_Coordinate[1], null);
			}

		}
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		switchTimer.setStop(true);
	}


}

/**
 * 定时器线程管理
 */
class SwitchTimer {
	private boolean value = true;
	private boolean isReset = false;
	private boolean isStop = false;

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public boolean getReset() {
		return isReset;
	}

	public void setReset(boolean reset) {
		isReset = reset;
	}

	public void setStop(boolean stop) {
		isStop = stop;
	}

	public boolean getStop() {
		return isStop;
	}
}

/**
 * 定时器线程
 */
class TimerThread extends Thread {
	int a = 0; //分钟
	int b = 0; //秒钟
	Text timer_Text;
	SwitchTimer switchTimer;
	String time; //时间文本
	boolean isOpen; //是否更新时间
	boolean isReset; //是否重置时间

	/**
	 * @param timer_Text  用于显示时间的文本
	 * @param switchTimer 该线程的管理着
	 */
	public TimerThread(Text timer_Text, SwitchTimer switchTimer) {
		this.timer_Text = timer_Text;
		this.switchTimer = switchTimer;
	}

	public void run() {

		while (true) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (isOpen) {
				try {
					b++;
					/*
					 * 当b灯鱼60时向前进一
					 */
					if (b >= 60) {
						a++;
						b = 0;
					}
					time = String.format("%02d", a) + ":" + String.format("%02d", b);
					timer_Text.setText(time);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			/*
			 * 实时获得状态
			 */
			isOpen = switchTimer.getValue();
			isReset = switchTimer.getReset();
			if (isReset) {
				/*
				 * 重置时间
				 */
				a = 0;
				b = 0;
				time = String.format("%02d", a) + ":" + String.format("%02d", b);
				timer_Text.setText(time);
				switchTimer.setReset(false);
			}

			if (switchTimer.getStop()) {
				break;
			}
		}
	}
}