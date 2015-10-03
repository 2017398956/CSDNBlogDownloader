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
	 * ���±���������
	 * @param html �������ķ������
	 * @param valifyName �Ƿ�Ϸ���������
	 * @return ���±���
	 */
	 public static String blogTitleParser(String html, boolean valifyName) {
		 String title = null;
		 Pattern pattern = Pattern.compile("<title>(.*?) -"); 
		 Matcher matcher = pattern.matcher(html); 
		 if (matcher.find()) { 
			 title = matcher.group(1); 
			 if (valifyName) {
				 title = Parser.fileNameValify(title);
			 }
		 }
		 return title; 
	 }
	 
	/**
	 * ���·���������
	 * @param html ��������html����
	 * @param valifyName �Ƿ�Ϸ���������
	 * @return ���·���
	 */
	public static List<String> blogCategoriesParser(String html, boolean valifyName) {
		List<String> categories = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\'blog_articles_fenlei\']);\">(.*?)</a>"); 
		Matcher matcher = pattern.matcher(html); 
		while(matcher.find()) {
			String category = matcher.group(1);
			if (valifyName) {
				category = Parser.fileNameValify(category);
			}
			categories.add(category);
		}
		 return categories;
	}
	
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
	public static List<SimpleEntry<String, String>> categoriresParser(String html) {
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
			start = sb.indexOf("<img src=\"http", start);
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
	 * Ϊ������Ϣ��������
	 * @param user �û���
	 * @param imgUrl ͷ���ַ
	 * @param blogInfo ������Ϣ
	 * @return ������Ϣ����
	 */
	public static String blogInfoToIndex(String user, String imgUrl, List<String> blogInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<title>"  +user + "�� CSDN����</title>\n");		
		sb.append("<p><a href=\"" + "http://blog.csdn.net/"+ user
				+ "\"><font size=14px, color=orange>"  + user
				+ "</font></a></p>\n");
		sb.append("<table><tr>\n");
		sb.append("<td><img src=\"" + imgUrl + "\"" + "alt=\"ͷ��\" align=\"left\"></td>\n");
		sb.append("<td><table>\n");
		sb.append("  <tr><td><table>\n");
		sb.append("    <tr><td><font size=2px>" + "���ʣ�" + blogInfo.get(0) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "���֣�" + blogInfo.get(1) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "������" + blogInfo.get(2) + "</font></td></tr>\n");
		sb.append("  </table></td></tr>\n");
		sb.append("  <tr><td><table>\n");
		sb.append("    <tr><td><font size=2px>" + "ԭ����" + blogInfo.get(3) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "ת�أ�" + blogInfo.get(4) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "���ģ�" + blogInfo.get(5) + "</font></td></tr>\n");
		sb.append("    <tr><td><font size=2px>" + "���룺" + blogInfo.get(6) + "</font></td></tr>\n");
		sb.append("  </table></td></tr>\n");
		sb.append("</table></td>\n");
		sb.append("</tr></table>\n");
		return sb.toString();
	}

	/**
	 * Ϊ���๹������
	 * @param category ���·�������
	 * @param url ���·���Ŀ¼
	 * @return ���·�������
	 */
	public static String categoryToIndex(String category, String url) {
		StringBuilder sb = new StringBuilder();
		sb.append("<li><a href=\"" +  "file:\\\\\\" + url + "\" ");
		sb.append("title=\"" + category + "\">" + category);
		sb.append("</a></li>\n");
		sb.append("<ul style=\"list-style-type:none\">\n");
		return sb.toString();
	}

	/**
	 * ��������ͷ
	 * @param url ���ͷ������ص�ַ
	 * @return ������ײ�
	 */
	public static String categoryOpenToIndex(String url) {
		return "<p><a href=\"" + "file:\\\\\\" + url + "\">����Ŀ¼:</a></p>\n";
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
		sb.append("    <li><a href=\"" + "file:\\\\\\" + url + "\" ");
		sb.append("title=\"" + title + "\">" + title);
		sb.append("</li</a>\n");
		return sb.toString();
	}

}
