package pers.gobang.viewalter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pers.gobang.config.StaticResourcesConfig;
import pers.gobang.method.User;
import pers.gobang.view.adult.game.AGameController;
import pers.gobang.view.adult.mode.AModeController;
import pers.gobang.view.adult.welcome.AWelcomeController;
import pers.gobang.view.child.game.CGameController;
import pers.gobang.view.child.mode.CModeController;
import pers.gobang.view.child.welcome.CWelcomeController;
import pers.gobang.view.login.LoginController;
import pers.gobang.view.regist.RegistController;

import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewAlter extends Application {

	private static final Logger logger = Logger.getLogger(ViewAlter.class.getName());
	private Stage stage;
	private Pane pane;
	private User logged;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle(StaticResourcesConfig.PROGRAM_NAME);
		stage.getIcons().add(new Image(StaticResourcesConfig.ICON_IMAGE_PATH));
		gotoLogin();
		stage.show();
	}

	/**
	 * 跳转到登录界面
	 */
	public void gotoLogin() {
		try {
			LoginController login = (LoginController) replaceSceneContent(StaticResourcesConfig.LOGIN_FXML_PATH);
			stage.setResizable(false);
			Scene scene = new Scene(pane, StaticResourcesConfig.STAGE_MAIN_WIDTH, StaticResourcesConfig.STAGE_MAIN_HEIGHT);
			scene.getStylesheets().add(Objects.requireNonNull(ViewAlter.class.getResource(StaticResourcesConfig.LOGIN_CSS_PATH)).toExternalForm());
			stage.setScene(scene);
			login.setApp(this);
		} catch (Exception ex) {
			System.out.println("goto_Error");
			logger.log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * 跳转到注册界面
	 */
	public void gotoRegist() {
		try {
			RegistController regist = (RegistController) replaceSceneContent(StaticResourcesConfig.REGIST_FXML_PATH);
			stage.setResizable(true);
			Scene scene = new Scene(pane, StaticResourcesConfig.STAGE_REGIST_WIDTH, StaticResourcesConfig.STAGE_REGIST_HEIGHT);
			scene.getStylesheets().add(Objects.requireNonNull(ViewAlter.class.getResource(StaticResourcesConfig.REGIST_CSS_PATH)).toExternalForm());
			stage.setScene(scene);
			regist.setApp(this);
		} catch (Exception ex) {
			System.out.println("goto_Error");
			logger.log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * 跳转到儿童欢迎界面
	 */
	public void gotoChild_Welcome() {
		try {
			CWelcomeController cwelcome = (CWelcomeController) replaceSceneContent(StaticResourcesConfig.CWELCOME_FXML_PATH);
			stage.setResizable(true);
			Scene scene = new Scene(pane, StaticResourcesConfig.STAGE_CHILD_WIDTH, StaticResourcesConfig.STAGE_CHILD_HEIGHT);
			scene.getStylesheets().add(Objects.requireNonNull(ViewAlter.class.getResource(StaticResourcesConfig.CWELCOME_CSS_PATH)).toExternalForm());
			stage.setScene(scene);
			cwelcome.setApp(this);
		} catch (Exception ex) {
			System.out.println("goto_Error");
			logger.log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * 跳转到儿童模式选择界面
	 */
	public void gotoChild_Mode() {
		try {
			CModeController cmode = (CModeController) replaceSceneContent(StaticResourcesConfig.CMODE_FXML_PATH);
			stage.setResizable(true);
			Scene scene = new Scene(pane, StaticResourcesConfig.STAGE_CHILD_WIDTH, StaticResourcesConfig.STAGE_CHILD_HEIGHT);
			scene.getStylesheets().add(Objects.requireNonNull(ViewAlter.class.getResource(StaticResourcesConfig.CMODE_CSS_PATH)).toExternalForm());
			stage.setScene(scene);
			cmode.setApp(this);
		} catch (Exception e) {
			System.out.println("goto_Error");
			e.printStackTrace();
		}
	}

	/**
	 * 跳转到儿童游戏界面
	 */
	public void gotoChild_Game() {
		try {
			CGameController cgame = (CGameController) replaceSceneContent(StaticResourcesConfig.CGAME_FXML_PATH);
			stage.setResizable(true);
			Scene scene = new Scene(pane, StaticResourcesConfig.STAGE_CHILD_WIDTH, StaticResourcesConfig.STAGE_CHILD_HEIGHT);
			scene.getStylesheets().add(Objects.requireNonNull(ViewAlter.class.getResource(StaticResourcesConfig.CGAME_CSS_PATH)).toExternalForm());
			stage.setScene(scene);
			cgame.setApp(this);
		} catch (Exception e) {
			System.out.println("goto_Error");
			e.printStackTrace();
		}
	}

	/**
	 * 跳转至成人欢迎界面
	 */
	public void gotoAdult_Welcome() {
		AWelcomeController awelcome = (AWelcomeController) replaceSceneContent(StaticResourcesConfig.AWELCOME_FXML_PATH);
		stage.setResizable(true);
		Scene scene = new Scene(pane, StaticResourcesConfig.STAGE_CHILD_WIDTH, StaticResourcesConfig.STAGE_CHILD_HEIGHT);
		scene.getStylesheets().add(Objects.requireNonNull(ViewAlter.class.getResource(StaticResourcesConfig.AWELCOME_CSS_PATH)).toExternalForm());
		stage.setScene(scene);
		awelcome.setApp(this);
	}

	/**
	 * 跳转至成人模式选择界面
	 */
	public void gotoAdult_Mode() {
		AModeController amode = (AModeController) replaceSceneContent(StaticResourcesConfig.AMODE_FXML_PATH);
		stage.setResizable(true);
		Scene scene = new Scene(pane, StaticResourcesConfig.STAGE_CHILD_WIDTH, StaticResourcesConfig.STAGE_CHILD_HEIGHT);
		scene.getStylesheets().add(Objects.requireNonNull(ViewAlter.class.getResource(StaticResourcesConfig.AMODE_CSS_PATH)).toExternalForm());
		stage.setScene(scene);
		amode.setApp(this);
	}

	/**
	 * 跳转至成人游戏界面
	 */
	public void gotoAdult_Game() {
		AGameController amode = (AGameController) replaceSceneContent(StaticResourcesConfig.AGAME_FXML_PATH);
		stage.setResizable(true);
		Scene scene = new Scene(pane, StaticResourcesConfig.STAGE_CHILD_WIDTH, StaticResourcesConfig.STAGE_CHILD_HEIGHT);
		scene.getStylesheets().add(Objects.requireNonNull(ViewAlter.class.getResource(StaticResourcesConfig.AGAME_CSS_PATH)).toExternalForm());
		stage.setScene(scene);
		amode.setApp(this);
	}

	/**
	 * 设置已登录用户对象
	 */
	public void set_Logged_User(User logged) {
		this.logged = logged;
	}

	/**
	 * 返回已登录用户对象
	 */
	public User getLogged() {
		return logged;
	}

	/**
	 * 获得stage对象
	 *
	 * @return 返回stage对象
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * 替换场景
	 */
	private Initializable replaceSceneContent(String fxml) {
		FXMLLoader loader = new FXMLLoader();
		try (InputStream in = ViewAlter.class.getResourceAsStream(fxml)) {
			loader.setBuilderFactory(new JavaFXBuilderFactory());
			loader.setLocation(ViewAlter.class.getResource(fxml));
			pane = loader.load(in);
			stage.sizeToScene();
		} catch (Exception e) {
			System.out.println("replaceSceneContent_Error");
			logger.log(Level.SEVERE, "页面加载异常！");
		}
		return loader.getController();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
