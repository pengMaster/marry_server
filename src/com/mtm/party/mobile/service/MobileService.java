package com.mtm.party.mobile.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import com.mchange.v2.log.LogUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.mtm2000.common.hibernate.HibernateDao;
import cn.mtm2000.common.hibernate.QlBuilder;

import com.mtm.party.mobile.model.BlessComment;
import com.mtm.party.mobile.model.BlessUser;
import com.mtm.party.mobile.model.ImageHomeBean;
import com.mtm.party.mobile.model.ShareInfo;
import com.mtm.party.user.model.DetailImages;
import com.mtm.party.user.model.MapInfo;
import com.mtm.party.user.model.UserLogo;

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
	public List getBlessUserByOpenId(String open_id,String userId){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_BLESS_USER c where c.open_id=").value(open_id);
		ql.segment("and c.user_id=").value(userId);;
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
	public List getAllBlessUser(String openId){
		QlBuilder ql=new QlBuilder();
		List list=new ArrayList();
		ql.segment(" select c.* FROM T_BLESS_USER c where c.user_id=");
		ql.value(openId);
		ql.segment("order by c.create_time desc");
		list = dao.listBySql(ql);
		if (null !=list) {
			return list;
		}else{
			return null;
		}
	}
	public List getAllBlessUser(){
		QlBuilder ql=new QlBuilder();
		List list=new ArrayList();
		ql.segment(" select * FROM T_BLESS_USER c where 1=1 order by c.create_time desc");
		list = dao.listBySql(ql);
		if (null !=list) {
			return list;
		}else{
			return null;
		}
	}
	//获取评论列表
	public List getAllBlessComment(String openId){
		QlBuilder ql=new QlBuilder();
		List list=new ArrayList();
		ql.segment(" select c.* FROM T_BLESS_COMMENT c  where c.user_id=");
		ql.value(openId);
		ql.segment("order by c.create_time desc");
		list = dao.listBySql(ql);
		if (null !=list) {
			return list;
		}else{
			return null;
		}
	}
	public List getAllBlessComment(){
		QlBuilder ql=new QlBuilder();
		List list=new ArrayList();
		ql.segment(" select * FROM T_BLESS_COMMENT c where 1=1 order by c.create_time desc");
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
	
	
	//------------------------------- 用户模块 --------------------------------
	/**
	 * 获取用户图片
	 * @param id
	 * @return
	 */
	public List getUserImageById(String id){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_IMAGE c where c.id=").value(id);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			return obj;
		}else{
			return null;
		}
	}
	/**
	 * 获取地图信息
	 * @param id
	 * @return
	 */
	public List getMapInfoByOpenId(String id){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_MAP_INFO c where c.userId=").value(id);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			return obj;
		}else{
			return null;
		}
	}
	/**
	 * 获取用户图片
	 * @param id
	 * @return
	 */
	public List getUserImageByName(String imageName){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_IMAGE c where c.image_name=").value(imageName);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			return obj;
		}else{
			return null;
		}
	}
	/**
	 * 获取用户图片
	 * @param id
	 * @return
	 */
	public List getShareInfoByUserId(String userId){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_SHARE_INFO c where c.user_id=").value(userId);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			return obj;
		}else{
			return null;
		}
	}

	public List getStatus(){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select * FROM t_status");
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			return obj;
		}else{
			return null;
		}
	}
	/**
	 * 根据openid获取imgs
	 * @param openId
	 * @return
	 */
	public List<ImageHomeBean> getHostUserImgs(String openId){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_IMAGE c where c.user_id=").value(openId);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			List<ImageHomeBean> list = new ArrayList<ImageHomeBean>();
			for(int i=0;i<obj.size();i++){
				if (null!=obj.get(i)) {
					ImageHomeBean iBean = new ImageHomeBean();
					Object[] objects = (Object[])obj.get(i);
					iBean.setId(objects[0]+"");
					iBean.setImgUrl(objects[1]+"");
					iBean.setTitle(objects[2]+"");
					iBean.setUserId(objects[3]+"");
					iBean.setCreateTime(objects[4]+"");
					iBean.setUpdateTime(objects[5]+"");
					list.add(iBean);
				}
			}
			return list;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据userId获取imgs
	 * @param userId
	 * @return
	 */
	public List<DetailImages> getDetailImages(String userId){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_DETAIL_IMAGES c where c.user_id=").value(userId);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			List<DetailImages> list = new ArrayList<DetailImages>();
			for(int i=0;i<obj.size();i++){
				if (null!=obj.get(i)) {
					DetailImages iBean = new DetailImages();
					Object[] objects = (Object[])obj.get(i);
					iBean.setId(objects[0]+"");
					iBean.setCreateTime(objects[1]+"");
					iBean.setUpdateTime(objects[2]+"");
					iBean.setUserId(objects[3]+"");
					iBean.setBannerId(objects[4]+"");
					iBean.setDesc(objects[5]+"");
					iBean.setImgUrl(objects[6]+"");

					list.add(iBean);
				}
			}
			return list;
		}else{
			return null;
		}
	}
	/**
	 * 根据id获取imgs
	 * @param images
	 * @return
	 */
	public void deleteDetailImages(DetailImages images){
		dao.delete(images);
	}
	/**
	 * 根据id获取imgs
	 * @param images
	 * @return
	 */
	public void deleteItemImages(ImageHomeBean images){
		dao.delete(images);
	}

	/**
	 * 根据id获取imgs
	 * @param id
	 * @return
	 */
	public void deleteDetailImagesById(String id){
		String sql = "delete FROM T_DETAIL_IMAGES where banner_id='" + id+"'";
		Session session = template.getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
	/**
	 * 根据id获取imgs
	 * @param id
	 * @return
	 */
	public List<DetailImages> getDetailImagesById(String id){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_DETAIL_IMAGES c where c.id=").value(id);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			List<DetailImages> list = new ArrayList<DetailImages>();
			for(int i=0;i<obj.size();i++){
				if (null!=obj.get(i)) {
					DetailImages iBean = new DetailImages();
					Object[] objects = (Object[])obj.get(i);
					iBean.setId(objects[0]+"");
					iBean.setCreateTime(objects[1]+"");
					iBean.setUpdateTime(objects[2]+"");
					iBean.setUserId(objects[3]+"");
					iBean.setBannerId(objects[4]+"");
					iBean.setDesc(objects[5]+"");
					iBean.setImgUrl(objects[6]+"");

					list.add(iBean);
				}
			}
			return list;
		}else{
			return null;
		}
	}
	/**
	 * 根据id获取imgs
	 * @param id
	 * @return
	 */
	public List<UserLogo> getUserLogoByUserId(String userId){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_USER_LOGO c where c.user_id=").value(userId);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			List<UserLogo> list = new ArrayList<UserLogo>();
			for(int i=0;i<obj.size();i++){
				if (null!=obj.get(i)) {
					UserLogo iBean = new UserLogo();
					Object[] objects = (Object[])obj.get(i);
					iBean.setId(objects[0]+"");
					iBean.setCreateTime(objects[2]+"");
					iBean.setUpdateTime(objects[3]+"");
					iBean.setUserId(objects[4]+"");
					iBean.setAppTitleName(objects[5]+"");
					iBean.setImgUrl(objects[1]+"");

					list.add(iBean);
				}
			}
			return list;
		}else{
			return null;
		}
	}
	/**
	 * 根据bannerId获取imgs
	 * @param bannerId
	 * @return
	 */
	public List<DetailImages> getDetailImagesByBannerId(String bannerId){
		QlBuilder ql=new QlBuilder();
		ql.segment(" select c.* FROM T_DETAIL_IMAGES c where c.banner_id=").value(bannerId);
		List obj = dao.listBySql(ql);
		if (null !=obj && obj.size()>0) {
			List<DetailImages> list = new ArrayList<DetailImages>();
			for(int i=0;i<obj.size();i++){
				if (null!=obj.get(i)) {
					DetailImages iBean = new DetailImages();
					Object[] objects = (Object[])obj.get(i);
					iBean.setId(objects[0]+"");
					iBean.setCreateTime(objects[2]+"");
					iBean.setUpdateTime(objects[3]+"");
					iBean.setUserId(objects[4]+"");
					iBean.setBannerId(objects[5]+"");
					iBean.setDesc(objects[6]+"");
					iBean.setImgUrl(objects[1]+"");

					list.add(iBean);
				}
			}
			return list;
		}else{
			return null;
		}
	}
	/**
	 * 保存用户图片
	 * @param bean
	 */
	public void save(ImageHomeBean bean) {
		dao.save(bean);
	}
	
	/**
	 * 更新用户图片
	 * @param bean
	 */
	public void update(ImageHomeBean bean) {
		dao.update(bean);
	}
	/**
	 * 保地圖图片
	 * @param bean
	 */
	public void save(MapInfo bean) {
		dao.save(bean);
	}
	
	/**
	 * 更新地图图片
	 * @param bean
	 */
	public void update(MapInfo bean) {
		dao.update(bean);
	}
	/**
	 * 保存分享图片
	 * @param bean
	 */
	public void save(ShareInfo bean) {
		dao.save(bean);
	}
	
	/**
	 * 更新分享图片
	 * @param bean
	 */
	public void update(ShareInfo bean) {
		dao.update(bean);
	}
	

	public void save(DetailImages bean) {
		dao.save(bean);
	}
	

	public void update(DetailImages bean) {
		dao.update(bean);
	}
	public void save(UserLogo bean) {
		dao.save(bean);
	}
	

	public void update(UserLogo bean) {
		dao.update(bean);
	}
	
	
	
}
