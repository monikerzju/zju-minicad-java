import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBar extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JButton[] jbtn;
	private static Dimension pSize;
	private static int number = 0;
	private static Color color = Color.black;
	private static final String[] btnHint = { "line", "rectangle", "text", "circle", "file" };
	public static final Color[] clr = { Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green,
			Color.lightGray, Color.pink, Color.orange, Color.red, Color.white, Color.yellow };
	protected CADPanel cadPanel = new CADPanel();

	// 设置颜色按钮
	class CADColorJPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private int i;
		private JButton[] jbtn;

		class ColorListener implements ActionListener {
			private Color clr;

			ColorListener(Color clr) {
				this.clr = clr;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				color = clr;
				List<Element> ls = ShapeManager.getList();
				for (Element ele : ls)
					if (ele.select == true)
						ele.eColor = color;
				cadPanel.repaint();
			}
		}

		// 使用表格分布，给每个按钮设置相应的颜色
		public CADColorJPanel() {
			this.setLayout(new GridLayout(4, 3));
			jbtn = new JButton[12];
			for (i = 0; i < 12; i++) {
				jbtn[i] = new JButton();
				jbtn[i].setBackground(clr[i]);
				jbtn[i].setOpaque(true);
				jbtn[i].setBorderPainted(false);
				jbtn[i].addActionListener(new ColorListener(clr[i]));
				this.add(jbtn[i]);
			}
		}
	}

	class ButtonListener implements ActionListener {
		private int num;

		public ButtonListener(int num) {
			this.num = num;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			number = num;
		}
	}

	class IOListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				Starter.open();
				cadPanel.repaint();
			} else
				Starter.save();
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	// 设置四种按钮并添加相应的监听器
	// 添加颜色按钮所在的Component
	public ToolBar() {
		Font font = new Font("Serif", Font.BOLD, 14);
		pSize = new Dimension(Starter.wSize / 6, Starter.wSize);
		this.setPreferredSize(pSize);
		this.setLayout(new GridLayout(6, 1));
		jbtn = new JButton[5];
		for (int i = 0; i < 5; i++) {
			jbtn[i] = new JButton(btnHint[i]);
			jbtn[i].setOpaque(true);
			jbtn[i].setBorderPainted(false);
			jbtn[i].setFont(font);
			if (i != 4)
				jbtn[i].addActionListener(new ButtonListener(i));
			else
				jbtn[i].addMouseListener(new IOListener());
			this.add(jbtn[i]);
		}
		this.add(new CADColorJPanel());
	}

	public static int getButton() {
		return number;
	}
}
