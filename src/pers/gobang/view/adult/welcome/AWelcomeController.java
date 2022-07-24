package pers.gobang.view.adult.welcome;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pers.gobang.ViewAlter;

import java.net.URL;
import java.util.ResourceBundle;

public class AWelcomeController extends ViewAlter implements Initializable {

	@FXML
	public VBox adult_pane;
	public Button adult_Button_StartGame;
	public AnchorPane anchor_pane;
	public VBox vbox_title;
	public VBox vbox_start;

	private ViewAlter viewAlter;
	private Stage stage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * 开始游戏按钮点击事件
	 */
	public void adult_Button_StartGame() {
		stage.setMaximized(false);
		viewAlter.gotoAdult_Mode();
	}

	/**
	 * 初始化界面
	 *
	 * @param viewAlter viewAlter对象
	 */
	public void setApp(ViewAlter viewAlter) {
		this.viewAlter = viewAlter;
		stage = (Stage) adult_pane.getScene().getWindow();
		stage.setMinWidth(1920);
		stage.setMinHeight(1080);
		adult_Button_StartGame.setStyle("-fx-background-size:" + adult_Button_StartGame.getWidth());
		stage.setMaximized(true);
		adult_pane.setStyle("-fx-background-size:" + adult_pane.getWidth());
	}
}
