package type;

import java.util.ArrayList;
import java.util.List;

/**
 * ������
 * @author Geurney
 *
 */
public class Blog {
	/**
	 * ���±���
	 */
	private String title;
	
	/**
	 * ���·���
	 */
	private List<String> category;
	
	/**
	 * ����url��ַ
	 */
	private String url;
	
	/**
	 * ���±���·��
	 */
	private String path;
	
	/**
	 * Blog ���캯��
	 */
	public Blog() {
		category = new ArrayList<String>();
	}

	/**
	 * ��ȡ���±���
	 * @return ���±���
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * �������±���
	 * @param title ����
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * ��ȡ���·���
	 * @return ���·���
	 */
	public List<String> getCategory() {
		return category;
	}

	/**
	 * �������·���
	 * @param category ����
	 */
	public void setCategory(List<String> category) {
		this.category = category;
	}

	/**
	 * ��ȡ����url
	 * @return ����url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * ��������url
	 * @param url ����url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * ��ȡ���±���·��
	 * @return ���±���·��
	 */
	public String getPath() {
		return path;
	}

	/**
	 * �����ļ�����·�� 
	 * @param path �ļ�����·�� 
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
}
