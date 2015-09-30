package util;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * �����ࡣ
 * @author Geurney
 *
 */
public abstract class Util {
	/**
	 * ����ͼƬ
	 * @param imageUrl ͼƬurl��ַ
	 * @param folderName ͼƬ����Ŀ¼
	 * @param fileName ͼƬ�ļ���
	 * @return True ͼƬ���سɹ���False ͼƬ����ʧ��
	 */
	public static boolean downloadImage(String imageUrl, String folderName,
			String fileName) {
		URL url;
		try {
			url = new URL(imageUrl);
		} catch (MalformedURLException e) {
			System.out.println("�����޷�ʶ��ͼƬ��ַ��  " + folderName + "  " + imageUrl);
			return false;
		}

		File folder = new File(folderName);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		OutputStream out;
		try {
			out = new BufferedOutputStream(new FileOutputStream(folderName
					+ "\\" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("�����޷�����ͼƬ�ļ���"  + folderName + "  " + imageUrl);
			return false;
		}

		InputStream in;
		int buffer;
		try {
			in = url.openStream();
			while((buffer = in.read()) != -1) {
				out.write(buffer);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println("��������ʧ�ܣ�"  + folderName + "  " + imageUrl);
			return false;
		}
		return true;
	}
	
	/**
	 * ����ҳ�����
	 * @param url ��ҳ��ַ
	 * @return True �򿪳ɹ���False ��ʧ�ܡ�
	 */
	public static boolean openBrowser(String url) {
		try {
	    	Desktop.getDesktop().browse(new URL(url).toURI());
	    	return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * ���ļ������
	 * @param path �ļ�·��
	 * @return True �򿪳ɹ���False ��ʧ�ܡ�
	 */
	public static boolean openExplorer(String path) {
		try {
			Runtime.getRuntime().exec("explorer.exe /select," + path);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * ��ȡ�ļ�
	 * @param filePath �ļ�·��
	 * @return �ļ�����
	 * @throws IOException ��ȡ�ļ�ʧ��
	 */
	public static String readFile(String filePath) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		while((line = reader.readLine()) != null) {
			sb.append(line);
		}
		int buffer;
		while((buffer = reader.read()) != -1) {
				sb.append(buffer);
		}
		reader.close();
		return sb.toString();
	}
	
	/**
	 * д���ļ�
	 * @param filePath ��д���ļ�·��
	 * @param content ��д������
	 * @return True д��ɹ���False д��ʧ��
	 */
	public static boolean writeToFile(String filePath, String content) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(content);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("�޷�д���ļ���" + filePath);
			return false;
		}
	}

}
