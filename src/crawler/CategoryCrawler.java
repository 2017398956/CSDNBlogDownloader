package crawler;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * �������档��ȡһ�������µ������������ӡ� 
 * @author Geurney
 *
 */
public class CategoryCrawler extends Crawler {
	/**
	 * �������ӡ���ֵ�ԣ������url��ַ��
	 */
	private List<SimpleEntry<String, String>> links;
	
	/**
	 * �����������
	 */
	public CategoryCrawler() {
		links = new ArrayList<SimpleEntry<String, String>>();
	}

	@Override
	public boolean crawl(SimpleEntry<String, String> link) {
		int index = 1;
		String url = link.getValue();
		while(connect(url + "/" + index)) {
			// Elements linksOnPage = html.select("a[href][title=\"�Ķ�����\"]");
			Elements pagelinks = document.select(".link_title a[href]");
			for (Element pagelink : pagelinks) {
				links.add(new SimpleEntry<String, String>(pagelink.text(),
						pagelink.absUrl("href")));
			}
			if (document.text().contains("��һҳ")) {
				index++;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��ȡ��������
	 * @return ��������
	 */
	public List<SimpleEntry<String, String>> links() {
		return links;
	}

}
