package model;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import type.Blog;
import crawler.BlogCrawler;
/**
 * Blogģʽ����������������
 * @author Geurney
 *
 */
public class UrlModel extends Model {

	/**
	 * ���±���Ŀ¼
	 */
	private String root;

	/**
	 * �������� 
	 */
	private List<String> urls;
	
	/**
	 * ���ص�Blogs
	 */
	private List<Blog> blogs;
	
	/**
	 * BlogModel���췽��
	 */
	public UrlModel() {
		urls =  new ArrayList<String>();
		blogs = new ArrayList<Blog>();
	}

	/**
	 * �������±���Ŀ¼
	 * @param root ���±���Ŀ¼
	 */
	public void setRoot(String root) {
		this.root = root;
	}
	
	/**
	 * ��ȡ�����е���������
	 * @param input ��������
	 */
	public void addUrls(String input) {
		urls.addAll(Arrays.asList(input.trim().split("\n")));
	}
	
	/**
	 * ��ȡ����
	 */
	public void crawl() {
		BlogCrawler crawler = new BlogCrawler(); 
		for (String url : urls) {
			Blog blog = crawler.crawl(url, root);
			if(blog != null) {
				blogs.add(blog);
			}
		}
	}
	
	@Override
	protected Void doInBackground() {
		long start;
		long end;
		status = true;
		double progress = 0;
		double progressStep = 100.0 /  urls.size();
		startTime = System.currentTimeMillis();
		
		BlogCrawler crawler = new BlogCrawler(); 
		for (String url : urls) {
			start = System.currentTimeMillis();
			Blog blog = crawler.crawl(url, root);
			end = System.currentTimeMillis();
			if(isCancelled()) {
				return null;
			}
			firePropertyChange(STATE_PROGRESS, null, (int) (progress += progressStep));
			if(blog == null) {
				continue;
			} 
			blogs.add(blog);
			firePropertyChange(STATE_SYSTEM, null, "�������£�\"" + blog.getTitle() + "\"    " + (end - start) + "����\n");
		}
		endTime = System.currentTimeMillis();
		
		List<String> logs = crawler.getErrorLog();
		if(logs.size() != 0) {
			for (String error : logs) {
				firePropertyChange(STATE_SYSTEM, null, error + "\n");
			}
		}
		return null;
	}

	 @Override
	 public void done() {
		 if (!isCancelled()) {
			 Toolkit.getDefaultToolkit().beep();
			 firePropertyChange(STATE_PROGRESS, null, 100);
			 firePropertyChange(URL_STATE_DONE, null, "�ɹ�����" + blogs.size() + "ƪ���£��ܺ�ʱ" + (endTime - startTime) / 1000 +"�룡\n");
		 } else {
			 firePropertyChange(URL_STATE_DONE, null, "��ֹ��\n");
		 }
		 status = false;
	 }
	 
}
