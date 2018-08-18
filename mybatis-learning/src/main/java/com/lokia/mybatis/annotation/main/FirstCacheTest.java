package com.lokia.mybatis.annotation.main;

import com.lokia.mybatis.annotation.mapper.BuildingMapper;
import com.lokia.mybatis.bean.Building;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author gushu
 * @data 2018/8/18
 */
public class FirstCacheTest extends AnnotationTest{

    public static void main(String[] args) {
        String fileLocation = "mybatis-config.xml";
        SqlSessionFactory sessionFactory = getSqlSessionFactory(fileLocation);
        testCache4FirstLevel(sessionFactory, true);
    }
    /**
     * 一级缓存只在SqlSession内存活，不同SqlSession所使用的一级缓存是不同的，即他们是互相独立的
     *
     * @param sessionFactory
     * @param executable
     */
    private static void testCache4FirstLevel(SqlSessionFactory sessionFactory, boolean executable) {
        if (!executable) {
            return;
        }
        SqlSession session1 = sessionFactory.openSession(false);
        SqlSession session2 = sessionFactory.openSession(false);

        String id = "BD15101103674482";
        String originalBuildingName;
        String buildingName1;
        String buildingName2;

        BuildingMapper mapper1 = session1.getMapper(BuildingMapper.class);
        Building building1 = mapper1.getById4FirstCache(id);
        originalBuildingName = building1.getName();
        buildingName1 = building1.getName();

        BuildingMapper mapper2 = session2.getMapper(BuildingMapper.class);
        Building building2 = mapper2.getById4FirstCache(id);
        buildingName2 = building2.getName();

        // test whether buildingName1 is equals to buildingName2
        log.error("Whether buildingName1 is equals to buildingName2: {}", Objects.equals(buildingName1, buildingName2));

        // update
        log.error("update buildingName in session2");
        building2.setName(building2.getName() + "-" + System.currentTimeMillis());
        mapper2.updateName(building2);

        // read after updating
        building2 = mapper2.getById(id); // 会从数据库中重新读取数据，从log中的SQL可以看出; 因为缓存随着update语句的执行已经失效了
        buildingName2 = building2.getName();

        building1 = mapper1.getById(id); // 会从当前SqlSession中的缓存中读取数据，因为它还没有失效
        buildingName1 = building1.getName();
        log.error("Whether buildingName1 is equals to buildingName2 after session2 updating: {},buildingName1:{},buildingName2:{}", new Object[]{Objects.equals(buildingName1, buildingName2), buildingName1, buildingName2});
        // reset data
        int index = originalBuildingName.indexOf("-");
        if (index != -1) {
            String tmpName = originalBuildingName.substring(0, index);
            building1.setName(tmpName);
            mapper1.updateName(building1);
            log.debug("reset successfully");
        }


        // commit transaction if needed.
        doCommitIfNeeded(session1);


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
