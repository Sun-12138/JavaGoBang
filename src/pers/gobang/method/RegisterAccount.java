package pers.gobang.method;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RegisterAccount {
	/**
	 * 保存为XML对象
	 *
	 * @param object    需要保存的对象
	 * @param file_Path 需要保存到的路径
	 */
	public void saveUser(Object object, FileOutputStream file_Path) {
		BufferedOutputStream out = new BufferedOutputStream(file_Path);
		XMLEncoder encoder = new XMLEncoder(out);
		encoder.writeObject(object);
		encoder.flush();
		encoder.close();
	}

	/**
	 * 从XML中加载对象
	 *
	 * @param file_Path 需要读取的xml路径
	 * @return 返回对象
	 */
	public Object loadObject(FileInputStream file_Path) {
		BufferedInputStream in = new BufferedInputStream(file_Path);
		XMLDecoder decoder = new XMLDecoder(in);
		Object obj = decoder.readObject();
		decoder.close();
		System.out.println(obj);
		return obj;
	}
}

