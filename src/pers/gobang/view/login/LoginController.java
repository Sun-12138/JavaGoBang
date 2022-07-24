package pers.gobang.view.login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import pers.gobang.config.StaticResourcesConfig;
import pers.gobang.method.*;
import pers.gobang.ViewAlter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends ViewAlter implements Initializable {

	@FXML
	public Text text_Welcome;
	public TextField textf_Login_Account;
	public Label lab_Login_Password;
	public PasswordField passwordf_Login_Password;
	public Label lab_Account;
	public TextField textf_Login_Password;
	public Button login_Button_isShow;
	public Button login_Button_Regist;

	private ViewAlter viewAlter;
	private final ImageView image_Password_Show = new ImageView(StaticResourcesConfig.PASSWORD_SHOW); //密码是否显示的小眼睛
	private final ImageView image_Password_Noshow = new ImageView(StaticResourcesConfig.PASSWORD_NOSHOW); //密码是否显示的小眼睛

	int login_Button_isShow_tmp = 0; //0代表不显示密码 1代表显示密码
	MessageDialog messageDialog = new MessageDialog();

	/**
	 * 登录按钮事件
	 */
	public void login_Button_Login() {
		FileInputStream fileInputStream;
		String login_user_account;
		String login_user_password;
		try {
			login_user_account = textf_Login_Account.getText();
			String type = "SHA-256";
			if (login_Button_isShow_tmp == 0) {
				/*
				 * 对密码进行SHA-256加密
				 */
				System.out.println("passwordf");
				login_user_password = HashEncryption.sign(passwordf_Login_Password.getText(), type);
			} else {
				/*
				 * 对密码进行SHA-256加密
				 */
				login_user_password = HashEncryption.sign(textf_Login_Password.getText(), type);
			}
		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * 判断用户输入的信息是否完整
			 */
			messageDialog.showDialog("提示", "登录失败！！", "请检查您输入的信息是否完整", StaticResourcesConfig.ICON_IMAGE_PATH, null);
			return;
		}
		/*
		 * 加载账户信息
		 */
		RegisterAccount registerAccount = new RegisterAccount();
		try {
			fileInputStream = new FileInputStream(StaticResourcesConfig.USER_DATA_PATH + login_user_account + ".dat");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			messageDialog.showDialog("提示", "登录失败！！", "您登录的账户名不存在，请注册后再试", StaticResourcesConfig.ICON_IMAGE_PATH, null);
			return;
		}
		/*
		 * 取出用户信息
		 */
		User login_User = (User) registerAccount.loadObject(fileInputStream);
		/*
		 * 判断账户密码是否正确
		 */
		if (!login_User.getPassword().equals(login_user_password)) {
			messageDialog.showDialog("提示", "登录失败！！", "密码错误,请检查后重试", StaticResourcesConfig.ICON_IMAGE_PATH, null);
			return;
		}
		/*
		 * 将已登录用户的对象传给ViewAlter
		 */
		viewAlter.set_Logged_User(login_User);
		/*
		 * 根据生日计算年龄，若年龄小于等于12则判断为儿童，反之则成人
		 */
		if (new GetAge().getAgeByBirth(viewAlter.getLogged().getBirthday()) <= 12 ) {
			viewAlter.gotoChild_Welcome();
		} else {
			viewAlter.gotoAdult_Welcome();
		}

	}

	/**
	 * 跳转到注册界面
	 */
	public void login_Button_Regist() {
		viewAlter.gotoRegist();
	}

	/**
	 * 设置Password是否可见
	 */
	public void login_Button_isShow() {
		if (login_Button_isShow_tmp == 1) {
			String password = textf_Login_Password.getText();
			passwordf_Login_Password.setVisible(true);
			passwordf_Login_Password.requestFocus();
			passwordf_Login_Password.setText(password);
			passwordf_Login_Password.positionCaret(password.length());
			textf_Login_Password.setVisible(false);
			login_Button_isShow.setGraphic(image_Password_Noshow);
			login_Button_isShow_tmp = 0;
		} else {
			String password = passwordf_Login_Password.getText();
			textf_Login_Password.setVisible(true);
			textf_Login_Password.requestFocus();
			textf_Login_Password.setText(password);
			textf_Login_Password.positionCaret(password.length());
			passwordf_Login_Password.setVisible(false);
			login_Button_isShow.setGraphic(image_Password_Show);
			login_Button_isShow_tmp = 1;
		}
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
		init_PasswordShow();
		this.viewAlter = viewAlter;
	}

	/**
	 * 初始化是否显示密码按钮
	 */
	private void init_PasswordShow() {
		login_Button_isShow.setBackground(null);
		login_Button_isShow.setBorder(null);
		login_Button_isShow.setGraphic(image_Password_Noshow);
	}

}
