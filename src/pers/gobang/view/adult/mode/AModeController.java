package pers.gobang.view.adult.mode;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pers.gobang.ViewAlter;

import java.net.URL;
import java.util.ResourceBundle;

public class AModeController extends ViewAlter implements Initializable {

	@FXML
	public VBox adult_pane;
	public Button adult_Button_OneGame;
	public Button adult_Button_TwoGame;

	private ViewAlter viewAlter;
	private Stage stage;

	/**
	 * 单人游戏按钮点击事件
	 */
	public void adult_Button_OneGame() {

		/*
		 * true代表单人
		 */
		viewAlter.getLogged().setGame_mod(true);
		stage.setMaximized(false);
		viewAlter.gotoAdult_Game();
	}

	/**
	 * 双人游戏按钮点击事件
	 */
	public void adult_Button_TwoGame() {

		/*
		 * false代表双人
		 */
		viewAlter.getLogged().setGame_mod(false);
		stage.setMaximized(false);
		viewAlter.gotoAdult_Game();
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
		adult_Button_OneGame.setStyle("-fx-background-size:" + adult_Button_OneGame.getWidth());
		adult_Button_TwoGame.setStyle("-fx-background-size:" + adult_Button_TwoGame.getWidth());
		stage.setMaximized(true);
		adult_pane.setStyle("-fx-background-size:" + adult_pane.getWidth());
	}

}
