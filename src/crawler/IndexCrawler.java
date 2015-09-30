package crawler;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import parser.Parser;
import util.Util;

/**
 * ��ҳ����. ��ȡ�û���ҳ��Ϣ��������Ϣ���û�ͷ������·��ࡣ
 * 
 * @author Geurney
 *
 */
public class IndexCrawler extends Crawler {
	/**
	 * ���ص�ַ
	 */
	private String root;
	
	/**
	 * ������Ϣ������Ϊ�����ʡ����֡�������ԭ����ת�ء����ĺ����ۡ�
	 */
	private List<String> blogInfo;
	
	/**
	 * ���·��ࡣ��ֵ�ԣ�������url��ַ 
	 */
	private List<SimpleEntry<String, String>> categories;
	
	/**
	 * ������ҳ����
	 * @param root �û�ͷ�񱣴��ַ
	 */
	public IndexCrawler(String root) {
		this.root = root;
		blogInfo = new ArrayList<String>();
	}

	@Override
	public boolean crawl(SimpleEntry<String, String> link) {
		if (connect(link.getValue())) {
			String html = document.html();
			blogInfo = Parser.bloggerParser(html);
			categories = Parser.categoryParser(html);
			for (SimpleEntry<String, String> category : categories) {
				new File(root + "\\blog\\" + category.getKey()).mkdirs();
			}
			Element imageUrl = document.select(
					"img[src^=http://avatar.csdn.net/]").first();
			String profileUrl = imageUrl.absUrl("src");
			Util.downloadImage(profileUrl, root, "user.jpg");
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ������Ϣ
	 * @return ������Ϣ
	 */
	public List<String> blogInfo() {
		return blogInfo;
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @return ������Ϣ
	 */
	public List<SimpleEntry<String, String>> categories() {
		return categories;
	}
}
