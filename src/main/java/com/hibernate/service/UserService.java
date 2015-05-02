package com.hibernate.service;

import com.hibernate.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final String EMAIL = "email";
    private static final String ID = "id";

    @Autowired
    private SessionFactory sessionFactory;

    public int saveWithOutTransaction(final User user) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            return (int) session.save(user);
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int saveWithOutTransactionButAfterSaveExecuteSessionFlush(final User user) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            int id = (int) session.save(user);
            session.flush();
            return id;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int saveWithTransaction(final User user) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            int id = (int) session.save(user);
            transaction.commit();
            return id;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void persistWithTransaction(final User user) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void persistWithTransactionAndAfterPersistAddNewEmail(final User user, final String emailAfterSave) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            session.persist(user);

            user.setEmail(emailAfterSave);

            transaction.commit();
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void saveOrUpdateWithTransaction(final User user) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void saveOrUpdateWithTransactionAndAfterPersistAddNewEmail(final User user, final String emailAfterSave) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(user);

            user.setEmail(emailAfterSave);

            transaction.commit();
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateWithTransaction(final User user) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateWithTransactionAfterUpdateEmail(final User user, final String afterUpdateEmail) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            session.update(user);
            user.setEmail(afterUpdateEmail);

            transaction.commit();
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int mergeWithTransactionAfterMargeEmailOldValueAndNewValue(final User user, final String margeAfterEmailOldValue, final String margeAfterEmailNewValue) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();

            final User newUser = (User) session.merge(user);

            user.setEmail(margeAfterEmailOldValue);
            newUser.setEmail(margeAfterEmailNewValue);

            transaction.commit();
            return newUser.getId();
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int mergeWithTransactionAfterMargeEmailChangeOrderNewValueAndOldValue(final User user, final String margeAfterEmailOldValue, final String margeAfterEmailNewValue) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();

            final User newUser = (User) session.merge(user);

            newUser.setEmail(margeAfterEmailNewValue);
            user.setEmail(margeAfterEmailOldValue);

            transaction.commit();
            return newUser.getId();
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public User loadWithOutTransaction(final int id) {
        Session session = null;
        try{
            session = sessionFactory.openSession();

            return (User) session.load(User.class, id);
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public User loadWithTransaction(final int id) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            final User user = (User) session.load(User.class, id);
            transaction.commit();
            return user;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public User getWithOutTransaction(final int id) {
        Session session = null;
        try{
            session = sessionFactory.openSession();

            session.get(User.class, id);

            return (User) session.get(User.class, id);
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public User getWithTransaction(final int id) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();
            final User user = (User) session.get(User.class, id);
            transaction.commit();
            return user;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int findUserByIdAndUpdateEmail(final int id, String emailBeforeSave, final String emailAfterSave) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            final Transaction transaction = session.beginTransaction();

            final User user = (User) session.load(User.class, id);
            user.setEmail(emailBeforeSave);

            int afterSaveId = (int) session.save(user);

            user.setEmail(emailAfterSave);

            transaction.commit();
            return afterSaveId;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public User findById(final int id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();

            final Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq(ID, id));
            final List list = criteria.list();
            if (list != null){
                return (User) list.get(0);
            }
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public List<User> findByEmail(final String email) {
        Session session = null;
        try {
            session = sessionFactory.openSession();

            final Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq(EMAIL, email));
            return criteria.list();
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
