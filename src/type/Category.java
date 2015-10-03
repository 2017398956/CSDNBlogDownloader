package type;

import java.util.ArrayList;
import java.util.List;

/**
 * ������
 * @author Geurney
 *
 */
public class Category {
	/**
	 * ��������
	 */
	private String title;

	/**
	 * ����url
	 */
	private String url;
	
	/**
	 * ���ౣ��·��
	 */
	private String path;
	
	/**
	 * �����µ����� 
	 */
	private List<Blog> blogs;
	
	/**
	 * Category���캯��
	 */
	public Category() {
		blogs = new ArrayList<Blog>();
	}
	
	/**
	 * ��ȡ����ı���
	 * @return �������
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * ���÷���ı���
	 * @param title �������
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * ��ȡ�����url
	 * @return �����url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * ���÷����url
	 * @param url �����url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * ��ȡ���ౣ��·��
	 * @return ���ౣ��·��
	 */
	public String getPath() {
		return path;
	}

	/**
	 * ���÷��ౣ��·��
	 * @param path ���ౣ��·��
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * ��ȡ���������
	 * @return ��������
	 */
	public List<Blog> getBlogs() {
		return blogs;
	}
}
