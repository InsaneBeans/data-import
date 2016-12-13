package com.bonc.test.file;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestUrl {
	
	public static void main(String[] args) throws Exception {
		String urlString = "https://maven.bonc.com.cn/releases/ti/ti-data/1.0.2/ti-data-1.0.2.jar";
		TestUrl.testUrl(urlString);
	}

	public static void testUrl(String filePath) throws Exception {
		String read;
		URL url = new URL(filePath);
		HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
		urlConn.setConnectTimeout(5000);
		urlConn.setReadTimeout(5000);
//		InputStream iStream =  urlConn.getInputStream();
		BufferedReader br =new BufferedReader(new InputStreamReader( urlConn.getInputStream()));
		while( (read = br.readLine())!=null){
			System.out.println(read);
		}
	}

}
