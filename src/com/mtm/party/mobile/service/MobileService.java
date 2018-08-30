package com.mtm.party.mobile.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mtm.party.mobile.model.BlessComment;
import com.mtm.party.mobile.model.BlessUser;

import cn.mtm2000.common.hibernate.HibernateDao;
import cn.mtm2000.common.hibernate.PageData;
import cn.mtm2000.common.hibernate.QlBuilder;

/**
 * 
 * @author wangsong
 *
 */
@Service
@Transactional
public class MobileService {

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
	
	//保存
	public void save(Object obj){
		dao.save(obj);
	}
	//根据nick_image查找
	public List getBlessUserByNickImage(String nick_image){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_BLESS_USER c where c.nick_image=").value(nick_image);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			return obj;
		}else{
			return null;
		}
	}
	//根据nick_image查找
	public List getBlessUserByOpenId(String open_id){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_BLESS_USER c where c.open_id=").value(open_id);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			return obj;
		}else{
			return null;
		}
	}
	//获取赞列表
	public List getAllBlessUser(){
		QlBuilder ql=new QlBuilder();
		List list=new ArrayList();
		ql.segment(" select c.* FROM T_BLESS_USER c order by c.create_time desc");
		list = dao.listBySql(ql);
		if (null !=list) {
			return list;
		}else{
			return null;
		}
	}
	//获取评论列表
	public List getAllBlessComment(){
		QlBuilder ql=new QlBuilder();
		List list=new ArrayList();
		ql.segment(" select c.* FROM T_BLESS_COMMENT c order by c.create_time desc");
		list = dao.listBySql(ql);
		if (null !=list) {
			return list;
		}else{
			return null;
		}
	}
	
	public void save(BlessUser blessUser) {
		dao.save(blessUser);
	}
	
	public void save(BlessComment blessUser) {
		dao.save(blessUser);
	}
	
	
}
