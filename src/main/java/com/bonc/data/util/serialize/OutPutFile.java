package com.bonc.data.util.serialize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件输出实现,随机文件名生成，检测文件夹是否存在，不存在则采用递归实现文件夹的创建
 * 
 * @author huh
 */
@Component
@ConfigurationProperties
public class OutPutFile {
    
    /**
     * 检查输出文件夹是否存在，不存在则创建
     * 
     * @param fileContent  写入文件的内容
     * @param fullFilePath 完整文件路径，包括文件目录
     */
    protected void outputFolderCheck(String fileContent,String fullFilePath){
       String filePath = new File(fullFilePath).getParent(); 
       File folder = new File(filePath);
       createFolder(folder);
    }
    
    /**
     * 递归创建文件夹
     * 
     * @param folder 需创建的文件夹名称
     */
    private void createFolder(File folder){
        if(!folder.exists()){
            createFolder(folder.getParentFile());
            folder.mkdir();
        }
    }
    
    /**
     * 根据路径输出对应文件(JSON、XML),输出文件字符编码设置为UTF-8
     * 
     * @param fileContent  输出文件的内容
     * @param fullFileName 完整文件名，包含文件路径 
     * @throws IOException 
     */
    public void outputFile(String fileContent, String fullFileName){
        try{
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fullFileName), Charset.forName("UTF-8"));
            BufferedWriter bfw = new BufferedWriter(osw);
            try{ 
                bfw.write(fileContent);
                bfw.flush();
            } catch (Exception e){
                e.printStackTrace();
                return;
            } finally {
                bfw.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * 随机生成文件名,使用UUID生成随机字符串
     */
    public String generateFileName(){
        return UUID.randomUUID().toString();
    }
}