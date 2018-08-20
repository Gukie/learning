package com.lokia.mybatis.annotation.main;

import com.lokia.mybatis.annotation.mapper.BuildingMapper;
import com.lokia.mybatis.bean.Building;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author gushu
 * @data 2018/8/20
 */
public class SecondCacheTest extends  AnnotationTest{
    public static void main(String[] args) {
        String fileLocation = "mybatis-config.xml";
        SqlSessionFactory sessionFactory = getSqlSessionFactory(fileLocation);
//        SqlSessionManager sqlSessionManager = SqlSessionManager.newInstance(sessionFactory);
        testCache4SecondLevel(sessionFactory);
    }

    private static void testCache4SecondLevel(SqlSessionFactory sessionFactory) {
        SqlSession session1 = sessionFactory.openSession(true);
        SqlSession session2 = sessionFactory.openSession(true);

//        SqlSessionManager

        BuildingMapper mapper1 = session1.getMapper(BuildingMapper.class);
        BuildingMapper mapper2 = session2.getMapper(BuildingMapper.class);

        String id = "BD15101103674482";
        String buildingName1;
        String buildingName2;

        Building building1 = mapper1.getById4SecondCache(id);
        buildingName1 = building1.getName();

        session1.commit();// 如果不提交，当前session所产生的二级缓存，在其他session中是不可见的

        Building building2 = mapper2.getById4SecondCache(id);
        buildingName2 = building2.getName();

        // test whether buildingName1 is equals to buildingName2
        log.error("Whether buildingName1 is equals to buildingName2 after session2 updating: {},buildingName1:{},buildingName2:{}", new Object[]{Objects.equals(buildingName1, buildingName2), buildingName1, buildingName2});


//
        // commit transaction if needed.
        doCommitIfNeeded(session1);
        doCommitIfNeeded(session2);
    }

    /**
     * 默认的SqlSession是autoCommit=false，意味着一个SqlSession就一个事务，这就需要显式的进行commit，否则在这个SqlSession做的事情，将不会对数据库有任何影响
     * @param session
     */
    private static void doCommitIfNeeded(SqlSession session) {
        try {
            Connection connection = session.getConnection();
            if (connection != null && !connection.getAutoCommit()) {
                connection.commit();
            }
        } catch (SQLException e) {

        }
    }
}
