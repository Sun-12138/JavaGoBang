package pers.gobang.view.child.mode;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pers.gobang.ViewAlter;

import java.net.URL;
import java.util.ResourceBundle;

public class CModeController extends ViewAlter implements Initializable {

	@FXML
	public AnchorPane child_pane;
	public BorderPane child_border;
	public Button child_Button_OneGame;
	public Button child_Button_TwoGame;

	private Stage stage;
	private ViewAlter viewAlter;

	/**
	 * 单人游戏按钮点击事件
	 */
	public void child_Button_OneGame() {
		/*
		 + true代表单人
		 *
		 */
		viewAlter.getLogged().setGame_mod(true);
		stage.setMaximized(false);
		viewAlter.gotoChild_Game();
	}

	/**
	 * 双人游戏按钮点击事件
	 */
	public void child_Button_TwoGame() {
		/*
		 + false代表双人
		 *
		 */
		viewAlter.getLogged().setGame_mod(false);
		stage.setMaximized(false);
		viewAlter.gotoChild_Game();
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
		stage.setMinWidth(1920);
		stage.setMinHeight(1080);
		child_Button_OneGame.setStyle("-fx-background-size:" + child_Button_OneGame.getWidth());
		child_Button_TwoGame.setStyle("-fx-background-size:" + child_Button_TwoGame.getWidth());
		stage.setMaximized(true);
		child_pane.setStyle("-fx-background-size:" + child_pane.getWidth());
	}

}
