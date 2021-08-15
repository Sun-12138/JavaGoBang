package pers.gobang.method;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Optional;

public class MessageDialog {

    /**
     * 显示一个Dialog
     * @param title dialog标题
     * @param header_title 内容头标题
     * @param context_title 内容
     * @param icon_path dialog图标路径
     * @param image_path 在dialog上显示的图标路径
     * @return 若返回1则显示点击了yes按钮，反之返回0
     */
    public int showDialog(String title, String header_title, String context_title, String icon_path, String image_path) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header_title);
        alert.setContentText(context_title);

        if (image_path != null) {
            ImageView imageView = new ImageView(image_path);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            alert.setGraphic(imageView);
        }
        if(icon_path != null) {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(icon_path));
        }

        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.isPresent() && buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
            return 1;
        }
        else {
            return 0;
        }
    }
}
