import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IOFunction {
	private static final String sepa = java.io.File.separator;
	
	//读文件，处理文件中的字符串
	//通过文件构造相应对象存储进ShapeManager中
	//文件格式见下方save()函数
	public static void open(String path, String file) throws FileNotFoundException, IOException {
		ShapeManager.removeAll();
		FileReader rd = new FileReader(path + sepa + file);
		BufferedReader br = new BufferedReader(rd);
		String s = new String(br.readLine());
		br.close();
		int i;
		List<String> ls = new ArrayList<String>();
		for (i = 0; i < s.length(); i++) {
			int j;
			StringBuilder sb = new StringBuilder();
			for (j = i; j < s.length() - 1; j++) {
				if (s.charAt(j) == ' ' && s.charAt(j + 1) == ' ') {
					j++;
					sb.append(' ');
				} else if (s.charAt(j) == ' ')
					break;
				else
					sb.append(s.charAt(j));
			}
			i = j;
			ls.add(sb.toString());
		}
		List<Element> le = ShapeManager.getList();
		Element e;
		i = 0;
		while (i != ls.size()) {
			if (ls.get(i).equals("Line"))
				e = new Line(Integer.parseInt(ls.get(++i)), Integer.parseInt(ls.get(i + 1)),
						Integer.parseInt(ls.get(i + 2)), Integer.parseInt(ls.get(i + 3)));
			else if (ls.get(i).equals("Rectangle"))
				e = new Rectangle(Integer.parseInt(ls.get(++i)), Integer.parseInt(ls.get(i + 1)),
						Integer.parseInt(ls.get(i + 2)), Integer.parseInt(ls.get(i + 3)));
			else if (ls.get(i).equals("Oval"))
				e = new Oval(Integer.parseInt(ls.get(++i)), Integer.parseInt(ls.get(i + 1)),
						Integer.parseInt(ls.get(i + 2)), Integer.parseInt(ls.get(i + 3)));
			else {
				e = new StringText(Integer.parseInt(ls.get(++i)), Integer.parseInt(ls.get(i + 1)), ls.get(i + 2));
				((StringText) e).f = new Font("SansSarif", Font.BOLD, Integer.parseInt(ls.get(i + 3)));
			}
			i += 4;
			e.eColor = ToolBar.clr[Integer.parseInt(ls.get(i))];
			e.strokeWidth = Float.parseFloat(ls.get(i + 1));
			if (ls.get(i + 2).equals("true"))
				e.select = true;
			else
				e.select = false;
			i += 3;
			le.add(e);
		}
	}

	//写文件
	//文件格式如下
	//Line x1 y1 x2 y2 color stroke isSelected
	//Rectangle x y width height color ... ...
	//...
	//StringText要记录内容，可能有空格，此时我们用2个空格代表1个空格（类似于转义符号'\'）
	public static void save(String path, String file) throws IOException {
		StringBuilder sb = new StringBuilder();
		List<Element> ls = ShapeManager.getList();
		for (Element e : ls) {
			if (e instanceof Line)
				sb.append("Line " + (int)((Line) e).x1 + " " + (int)((Line) e).y1 + " " + (int)((Line) e).x2 + " " + (int)((Line) e).y2
						+ " ");
			else if (e instanceof Rectangle)
				sb.append("Rectangle " + (int)((Rectangle) e).x + " " + (int)((Rectangle) e).y + " " + (int)((Rectangle) e).width + " "
						+ (int)((Rectangle) e).height + " ");
			else if (e instanceof Oval)
				sb.append("Oval " + ((Oval) e).x + " " + ((Oval) e).y + " " + ((Oval) e).width + " " + ((Oval) e).height
						+ " ");
			else {
				String dest = ((StringText) e).content;
				dest = dest.replace(" ", "  ");
				sb.append("Text " + (int)((StringText) e).x + " " + (int)((StringText) e).y + " " + dest + " "
						+ ((StringText) e).f.getSize() + " ");
			}
			for (int i = 0; i < ToolBar.clr.length; i++)
				if (e.eColor.equals(ToolBar.clr[i])) {
					sb.append(i + " ");
					break;
				}
			sb.append(e.strokeWidth + " ").append(e.select + " ");
		}
		FileWriter wt = new FileWriter(path + sepa + file);
		wt.write(sb.toString());
		wt.flush();
		wt.close();
	}
}
