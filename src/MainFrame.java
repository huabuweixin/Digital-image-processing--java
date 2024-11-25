
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

import java.io.*;
import javax.imageio.*;
import java.util.*;

public class MainFrame extends JFrame {
	JMenuBar mb;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem clearItem;
    JMenuItem exitItem;
    
    JMenu editMenu;      //学生可修改 ，增加菜单
    JMenuItem addItem;
    JMenuItem Item1;     //学生可修改 ，增加菜单
    JMenuItem Item2;     //学生可修改 ，增加菜单
    JMenu Item3;
    JMenu Item4;
    JMenuItem Item5;
    JMenuItem Item6;
    JMenuItem Item7;
    JMenuItem Item8;
    JMenuItem Item9;
    JMenuItem Item10;
    JMenu  Item11;
    JMenuItem Item12;
    JMenuItem Item13;
    JMenu Item14;
    JMenuItem Item15;
    JMenuItem Item16;
    JMenuItem Item17;
    JMenuItem Item18;
    JMenuItem Item19;
    JMenuItem Item20;
    JMenuItem Item21;
    JMenuItem Item22;
    JMenuItem Item23;
    JMenuItem Item24;
    JMenuItem Item25;
    JMenu Item26;
    JMenuItem Item27;
    JMenuItem Item28;
    JMenuItem Item29;
    JMenuItem Item30;
    JMenu Item31;
    JMenuItem Item32;
    JMenuItem Item33;
    JMenuItem Item34;
    JMenu Item35;
    JMenuItem Item36;
    JMenuItem Item37;
    //JToolBar tb;
    //JButton newBtn;
    //JButton openBtn;
    //JButton saveBtn;
    
    
    ImagePanel imagePanel;
    ImagePanel imageLabel;
    JScrollPane scrollPane;
    ImageIcon imageIcon;
    BufferedImage image;
    
    JFileChooser chooser;
    ImagePreviewer imagePreviewer;
    ImageFileView fileView;
    
    ImageFileFilter bmpFilter;
    ImageFileFilter jpgFilter;
	ImageFileFilter gifFilter;
	ImageFileFilter bothFilter;
	
	    
    public MainFrame() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit(e);
            }
        });
        
        initComponents();
    }
    
    private void initComponents() {
    	Container contentPane = getContentPane();
    	
    	JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    	
    	imagePanel = new ImagePanel(image);
        scrollPane = new JScrollPane(imagePanel);
       
        imageLabel=new ImagePanel(image);
        
        splitPane.setLeftComponent(new JScrollPane(imageLabel));
    	splitPane.setRightComponent(scrollPane);
        contentPane.add(splitPane, BorderLayout.CENTER);
        
        chooser = new JFileChooser();
        imagePreviewer = new ImagePreviewer(chooser);
        fileView = new ImageFileView();
	    bmpFilter = new ImageFileFilter("bmp", "BMP Image Files");
	    jpgFilter = new ImageFileFilter("jpg", "JPEG Compressed Image Files");
		gifFilter = new ImageFileFilter("gif", "GIF Image Files");
		bothFilter = new ImageFileFilter(new String[] {"bmp", "jpg", "gif"}, "BMP, JPEG and GIF Image Files");
	    chooser.addChoosableFileFilter(jpgFilter);
	    chooser.addChoosableFileFilter(gifFilter);
	    chooser.addChoosableFileFilter(bmpFilter);
        chooser.addChoosableFileFilter(bothFilter);
        chooser.setAccessory(imagePreviewer);
        chooser.setFileView(fileView);
        chooser.setAcceptAllFileFilterUsed(false);
         
              
		//----菜单条------------------------------------------------------------
		mb = new JMenuBar();
		setJMenuBar(mb);
		//----File菜单----------------------------------------------------------
		fileMenu = new JMenu("文件(F)");
		//fileMenu.setIcon(fileIcon);
		fileMenu.setMnemonic('F');
		mb.add(fileMenu);
		
		//newItem = new JMenuItem("新建(N)"); 
		//newItem.setMnemonic('N');
		//newItem.setAccelerator(KeyStroke.getKeyStroke('N', Event.CTRL_MASK));
		
		openItem = new JMenuItem("打开(O)");  
		openItem.setMnemonic('O');
		openItem.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK));
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile(e);
			}
		});
		
		saveItem = new JMenuItem("保存(S)"); 
		saveItem.setMnemonic('S');
		saveItem.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
		
		clearItem = new JMenuItem("清除右图(C)");
		clearItem.setMnemonic('C');
		clearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear_rImage(e);
			}
		});
		exitItem = new JMenuItem("退出(X)");
		exitItem.setMnemonic('X');
		
		//fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(clearItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		//----Edit菜单----------------------------------------------------------
		editMenu = new JMenu("编辑(E)");
		editMenu.setMnemonic('E');
		mb.add(editMenu);
		
		addItem = new JMenuItem("图像加运算(A)");   //, undoIcon);
		addItem.setMnemonic('A');
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imageAdd(e);       //子菜单处理程序
			}
		});
		
		Item1 = new JMenuItem("取反(U)");   //, undoIcon);
		Item1.setMnemonic('U');
		//undoItem.setAccelerator(KeyStroke.getKeyStroke('Z', Event.CTRL_MASK));
		Item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item1_process(e);       //子菜单1处理程序
			}
		});
		
		Item2 = new JMenuItem("添加水印(R)");   //, redoIcon);
		Item2.setMnemonic('R');
		//redoItem.setAccelerator(KeyStroke.getKeyStroke('Y', Event.CTRL_MASK));
		Item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item2_process(e);       //子菜单2处理程序
			}
		});
		Item3=new JMenu("放缩");
		Item5=new JMenuItem("最近邻内插法");
		Item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item5_process(e);       
			}
		});
		Item6=new JMenuItem("双线性内插法");
		Item6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item6_process(e);       
			}
		});
		Item3.add(Item5);
		Item3.add(Item6);
		Item4=new JMenu("旋转(T)");
		Item4.setMnemonic('T');
		Item7=new JMenuItem("最近邻内插法");
		Item7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item7_process(e);       
			}
		});
		Item8=new JMenuItem("双线性内插法");
		Item8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item8_process(e);       
			}
		});
		Item4.add(Item7);
		Item4.add(Item8);
		Item9=new JMenuItem("几何失真校正");
		Item9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item9_process(e);       
			}
		});
		Item11=new JMenu("图像增强");
		Item10=new JMenuItem("线性图像增强");
		Item10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item10_process(e);       
			}
		});
		Item12=new JMenuItem("幂律图像增强");
		Item12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item12_process(e);       
			}
		});
		Item13=new JMenuItem("直方图像增强");
		Item13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item13_process(e);
			}
		});		
		Item11.add(Item10);
		Item11.add(Item12);
		Item11.add(Item13);
		Item14=new JMenu("图像滤波");
		Item15=new JMenuItem("3*3算数均值滤波");
		Item15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item15_process(e);
			}
		});		
		Item16=new JMenuItem("5*5算术均值滤波");
		Item16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item16_process(e);
			}
		});	
		Item17=new JMenuItem("3*3加权均值滤波");
		Item17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item17_process(e);
			}
		});	
		Item18=new JMenuItem("中值滤波");
		Item18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item18_process(e);
			}
		});	
		Item19=new JMenuItem("算术均值滤波（可选）");
		Item19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item19_process(e);
			}
		});	
		Item20=new JMenuItem("中值滤波（可选）");
		Item20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item20_process(e);
			}
		});	
		Item21=new JMenuItem("拉普拉斯边缘提取");
		Item21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item21_process(e);
			}
		});	
		Item22=new JMenuItem("拉普拉斯边缘锐化");
		Item22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item22_process(e);
			}
		});	
		Item23=new JMenuItem("边缘提取(可选)");
		Item23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item23_process(e);
			}
		});	
		Item24=new JMenuItem("傅里叶变换正变换");
		Item24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item24_process(e);
			}
		});	
		Item25 =new JMenuItem("傅里叶变换逆变换");
		Item25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item24_process(e);
			}
		});	
		Item26=new JMenu("滤波器");
		Item27=new JMenuItem("低通滤波器");
		Item27.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item27_process(e);
			}
		});
		Item28=new JMenuItem("高通滤波器");
		Item28.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item28_process(e);
			}
		});
		Item29=new JMenuItem("彩转灰");
		Item29.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item29_process(e);
			}
		});
		Item30=new JMenuItem("彩色直方图均衡化");
		Item30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item30_process(e);
			}
		});
		Item31=new JMenu("彩色中值滤波");
		Item32=new JMenuItem("矢量中值");
		Item32.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item32_process(e);
			}
		});
		Item33=new JMenuItem("标量中值");
		Item33.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item33_process(e);
			}
		});
		Item34=new JMenuItem("单通道阈值");
		Item34.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item34_process(e);
			}
		});
		Item35=new JMenu("色彩阈值分割");
		Item36=new JMenuItem("色彩阈值分割RGB");
		Item36.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item36_process(e);
			}
		});
		Item37=new JMenuItem("色彩阈值分割HSI");
		Item37.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item37_process(e);
			}
		});
		Item26.add(Item27);
		Item26.add(Item28);
		Item14.add(Item15);
		Item14.add(Item16);
		Item14.add(Item17);
		Item14.add(Item18);
		Item14.add(Item19);
		Item14.add(Item20);
		Item31.add(Item32);
		Item31.add(Item33);
		Item35.add(Item36);
		Item35.add(Item37);
		editMenu.add(addItem);
		editMenu.add(Item1);
		editMenu.add(Item2);
		editMenu.add(Item3);
		editMenu.add(Item4);
		editMenu.add(Item9);
		editMenu.add(Item11);
		editMenu.add(Item14);
		editMenu.add(Item21);
		editMenu.add(Item22);
		editMenu.add(Item23);
		editMenu.add(Item24);
		editMenu.add(Item25);
		editMenu.add(Item26);
		editMenu.add(Item29);
		editMenu.add(Item30);
		editMenu.add(Item31);
		editMenu.add(Item34);
		editMenu.add(Item35);
    }
    
    /** 退出程序事件 */
    private void exit(WindowEvent e) {
        System.exit(0);
    }
    
    void openFile(ActionEvent e) {
    	chooser.setDialogType(JFileChooser.OPEN_DIALOG);
    	if(chooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
    		try { image = ImageIO.read(chooser.getSelectedFile()); }
        	catch(Exception ex) { return ;}
        	//imagePanel.setImage(image);  //右图像
        	imageLabel.setImage(image); //左图像 
        	imagePanel.repaint();
        	
    	}
    }
    
    void saveFile(ActionEvent e) {
        	
    }
    
    void clear_rImage(ActionEvent e) {
    	
     	imagePanel.setImage(null);
    	imagePanel.repaint();	
    }
      
    void imageAdd(ActionEvent e) {
    	BufferedImage image1=null;	
       	chooser.setDialogType(JFileChooser.OPEN_DIALOG);
    	if(chooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
    		try { image1 = ImageIO.read(chooser.getSelectedFile());
    		}
        	catch(Exception ex) { return ;}
    	}
    	JPanel inputPanel = new JPanel();  
        inputPanel.setLayout(new GridLayout(2, 2));  
        JLabel xLabel = new JLabel("请输入x的值：");  
        JTextField xField = new JTextField(10);  
        inputPanel.add(xLabel);  
        inputPanel.add(xField);  
        JLabel yLabel = new JLabel("请输入y的值：");  
        JTextField yField = new JTextField(10);  
        inputPanel.add(yLabel);  
        inputPanel.add(yField);  
        int result = JOptionPane.showConfirmDialog(null, inputPanel, "输入x和y的值", JOptionPane.OK_CANCEL_OPTION);  
        if (result != JOptionPane.OK_OPTION) {  
            // 用户点击了取消或关闭了弹窗  
            return;  
        }  
  
        float x = Float.parseFloat(xField.getText());  
        float y = Float.parseFloat(yField.getText());  
      	image=EditImage.image_add(image,image1,x,y);
    	imagePanel.setImage(image);
    	imagePanel.repaint();
    }    
    
    void Item1_process(ActionEvent e) {
    	BufferedImage image1=null;	
       	chooser.setDialogType(JFileChooser.OPEN_DIALOG);
       	if(chooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
    		try { image1 = ImageIO.read(chooser.getSelectedFile()); }
        	catch(Exception ex) { return ;}
    	}
       	image=EditImage.ImageNegative(image1);
       	imagePanel.setImage(image);
    	imagePanel.repaint();
    }
    
    void Item2_process(ActionEvent e) {
    	BufferedImage image1=null;	
       	chooser.setDialogType(JFileChooser.OPEN_DIALOG);
       	if(chooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
    		try { image1 = ImageIO.read(chooser.getSelectedFile()); }
        	catch(Exception ex) { return ;}
    	}
       	JPanel inputPanel = new JPanel();  
        inputPanel.setLayout(new GridLayout(3, 3));  
        JLabel fLabel = new JLabel("请输入透明度：");  
        JTextField fField = new JTextField(10);  
        inputPanel.add(fLabel);  
        inputPanel.add(fField);  
        JLabel xLabel = new JLabel("水印位置x：");  
        JTextField xField = new JTextField(10);  
        inputPanel.add(xLabel);  
        inputPanel.add(xField);  
        JLabel yLabel = new JLabel("水印位置y：");  
        JTextField yField = new JTextField(10);  
        inputPanel.add(yLabel);  
        inputPanel.add(yField);  
        int result = JOptionPane.showConfirmDialog(null, inputPanel, "水印透明度及其位置", JOptionPane.OK_CANCEL_OPTION);  
        if (result != JOptionPane.OK_OPTION) {  
            // 用户点击了取消或关闭了弹窗  
            return;  
        }  
  
        float f = Float.parseFloat(fField.getText());  
        int x = Integer.parseInt(xField.getText());
        int y = Integer.parseInt(yField.getText());
       	image=EditImage.Imagewater(image,image1,f,x,y);     	
    	imagePanel.setImage(image);
    	imagePanel.repaint();	
    }
    void Item5_process(ActionEvent e) {
    	JPanel inputPanel = new JPanel();  
        inputPanel.setLayout(new GridLayout(2, 2));  
        JLabel xLabel = new JLabel("请输入宽度系数x的值：");  
        JTextField xField = new JTextField(10);  
        inputPanel.add(xLabel);  
        inputPanel.add(xField);  
        JLabel yLabel = new JLabel("请输入高度系数y的值：");  
        JTextField yField = new JTextField(10);  
        inputPanel.add(yLabel);  
        inputPanel.add(yField);  
        int result = JOptionPane.showConfirmDialog(null, inputPanel, "输入x和y的值", JOptionPane.OK_CANCEL_OPTION);  
        if (result != JOptionPane.OK_OPTION) {  
            // 用户点击了取消或关闭了弹窗  
            return;  
        }  
  
        float x = Float.parseFloat(xField.getText());  
        float y = Float.parseFloat(yField.getText());
        image=EditImage.ImagezoomN(image, x, y);
        imagePanel.setImage(image);
        imagePanel.repaint();
        
    }
    void Item6_process(ActionEvent e) {
    	JPanel inputPanel = new JPanel();  
        inputPanel.setLayout(new GridLayout(2, 2));  
        JLabel xLabel = new JLabel("请输入宽度系数x的值：");  
        JTextField xField = new JTextField(10);  
        inputPanel.add(xLabel);  
        inputPanel.add(xField);  
        JLabel yLabel = new JLabel("请输入高度系数y的值：");  
        JTextField yField = new JTextField(10);  
        inputPanel.add(yLabel);  
        inputPanel.add(yField);  
        int result = JOptionPane.showConfirmDialog(null, inputPanel, "输入x和y的值", JOptionPane.OK_CANCEL_OPTION);  
        if (result != JOptionPane.OK_OPTION) {  
            // 用户点击了取消或关闭了弹窗  
            return;  
        }  
  
        float x = Float.parseFloat(xField.getText());  
        float y = Float.parseFloat(yField.getText());
        image=EditImage.ImagezoomS(image, x, y);
        imagePanel.setImage(image);
        imagePanel.repaint();
        
    }
  void Item7_process(ActionEvent e) {
	  JPanel inputPanel = new JPanel();  
      inputPanel.setLayout(new GridLayout(1, 1));  
      JLabel xLabel = new JLabel("请输入旋转角度f：");  
      JTextField xField = new JTextField(10);  
      inputPanel.add(xLabel);  
      inputPanel.add(xField);  
      int result = JOptionPane.showConfirmDialog(null, inputPanel, "输入旋转角度", JOptionPane.OK_CANCEL_OPTION);  
      if (result != JOptionPane.OK_OPTION) {  
          // 用户点击了取消或关闭了弹窗  
          return;  
      }
      float f = Float.parseFloat(xField.getText());
      image=EditImage.rotateImageN(image, f);
      imagePanel.setImage(image);
      imagePanel.repaint();
    }
  void Item8_process(ActionEvent e) {
	  JPanel inputPanel = new JPanel();  
      inputPanel.setLayout(new GridLayout(1, 1));  
      JLabel xLabel = new JLabel("请输入旋转角度f：");  
      JTextField xField = new JTextField(10);  
      inputPanel.add(xLabel);  
      inputPanel.add(xField);  
      int result = JOptionPane.showConfirmDialog(null, inputPanel, "输入旋转角度", JOptionPane.OK_CANCEL_OPTION);  
      if (result != JOptionPane.OK_OPTION) {  
          // 用户点击了取消或关闭了弹窗  
          return;  
      }
      float f = Float.parseFloat(xField.getText());
      image=EditImage.rotateImageS(image, f);
      imagePanel.setImage(image);
      imagePanel.repaint();
  }
  void Item9_process(ActionEvent e) {
      image=EditImage.ImageCorrection(image);
      imagePanel.setImage(image);
      imagePanel.repaint();
  }
  void Item10_process(ActionEvent e) {
      image=EditImage.Imageprove1(image);
      imagePanel.setImage(image);
      imagePanel.repaint();
  }
  void Item12_process(ActionEvent e) {
	  JPanel inputPanel = new JPanel();  
      inputPanel.setLayout(new GridLayout(1, 1));  
      JLabel xLabel = new JLabel("请输入幂次f：");  
      JTextField xField = new JTextField(10);  
      inputPanel.add(xLabel);  
      inputPanel.add(xField);  
      int result = JOptionPane.showConfirmDialog(null, inputPanel, "输入幂次", JOptionPane.OK_CANCEL_OPTION);  
      if (result != JOptionPane.OK_OPTION) {  
          // 用户点击了取消或关闭了弹窗  
          return;  
      }
      float f = Float.parseFloat(xField.getText());
      image=EditImage.Imageprove2(image,f);
      imagePanel.setImage(image);
      imagePanel.repaint();
  }
  void Item13_process(ActionEvent e) {
      image=EditImage.Imageprove3(image);
      imagePanel.setImage(image);
      imagePanel.repaint();
  }
  void Item15_process(ActionEvent e) {
	  image=EditImage.arithmetic3(image);
      imagePanel.setImage(image);
      imagePanel.repaint();
  }
 void Item16_process(ActionEvent e) {
	 image=EditImage.arithmetic5(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item17_process(ActionEvent e) {
	 image=EditImage.Weightarithmetic(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item18_process(ActionEvent e) {
	 image=EditImage.midaverage(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item19_process(ActionEvent e) {
	 JPanel inputPanel = new JPanel();  
     inputPanel.setLayout(new GridLayout(1, 1));  
     JLabel xLabel = new JLabel("请x*x中x的值：");  
     JTextField xField = new JTextField(10);  
     inputPanel.add(xLabel);  
     inputPanel.add(xField);  
     int result = JOptionPane.showConfirmDialog(null, inputPanel, "输入x*x中x的值", JOptionPane.OK_CANCEL_OPTION);  
     if (result != JOptionPane.OK_OPTION) {  
         // 用户点击了取消或关闭了弹窗  
         return;  
     }
     int x = Integer.parseInt(xField.getText());
	 image=EditImage.arithmeticx(image,x);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item20_process(ActionEvent e) {
	 JPanel inputPanel = new JPanel();  
     inputPanel.setLayout(new GridLayout(1, 1));  
     JLabel xLabel = new JLabel("请x*x中x的值：");  
     JTextField xField = new JTextField(10);  
     inputPanel.add(xLabel);  
     inputPanel.add(xField);  
     int result = JOptionPane.showConfirmDialog(null, inputPanel, "输入x*x中x的值", JOptionPane.OK_CANCEL_OPTION);  
     if (result != JOptionPane.OK_OPTION) {  
         // 用户点击了取消或关闭了弹窗  
         return;  
     }
     int x = Integer.parseInt(xField.getText());
	 image=EditImage.midaveragex(image,x);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item21_process(ActionEvent e) {
	 image=EditImage.laplaceedge(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item22_process(ActionEvent e) {
	 JPanel inputPanel = new JPanel();  
     inputPanel.setLayout(new GridLayout(1, 1));  
     JLabel xLabel = new JLabel("请输入k值：");  
     JTextField xField = new JTextField(10);  
     inputPanel.add(xLabel);  
     inputPanel.add(xField);  
     int result = JOptionPane.showConfirmDialog(null, inputPanel, "输入k值", JOptionPane.OK_CANCEL_OPTION);  
     if (result != JOptionPane.OK_OPTION) {  
         // 用户点击了取消或关闭了弹窗  
         return;  
     }
     int x = Integer.parseInt(xField.getText());
	 image=EditImage.laplaceprove(image,x);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item23_process(ActionEvent e) {
	 JPanel inputPanel = new JPanel();  
     inputPanel.setLayout(new GridLayout(3, 2)); 
     JLabel yLabel = new JLabel("1:Sobel算子,2:Prewitt算子,3:Roberts算子");  
     JLabel xLabel = new JLabel("请输入x:");  
     JTextField xField = new JTextField(10);
     inputPanel.add(yLabel); 
     inputPanel.add(xLabel);  
     inputPanel.add(xField);  
     int result = JOptionPane.showConfirmDialog(null, inputPanel, "边缘提取(可选)", JOptionPane.OK_CANCEL_OPTION);  
     if (result != JOptionPane.OK_OPTION) {  
         // 用户点击了取消或关闭了弹窗  
         return;  
     }
     int x = Integer.parseInt(xField.getText());
	 image=EditImage.Edge(image,x);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item24_process(ActionEvent e) {
	 image=EditImage.FFTimage(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item25_process(ActionEvent e) {
	 image=EditImage.FFTimagerecover(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item27_process(ActionEvent e) {
	 JPanel inputPanel = new JPanel();  
     inputPanel.setLayout(new GridLayout(1, 1));  
     JLabel xLabel = new JLabel("请输入D0：");  
     JTextField xField = new JTextField(10);  
     inputPanel.add(xLabel);  
     inputPanel.add(xField);  
     int result = JOptionPane.showConfirmDialog(null, inputPanel, "D0", JOptionPane.OK_CANCEL_OPTION);  
     if (result != JOptionPane.OK_OPTION) {  
         // 用户点击了取消或关闭了弹窗  
         return;  
     }
     double d = Float.parseFloat(xField.getText());
	 image=EditImage.FFTimagelow(image,d);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item28_process(ActionEvent e) {
	 JPanel inputPanel = new JPanel();  
     inputPanel.setLayout(new GridLayout(1, 1));  
     JLabel xLabel = new JLabel("请输入D0：");  
     JTextField xField = new JTextField(10);  
     inputPanel.add(xLabel);  
     inputPanel.add(xField);  
     int result = JOptionPane.showConfirmDialog(null, inputPanel, "D0", JOptionPane.OK_CANCEL_OPTION);  
     if (result != JOptionPane.OK_OPTION) {  
         // 用户点击了取消或关闭了弹窗  
         return;  
     }
     double d = Float.parseFloat(xField.getText());
	 image=EditImage.FFTimagerecover(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
  }
 void Item29_process(ActionEvent e) {
	 image=EditImage.colortogray(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
 }
 void Item30_process(ActionEvent e) {
	 image=EditImage.colorImageprove(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
 }
 void Item32_process(ActionEvent e) {
	 image=EditImage.vectormid(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
 }
 void Item33_process(ActionEvent e) {
	 image=EditImage.scalarmid(image);
     imagePanel.setImage(image);
     imagePanel.repaint();
 }
 void Item34_process(ActionEvent e) {
	 JPanel inputPanel = new JPanel();  
     inputPanel.setLayout(new GridLayout(1, 1));  
     JLabel xLabel = new JLabel("请输入T：");  
     JTextField xField = new JTextField(10);  
     inputPanel.add(xLabel);  
     inputPanel.add(xField);  
     int result = JOptionPane.showConfirmDialog(null, inputPanel, "T", JOptionPane.OK_CANCEL_OPTION);  
     if (result != JOptionPane.OK_OPTION) {  
         // 用户点击了取消或关闭了弹窗  
         return;  
     }
     int t = Integer.parseInt(xField.getText());
	 image=EditImage.singlechannel(image,t);
     imagePanel.setImage(image);
     imagePanel.repaint();
 }
 void Item36_process(ActionEvent e) {
	 JPanel inputPanel = new JPanel();  
     inputPanel.setLayout(new GridLayout(4, 3));  
     JLabel xLabel = new JLabel("请输入T：");  
     JTextField xField = new JTextField(10);  
     inputPanel.add(xLabel);  
     inputPanel.add(xField);  
     JLabel rLabel = new JLabel("请输入R0：");  
     JTextField rField = new JTextField(3);  
     inputPanel.add(rLabel);  
     inputPanel.add(rField);  
     JLabel gLabel = new JLabel("请输入G0：");  
     JTextField gField = new JTextField(3);  
     inputPanel.add(gLabel);  
     inputPanel.add(gField);  
     JLabel bLabel = new JLabel("请输入B0：");  
     JTextField bField = new JTextField(3);  
     inputPanel.add(bLabel);  
     inputPanel.add(bField);  
     int result = JOptionPane.showConfirmDialog(null, inputPanel, "T", JOptionPane.OK_CANCEL_OPTION);  
     if (result != JOptionPane.OK_OPTION) {  
         // 用户点击了取消或关闭了弹窗  
         return;  
     }
     int t = Integer.parseInt(xField.getText());
     int r0=Integer.parseInt(rField.getText());
     int g0=Integer.parseInt(gField.getText());
     int b0=Integer.parseInt(bField.getText());
	 image=EditImage.rgbchannel(image,t,r0,g0,b0);
     imagePanel.setImage(image);
     imagePanel.repaint();
 }
 void Item37_process(ActionEvent e) {
	 JPanel inputPanel = new JPanel();  
     inputPanel.setLayout(new GridLayout(4, 3));  
     JLabel xLabel = new JLabel("请输入T：");  
     JTextField xField = new JTextField(10);  
     inputPanel.add(xLabel);  
     inputPanel.add(xField);  
     JLabel rLabel = new JLabel("请输入H0：");  
     JTextField rField = new JTextField(3);  
     inputPanel.add(rLabel);  
     inputPanel.add(rField);  
     JLabel gLabel = new JLabel("请输入S0：");  
     JTextField gField = new JTextField(3);  
     inputPanel.add(gLabel);  
     inputPanel.add(gField);  
     JLabel bLabel = new JLabel("请输入I0：");  
     JTextField bField = new JTextField(3);  
     inputPanel.add(bLabel);  
     inputPanel.add(bField);  
     int result = JOptionPane.showConfirmDialog(null, inputPanel, "T", JOptionPane.OK_CANCEL_OPTION);  
     if (result != JOptionPane.OK_OPTION) {  
         // 用户点击了取消或关闭了弹窗  
         return;  
     }
     int t = Integer.parseInt(xField.getText());
     int r0=Integer.parseInt(rField.getText());
     int g0=Integer.parseInt(gField.getText());
     int b0=Integer.parseInt(bField.getText());
	 image=EditImage.rgbchannel(image,t,r0,g0,b0);
     imagePanel.setImage(image);
     imagePanel.repaint();
 }


}

