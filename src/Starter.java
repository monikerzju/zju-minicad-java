import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Starter {
	private static JFrame frm;
	public static final int wSize = 600;

	//初始界面，建立右侧工具栏ToolBar，给Frame添加基本的属性和Components
	//Frame使用边界布局，东侧为ToolBar，中间为ToolBar的成员变量cadPanel
	//Frame显示在屏幕中间，大小不可以调整
	public Starter() {
		ToolBar t = new ToolBar();
		frm = new JFrame("MiniCAD");
		frm.setSize(wSize, wSize);
		frm.setResizable(false);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setLayout(new BorderLayout());
		frm.add(t, BorderLayout.EAST);
		frm.add(t.cadPanel, BorderLayout.CENTER);
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);
	}

	//静态方法，打开.cad文件，若错误则抛出相应异常
	//调用IOFunction类的read()静态方法，把文件内容进行转换，读到ShapeManager类的List<Shape>中
	public static void open() {
		FileDialog op = new FileDialog(frm, "Open", FileDialog.LOAD);
		op.setVisible(true);
		op.setLocationRelativeTo(null);
		String path = op.getDirectory();
		String fileName = op.getFile();
		if (path == null || fileName == null)
			return;
		try {
			IOFunction.open(path, fileName);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error", "File not found!", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error", "IO exception!", JOptionPane.ERROR_MESSAGE);
		}
	}

	//静态方法，写入.cad文件，同样可能抛出异常
	//调用IOFuncution类的save()方法，把List<Shape>里面的图形转化为文本写到文件中
	public static void save() {
		FileDialog sv = new FileDialog(frm, "Save", FileDialog.SAVE);
		sv.setVisible(true);
		sv.setLocationRelativeTo(null);
		String path = sv.getDirectory();
		String fileName = sv.getFile();
		if (path == null || fileName == null)
			return;
		try {
			IOFunction.save(path, fileName);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error", "IO exception!", JOptionPane.ERROR_MESSAGE);
		}
	}

	//main()函数入口
	public static void main(String[] args) {
		new Starter();
	}
}
