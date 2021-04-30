package com.bjpowernode.crm.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
//user工具类 负责创建SqlSession
public class sqlsessionUtil {

    private static InputStream inputStream;
    private static SqlSessionFactory sqlSessionFactory = null;
    static {
        //mybatis主配置文件路径
        String config = "mybatis-config.xml";
        try {
            inputStream = Resources.getResourceAsStream(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    //创建线程安全池
    private static ThreadLocal<SqlSession> t = new ThreadLocal<SqlSession>();
    public static SqlSession getSqlSession(){
        //获取
        SqlSession session = t.get();
        //判断是否为null
        if (session == null) {
            //开启
            session = sqlSessionFactory.openSession();
            //加入
            t.set(session);
        }
        return session;
    }

    public static void sqlClose(SqlSession sqlSession){
        if (sqlSession != null) {
            //关闭SqlSession
            sqlSession.close();
            //清除线程池
            t.remove();
        }

    }
}