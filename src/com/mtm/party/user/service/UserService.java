package com.mtm.party.user.service;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mtm.party.user.model.User;
import com.mtm.party.user.model.UserRecord;

import cn.mtm2000.common.hibernate.HibernateDao;
import cn.mtm2000.common.hibernate.QlBuilder;

/**
 * @author qubin
 * @date 2017-07-21
 */

@Service
@Transactional
public class UserService {
	
	@Resource
	private HibernateTemplate template;

	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}
	@Resource
	private HibernateDao dao;

	public HibernateDao getDao() {
		return dao;
	}

	public void setDao(HibernateDao dao) {
		this.dao = dao;
	}
	
	/**
	 * 根据用户名密码获取user
	 * @param account
	 * @param password
	 * @return
	 */
	public User getUserByLoginAndPwd(String account, String password) {
		QlBuilder s = new QlBuilder();
		Object o = null;
		if (!account.equals("") && !password.equals("")) {
			s.segment("from User u where u.account = ");
			s.value(account);
			s.segment(" and u.password = ");
			s.value(password);
			o = dao.uniqueResult(s);
		}
		if (o == null) {
			return null;
		}
		return (User) o;
	}
	
	/**
	 * 保存用户
	 * @param user
	 */
	public void saveUser(User user){
		dao.save(user);
	}
	/**
	 * 保存用户
	 * @param user
	 */
	public void saveUserRecord(UserRecord user){
		dao.save(user);
	}
	/**
	 * 关联用户
	 * @param user
	 */
	public void updateUser(User user){
		dao.update(user);
	}
	
	/**
	 * 根据身份证号、手机号获取user
	 * @param account
	 * @param password
	 * @return
	 */
	public User getUserByIdCardAndPhone(String idcard, String phone) {
		QlBuilder s = new QlBuilder();
		Object o = null;
		if (!idcard.equals("") && !phone.equals("")) {
			s.segment("from User u where u.idcard = ");
			s.value(idcard);
			s.segment(" and u.phone = ");
			s.value(phone);
			o = dao.uniqueResult(s);
		}
		if (o == null) {
			return null;
		}
		return (User) o;
	}
	
	/**
	 * 根据ID获取user
	 * @param userId
	 * @return
	 */
	public Object getUserById(String openId) {
		QlBuilder s = new QlBuilder();
		Object o = null;
		s.segment("select * from T_USER u where u.openId=");;
		s.value(openId);
		o = dao.listBySql(s);
		if (o == null) {
			return null;
		}
		return o;
	}
	
	/**
	 * 获取所有用户信息
	 * @param userId
	 * @return
	 */
	public Object getUsers() {
		QlBuilder s = new QlBuilder();
		Object o = null;
		s.segment("select * from T_USER u");;
		o = dao.listBySql(s);
		if (o == null) {
			return null;
		}
		return  o;
	}
	/**
	 * 根据openid获取user
	 * @param account
	 * @param password
	 * @return
	 */
	public User getUserByOpenId(String openId) {
		QlBuilder s = new QlBuilder();
		Object o = null;
		if (!openId.equals("")) {
			s.segment("from User u where u.openId = ");
			s.value(openId);
			o = dao.uniqueResult(s);
		}
		if (o == null) {
			return null;
		}
		return (User) o;
	}
	
	/**
	 * 根据身份证号获取user
	 * @param account
	 * @param password
	 * @return
	 */
	public User getUserByuserIdCard(String idCard) {
		QlBuilder s = new QlBuilder();
		Object o = null;
		if (!idCard.equals("")) {
			s.segment("from User u where u.idcard = ");
			s.value(idCard);
			o = dao.uniqueResult(s);
		}
		if (o == null) {
			return null;
		}
		return (User) o;
	}
}
