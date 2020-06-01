package com.imengyu.datacenter.utils.auth;


import com.imengyu.datacenter.exception.BadTokenException;
import com.imengyu.datacenter.exception.InvalidArgumentException;
import com.imengyu.datacenter.utils.StringUtils;
import com.imengyu.datacenter.utils.encryption.AESUtils;


import java.util.Calendar;
import java.util.Date;

/**
 * 权限令牌认证工具
 */
public class TokenAuthUtils {

    /**
     * 令牌密钥，务必请修改为自己的令牌
     */
    public static final String TOKEN_DEFAULT_KEY = "FishBlogDefaultTokenKey";

    public static final Integer TOKEN_CHECK_UNKNOW = 0;
    public static final Integer TOKEN_CHECK_OK = 1;
    public static final Integer TOKEN_CHECK_BAD_TOKEN = 2;
    public static final Integer TOKEN_CHECK_EXPIRED = 3;

    /**
     * 生成 权限令牌
     * @param expireTime 过期时间（秒）
     * @param tokenData 令牌内容
     * @return
     * @throws InvalidArgumentException
     */
    public static String genToken(Integer expireTime, String tokenData) throws InvalidArgumentException { return genToken(expireTime, tokenData, "s", TOKEN_DEFAULT_KEY); }

    /**
     * 生成 权限令牌
     * @param expireTime 过期时间，单位使用 tokenExpireTimeUnit 定义的
     * @param tokenData 令牌内容
     * @param tokenExpireTimeUnit 过期时间单位，（s/m/h/d）
     * @param tokenKey Token 解密钥匙
     * @return 权限令牌
     * @throws InvalidArgumentException 参数有误异常
     */
    public static String genToken(Integer expireTime, String tokenData, String tokenExpireTimeUnit, String tokenKey)  throws InvalidArgumentException{
        if(StringUtils.isEmpty(tokenKey))
            throw new InvalidArgumentException("Bad token Key");
        if(expireTime<0)
            throw new InvalidArgumentException("Expire time must greater tan zero");

        //Calc now date time and expire date time
        Date nowDate = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(nowDate);
        switch(tokenExpireTimeUnit)
        {
            case "s": rightNow.add(Calendar.SECOND, expireTime); break;
            case "m": rightNow.add(Calendar.MINUTE, expireTime); break;
            case "h": rightNow.add(Calendar.HOUR, expireTime); break;
            case "d": rightNow.add(Calendar.DAY_OF_MONTH, expireTime); break;
        }
        Date dateExpire=rightNow.getTime();


        String orgTokenData = String.valueOf(nowDate.getTime()) + "=" + String.valueOf(dateExpire.getTime()) + "=" + tokenData;
        return AESUtils.encrypt(orgTokenData, tokenKey);
    }

    /**
     * 解密 权限令牌
     * @param token 权限令牌
     * @param tokenKey 生成权限令牌时提供的密钥
     * @return 权限令牌
     * @throws InvalidArgumentException 参数有误异常
     */
    public static String[] decodeToken(String token, String tokenKey) throws InvalidArgumentException, BadTokenException {
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(tokenKey))
            throw new InvalidArgumentException("Bad tokenKey");
        if(token.contains(" "))
            token=token.replace(' ', '+');
        String orgTokenData = AESUtils.decrypt(token, tokenKey);
        if(orgTokenData == null)
            throw new BadTokenException("无法解码TOKEN");
        return orgTokenData.split("=");
    }

    /**
     * 解密 权限令牌 并自动分割返回内容
     * @param token 权限令牌
     * @param tokenKey 生成权限令牌时提供的密钥
     * @param divider 内容分割字符
     * @return 返回已分割的权限令牌内容
     * @throws InvalidArgumentException 参数有误异常
     * @throws BadTokenException 权限令牌有误异常
     */
    public static String[] decodeTokenAndGetData(String token, String tokenKey, String divider) throws InvalidArgumentException, BadTokenException {
        String[] rs;
        try {
            rs = decodeToken(token, tokenKey);
            if(rs.length<3) throw new BadTokenException("TOKEN长度有误");
            return rs[2].split(divider);
        } catch (BadTokenException e){
            throw e;
        }
    }

    /**
     * 检查 权限令牌 有效性
     * @param token 权限令牌
     * @param tokenKey 生成权限令牌时提供的密钥
     * @return 权限令牌
     * @throws InvalidArgumentException 参数有误异常
     */
    public static Integer checkToken(String token, String tokenKey) throws InvalidArgumentException, BadTokenException {
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(tokenKey))
            throw new InvalidArgumentException("Bad tokenKey");
        String orgTokenData = AESUtils.decrypt(token, tokenKey);
        if(orgTokenData == null)
            throw new BadTokenException("无法解码TOKEN");
        String[] orgTokenDataArr =  orgTokenData.split("=");
        if(StringUtils.isEmpty(orgTokenData))
            throw new InvalidArgumentException("Bad tokenKey");
        if(orgTokenDataArr.length < 3) {
            return TOKEN_CHECK_BAD_TOKEN;
        }
        try {
            long expTime = Long.parseLong(orgTokenDataArr[1]);
            if (new Date().getTime() > expTime) return TOKEN_CHECK_EXPIRED;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return TOKEN_CHECK_BAD_TOKEN;
        }
        return TOKEN_CHECK_OK;
    }

    /**
     * 本地异常日志记录对象
     */
    //private static  final Logger logger = LoggerFactory.getLogger(FishblogApplication.class);

}
