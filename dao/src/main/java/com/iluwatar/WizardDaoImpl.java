package com.iluwatar;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

public class WizardDaoImpl extends DaoBaseImpl<Wizard> implements WizardDao {

	@Override
	public Wizard findByName(String name) {
		Session session = getSession();
		Transaction tx = null;
		Wizard result = null;
		try {
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(Expression.eq("name", name));
			result = (Wizard) criteria.uniqueResult();
			result.getSpellbooks().size();
			tx.commit();
		}
		catch (Exception e) {
			if (tx!=null) tx.rollback();
			throw e;
		}
		finally {
			session.close();
		}		
		return result;
	}
}
