package com.mtm.party.mobile.ctrl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mtm.party.mobile.util.ImageUtils;

public class Test {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String number = "123.456";
		String intNumber = number.substring(0,number.indexOf("."));
		System.out.println(intNumber); 
	}

}
