package crawler;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/**
 * ��������ࡣ
 * @author Geurney
 *
 */
public abstract class Crawler {
	/**
	 * �����header 
	 */
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
	/**
	 * ��ȡ��ҳ��
	 */
	protected Document document;
	
	/**
	 * ��ȡurl����
	 * @param link ���ӱ�������ӵ�ַ
	 * @return True ��ȡ�ɹ���False ��ȡʧ��
	 */
	public abstract boolean crawl(SimpleEntry<String, String> link);

	/**
	 * �������ӡ���ೢ��20�Σ����Լ��100ms��
	 * @param url url��ַ
	 * @return True ���ӳɹ���False ����ʧ��
	 */
	protected boolean connect(String url) {
		int trytime = 1;
		while (true) {
			try {
				Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
				document = connection.get();
				if (connection.response().statusCode() == 200 && connection.response().contentType().contains("text/html")) {
					break;
				}
			} catch (IOException e1) {}

			if (trytime++ == 20) {
				return false;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		return true;
	}
}
