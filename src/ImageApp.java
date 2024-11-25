
import java.awt.*;
import javax.swing.*;
 
public class ImageApp
{
	public static void main(String args[]) {
		//设置默认字体
		Font defaultFont = new Font("System", Font.PLAIN, 12);
		UIManager.put("Button.font", defaultFont);
		UIManager.put("CheckBox.font", defaultFont);
		UIManager.put("RadioButton.font", defaultFont);
		UIManager.put("ToolTip.font", defaultFont);
		UIManager.put("ComboBox.font", defaultFont);
		UIManager.put("Label.font", defaultFont);
		UIManager.put("List.font", defaultFont);
		UIManager.put("Table.font", defaultFont);
		UIManager.put("TableHeader.font", defaultFont);
		UIManager.put("MenuBar.font", defaultFont);
		UIManager.put("Menu.font", defaultFont);
		UIManager.put("MenuItem.font", defaultFont);		
		UIManager.put("PopupMenu.font", defaultFont);
		UIManager.put("Tree.font", defaultFont);
		UIManager.put("ToolBar.font", defaultFont);	
		//创建主窗口
	    MainFrame mainFrame = new MainFrame();
	    mainFrame.setTitle("smallPhotoshop");
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
	}
}
