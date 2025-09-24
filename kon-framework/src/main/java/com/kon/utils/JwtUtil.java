package com.kon.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 * 修复了HS256算法与密钥不兼容的问题
 *
 * @author 35238
 * @date 2023/7/22 0022 21:18
 */
public class JwtUtil {

    // 有效期为72小时
    public static final Long JWT_TTL = 72 * 60 * 60 * 1000L;

    // 注意：HS256算法要求密钥长度至少为256位(32个字符)
    // 建议从配置文件读取，不要硬编码在代码中
    public static final String JWT_KEY = "yourSecretKeyMustBeAtLeast32CharactersLongForHS256";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成jtw
     *
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    /**
     * 生成jtw
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成符合HS256算法要求的密钥
        SecretKey secretKey = generalKey();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setSubject(subject)      // 主题 可以是JSON数据
                .setIssuer("Yui")         // 签发者
                .setIssuedAt(now)         // 签发时间
                // 使用正确的密钥和算法进行签名
                .signWith(secretKey, signatureAlgorithm)
                .setExpiration(expDate);
    }

    /**
     * 创建token
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjYWM2ZDVhZi1mNjVlLTQ0MDAtYjcxMi0zYWEwOGIyOTIwYjQiLCJzdWIiOiJzZyIsImlzcyI6InNnIiwiaWF0IjoxNjM4MTA2NzEyLCJleHAiOjE2MzgxMTAzMTJ9.JVsSbkP94wuczb4QryQbAke3ysBDIL5ou8fWsbt_ebg";
        Claims claims = parseJWT(token);
        System.out.println(claims);
    }

    /**
     * 生成加密后的秘钥 secretKey
     * 修复：使用HmacSHA256算法而不是AES
     *
     * @return
     */
    public static SecretKey generalKey() {
        // 1. 验证密钥长度，HS256需要至少32个字符
        if (JWT_KEY.length() < 32) {
            throw new IllegalArgumentException("Secret key must be at least 32 characters long for HS256 algorithm");
        }

        // 2. 使用UTF-8编码确保一致性
        byte[] keyBytes = JWT_KEY.getBytes(StandardCharsets.UTF_8);

        // 3. 生成符合HS256要求的HmacSHA256密钥
        // 推荐使用Keys.hmacShaKeyFor()方法，它会自动处理算法匹配
        return Keys.hmacShaKeyFor(keyBytes);

        // 或者使用传统方式，但需要明确指定HmacSHA256算法
        // return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * 解析JWT
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
