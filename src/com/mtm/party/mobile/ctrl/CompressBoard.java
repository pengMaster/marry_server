package com.mtm.party.mobile.ctrl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CompressBoard implements ActionListener, ChangeListener{
	
	private File file = null; // 文件对象
	private String inputDir; // 输入图路径
	private String outputDir; // 输出图路径
	private String inputFileName; // 输入图文件名
	private String outputFileName; // 输出图文件名
	private int outputWidth = 100; // 默认输出图片宽
	private int outputHeight = 100; // 默认输出图片高
	private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)

	private JFrame frmPc;
	private JTextField tf_input;
	private JTextField tf_output;
	private JTextField tf_width;
	private JTextField tf_height;
	
	private JButton btn_compress;
	
	private JRadioButton rb_yes;
	private JRadioButton rb_no;
	
	private JProgressBar progressbar;//进度条
	private Timer timer;
	
	private JLabel lb_tips;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					CompressBoard window = new CompressBoard();
					window.frmPc.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CompressBoard(){
		frmPc = new JFrame();
		frmPc.setResizable(false);
		frmPc.setTitle("PC超级图片压缩工具");
		frmPc.setBounds(100, 100, 450, 278);
		frmPc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPc.getContentPane().setLayout(null);
		
		
		JPanel panel_file_chose = new JPanel();
		panel_file_chose.setBounds(0, 0, 434, 240);
		frmPc.getContentPane().add(panel_file_chose);
		panel_file_chose.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("文件选择：");
		lblNewLabel.setBounds(58, 9, 66, 15);
		panel_file_chose.add(lblNewLabel);
		
		tf_input = new JTextField();
		tf_input.setEditable(false);
		tf_input.setBounds(128, 6, 186, 21);
		panel_file_chose.add(tf_input);
		tf_input.setColumns(30);
		
		JButton btn_input = new JButton("...");
		//响应文件选择事件
		btn_input.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        JFileChooser jfc_input = new JFileChooser();  
		        jfc_input.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
		        jfc_input.showDialog(new JLabel(), "选择进行压缩的文件");  
		        File file=jfc_input.getSelectedFile();  
		        if(file.isDirectory()){  
		            System.out.println("选择输入文件夹:"+file.getAbsolutePath());  
		            tf_input.setText(file.getAbsolutePath()); 
		            inputDir = file.getAbsolutePath();
		        }else if(file.isFile()){  
		            System.out.println("选择输入:"+file.getAbsolutePath());  
		            tf_input.setText(file.getAbsolutePath()); 
		            inputDir = file.getAbsolutePath();
		            inputFileName = jfc_input.getSelectedFile().getName();
		        }  
		        System.out.println(jfc_input.getSelectedFile().getName());  
			}
		});
		btn_input.setBounds(324, 5, 51, 23);
		panel_file_chose.add(btn_input);
		
		JLabel lblNewLabel_1 = new JLabel("等比压缩：");
		lblNewLabel_1.setBounds(58, 52, 66, 15);
		panel_file_chose.add(lblNewLabel_1);
		
		rb_yes = new JRadioButton("是");
		rb_yes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("选择是");
				tf_width.setEditable(false);
				tf_width.setText(null);
				tf_height.setEditable(false);
				tf_height.setText(null);
			}
		});
		rb_yes.setSelected(true);
		rb_yes.setBounds(148, 48, 51, 23);
		panel_file_chose.add(rb_yes);
		
		rb_no = new JRadioButton("否");
		rb_no.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tf_width.setEditable(true);
				tf_height.setEditable(true);
			}
		});
		rb_no.setBounds(213, 48, 43, 23);
		panel_file_chose.add(rb_no);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb_yes);
		bg.add(rb_no);
		
		JLabel label = new JLabel("输出路径：");
		label.setBounds(58, 128, 66, 15);
		panel_file_chose.add(label);
		
		tf_output = new JTextField();
		tf_output.setEditable(false);
		tf_output.setColumns(30);
		tf_output.setBounds(128, 125, 186, 21);
		panel_file_chose.add(tf_output);
		
		tf_input.setBackground(Color.WHITE);
		tf_output.setBackground(Color.WHITE);
		
		JButton btn_output = new JButton("...");
		//响应输出压缩图片路径
		btn_output.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        JFileChooser jfc_output = new JFileChooser();  
		        jfc_output.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
		        jfc_output.showDialog(new JLabel(), "选择保存压缩图片的路径");  
		        File file=jfc_output.getSelectedFile();  
		        if(file.isDirectory()){  
		            System.out.println("压缩输出文件夹:"+file.getAbsolutePath()); 
		            tf_output.setText(file.getAbsolutePath());
		            outputDir = file.getAbsolutePath();
		            outputFileName = inputFileName;
		        }else if(file.isFile()){  
		            System.out.println("文件:"+file.getAbsolutePath());  
		        }  
		        //System.out.println(jfc_output.getSelectedFile().getName());  
			}
		});
		btn_output.setBounds(324, 124, 51, 23);
		panel_file_chose.add(btn_output);
		
		JLabel label_1 = new JLabel("宽高设置：");
		label_1.setBounds(58, 91, 66, 15);
		panel_file_chose.add(label_1);
		
		tf_width = new JTextField();
		tf_width.setEditable(false);
		tf_width.setToolTipText("");
		tf_width.setBounds(163, 88, 53, 21);
		panel_file_chose.add(tf_width);
		tf_width.setColumns(10);
		
		JLabel label_2 = new JLabel("宽");
		label_2.setBounds(147, 91, 17, 15);
		panel_file_chose.add(label_2);
		
		JLabel label_3 = new JLabel("高");
		label_3.setBounds(244, 91, 17, 15);
		panel_file_chose.add(label_3);
		
		tf_height = new JTextField();
		tf_height.setEditable(false);
		tf_height.setToolTipText("");
		tf_height.setColumns(10);
		tf_height.setBounds(261, 88, 53, 21);
		panel_file_chose.add(tf_height);
		
		btn_compress = new JButton("开始压缩");
		btn_compress.setForeground(Color.blue);
		btn_compress.addActionListener(this);
		
		//timer = new Timer(100, this);
		
		//开始进行压缩图片
		btn_compress.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//判断是否进行等比压缩
				if(rb_yes.isSelected()){
					proportion = true;
				}
				else{
					proportion = false;
					outputWidth = Integer.valueOf(tf_width.getText());
					outputHeight = Integer.valueOf(tf_height.getText());
				}
				//将进度条初始化
				progressbar.setValue(0);
			}
		});
		btn_compress.setBounds(165, 173, 93, 23);
		panel_file_chose.add(btn_compress);
		
		//进度条设置
		progressbar = new JProgressBar();
		progressbar.setBounds(261, 216, 146, 14);
		panel_file_chose.add(progressbar);
		progressbar.setOrientation(JProgressBar.HORIZONTAL);
		progressbar.setMinimum(0);
		progressbar.setMaximum(100);
		progressbar.setValue(0);
		progressbar.setStringPainted(true);
		progressbar.addChangeListener(this);
		progressbar.setPreferredSize(new Dimension(300, 20));
		progressbar.setBorderPainted(true);
		progressbar.setBackground(Color.pink);
		progressbar.setVisible(false);
		
		
		lb_tips = new JLabel("目前已完成进度：");
		lb_tips.setBounds(261, 191, 163, 15);
		panel_file_chose.add(lb_tips);
		lb_tips.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	//private void initialize() 
	
	// 图片处理
		public String compressPic() {
			
			lb_tips.setVisible(true);
			progressbar.setVisible(true);
			// 获得源文件
			file = new File(inputDir);
			
			if (!file.exists()) {
				//throw new Exception("文件不存在");
			}
			//得到指定目录下的所有文件
			if(file.isDirectory()){
				List<File> fileList = FileUtil.getAllFiles(file);
				System.out.println("共有jpg文件："+ fileList.size() + "个");
				int value = progressbar.getValue();
				for(int i = 0; i < fileList.size(); i++){
					FileUtil.compressHandle(fileList.get(i), outputDir, outputWidth, outputHeight, proportion);
					value += 100/fileList.size();
					if(i == (fileList.size()-1)){
						progressbar.setValue(100);
						break;
					}
					progressbar.setValue(value);
				}
			}
			else{//选中的非文件目录，而是需要进行压缩的目标文件
				FileUtil.compressHandle(file, outputDir, outputWidth, outputHeight, proportion);
				progressbar.setValue(100);
				//timer.stop();
			}

			return "ok";
		}
		
		public String compressPic(String outputDir, String inputFileName,
				String outputFileName) {
			// 输出图路径
			this.outputDir = outputDir;
			// 输入图文件名
			this.inputFileName = inputFileName;
			// 输出图文件名
			this.outputFileName = outputFileName;
			return compressPic();
		}

		public void stateChanged(ChangeEvent e1) {	
			
			int value = progressbar.getValue();
			if (e1.getSource() == progressbar) {
			lb_tips.setText("目前已完成进度：" + Integer.toString(value) + "%");
			lb_tips.setForeground(Color.blue);
		}
		}


		public void actionPerformed(ActionEvent e) {
			new Thread(new Runnable() {
				public void run(){
					//处理压缩图片过程
					lb_tips.setVisible(true);
					progressbar.setVisible(true);
					// 获得源文件
					file = new File(inputDir);
					
					if (!file.exists()) {
						//throw new Exception("文件不存在");测
					}
					//得到指定目录下的所有文件
					if(file.isDirectory()){
						List<File> fileList = FileUtil.getAllFiles(file);
						System.out.println("共有jpg文件："+ fileList.size() + "个");
						int value = progressbar.getValue();
						for(int i = 0; i < fileList.size(); i++){
							FileUtil.compressHandle(fileList.get(i), outputDir, outputWidth, outputHeight, proportion);
							value += 100/fileList.size();
							if(i == (fileList.size()-1)){
								progressbar.setValue(100);
								break;
							}
							progressbar.setValue(value);
						}
					}
					else{//选中的非文件目录，而是需要进行压缩的目标文件
						FileUtil.compressHandle(file, outputDir, outputWidth, outputHeight, proportion);
						progressbar.setValue(100);
						//timer.stop();
					}
				}
			}).start();
		}
}
