package com.lokia.mybatis.annotation;

import com.lokia.mybatis.annotation.mapper.BuildingMapper;
import com.lokia.mybatis.bean.Building;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.Properties;

/**
 * @author gushu
 * @data 2018/8/17
 */
public class AnnotationTest {

    public static void main(String[] args) {

        String fileLocation = "mybatis-config.xml";
        SqlSessionFactory sessionFactory = getSqlSessionFactory(fileLocation);
//        SqlSession sqlSession = sessionFactory.openSession();
//        try {
//            BuildingMapper buildingMapper = sqlSession.getMapper(BuildingMapper.class);
//            Building building = buildingMapper.getById("BD15101103674482");
//            System.out.println(building);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            // release resource.
//            sqlSession.close();
//        }


        try(SqlSession sqlSession = sessionFactory.openSession()) {
            BuildingMapper buildingMapper = sqlSession.getMapper(BuildingMapper.class);
            Building building = buildingMapper.getById("BD15101103674482");
            System.out.println(building);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SqlSessionFactory getSqlSessionFactory(String fileLocation) {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sessionFactory = null;
        try {
            sessionFactory = builder.build(Resources.getResourceAsReader(fileLocation));
            loadJdbcExtraConfig4DataSource(sessionFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    /**
     * 加载JDBC driver的一些配置，这个配置在 mybatis-config.xml中无法设置. 因为 driverProperties 的值是一个Property, 不能通过以下方式进行设置，只能通过code
     *
     * <pre>{@code
     * <property name="driverProperties" value="e"></property>
     * }</pre>
     * @param sessionFactory
     */
    private static void loadJdbcExtraConfig4DataSource(SqlSessionFactory sessionFactory) {
        Configuration configuration = sessionFactory.getConfiguration();
        Environment environment = configuration.getEnvironment();
        PooledDataSource dataSource = (PooledDataSource) environment.getDataSource();

        try {
            Properties driverProps = Resources.getResourceAsProperties("jdbc-datasource-extra.properties");
            dataSource.setDriverProperties(driverProps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
