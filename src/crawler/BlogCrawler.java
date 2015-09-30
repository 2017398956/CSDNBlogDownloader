package crawler;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import parser.Parser;
import util.Util;
/**
 * �������档��ȡ�������ݡ�
 * @author Geurney
 *
 */
public class BlogCrawler extends Crawler {
	/**
	 * ���±���Ŀ¼
	 */
	private String root;
	
	/**
	 * ���±���
	 */
	private String title;
	
	/**
	 * ������������
	 * @param root ���±���Ŀ¼
	 */
	public BlogCrawler(String root) {
		this.root = root;
	}

	@Override
	public boolean crawl(SimpleEntry<String, String> link) {
		if (connect(link.getValue())) {
			String html = document.html();
			title = Parser.fileNameValify(link.getKey());
			html = Parser.docParser(html);
			String blogPath = root + "\\" + title + ".html";
			List<String> images = Parser.imageParser(html);
			String imagePath = root + "\\" + title;
			for (int i = 0; i < images.size(); i++) {
				Util.downloadImage(images.get(i), imagePath, (i+1) + "");
			}
			html = Parser.imageLocalize(html, title);
			html = Parser.updateLinks(html);
			return Util.writeToFile(blogPath, html);
		}
		return false;
	}

	/**
	 * ��ȡ���±���·��
	 * @return ���±���·��
	 */
	public String blogPath() {
		return root + "\\" + title + ".html";
	}
	
	/**
	 * ��ȡ���±���
	 * @return ���±���
	 */
	public String title() {
		return title;
	}

}
