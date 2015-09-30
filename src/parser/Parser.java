package parser;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * ������.
 * @author Geurney
 *
 */
public class Parser {
	/**
	 * ������Ϣ��������
	 * @param html ��������html����
	 * @return ������Ϣ�����ʡ����֡�������ԭ����ת�ء����ĺ����ۡ�
	 */
	public static List<String> bloggerParser(String html) {
		List<String> result = new ArrayList<String>();
		int start;
		int end;
		// Get blog Info
		start = html.indexOf("<li>���ʣ�<span>") + "<li>���ʣ�<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>���֣�<span>") + "<li>���֣�<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>������<span>") + "<li>������<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		// Get blog statistics
		start = html.indexOf("<li>ԭ����<span>") + "<li>ԭ����<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>ת�أ�<span>") + "<li>ת�أ�<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>���ģ�<span>") + "<li>���ģ�<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));

		start = html.indexOf("<li>���ۣ�<span>") + "<li>���ۣ�<span>".length();
		end = html.indexOf("</span>", start);
		result.add(html.substring(start, end));
		return result;
	}
	
	/**
	 * ���·�����������
	 * @param html ��������html����
	 * @return ���·�����������
	 */
	public static List<SimpleEntry<String, String>> categoryParser(String html) {
		List<SimpleEntry<String, String>> list = new ArrayList<SimpleEntry<String, String>>();
		String prefix = "http://blog.csdn.net";
		Pattern pattern;
		Matcher matcher;
		int start = html.indexOf("���·���");
		while (true) {
			String category_url = null;
			pattern = Pattern.compile("<a href=\"(.*?)\" onclick");
			matcher = pattern.matcher(html);
			if (matcher.find(start)) {
				category_url = prefix + matcher.group(1);
			} else {
				break;
			}
			String category_name = null;
			pattern = Pattern
					.compile("wenzhangfenlei']\\); \">(.*?)</a><span>");
			matcher = pattern.matcher(html);
			if (matcher.find(start)) {
				category_name = matcher.group(1);
			}
			pattern = Pattern.compile("</a><span>(.*?)</span>");
			matcher = pattern.matcher(html);
			if (matcher.find(start)) {
				category_name = category_name + matcher.group(1);
			}
			Parser.fileNameValify(category_name);
			list.add(new SimpleEntry<String, String>(category_name,
					category_url));
			start = html.indexOf("</li>", start + 1);
		}
		return list;
	}
	
	/**
	 * ͼƬ��������
	 * @param html ��������html����
	 * @return ͼƬurl����
	 */
	public static List<String> imageParser(String html) {
		List<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("<img src=\"(.*?)\"");
		Matcher matcher = pattern.matcher(html);
		while(matcher.find()) {
			String url =matcher.group(1);
			if(url.length() != 0) {
				result.add(url);
			}
		}
		return result;
	}
	
	/**
	 * ����������������
	 * @param category �������ķ������
	 * @return �����������
	 */
	public static int numberOfLinksParser(String category) {
		int start = category.lastIndexOf("(") + 1;
		return Integer.valueOf(category.substring(start, category.length() - 1));
	}

	/*
	 * public static String titleParser(String html) { String title = null;
	 * Pattern pattern = Pattern.compile("<title>(.*?) -"); Matcher matcher =
	 * pattern.matcher(html); if (matcher.find()) { title = matcher.group(1); }
	 * title = Parser.fileNameValify(title); return title; }
	 */

	/**
	 * ������������
	 * @param html ��������html����
	 * @return ��������
	 */
	public static String docParser(String html) {
		StringBuilder sb = new StringBuilder(html);
		int start;
		int end;
		// Remove toolbar
		start = 0;
		end = sb.indexOf("<div id=\"container\">");
		sb.delete(start, end);
		// Remove navigator
		start = sb.indexOf("<div id=\"navigator\">");
		end = sb.indexOf("<script type=\"text/javascript\">");
		sb.delete(start, end);
		// Remove ad
		start = sb.indexOf("<div class=\"ad_class\">");
		end = sb.indexOf("<div id=\"article_details\"");
		sb.delete(start, end);
		// Remove rest
		start = sb.indexOf("<!-- Baidu Button BEGIN -->");
		end = sb.length();
		sb.delete(start, end);
		return sb.toString();
	}

	/**
	 * ͼƬ��ַ���ػ�����html�е�ͼƬ��ַ��Ϊ���ص�ͼƬ��ַ��
	 * @param html ��������html����
	 * @param title ���±���
	 * @return ͼƬ��ַ���ػ������������
	 */
	public static String imageLocalize(String html, String title) {
		StringBuilder sb = new StringBuilder(html);
		int start = 0;
		int end = 0;
		int imgName = 1;
		while (true) {
			start = sb.indexOf("<img src=\"http:", start);
			if (start == -1) {
				break;
			}
			start += "<img src=\"".length();
			end = sb.indexOf("\" ", start + 1);
			sb.replace(start, end, title + "\\" + (imgName++));
		}
		return sb.toString();
	}
	
	/**
	 * �������ӡ���html�е����Ӹ��¡�
	 * @param html ��������html����
	 * @return �������Ӻ����������
	 */
	public static String updateLinks(String html) {
		StringBuilder sb = new StringBuilder(html);
		int start = sb.indexOf("\"link_title\"><a href=\"")
				+ "\"link_title\"><a href=\"".length();
		sb.insert(start, "http://blog.csdn.net");
		start = sb.indexOf("link_categories\"> ���ࣺ <a href=\"")
				+ "link_categories\"> ���ࣺ <a href=\"".length();
		sb.insert(start, "http://blog.csdn.net");
		return sb.toString();
	}

	/**
	 * �ļ����Ϸ������滻�ļ����еķǷ��ַ�
	 * @param filename ��������ļ���
	 * @return �������ļ���
	 */
	public static String fileNameValify(String filename) {
		String SPECIAL_CHARS = "[\\\\/:*?\"<>|]+";
		Pattern pattern = Pattern.compile(SPECIAL_CHARS);
		Matcher matcher = pattern.matcher(filename);
		if (matcher.find()) {
			filename = matcher.replaceAll("-");
		}
		return filename;
	}

	/**
	 * Ϊ���๹������
	 * @param category ���·�������
	 * @param url ���·���Ŀ¼
	 * @return ���·�������
	 */
	public static String categoryToIndex(String category, String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li><a href=\"" + url + "\" ");
		sb.append("title=\"" + category + "\">" + category);
		sb.append("</a></li>\n");
		sb.append("<ul style=\"list-style-type:none\">\n");
		return sb.toString();
	}

	/**
	 * ��������ĩβ
	 * @return ����Ľ�β
	 */
	public static String categoryCloseToIndex() {
		return "</ul>\n";
	}
	
	/**
	 * Ϊ���¹�������
	 * @param title ���±��� 
	 * @param url ���µ�ַ
	 * @return ��������
	 */
	public static String blogToIndex(String title, String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("    <li><a href=\"" + url + "\" ");
		sb.append("title=\"" + title + "\">" + title);
		sb.append("</li</a>\n");
		return sb.toString();
	}

	/**
	 * Ϊ������Ϣ��������
	 * @param user �û���
	 * @param blogInfo ������Ϣ
	 * @return ������Ϣ����
	 */
	public static String blogInfoToIndex(String user, List<String> blogInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<title>" +user + "�� CSDN����</title>\n");
		sb.append("<p><a href=\"" + "http://blog.csdn.net/"+user
				+ "\"><font size=14px, color=orange>"  +user
				+ "</font></a></p>\n");
		sb.append("<p><font size=2px>");
		sb.append("���ʣ�" + blogInfo.get(0)
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("���֣�" + blogInfo.get(1)
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("������" + blogInfo.get(2) + "</font></p>\n");
		sb.append("<p><font size=2px>");
		sb.append("ԭ����" + blogInfo.get(3)
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("ת�أ�" + blogInfo.get(4)
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("���ģ�" + blogInfo.get(5)
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("����" + blogInfo.get(6) + "</font></p>\n");
		return sb.toString();
	}
}
