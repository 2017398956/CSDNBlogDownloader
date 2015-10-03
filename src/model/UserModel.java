package model;

import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import parser.Parser;
import type.Blog;
import type.Category;
import type.User;
import crawler.BlogCrawler;
import crawler.CategoryCrawler;
import crawler.IndexCrawler;

public class UserModel extends Model {
	/**
	 * �û�
	 */
	private User user;
	
	/**
	 * ״̬
	 */
	private boolean status;

	/**
	 * �����ص�������
	 */
	private int numberOfBlogs;
	
	/**
	 * BlogModel���췽��
	 */
	public UserModel() {
		user = new User();
	}
	
	/**
	 * ��ȡ�û���
	 * @return �û���
	 */
	public String getUser() {
		return user.getName();
	}

	/**
	 * �����û���
	 * @param user �û���
	 */
	public void setUser(String user) {
		this.user.setName(user);
		this.user.setUrl(host + user);
	}
	
	/**
	 * �����û�Ŀ¼
	 * @param root the root to set
	 */
	public void setRoot(String root) {
		user.setPath(root);
	}
	
	/**
	 * �Ƿ���
	 * @return ����״̬
	 */
	public boolean isBusy() {
		return status;
	}
	
	/**
	 * ��ȡ�û�
	 */
	public void crawl() {
		IndexCrawler indexCrawler = new IndexCrawler();
		user = indexCrawler.crawl(user.getUrl(), user.getPath());
		if (user == null) {
			return;
		}
		List<Category> categories = user.getCategoreis();
		for (int i = 0; i < categories.size(); i++) {
			Category category = categories.remove(i);
			
			CategoryCrawler categoryCrawler = new CategoryCrawler(); 
			category = categoryCrawler.crawl(category.getUrl(), category.getPath());
			if (category == null) {
				i--;
				continue;
			}
			categories.add(i, category);

			BlogCrawler blogCrawler = new BlogCrawler();
			List<Blog> blogs = category.getBlogs();
			for (int j = 0; j < blogs.size(); j++) {
				Blog blog = blogs.remove(j);
				blog = blogCrawler.crawl(blog.getUrl(),blog.getPath());
				if (blog == null) {
					j--;
					continue;
				}
				List<String> list = new ArrayList<String>();
				list.add(category.getTitle());
				blog.setCategory(list);
				blogs.add(j, blog);
			}
			createIndex();
		}
	}
	
	/**
	 * ��������
	 * @return True �����ɹ���False ����ʧ��
	 */
	public boolean createIndex() {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(user.getPath() + "\\" + "index.html"));
			writer.write(Parser.blogInfoToIndex(user.getName(), user.getProfileImage(), user.getBlogInfo()));
			writer.write(Parser.categoryOpenToIndex(user.getBlogFolder()));
			for (Category category : user.getCategoreis()) {
				writer.write("\n" + Parser.categoryToIndex(category.getTitle(), category.getPath()));
				for (Blog blog: category.getBlogs()) {
					writer.write(Parser.blogToIndex(blog.getTitle(), blog.getPath()));
				}
				writer.write(Parser.categoryCloseToIndex());
			}
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	protected Void doInBackground() {
		long start;
		long end;
		status = true;
		double progress = 5;
		double categoryStep;
		double blogStep;
		startTime = System.currentTimeMillis();
		
		start = startTime;
		IndexCrawler indexCrawler = new IndexCrawler();
		firePropertyChange(STATE_SYSTEM, null, "��ȡ�û�������Ϣ...");
		user = indexCrawler.crawl(user.getUrl(), user.getPath());
		if (user == null) {
			firePropertyChange(STATE_SYSTEM, null, "�����û�" + user.getName()+"ʧ�ܣ�\n");
			return null;
		}
		end = System.currentTimeMillis();
		if(isCancelled()) {
			return null;
		}
		firePropertyChange(STATE_BLOGINFO, null, user.getBlogInfo());
		firePropertyChange(STATE_PROFILE, null, user.getProfileImage());
		firePropertyChange(STATE_SYSTEM, null, "��� ��    " + (end - start) + "����\n");
		firePropertyChange(STATE_PROGRESS, null, (int) progress);
		
		CategoryCrawler categoryCrawler = new CategoryCrawler(); 
		BlogCrawler blogCrawler = new BlogCrawler();
		List<Category> categories = user.getCategoreis();
		categoryStep = 90 / categories.size();
		for (int i = 0; i < categories.size(); i++) {
			start = System.currentTimeMillis();
			Category category = categories.remove(i);
			category = categoryCrawler.crawl(category.getUrl(), user.getBlogFolder());
			if(isCancelled()) {
				return null;
			}
			if (category == null) {
				i--;
				firePropertyChange(STATE_SYSTEM, null, i + "�������ʧ�ܣ�\n");
				continue;
			}
			categories.add(i, category);
			List<Blog> blogs = category.getBlogs();
			if (blogs.size() == 0) {
				continue;
			}
			blogStep = categoryStep / blogs.size();
			firePropertyChange(STATE_SYSTEM, null,
					"���ط��ࣺ" + category.getTitle() + "("
							+ category.getBlogs().size() + ") ");
			for (int j = 0; j < blogs.size(); j++) {
				Blog blog = blogs.remove(j);
				blog = blogCrawler.crawl(blog.getUrl(), category.getPath());
				if(isCancelled()) {
					return null;
				}
				firePropertyChange(STATE_PROGRESS, null, (int) (progress += blogStep));
				if (blog == null) {
					j--;
					firePropertyChange(STATE_SYSTEM, null, "��");
					continue;
				}
				List<String> list = new ArrayList<String>();
				list.add(category.getTitle());
				blog.setCategory(list);
				blogs.add(j, blog);
				firePropertyChange(STATE_SYSTEM, null, ">");
			}
			numberOfBlogs += blogs.size();
			end = System.currentTimeMillis();
			firePropertyChange(STATE_SYSTEM, null, " ��� ��    " + (end - start) + "����\n");
		}
		firePropertyChange(STATE_PROGRESS, null, 90);
		firePropertyChange(STATE_SYSTEM, null, "��������...");
		start = System.currentTimeMillis();
		if (createIndex()) {
			end = System.currentTimeMillis();
			firePropertyChange(STATE_SYSTEM, null, "��� ��   " + (end - start) + "����\n");
		} else {
			firePropertyChange(STATE_SYSTEM, null, "ʧ�ܣ�\n");	
		}
		endTime = System.currentTimeMillis();
		List<String> logs = categoryCrawler.getErrorLog();
		if (logs.size() != 0) {
			for (String error : logs) {
				firePropertyChange(STATE_SYSTEM, null, error + "\n");
			}
		}
		List<String> bloglogs = blogCrawler.getErrorLog();
		if (bloglogs.size() != 0) {
			for (String error : bloglogs) {
				firePropertyChange(STATE_SYSTEM, null, error + "\n");
			}
		}
		return null;
	}

	 @Override
	 public void done() {
		 if (!isCancelled() && user != null) {
			 Toolkit.getDefaultToolkit().beep();
			 firePropertyChange(STATE_PROGRESS, null, 100);
			 firePropertyChange(USER_STATE_DONE, null, "�ɹ�����" + user.getCategoreis().size() + "��" + numberOfBlogs + "ƪ���£� ��ʱ" + (endTime - startTime) / 1000 +"��!\n");
		 } else {
			 firePropertyChange(USER_STATE_DONE, null, "\n��ֹ��\n");
		 }
		 status = false;
	 }

}
