package pers.gobang.view.regist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import pers.gobang.config.StaticResourcesConfig;
import pers.gobang.method.*;
import pers.gobang.viewalter.ViewAlter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistController extends ViewAlter implements Initializable {

	@FXML
	public FlowPane root_Pane;
	public Label lab_Regist_Account;
	public Label lab_Regist_Password;
	public Text text_Registered;
	public TextField textf_Regist_Account;
	public PasswordField passwordf_Regist_Password;
	public Button regist_Button_Regist;
	public Button regist_Button_Back;
	public ComboBox<String> regist_Comb_Sex;
	public Label lab_Regist_Birthday;
	public Label lab_Regist_Sex;
	public DatePicker Date_Regist_Birthday;
	public Label lab_Regist_Name;
	public TextField textf_Regist_name;
	public Button regist_Button_isShow;
	public TextField textf_Regist_Password;

	private ViewAlter viewAlter;
	private final ImageView image_Password_Show = new ImageView(StaticResourcesConfig.PASSWORD_SHOW); //密码是否显示的小眼睛
	private final ImageView image_Password_Noshow = new ImageView(StaticResourcesConfig.PASSWORD_NOSHOW); //密码是否显示的小眼睛

	int regist_Button_isShow_tmp = 0;//*0代表不显示密码 1代表显示密码

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * 初始化界面
	 *
	 * @param viewAlter viewAlter对象
	 */
	public void setApp(ViewAlter viewAlter) {
		init_ComboBox();
		init_DatePicker();
		init_PasswordShow();
		this.viewAlter = viewAlter;
	}

	/**
	 * 注册按钮
	 */
	public void regist_Button_Regist() {
		MessageDialog messageDialog = new MessageDialog();
		FileOutputStream file_Out = null;
		String regist_user_account;
		String regist_user_password;
		String regist_user_name;
		Date regist_user_birthday;
		String regist_user_sex;
		try {
			/*
			 * 获取用户注册信息
			 */
			regist_user_account = textf_Regist_Account.getText();
			String type = "SHA-256";
			if (regist_Button_isShow_tmp == 0) {
				/*
				 * 对密码进行SHA-256加密
				 */
				regist_user_password = HashEncryption.sign(passwordf_Regist_Password.getText(), type);
			} else {
				/*
				 * 对密码进行SHA-256加密
				 */
				regist_user_password = HashEncryption.sign(textf_Regist_Password.getText(), type);
			}
			regist_user_name = textf_Regist_name.getText();
			regist_user_birthday = new GetAge().toDate(Date_Regist_Birthday.getValue());
			regist_user_sex = regist_Comb_Sex.getSelectionModel().getSelectedItem();
		} catch (Exception e) {
			/*
			 * 判断用户输入的信息是否完整
			 */
			e.printStackTrace();
			messageDialog.showDialog("提示", "注册失败！！", "请检查您填写的信息", StaticResourcesConfig.ICON_IMAGE_PATH, null);
			return;
		}
		/*
		 * 获取已注册账号
		 */
		File file = new File(StaticResourcesConfig.USER_DATA_PATH);
		File[] accountName = file.listFiles();
		/*
		 * 检查账号名是否存在
		 */
		for (int i = 0; i < Objects.requireNonNull(accountName).length; i++) {
			String account = accountName[i].toString().substring(StaticResourcesConfig.USER_DATA_PATH.length(), accountName[i].toString().indexOf(".dat"));
			if (account.equals(regist_user_account)) {
				messageDialog.showDialog("提示", "注册失败！！", "您注册的账户名已存在", StaticResourcesConfig.ICON_IMAGE_PATH, null);
				return;
			}
		}
		/*
		 * 实例化User对象
		 */
		User user = new User(regist_user_account, regist_user_password, regist_user_name, regist_user_birthday, regist_user_sex, null);
		RegisterAccount login_account = new RegisterAccount();
		try {
			file_Out = new FileOutputStream(StaticResourcesConfig.USER_DATA_PATH + regist_user_account + ".dat");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/*
		 * 保存User对象
		 */
		login_account.saveUser(user, file_Out);
		/*
		 * 显示Dialog注册成功
		 */
		if (messageDialog.showDialog("提示", "注册成功!!", null, StaticResourcesConfig.ICON_IMAGE_PATH, null) != 2) {
			viewAlter.gotoLogin();
		}
	}

	/**
	 * 返回按钮，用于返回登录界面
	 */
	public void regist_Button_Back() {
		viewAlter.gotoLogin();
	}

	/**
	 * 切换Password是否可见
	 */
	public void regist_Button_isShow() {
		if (regist_Button_isShow_tmp == 1) {
			String password = textf_Regist_Password.getText();
			passwordf_Regist_Password.setVisible(true);
			passwordf_Regist_Password.requestFocus();
			passwordf_Regist_Password.setText(password);
			passwordf_Regist_Password.positionCaret(password.length());
			textf_Regist_Password.setVisible(false);
			regist_Button_isShow.setGraphic(image_Password_Noshow);
			regist_Button_isShow_tmp = 0;
		} else {
			String password = passwordf_Regist_Password.getText();
			textf_Regist_Password.setVisible(true);
			textf_Regist_Password.requestFocus();
			textf_Regist_Password.setText(password);
			textf_Regist_Password.positionCaret(password.length());
			passwordf_Regist_Password.setVisible(false);
			regist_Button_isShow.setGraphic(image_Password_Show);
			regist_Button_isShow_tmp = 1;
		}
	}

	/**
	 * 初始化ComboBox
	 */
	private void init_ComboBox() {
		ObservableList<String> sex = FXCollections.observableArrayList("男", "女");
		regist_Comb_Sex.getItems().addAll(sex);
		regist_Comb_Sex.setValue("请选择你的性别");
	}

	/**
	 * 初始化DatePicker
	 */
	private void init_DatePicker() {
		Date_Regist_Birthday.setPromptText("请选择您的出生日期");
	}

	/**
	 * 初始化PasswordShow
	 */
	private void init_PasswordShow() {
		regist_Button_isShow.setBackground(null);
		regist_Button_isShow.setBorder(null);
		regist_Button_isShow.setGraphic(image_Password_Noshow);

	}
}
