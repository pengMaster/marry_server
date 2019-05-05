package com.mtm.party.mobile.ctrl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.mtm2000.common.util.ValidUtil;

import com.google.gson.Gson;
import com.mtm.party.mobile.model.BlessComment;
import com.mtm.party.mobile.model.BlessUser;
import com.mtm.party.mobile.model.HttpHeaderInfoBean;
import com.mtm.party.mobile.model.ImageHomeBean;
import com.mtm.party.mobile.model.ImageList;
import com.mtm.party.mobile.model.ShareInfo;
import com.mtm.party.mobile.service.MobileService;
import com.mtm.party.mobile.util.HttpHeaderUtils;
import com.mtm.party.user.model.DetailImages;
import com.mtm.party.user.model.HostUser;
import com.mtm.party.user.model.MapInfo;
import com.mtm.party.user.model.User;
import com.mtm.party.user.model.UserInfo;
import com.mtm.party.user.model.UserLogo;
import com.mtm.party.user.model.UserRecord;
import com.mtm.party.user.service.UserService;
import com.mtm.party.util.FileUtils;
import com.mtm.party.util.HttpRequestor;
import com.mtm.party.util.StringUtils;

/**
 * 中文参数接收方式:URLDecoder.decode(request.getParameter("body"), "UTF-8");
 * <p>
 * 微信小程序对接
 */

@Controller
@RequestMapping("/mobile")
public class MobileController {

    private final String SAVE_USER = "SAVE_USER";// 用户注册
    private final String GET_OPENID = "GET_OPENID";// 支付申请订单
    private final String LOGIN_IN = "LOGIN_IN";// 登录
    private final String GET_IMAGE = "GET_IMAGE";// 获取图片
    private final String SAVE_IMAGE_HOME = "SAVE_IMAGE_HOME";
    private final String GET_PRAISE = "GET_PRAISE";// 获取赞列表
    private final String SAVE_PRAISE = "SAVE_PRAISE";// 保存赞
    private final String GET_COMMENT = "GET_COMMENT";// 获取评论列表
    private final String SAVE_COMMENT = "SAVE_COMMENT";// 保存评论
    private final String SAVE_HOST_USER = "SAVE_HOST_USER";// 创建小程序的用户
    private final String COPY_FILE = "COPY_FILE";// COPY_FILE
    private final String GET_HOME_IMAGES = "GET_HOME_IMAGES";// GET_HOME_IMAGES
    private final String SAVE_MAP_INFO = "SAVE_MAP_INFO";// 保存地图页信息
    private final String SAVE_MAP_IMAGE = "SAVE_MAP_IMAGE";// 保存地图页图片
    private final String GET_MAP_INFO = "GET_MAP_INFO";// GET_MAP_INFO
    private final String SAVE_SHARE_INFO = "SAVE_SHARE_INFO";// SAVE_SHARE_INFO
    private final String GET_SHARE_INFO = "GET_SHARE_INFO";// GET_SHARE_INFO
    private final String SAVE_DETAIL_IMAGES = "SAVE_DETAIL_IMAGES";// SAVE_DETAIL_IMAGES
    private final String GET_DETAIL_IMAGES = "GET_DETAIL_IMAGES";// GET_DETAIL_IMAGES
    private final String GET_HOST_USER = "GET_HOST_USER";// 获取宿主用户
    private final String SAVE_IMAGE_LOGO = "SAVE_IMAGE_LOGO";// SAVE_IMAGE_LOGO
    private final String GET_IMAGE_LOGO = "GET_IMAGE_LOGO";// GET_IMAGE_LOGO
    private final String DELETE_DETAIL_IMAGES = "DELETE_DETAIL_IMAGES";// DELETE_DETAIL_IMAGES
    private final String DELETE_ITEM_IMAGES = "DELETE_ITEM_IMAGES";// DELETE_ITEM_IMAGES
    private final String isHide = "isHide";// isHide

    private JSONArray jsonArray = new JSONArray();
    @Resource
    private MobileService mobileService;
    @Resource
    private UserService userService;

    public MobileService getMobileService() {
        return mobileService;
    }

    public void setMobileService(MobileService mobileService) {
        this.mobileService = mobileService;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 接口方法总入口
     *
     * @return
     * @throws Exception
     * @author wangpeng
     */
    // http://localhost:8080/party//mobile/mobileIn.do
    // http://localhost:8080/party/wechat/image/arrow_chart.png
    @RequestMapping("mobileIn")
    public String mobileIn(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        // request.setCharacterEncoding("application/json; charset=UTF-8");
        HttpHeaderInfoBean headerInfoBean = HttpHeaderUtils
                .getHeaderInfos(request);
        if (ValidUtil.isEmpty(headerInfoBean.getMethod())) {
            headerInfoBean = HttpHeaderUtils.getHeaderInfosTest(request);
        }
        String method = headerInfoBean.getMethod();
        String json = "";
        try {
            request.setCharacterEncoding("UTF-8");
            if (SAVE_USER.equals(method)) {
                // 用户注册接口
                json = saveUser(request, response);
            } else if (GET_OPENID.equals(method)) {
                // 获取openiD接口
                json = getOpenID(request, response);
            } else if (LOGIN_IN.equals(method)) {
                // 用户登录方法
                json = loginin(request, response);
            } else if (GET_IMAGE.equals(method)) {
                // 获取图片
                json = getImages(request, response);
            } else if (GET_PRAISE.equals(method)) {
                // 获取赞列表
                json = getPraiseList(request, response);
            } else if (SAVE_PRAISE.equals(method)) {
                // 点赞
                json = savePraise(request, response);
            } else if (SAVE_COMMENT.equals(method)) {
                // 保存评论
                json = saveComment(request, response);
            } else if (GET_COMMENT.equals(method)) {
                // 获取评论列表
                json = getCommentList(request, response);
            } else if (SAVE_IMAGE_HOME.equals(method)) {
                // 保存首页图片
                json = saveHomeImage(request, response);
            } else if (SAVE_HOST_USER.equals(method)) {
                // 创建小程序的用户
                json = saveHostUser(request, response);
            } else if (COPY_FILE.equals(method)) {
                json = copyFile(request, response);
            } else if (GET_HOME_IMAGES.equals(method)) {
                json = getHomeImages(request, response);
            } else if (SAVE_MAP_IMAGE.equals(method)) {
                // 保存地图页信息
                json = saveMapImage(request, response);
            } else if (SAVE_MAP_INFO.equals(method)) {
                // 保存地图页信息
                json = saveMapInfo(request, response);
            } else if (GET_MAP_INFO.equals(method)) {
                // 获取地图页信息
                json = getMapInfo(request, response);
            } else if (SAVE_SHARE_INFO.equals(method)) {
                // 保存分享图片
                json = saveShareInfo(request, response);
            } else if (GET_SHARE_INFO.equals(method)) {
                // 获取分享信息
                json = getShareInfo(request, response);
            } else if (SAVE_DETAIL_IMAGES.equals(method)) {
                // 保存图片详情
                json = saveDetailImages(request, response);
            } else if (GET_DETAIL_IMAGES.equals(method)) {
                // 获取图片详情
                json = getDetailImages(request, response);
            } else if (GET_HOST_USER.equals(method)) {
                // 获取宿主用户
                json = getHostUser(request, response);
            } else if (SAVE_IMAGE_LOGO.equals(method)) {
                // 保存用户头像
                json = saveImageLogo(request, response);
            } else if (GET_IMAGE_LOGO.equals(method)) {
                // 获取用户头像
                json = getImageLogo(request, response);
            } else if (DELETE_DETAIL_IMAGES.equals(method)) {
                // 删除详情图片
                json = deleteDetailImage(request, response);
            } else if (DELETE_ITEM_IMAGES.equals(method)) {
                // 删除单个模块
                json = deleteItemImage(request, response);
            } else if (isHide.equals(method)) {
                // 是否隐藏信息
                json = hideContent(request, response);
            } else {
                json = "测试";
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String hideContent(HttpServletRequest request, HttpServletResponse response) {

        try {
            List statusLists = mobileService.getStatus();

            if (null == statusLists)
                return "error";
            String status = statusLists.get(0).toString();
            if ("0".equals(status)) {
                return "no";
            } else if ("1".equals(status)) {
                return "yes";
            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "yes";
        }
    }

    /**
     * 删除详情单个模块
     *
     * @param request
     * @param response
     * @return
     */
    private String deleteItemImage(HttpServletRequest request, HttpServletResponse response) {

        String itemJson = request.getParameter("itemJson");
        String hostUserId = request.getParameter("hostUserId");
        try {
            ImageHomeBean imageHomeBean = new Gson().fromJson(itemJson, ImageHomeBean.class);
            if (null == imageHomeBean)
                return "error";
            if (!hostUserId.equals(imageHomeBean.getUserId())) {
                return "notYou";
            }
            String imgUrl = imageHomeBean.getImgUrl();
            if (imgUrl.contains("party")) {
                String[] parties = imgUrl.split("party");
                String path = System.getProperty("catalina.home") + parties[1];
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                    mobileService.deleteItemImages(imageHomeBean);
                    String imgPath = System.getProperty("catalina.home") + "/userImg/"
                            + imageHomeBean.getUserId() + "/detailImages/" + imageHomeBean.getId() + "/";

                    boolean b = FileUtils.deleteDirectory(imgPath);
                    mobileService.deleteDetailImagesById(imageHomeBean.getId());
                    return "success";
                } else
                    return "notFile";
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 删除详情单个图片
     *
     * @param request
     * @param response
     * @return
     */
    private String deleteDetailImage(HttpServletRequest request, HttpServletResponse response) {

        String itemJson = request.getParameter("itemJson");
        String hostUserId = request.getParameter("hostUserId");
        System.out.println(hostUserId);
        try {
            DetailImages detailImages = new Gson().fromJson(itemJson, DetailImages.class);
            if (null == detailImages)
                return "error";
            if (!hostUserId.equals(detailImages.getUserId())) {
                return "notYou";
            }
            String imgUrl = detailImages.getImgUrl();
            if (imgUrl.contains("party")) {
                String[] parties = imgUrl.split("party");
                String path = System.getProperty("catalina.home") + parties[1];
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                    mobileService.deleteDetailImages(detailImages);
                } else {
                    return "notFile";
                }
            }

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 获取用户头像
     *
     * @param request
     * @param response
     * @return
     */
    private String getImageLogo(HttpServletRequest request,
                                HttpServletResponse response) {

        String userId = request.getParameter("userId");

        if (null != userId && !"null".equals(userId) ) {

            List<UserLogo> detailImages = mobileService.getUserLogoByUserId(userId);

            return new Gson().toJson(detailImages);

        }
        return "";
    }

    /**
     * 保存用户头像
     *
     * @param request
     * @param response
     * @return
     */
    private String saveImageLogo(HttpServletRequest request,
                                 HttpServletResponse response) {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

        MultipartFile multipartFile = req.getFile("file");

        String userId = request.getParameter("userId");

        String host = request.getParameter("host");

        String appName = request.getParameter("appName");

        String id = request.getParameter("id");

        String imgPath = System.getProperty("catalina.home") + "/userImg/"
                + userId + "/logo/";

        try {
            File file = new File(imgPath);

            if (!file.exists()) {

                file.mkdirs();
            }

            File fileImg = new File(imgPath, multipartFile
                    .getOriginalFilename());

            List<UserLogo> detailImages = mobileService
                    .getUserLogoByUserId(userId);

            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");

            if (null != detailImages) {
                UserLogo detailImages2 = detailImages.get(0);
                detailImages2.setId(detailImages2.getId());
                detailImages2.setImgUrl(host + "/userImg/" + userId + "/logo/"
                        + multipartFile.getOriginalFilename());
                detailImages2.setUserId(detailImages2.getUserId());
                detailImages2.setAppTitleName(appName);
                detailImages2.setUpdateTime(df.format(new Date()));
                mobileService.update(detailImages2);
            } else {
                UserLogo detaImages = new UserLogo();
                detaImages.setId(id);
                detaImages.setImgUrl(host + "/userImg/" + userId + "/logo/"
                        + multipartFile.getOriginalFilename());
                detaImages.setUserId(userId);
                detaImages.setCreateTime(df.format(new Date()));
                detaImages.setAppTitleName(appName);
                mobileService.save(detaImages);
            }

            multipartFile.transferTo(fileImg);

            copyFile(request, userId);

            return "保存成功";

        } catch (IOException e) {
            e.printStackTrace();
            return "保存失败";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "保存失败";
        }
    }

    /**
     * 获取宿主用户
     *
     * @param request
     * @param response
     * @return
     */
    private String getHostUser(HttpServletRequest request,
                               HttpServletResponse response) {

        String openId = request.getParameter("openId");

        Object hostUserById = userService.getHostUserById(openId);

        if (null != hostUserById) {
            return new Gson().toJson(hostUserById);
        }

        return "";
    }

    /**
     * 获取图片详情
     *
     * @param request
     * @param response
     * @return
     */
    private String getDetailImages(HttpServletRequest request,
                                   HttpServletResponse response) {

        String bannerId = request.getParameter("bannerId");

        List<DetailImages> detailImages = mobileService
                .getDetailImagesByBannerId(bannerId);

        return new Gson().toJson(detailImages);
    }

    /**
     * 保存图片详情
     *
     * @param request
     * @param response
     * @return
     */
    private String saveDetailImages(HttpServletRequest request,
                                    HttpServletResponse response) {

        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

        MultipartFile multipartFile = req.getFile("file");

        String userId = request.getParameter("userId");

        String hostUserId = request.getParameter("hostUserId");

        String host = request.getParameter("host");

        String bannerId = request.getParameter("bannerId");

        String id = request.getParameter("id");

        String desc = request.getParameter("desc");

        String imgPath = System.getProperty("catalina.home") + "/userImg/"
                + userId + "/detailImages/" + bannerId + "/";

        try {

            if (!userId.equals(hostUserId)) {
                return "notYou";
            }

            File file = new File(imgPath);

            if (!file.exists()) {

                file.mkdirs();
            }

            File fileImg = new File(imgPath, multipartFile
                    .getOriginalFilename());

            List<DetailImages> detailImages = mobileService
                    .getDetailImagesById(id);

            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");

            if (null != detailImages) {
                DetailImages detailImages2 = detailImages.get(0);
                detailImages2.setUpdateTime(df.format(new Date()));
                mobileService.update(detailImages2);
            } else {
                DetailImages detaImages = new DetailImages();
                detaImages.setId(id);
                detaImages.setImgUrl(host + "/userImg/" + userId
                        + "/detailImages/" + bannerId + "/"
                        + multipartFile.getOriginalFilename());
                detaImages.setUserId(userId);
                detaImages.setCreateTime(df.format(new Date()));
                detaImages.setBannerId(bannerId);
                detaImages.setDesc(desc);

                mobileService.save(detaImages);
            }

            multipartFile.transferTo(fileImg);

            copyFile(request, userId);

            return "保存成功";

        } catch (IOException e) {
            e.printStackTrace();
            return "保存失败";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "保存失败";
        }
    }

    /**
     * 获取分享信息
     *
     * @param request
     * @param response
     * @return
     */
    private String getShareInfo(HttpServletRequest request,
                                HttpServletResponse response) {

        String userId = request.getParameter("userId");

        List hostObj = (List) mobileService.getShareInfoByUserId(userId);

        return new Gson().toJson(hostObj);

    }

    /**
     * 保存分享图片
     *
     * @param request
     * @param response
     * @return
     */
    private String saveShareInfo(HttpServletRequest request,
                                 HttpServletResponse response) {

        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

        MultipartFile multipartFile = req.getFile("file");

        String userId = request.getParameter("userId");

        String host = request.getParameter("host");

        String imgPath = System.getProperty("catalina.home") + "/userImg/"
                + userId + "/share/";

        try {
            String inputTitleName = request.getParameter("inputTitleName");

            File file = new File(imgPath);

            if (!file.exists()) {

                file.mkdirs();
            }

            File fileImg = new File(imgPath, multipartFile
                    .getOriginalFilename());

            ShareInfo shareBean = new ShareInfo();

            List hostObj = (List) mobileService.getShareInfoByUserId(userId);

            shareBean.setUserId(userId);

            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");

            shareBean.setImgUrl(host + "/userImg/" + userId + "/share/"
                    + multipartFile.getOriginalFilename());

            if (null != hostObj) {
                Object[] objs = (Object[]) hostObj.get(0);
                shareBean.setId(objs[0] + "");
                shareBean.setCreateTime(objs[3] + "");
                shareBean.setUpdateTime(df.format(new Date()));
                if (!"".equals(inputTitleName))
                    shareBean.setInputTitleName(inputTitleName);
                else
                    shareBean.setCreateTime(objs[5] + "");
                mobileService.update(shareBean);
            } else {
                shareBean.setId(getId());
                shareBean.setCreateTime(df.format(new Date()));
                if (!"".equals(inputTitleName))
                    shareBean.setInputTitleName(inputTitleName);
                mobileService.save(shareBean);
            }

            multipartFile.transferTo(fileImg);

            copyFile(request, userId);

            return "保存成功";

        } catch (IOException e) {
            e.printStackTrace();
            return "保存失败";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "保存失败";
        }
    }

    /**
     * 获取地图页信息
     *
     * @param request
     * @param response
     * @return
     */
    private String getMapInfo(HttpServletRequest request,
                              HttpServletResponse response) {

        String userId = request.getParameter("userId");

        List hostObj = (List) mobileService.getMapInfoByOpenId(userId);

        return new Gson().toJson(hostObj);
    }

    /**
     * 保存地图页信息
     *
     * @param request
     * @param response
     * @return
     */
    private String saveMapInfo(HttpServletRequest request,
                               HttpServletResponse response) {

        try {
            String inviteName = request.getParameter("inviteName");
//            String inviteName = new String(request.getParameter("inviteName").getBytes("ISO8859-1"), "UTF-8");

            String inviteDateOne = request.getParameter("inviteDateOne");
//            String inviteDateOne = new String(request.getParameter("inviteDateOne").getBytes("ISO8859-1"), "UTF-8");

//            String inviteDateTwo = new String(request.getParameter("inviteDateTwo").getBytes("ISO8859-1"), "UTF-8");
            String inviteDateTwo = request.getParameter("inviteDateTwo");

//            String inviteAddress = new String(request.getParameter("inviteAddress").getBytes("ISO8859-1"), "UTF-8");
            String inviteAddress = request.getParameter("inviteAddress");

            String inviteLongitude = request.getParameter("inviteLongitude");

            String inviteLatitude = request.getParameter("inviteLatitude");

            String userId = request.getParameter("userId");

            String isOriginal = request.getParameter("isOriginal");

            try {
                MapInfo hostUser = new MapInfo();
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
                List hostObj = (List) mobileService.getMapInfoByOpenId(userId);

                if (null != hostObj) {
                    Object[] objs = (Object[]) hostObj.get(0);
                    hostUser.setId(objs[0] + "");
                    hostUser.setUpdateTime(df.format(new Date()));

                    if (null != userId && !"".equals(userId))
                        hostUser.setUserId(userId);
                    else
                        hostUser.setUserId(objs[1] + "");

                    if (!"".equals(inviteName))
                        hostUser.setInviteName(inviteName);
                    else
                        hostUser.setInviteName(objs[5] + "");

                    if (!"".equals(inviteAddress))
                        hostUser.setInviteAddress(inviteAddress);
                    else
                        hostUser.setInviteAddress(objs[8] + "");

                    if ( !"".equals(inviteDateOne))
                        hostUser.setInviteDateOne(inviteDateOne);
                    else
                        hostUser.setInviteDateOne(objs[6] + "");

                    if (!"".equals(inviteDateTwo))
                        hostUser.setInviteDateTwo(inviteDateTwo);
                    else
                        hostUser.setInviteDateTwo(objs[7] + "");

                    if (null != inviteLatitude && !"".equals(inviteLatitude))
                        hostUser.setInviteLatitude(inviteLatitude);
                    else
                        hostUser.setInviteLatitude(objs[10] + "");

                    if (null != inviteLongitude && !"".equals(inviteLongitude))
                        hostUser.setInviteLongitude(inviteLongitude);
                    else
                        hostUser.setInviteLongitude(objs[9] + "");

                    if (null != isOriginal && !"".equals(isOriginal))
                        hostUser.setIsOriginal(isOriginal);
                    else
                        hostUser.setIsOriginal(objs[4] + "");

                    if (null != objs[11] && !"".equals(objs[11]))
                        hostUser.setInviteBgUrl(objs[11] + "");

                    mobileService.update(hostUser);
                } else {
                    hostUser.setUserId(userId);
                    hostUser.setInviteName(inviteName);
                    hostUser.setInviteAddress(inviteAddress);
                    hostUser.setInviteDateOne(inviteDateOne);
                    hostUser.setInviteDateTwo(inviteDateTwo);
                    hostUser.setInviteLatitude(inviteLatitude);
                    hostUser.setInviteLongitude(inviteLongitude);
                    hostUser.setIsOriginal(isOriginal);
                    hostUser.setId(getId());
                    hostUser.setCreateTime(df.format(new Date()));
                    mobileService.save(hostUser);
                }
                return "保存成功";

            } catch (IllegalStateException e) {
                e.printStackTrace();
                return "保存失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "保存失败";
        }
    }

    /**
     * 保存地图页信息
     *
     * @param request
     * @param response
     * @return
     */
    private String saveMapImage(HttpServletRequest request,
                                HttpServletResponse response) {

        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

        MultipartFile multipartFile = req.getFile("file");

        String userId = request.getParameter("userId");

        String host = request.getParameter("host");

        String isOriginal = request.getParameter("isOriginal");

        String imgPath = System.getProperty("catalina.home") + "/userImg/"
                + userId + "/map/";

        try {
            File file = new File(imgPath);

            if (!file.exists()) {

                file.mkdirs();
            }

            File fileImg = new File(imgPath, multipartFile
                    .getOriginalFilename());

            MapInfo hostUser = new MapInfo();
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
            List hostObj = (List) mobileService.getMapInfoByOpenId(userId);


            if (null != hostObj) {
                Object[] objs = (Object[]) hostObj.get(0);
                hostUser.setId(objs[0] + "");
                hostUser.setUpdateTime(df.format(new Date()));
                if (null != objs[1] && !"".equals(objs[1]))
                    hostUser.setUserId(objs[1] + "");
                else
                    hostUser.setUserId(userId);
                if (null != objs[5] && !"".equals(objs[5]))
                    hostUser.setInviteName(objs[5] + "");

                if (null != objs[8] && !"".equals(objs[8]))
                    hostUser.setInviteAddress(objs[8] + "");

                if (null != objs[6] && !"".equals(objs[6]))
                    hostUser.setInviteDateOne(objs[6] + "");

                if (null != objs[7] && !"".equals(objs[7]))
                    hostUser.setInviteDateTwo(objs[7] + "");

                if (null != objs[10] && !"".equals(objs[10]))
                    hostUser.setInviteLatitude(objs[10] + "");

                if (null != objs[9] && !"".equals(objs[9]))
                    hostUser.setInviteLongitude(objs[9] + "");

                if (null != objs[4] && !"".equals(objs[4]))
                    hostUser.setIsOriginal(objs[4] + "");
                else
                    hostUser.setIsOriginal(isOriginal);

                if (null != multipartFile.getOriginalFilename())
                    hostUser.setInviteBgUrl(host + "/userImg/" + userId + "/map/"
                            + multipartFile.getOriginalFilename());
                mobileService.update(hostUser);
            } else {
                hostUser.setUserId(userId);
                hostUser.setIsOriginal(isOriginal);
                hostUser.setInviteBgUrl(host + "/userImg/" + userId + "/map/"
                        + multipartFile.getOriginalFilename());
                hostUser.setId(getId());
                hostUser.setCreateTime(df.format(new Date()));
                mobileService.save(hostUser);
            }

            if (null != multipartFile.getOriginalFilename()) {
                multipartFile.transferTo(fileImg);
                copyFile(request, userId);
            }

            return "保存成功";

        } catch (IOException e) {
            e.printStackTrace();
            return "保存失败";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "保存失败";
        }
    }

    /**
     * 获取首页图片
     *
     * @param request
     * @param response
     * @return
     */
    private String getHomeImages(HttpServletRequest request,
                                 HttpServletResponse response) {

        String openId = request.getParameter("openId");

        List<Object> hostUser = (List<Object>) userService
                .getHostUserById(openId);

        if (null != hostUser && hostUser.size() > 0) {
            // 已制作自己的小程序
            List<ImageHomeBean> hostUserImgs = mobileService
                    .getHostUserImgs(openId);
            if (null != hostUserImgs && hostUserImgs.size() > 0) {
                return new Gson().toJson(hostUserImgs);
            }
        }

        return null;
    }

    /**
     * 保存首页图片
     *
     * @param request
     * @param response
     * @return
     */
    private String saveHomeImage(HttpServletRequest request,
                                 HttpServletResponse response) {

        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;

        MultipartFile multipartFile = req.getFile("file");

        String userId = request.getParameter("userId");

        String id = request.getParameter("id");

        String title = request.getParameter("title");

        String host = request.getParameter("host");

        String imgPath = System.getProperty("catalina.home") + "/userImg/"
                + userId + "/";

        try {
            File file = new File(imgPath);

            if (!file.exists()) {

                file.mkdirs();
            }

            File fileImg = new File(imgPath, multipartFile
                    .getOriginalFilename());

            ImageHomeBean imageHomeBean = new ImageHomeBean();
            imageHomeBean.setId(id);
            imageHomeBean.setImgUrl(host + "/userImg/" + userId + "/"
                    + multipartFile.getOriginalFilename());
            imageHomeBean.setTitle(title);
            imageHomeBean.setUserId(userId);
            imageHomeBean.setImageName(multipartFile.getOriginalFilename());

            List userImage = mobileService.getUserImageById(id);

            if (null == userImage) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
                imageHomeBean.setCreateTime(df.format(new Date()));
                mobileService.save(imageHomeBean);
            } else {
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
                imageHomeBean.setUpdateTime(df.format(new Date()));
                mobileService.update(imageHomeBean);
            }

            multipartFile.transferTo(fileImg);

            copyFile(request, userId);

            return "保存成功";

        } catch (IOException e) {
            e.printStackTrace();
            return "保存失败";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "保存失败";
        }
    }

    /**
     * 图片文件复制
     *
     * @param request
     * @param response
     * @return
     */
    public String copyFile(HttpServletRequest request,
                           HttpServletResponse response) {

        String openId = request.getParameter("openId");

        String copyNewPath = request.getSession().getServletContext()
                .getRealPath("/")
                + "/userImg/" + openId + "/";
        String dirPath = System.getProperty("catalina.home") + "/userImg/"
                + openId + "/";

        try {
            File file = new File(copyNewPath);
            File dir = new File(dirPath);
            if (!dir.exists()) {
                return "未创建";
            }
            if (!file.exists()) {
                FileUtils.copy(dirPath, copyNewPath);
            } else {
                return "已存在";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "复制失败";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "复制成功";

    }

    /**
     * 图片文件复制
     *
     * @param request
     * @param request
     * @return
     */
    public String copyFile(HttpServletRequest request, String openId) {

        String copyNewPath = request.getSession().getServletContext()
                .getRealPath("/")
                + "/userImg/" + openId + "/";
        String dirPath = System.getProperty("catalina.home") + "/userImg/"
                + openId + "/";

        try {
            FileUtils.copy(dirPath, copyNewPath);
        } catch (IOException e) {
            e.printStackTrace();
            return "复制失败";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "复制成功";

    }

    /**
     * 保存赞
     *
     * @param request
     * @param response
     * @return
     */
    private String savePraise(HttpServletRequest request,
                              HttpServletResponse response) {
        String nickName = request.getParameter("nickName");
        String nickImage = request.getParameter("nickImage");
        String openId = request.getParameter("openId");
        String hostUserId = request.getParameter("hostUserId");
        try {
            List obj = null;
            if (null != openId && !"".equals(openId)) {
                if (null != hostUserId && !"".equals(hostUserId)) {
                    obj = mobileService
                            .getBlessUserByOpenId(openId, hostUserId);
                } else {
                    obj = mobileService.getBlessUserByOpenId(openId);
                }

            } else {
                if (null != nickImage && !"".equals(nickImage)) {
                    obj = mobileService.getBlessUserByNickImage(nickImage);
                } else {
                    return "点赞失败";
                }

            }
            if (null != obj && obj.size() > 0) {
                return "你已经点过赞了";
            }
            BlessUser blessUser = new BlessUser();
            blessUser.setNick_image(nickImage + "");
            blessUser.setNick_name(filter(nickName) + "");
            blessUser.setCreate_time(System.currentTimeMillis() + "");
            blessUser.setId(System.currentTimeMillis() + "");
            blessUser.setOpen_id(openId);
            blessUser.setUser_id(hostUserId);
            mobileService.save(blessUser);
            return "点赞成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "点赞失败";
    }

    /**
     * 保存评论
     *
     * @param request
     * @param response
     * @return
     */
    private String saveComment(HttpServletRequest request,
                               HttpServletResponse response) {

        String nickName = request.getParameter("nickName");
        String nickImage = request.getParameter("nickImage");
        String comment = request.getParameter("comment");
        String time = request.getParameter("time");
        String openId = request.getParameter("openId");
        String hostUserId = request.getParameter("hostUserId");

        try {
            if (null != hostUserId && !"".equals(hostUserId)) {
                BlessComment blessComment = new BlessComment();
                blessComment.setNick_image(nickImage + "");
                blessComment.setNick_name(filter(nickName) + "");
                blessComment.setComment(comment + "");
                blessComment.setOpen_id(openId + "");
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
                blessComment.setCreate_time(df.format(new Date()));
                blessComment.setId(System.currentTimeMillis() + "");
                blessComment.setUser_id(hostUserId);
                mobileService.save(blessComment);
                return "评论成功";
            } else {
                return "评论失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "评论失败";
    }

    /**
     * 获取赞列表
     *
     * @param request
     * @param response
     * @return
     */
    private String getPraiseList(HttpServletRequest request,
                                 HttpServletResponse response) {

        String userId = request.getParameter("userId");
        List object;
        try {
            if (null != userId) {
                object = mobileService.getAllBlessUser(userId);
            } else {
                object = mobileService.getAllBlessUser();
            }
            return new Gson().toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 获取评论列表
     *
     * @param request
     * @param response
     * @return
     */
    private String getCommentList(HttpServletRequest request,
                                  HttpServletResponse response) {

        String userId = request.getParameter("userId");
        List object;
        try {
            if (null != userId) {
                object = mobileService.getAllBlessComment(userId);
            } else {
                object = mobileService.getAllBlessComment();
            }
            return new Gson().toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取图片
     *
     * @param request
     * @param response
     * @return
     */
    public String getImages(HttpServletRequest request,
                            HttpServletResponse response) {

        String type = request.getParameter("homeType");// banner detail
        String moduleId = request.getParameter("moduleId");

        List<ImageList> imageList = new ArrayList<ImageList>();
        String baseUrl = "";
        try {

            String path = "";
            if ("banner".equals(type)) {
                path = request.getSession().getServletContext().getRealPath(
                        "/wechat")
                        + "/marry/banner/";
                baseUrl = "https://pengmaster.com/party/wechat/marry/banner/";
            } else {
                path = request.getSession().getServletContext().getRealPath(
                        "/wechat")
                        + "/marry/" + moduleId + "/";
                baseUrl = "https://pengmaster.com/party/wechat" + "/marry/"
                        + moduleId + "/";
            }
            System.out.println("path:" + path);
            File file = new File(path);
            File[] tempList = file.listFiles();
            if (null == tempList) {
                return "数据为空";
            }
            for (int i = 0; i < tempList.length; i++) {
                String name = tempList[i].getName();
                String orientation = "";
                File picture = new File(path + name);
                BufferedImage sourceImg = ImageIO.read(new FileInputStream(
                        picture));
                if (sourceImg.getWidth() > sourceImg.getHeight()) {
                    orientation = "horizontal";
                } else {
                    orientation = "vertical";
                }
                String nameType = "";
                if (null != name) {
                    nameType = name.substring(0, name.indexOf("."));
                }
                imageList.add(new ImageList(StringUtils.generateRefID(),
                        baseUrl + name, nameType, orientation));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Gson().toJson(imageList);
    }

    /**
     * 用户注册
     *
     * @return
     * @author wp
     */
    public String saveUser(HttpServletRequest request,
                           HttpServletResponse response) {

        String openId = request.getParameter("openId");
        String userInfos = request.getParameter("userInfo");
        Gson gson = new Gson();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化到秒
        try {
            // 用户操作记录
            UserRecord userRecord = new UserRecord();
            userRecord.setId(getId());
            userRecord.setOpenId(openId);
            UserInfo userInfo = gson.fromJson(userInfos, UserInfo.class);
            userRecord.setAvatarUrl(userInfo.getAvatarUrl() + "");
            userRecord.setCity(userInfo.getCity() + "");
            userRecord.setNickName(filter(userInfo.getNickName()) + "");
            userRecord.setProvince(userInfo.getProvince() + "");
            userRecord.setCreateTime(formatter.format(new Date()));
            userService.saveUserRecord(userRecord);

            if (null != openId && !"".equals(openId)) {
                Object userOlderList = userService.getUserById(openId);
                if (null != userOlderList) {
                    List<Object> list = (List<Object>) userOlderList;
                    if (list.size() > 0 && null != list.get(0)
                            && list.get(0) instanceof Object[]) {
                        Object[] listResult = (Object[]) list.get(0);
                        User user = new User();
                        user.setId(listResult[0] + "");
                        user.setOpenId(listResult[1] + "");
                        user.setAvatarUrl(listResult[2] + "");
                        user.setCity(listResult[3] + "");
                        user.setNickName(listResult[4] + "");
                        user.setProvince(listResult[5] + "");
                        user.setCreateTime(listResult[6] + "");
                        user.setUpdateTime(formatter.format(new Date()));
                        userService.updateUser(user);
                        System.out
                                .println("--------------update_user_success------------");
                        return "更新成功";
                    } else {
                        User user = new User();
                        user.setId(getId());
                        user.setOpenId(openId);
                        user.setAvatarUrl(userInfo.getAvatarUrl());
                        user.setCity(userInfo.getCity());
                        user.setNickName(filter(userInfo.getNickName()));
                        user.setProvince(userInfo.getProvince());
                        user.setCreateTime(formatter.format(new Date()));
                        userService.saveUser(user);
                        System.out
                                .println("--------------save_user_success------------");
                        return "保存成功";
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "保存失败";
        }
        return "保存成功";
    }

    /**
     * 保存创建自己小程序的用户
     *
     * @param request
     * @param response
     * @return
     */
    public String saveHostUser(HttpServletRequest request,
                               HttpServletResponse response) {

        String openId = request.getParameter("openId");
        String userInfos = request.getParameter("userInfo");
        String isOriginal = request.getParameter("isOriginal");
        String userPhone = request.getParameter("userPhone");
        String userWechat = request.getParameter("userWechat");
        Gson gson = new Gson();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化到秒
        try {

            if (null != openId && !"".equals(openId)) {
                Object userOlderList = userService.getHostUserById(openId);
                if (null != userOlderList) {
                    List<Object> list = (List<Object>) userOlderList;
                    if (list.size() > 0 && null != list.get(0)
                            && list.get(0) instanceof Object[]) {
                        Object[] listResult = (Object[]) list.get(0);
                        HostUser user = new HostUser();
                        user.setId(listResult[0] + "");
                        user.setOpenId(listResult[1] + "");
                        user.setAvatarUrl(listResult[2] + "");
                        user.setCity(listResult[3] + "");
                        user.setNickName(listResult[4] + "");
                        user.setProvince(listResult[5] + "");
                        user.setCreateTime(listResult[6] + "");
                        user.setUserPhone(listResult[7] + "");
                        user.setUserWechat(listResult[8] + "");
                        user.setUpdateTime(formatter.format(new Date()));
                        user.setIsOriginal(isOriginal);
                        userService.updateUser(user);
                        System.out
                                .println("--------------update_user_success------------");
                        return "更新成功";
                    } else {
                        UserInfo userInfo = gson.fromJson(userInfos,
                                UserInfo.class);
                        HostUser user = new HostUser();
                        user.setId(getId());
                        user.setOpenId(openId);
                        user.setAvatarUrl(userInfo.getAvatarUrl());
                        user.setCity(userInfo.getCity());
                        user.setNickName(filter(userInfo.getNickName()));
                        user.setProvince(userInfo.getProvince());
                        user.setCreateTime(formatter.format(new Date()));
                        user.setUserPhone(userPhone);
                        user.setUserWechat(userWechat);
                        user.setIsOriginal(isOriginal);
                        userService.saveUser(user);
                        System.out
                                .println("--------------save_user_success------------");
                        return "保存成功";
                    }
                }
            } else {
                return "请重新授权小程序";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "保存失败";
        }
        return "保存成功";
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @return
     */
    public String loginin(HttpServletRequest request,
                          HttpServletResponse response) {
        String openId = request.getParameter("openId");
        User user = userService.getUserByOpenId(openId);
        JSONObject JsonObject = new JSONObject();
        if (ValidUtil.isNoEmpty(user)) {
            JsonObject.put("result", "该用户已关联！");
            JsonObject.put("success", "200");
            JsonObject.put("user", user);
        } else {
            JsonObject.put("result", "请关联用户！");
            JsonObject.put("success", "202");
        }
        return JsonObject.toString();
    }

    /**
     * 解绑微信
     *
     * @param request
     * @param response
     * @return
     */
    public String unbundling(HttpServletRequest request,
                             HttpServletResponse response) {
        String idCard = request.getParameter("idCard");
        JSONObject JsonObject = new JSONObject();
        User user = userService.getUserByuserIdCard(idCard);
        if (ValidUtil.isNoEmpty(user)) {
            if (ValidUtil.isNoEmpty(user.getOpenId())) {
                user.setOpenId("");
                userService.updateUser(user);
                JsonObject.put("result", "解绑成功！");
                JsonObject.put("success", "300");
            } else {
                JsonObject.put("result", "请先关联用户！");
                JsonObject.put("success", "301");
            }
        } else {
            JsonObject.put("result", "身份证号码输入错误！");
            JsonObject.put("success", "302");
        }
        return JsonObject.toString();
    }

    /**
     * 获取openid
     *
     * @param request
     * @param response
     * @return
     */
    protected String getOpenID(HttpServletRequest request,
                               HttpServletResponse response) {
        JSONObject JsonObject = new JSONObject();
        try {
            String code = request.getParameter("code");
            String appid = request.getParameter("appid");
            String secret = request.getParameter("secret");
            String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="
                    + appid
                    + "&secret="
                    + secret
                    + "&js_code="
                    + code
                    + "&grant_type=authorization_code";
            // 第一次请求 获取access_token 和 openid
            String oppid;
            oppid = new HttpRequestor().doGet(requestUrl);
            JSONObject oppidObj = JSONObject.fromObject(oppid);
            // String access_token = (String) oppidObj.get("access_token");
            String openid = (String) oppidObj.get("openid");
            if (openid != null && !"".equals(openid)) {
                // User user = userService.getUserByOpenId(openid);
                // if (user == null) {
                // JsonObject.put("flag", false);
                // JsonObject.put("openid", openid);
                // } else {
                // JsonObject.put("flag", true);
                // JsonObject.put("openid", openid);
                // JsonObject.put("user", user);
                // }
                JsonObject.put("flag", true);
                JsonObject.put("openid", openid);
            } else {
                JsonObject.put("flag", false);
                JsonObject.put("openid", "");
                JsonObject.put("message", "获取openID失败！请重试！");
            }
            return JsonObject.toString();
        } catch (Exception e) {
            JsonObject.put("flag", false);
            JsonObject.put("message", "服务器异常");
            e.printStackTrace();
            return JsonObject.toString();
        }
    }

    /**
     * @描述 java生成流水号 14位时间戳 + 6位随机数
     * @作者 shaomy
     * @时间:2017-1-12 上午10:10:41
     * @参数:@return
     * @返回值：String
     */
    public static String getId() {
        String id = "";
        // 获取当前时间戳
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        // 获取6位随机数
        int random = (int) ((Math.random() + 1) * 100000);
        id = temp + random;
        return id;
    }

    public String filter(String content) {
        byte[] conbyte = content.getBytes();
        for (int i = 0; i < conbyte.length; i++) {
            if ((conbyte[i] & 0xF8) == 0xF0) {
                for (int j = 0; j < 4; j++) {
                    conbyte[i + j] = 0x30;
                }
                i += 3;
            }
        }
        content = new String(conbyte);
        return content.replaceAll("0000", "");
    }
}
