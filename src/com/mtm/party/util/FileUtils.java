package com.mtm.party.util;

import java.io.*;

public class FileUtils {

	
	

	 //复制方法
   public static void copy(String src, String des) throws Exception {
       //初始化文件复制
       File file1=new File(src);
       //把文件里面内容放进数组
       File[] fs=file1.listFiles();
       //初始化文件粘贴
       File file2=new File(des);
       //判断是否有这个文件有不管没有创建
       if(!file2.exists()){
           file2.mkdirs();
       }
       //遍历文件及文件夹
       for (File f : fs) {
           if(f.isFile()){
               //文件
               fileCopy(f.getPath(),des+"/"+f.getName()); //调用文件拷贝的方法
           }else if(f.isDirectory()){
               //文件夹
               copy(f.getPath(),des+"/"+f.getName());//继续调用复制方法      递归的地方,自己调用自己的方法,就可以复制文件夹的文件夹了
           }
       }
       
   }

   /**
    * 文件复制的具体方法
    */
   private static void fileCopy(String src, String des) throws Exception {
       //io流固定格式
       BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
       BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
       int i = -1;//记录获取长度
       byte[] bt = new byte[2014];//缓冲区
       while ((i = bis.read(bt))!=-1) {
           bos.write(bt, 0, i);
       }
       bis.close();
       bos.close();
       //关闭流
   }



    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }
}
