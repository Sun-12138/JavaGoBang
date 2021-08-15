package pers.gobang.method;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Circle {

	/**
	 *  画出一个空心圆
	 * @param x 棋盘坐标x
	 * @param y 棋盘坐标y
	 * @param circle_LineWidth 线的宽度
	 * @param chessPaneUnitSize 一个棋子的大小
	 * @param color 线条的颜色
	 * @return 返回空心圆对象
	 */
	public javafx.scene.shape.Circle drawCircle(int x, int y, int circle_LineWidth, int chessPaneUnitSize, Paint color) {
		javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle();
		circle.setCenterX(chessPaneUnitSize / 2 + (x * 64) + chessPaneUnitSize / 2);
		circle.setCenterY(chessPaneUnitSize / 2 + (y * 64) + chessPaneUnitSize / 2);
		circle.setStrokeWidth(circle_LineWidth);
		circle.setRadius(chessPaneUnitSize / 2);
		circle.setStroke(color);
		circle.setFill(Color.rgb(255, 255, 255, 0.0));
		return circle;
	}
}
