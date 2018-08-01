package com.mtm.party.mobile.ctrl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class FileUtil {
	
	//得到该文件夹，及其所有子文件夹下的所有目标文件
	public static List<File> getAllFiles(File file){
		List<File>valueFiles = new ArrayList<File>(); 
		File[] fs = file.listFiles();
		for(int i = 0; i < fs.length; i++){
			if(fs[i].isDirectory()){
				//递归得到每个子文件夹的目标文件
				valueFiles.addAll(getAllFiles(fs[i]));
			}
		}
		//把file文件夹下的目标文件放进去
		valueFiles.addAll(Arrays.asList(getFiles(file)));
		return valueFiles;
	}
	
	//得到一个文件夹下的目标文件，不包括子文件夹
	private static File[] getFiles(File file){
		FileFilter filter = new FileFilter(){
			//String regex = "\\w*\\.jpg";
			
			public boolean accept(File pathname){
				if(pathname.getName().toLowerCase().indexOf(".jpg")!=-1||
						pathname.getName().toLowerCase().indexOf(".jpeg")!=-1||
						pathname.getName().toLowerCase().indexOf(".png")!=-1||
						pathname.getName().toLowerCase().indexOf(".gif")!=-1
						){
					return true;
					}
				else
					return false;
				//return pathname.getName().matches(regex);
			}
		};
		
		File[] fs = file.listFiles(filter);
		return fs;
	}
	
	//处理压缩图片的函数
	public static void compressHandle(File input_file, String outputDir,int width, int height,
			boolean gp){
		try {
			Image img = ImageIO.read(input_file);
			if (img.getWidth(null) == -1) {
				System.out.println(" can't read,retry!" + "<BR>");
				//return "no";
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (gp == true) {
/*					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))
							/ (double) width + 0.5;
					double rate2 = ((double) img.getHeight(null))
							/ (double) height + 0.5;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);*/
					newWidth = img.getWidth(null);
					newHeight = img.getHeight(null);
				} else {
					newWidth = width; // 输出的图片宽度
					newHeight = height; // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				
				
				File f=new File(outputDir);
				if (!f.exists()){
					f.mkdirs(); 
				}
				FileOutputStream out = new FileOutputStream(outputDir
						+File.separator + input_file.getName());
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
