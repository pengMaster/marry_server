package com.mtm.party.mobile.ctrl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.ThumbnailParameter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import com.mtm.party.mobile.model.ImageList;
import com.mtm.party.mobile.util.ImageUtils;
import com.mtm.party.util.StringUtils;

public class Test {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {

		final String filePath = "D:\\workTime\\MEP_Project\\party\\party\\WebRoot\\wechat\\dqdy_zip\\";
		final String fileZipPath = "D:\\PlayTime\\婚纱照片\\Test_zip_one\\";

		File file = new File(filePath);
		File[] tempList = file.listFiles();
		if (null == tempList) {
			return;
		}
		
		final Rename rename = new Rename() {
			@Override
			public String apply(String name, ThumbnailParameter param) {
				System.out.println(name);
				//"..//zip//"+name;
				return  name;
			}
		};
		
		new Thread(new Runnable() {
			
			public void run() {
				try {
					Thumbnails.of(new File(filePath).listFiles())
					.scale(0.4)
					.outputFormat("jpg")
					.toFiles(rename);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();


	}

}
