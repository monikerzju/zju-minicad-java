import java.util.ArrayList;
import java.util.List;

//定义图形管理器
//用ArrayList实现
//由于只需要一个ShapeManager
//而且和其他UI耦合度较低
//所以设计为静态类，初始化时执行new操作
public class ShapeManager {
	private static List<Element> ls = new ArrayList<Element>();

	public static List<Element> getList() {
		return ls;
	}
	
	public static void add(Element e) {
		ls.add(e);
	}
	
	public static void remove(Element e) {
		ls.remove(e);
	}
	
	public static void removeAll() {
		ls.removeAll(ls);
	}
}
