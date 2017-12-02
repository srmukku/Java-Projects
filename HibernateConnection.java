package com.payroll;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnection {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	 
	private static SessionFactory buildSessionFactory() {
		try {
			// load from different directory
			SessionFactory sessionFactory = new Configuration().configure(
					"/hibernate-payroll.cfg.xml")
					.buildSessionFactory();
			System.out.println("Read the config file");
			return sessionFactory;
 
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
 
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
 
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
	
	public static void main(String a[]) {
		System.out.println("Read the config file"+sessionFactory);
		//UserInfo object = (UserInfo)sessionFactory.openSession().createQuery("SELECT u FROM UserInfo u ORDER BY u.userId DESC").setMaxResults(1).uniqueResult();
		//System.out.println("object:"+object.getUserId());
	}
	
	public static void closeSession(Session session){
		if(session != null){
			try{
				Connection con = session.close();
				if(!(con == null || con.isClosed()))
					con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
