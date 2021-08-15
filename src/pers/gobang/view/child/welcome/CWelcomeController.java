package pers.gobang.view.child.welcome;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pers.gobang.viewalter.ViewAlter;

import java.net.URL;
import java.util.ResourceBundle;

public class CWelcomeController extends ViewAlter implements Initializable {

	@FXML
	public AnchorPane child_pane;
	public Button child_Button_StartGame;

	private ViewAlter viewAlter;
	private Stage stage;

	/**
	 * 开始游戏按钮点击事件
	 */
	public void child_Button_StartGame() {
		stage.setMaximized(false);
		viewAlter.gotoChild_Mode();
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
		stage = (Stage) child_pane.getScene().getWindow();
		stage.setMinWidth(1920);
		stage.setMinHeight(1080);
		child_Button_StartGame.setStyle("-fx-background-size:" + child_Button_StartGame.getWidth());
		stage.setMaximized(true);
		child_pane.setStyle("-fx-background-size:" + child_pane.getWidth());
	}
}
