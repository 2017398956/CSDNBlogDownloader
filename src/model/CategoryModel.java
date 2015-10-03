package model;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import type.Blog;
import type.Category;
import crawler.BlogCrawler;
import crawler.CategoryCrawler;

public class CategoryModel extends Model {
	/**
	 * ���±���Ŀ¼
	 */
	private String root;

	/**
	 * ��������
	 */
	private List<String> urls;

	/**
	 * ���ص�Categories
	 */
	private List<Category> categories;
	
	/**
	 * ���d������
	 */
	private int numberOfBlogs;

	/**
	 * BlogModel���췽��
	 */
	public CategoryModel() {
		urls = new ArrayList<String>();
		categories = new ArrayList<Category>();
	}

	/**
	 * �������±���Ŀ¼
	 * 
	 * @param root
	 *            ���±���Ŀ¼
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * ������������
	 * 
	 * @param urls
	 *            ��������
	 */
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	/**
	 * ��ȡ�����е���������
	 * 
	 * @param input
	 *            ��������
	 */
	public void addCategories(String input) {
		urls.addAll(Arrays.asList(input.trim().split("\n")));
	}

	/**
	 * ��ȡ����͸����������
	 */
	public void crawl() {
		CategoryCrawler categoryCrawler = new CategoryCrawler();
		BlogCrawler blogCrawler = new BlogCrawler();
		for (String url : urls) {
			Category category = categoryCrawler.crawl(url, root);
			if (category != null) {
				categories.add(category);
				List<Blog> blogs = category.getBlogs();
				for (int i = 0; i < blogs.size(); i++) {
					Blog blog = blogs.remove(i);
					blog = blogCrawler.crawl(blog.getUrl(), category.getPath());
					if (blog != null) {
						List<String> list = new ArrayList<String>();
						list.add(category.getTitle());
						blog.setCategory(list);
						blogs.add(i, blog);
					}
				}
			}
		}
	}

	@Override
	protected Void doInBackground() throws Exception {
		long start;
		long end;
		status = true;
		double progress = 0;
		double categoryStep = 100.0 / urls.size();
		double blogStep;
		startTime = System.currentTimeMillis();
		
		CategoryCrawler categoryCrawler = new CategoryCrawler();
		BlogCrawler blogCrawler = new BlogCrawler();
		for (String url : urls) {
			start = System.currentTimeMillis();
			Category category = categoryCrawler.crawl(url, root);
			if(isCancelled()) {
				return null;
			}
			if (category == null) {
				firePropertyChange(STATE_SYSTEM, null, "�������ʧ��: " + url + "\n");
				continue;
			}
			categories.add(category);
			List<Blog> blogs = category.getBlogs();
			if (blogs.size() == 0) {
				continue;
			}
			blogStep = categoryStep / blogs.size();
			firePropertyChange(STATE_SYSTEM, null,
					"���ط��ࣺ" + category.getTitle() + "("
							+ category.getBlogs().size() + ") ");
			for (int i = 0; i < blogs.size(); i++) {
				Blog blog = blogs.remove(i);
				blog = blogCrawler.crawl(blog.getUrl(), category.getPath());
				if (isCancelled()) {
					return null;
				}
				firePropertyChange(STATE_PROGRESS, null, (int) (progress += blogStep));
				if (blog == null) {
					i--;
					firePropertyChange(STATE_SYSTEM, null, "��");
					continue;
				}
				List<String> list = new ArrayList<String>();
				list.add(category.getTitle());
				blog.setCategory(list);
				blogs.add(i, blog);
				firePropertyChange(STATE_SYSTEM, null, ">");
			}
			numberOfBlogs += blogs.size();
			end = System.currentTimeMillis();
			firePropertyChange(STATE_SYSTEM, null, " ��� ��    " + (end - start) + "����\n");
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
		if (!isCancelled()) {
			Toolkit.getDefaultToolkit().beep();
			firePropertyChange(STATE_PROGRESS, null, 100);
			firePropertyChange(CATEGORY_STATE_DONE, null,
					"�ɹ�����" + categories.size() + "��" + numberOfBlogs + "ƪ���£� �ܺ�ʱ" + (endTime - startTime) / 1000 +"�룡\n");
		} else {
			firePropertyChange(CATEGORY_STATE_DONE, null, "\n��ֹ��\n");
		}
		status = false;
	}

}
