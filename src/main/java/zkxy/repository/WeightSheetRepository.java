package zkxy.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zkxy.model.WeightSheet;
import zkxy.vo.WeightSheetStatisticsVO;

import java.util.List;

public interface WeightSheetRepository {

    //    @Select("SELECT  a.* ,p_type FROM zkxy_weight_sheets a ,zkxy_products b WHERE gong_id=#{gongId} "
//            + "and a.product_id=b.ID AND huibang_time>=#{startTime} AND huibang_time<#{endTime}")
//    List<WeightSheet> findAllByGongIdDateRange(@Param("gongId") int gongId, @Param("startTime") String startTime, @Param("endTime") String endTime);
//
    @Select("SELECT  a.* ,p_type FROM zkxy_weight_sheets a ,zkxy_products b WHERE  "
            + " a.product_id=b.ID AND huibang_time>=#{startTime} AND huibang_time<#{endTime}")
    List<WeightSheet> findAllDateRange(@Param("startTime") String startTime, @Param("endTime") String endTime);

    //
//
//    @Select("SELECT  a.* ,p_type FROM zkxy_weight_sheets a ,zkxy_products b WHERE a.product_id=b.ID AND  a.product_id =#{pds_id}   "
//            + " AND gong_id=#{gongId} and  huibang_time>=#{startTime} AND huibang_time<#{endTime}")
//    List<WeightSheet> findAllByGongIdDateRangeWithPds(@Param("gongId") int gongId, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("pds_id") int pds_id);
//
    @Select("SELECT  a.* ,p_type FROM zkxy_weight_sheets a ,zkxy_products b WHERE a.product_id=b.ID AND  a.product_id =#{pds_id} "
            + " and  huibang_time>=#{startTime} AND huibang_time<#{endTime}")
    List<WeightSheet> findAllDateRangeWithPds(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("pds_id") int pds_id);

    //
////	@Select("SELECT  a.* ,SUM(jing_zhong) AS fhl,b.p_type, COUNT(a.id) AS ccl FROM zkxy_weight_sheets a,zkxy_products b  "
////			+ "WHERE huibang_time>=#{startTime} AND huibang_time<#{endTime} AND a.product_id=b.id GROUP BY a.product_id ")
////	List<WeightSheetStatisticsVO> statisticsAllByDateRange(@Param("startTime") String startTime, @Param("endTime") String endTime);
////
//
//    @Select("SELECT a.product_id, SUM(jing_zhong) AS fhl,b.p_type, COUNT(a.id) AS ccl FROM zkxy_weight_sheets a,zkxy_products b  "
//            + "WHERE gong_id=#{gongId} and huibang_time>=#{startTime} AND huibang_time<#{endTime}" +
//            " AND a.product_id=b.id  GROUP BY a.product_id,b.p_type ")
//    List<WeightSheetStatisticsVO> statisticsAllByGongIdDateRange(@Param("gongId") int gongId, @Param("startTime") String startTime,
//                                                                 @Param("endTime") String endTime);
//
    @Select("SELECT a.product_id, SUM(jing_zhong) AS fhl,b.p_type, COUNT(a.id) AS ccl FROM zkxy_weight_sheets a,zkxy_products b  "
            + "WHERE huibang_time>=#{startTime} AND huibang_time<#{endTime} and jing_zhong!=0 " +
            " AND a.product_id=b.id  GROUP BY a.product_id,b.p_type ")
    List<WeightSheetStatisticsVO> statisticsAllDateRange(@Param("startTime") String startTime,
                                                         @Param("endTime") String endTime);

    //
//    @Select("SELECT a.product_id, SUM(jing_zhong) AS fhl,b.p_type, COUNT(a.id) AS ccl FROM zkxy_weight_sheets a,zkxy_products b  "
//            + "WHERE gong_id=#{gongId} and huibang_time>=#{startTime} AND huibang_time<#{endTime}" +
//            " AND a.product_id=b.id and a.gou_id =#{gou_id} GROUP BY a.product_id,b.p_type ")
//    List<WeightSheetStatisticsVO> statisticsAllByGongIdDateRangeWithGouId(@Param("gongId") int gongId, @Param("startTime") String startTime,
//                                                                          @Param("endTime") String endTime, @Param("gou_id") int gou_id);
//
    @Select("SELECT a.product_id, SUM(jing_zhong) AS fhl,b.p_type, COUNT(a.id) AS ccl FROM zkxy_weight_sheets a,zkxy_products b  "
            + "WHERE huibang_time>=#{startTime} AND huibang_time<#{endTime} and jing_zhong!=0 " +
            " AND a.product_id=b.id and a.gou_id =#{gou_id} GROUP BY a.product_id,b.p_type ")
    List<WeightSheetStatisticsVO> statisticsAllDateRangeWithGouId(@Param("startTime") String startTime,
                                                                  @Param("endTime") String endTime, @Param("gou_id") int gou_id);

    //
//
//    @Select("SELECT  a.* ,SUM(jing_zhong) AS fhl,b.p_type, COUNT(a.id) AS ccl FROM zkxy_weight_sheets a,zkxy_products b  "
//            + "WHERE gong_id=#{gongId} and huibang_time>=#{startTime} AND huibang_time<#{endTime} AND a.product_id=b.id AND  "
//            + " a.product_id =#{pds_id}   GROUP BY a.product_id ")
//    List<WeightSheetStatisticsVO> statisticsAllByGongIdDateRangeWithPdsWithPds(@Param("gongId") int gongId, @Param("startTime") String startTime,
//                                                                               @Param("endTime") String endTime, @Param("pds_id") int pds_id);
//
//
    @Select("SELECT  a.*,b.p_type  FROM zkxy_weight_sheets a ,zkxy_products b WHERE a.product_id=b.ID AND "
            + " gou_id=#{gouId} AND huibang_time>=#{startTime} AND huibang_time<#{endTime}")
    List<WeightSheet> findAllByGouIDAndDateRange(@Param("gouId") int gouId, @Param("startTime") String startTime,
                                                 @Param("endTime") String endTime);

    //
//
    @Select("SELECT  a.*,b.p_type  FROM zkxy_weight_sheets a ,zkxy_products b WHERE a.product_id=b.ID AND a.product_id =#{pds_id}  AND "
            + " gou_id=#{gouId} AND huibang_time>=#{startTime} AND huibang_time<#{endTime}")
    List<WeightSheet> findAllByGouIDAndDateRangeWithPds(@Param("gouId") int gouId, @Param("startTime") String startTime,
                                                        @Param("endTime") String endTime, @Param("pds_id") int pds_id);

    //
//
    @Select("SELECT  a.product_id, SUM(jing_zhong) AS fhl,b.p_type, COUNT(a.id) AS ccl FROM zkxy_weight_sheets a,zkxy_products b "
            + " WHERE gou_id=#{gouId} AND huibang_time>=#{startTime} AND huibang_time<#{endTime} and jing_zhong!=0  AND " +
            " a.product_id=b.id  GROUP BY a.product_id,b.p_type")
    List<WeightSheetStatisticsVO> statisticsByGouIDAndDateRange(@Param("gouId") int gouId, @Param("startTime") String startTime,
                                                                @Param("endTime") String endTime);

    //
//    @Select("SELECT  a.product_id, SUM(jing_zhong) AS fhl,b.p_type, COUNT(a.id) AS ccl FROM zkxy_weight_sheets a,zkxy_products b "
//            + " WHERE gou_id=#{gouId} AND huibang_time>=#{startTime} AND huibang_time<#{endTime} AND" +
//            " a.product_id=b.id  and  a.product_id =#{pds_id} GROUP BY a.product_id,b.p_type")
//    List<WeightSheetStatisticsVO> statisticsByGouIDAndDateRangeWithPdt(@Param("gouId") int gouId, @Param("startTime") String startTime,
//                                                                       @Param("endTime") String endTime, @Param("pds_id") int pds_id);
//
//
//    @Select("SELECT  a.* ,SUM(jing_zhong) AS fhl,b.p_type, COUNT(a.id) AS ccl FROM zkxy_weight_sheets a,zkxy_products b "
//            + " WHERE gou_id=#{gouId} AND huibang_time>=#{startTime} AND huibang_time<#{endTime} AND a.product_id=b.id AND "
//            + "  a.product_id =#{pds_id}  AND  a.product_id =#{pds_id} GROUP BY a.product_id")
//    List<WeightSheetStatisticsVO> statisticsByGouIDAndDateRangeWithPds(@Param("gouId") int gouId, @Param("startTime") String startTime,
//                                                                       @Param("endTime") String endTime, @Param("pds_id") int pds_id);
//
//
    @Select("<script>"
            + "SELECT  a.*,b.p_type  FROM zkxy_weight_sheets a,zkxy_products b "
            + "WHERE huibang_time &lt; #{endTime} AND huibang_time &gt;=#{startTime} AND a.product_id=b.ID AND  a.car_id in "
            + "<foreach item='item' index='index' collection='carNos' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach> "
            + "</script>")
    List<WeightSheet> findAllByCarNOsAndDateRange(@Param("carNos") List<String> carNos,
                                                  @Param("startTime") String startTime, @Param("endTime") String endTime);

    //
    @Select("<script>"
            + "SELECT  a.*,b.p_type  FROM zkxy_weight_sheets a,zkxy_products b "
            + "WHERE huibang_time &lt; #{endTime} AND huibang_time &gt;=#{startTime} AND a.product_id=b.ID "
            + "AND a.product_id =#{pds_id} AND a.car_id in "
            + "<foreach item='item' index='index' collection='carNos' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach> "
            + "</script>")
    List<WeightSheet> findAllByCarNOsAndDateRangeWithPds(@Param("carNos") List<String> carNos,
                                                         @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("pds_id") int pds_id);

    //
//
    @Select("<script>"
            + "SELECT a.product_id, b.p_type,SUM(jing_zhong) AS fhl,COUNT(a.id) AS ccl  FROM zkxy_weight_sheets a,zkxy_products b "
            + "WHERE huibang_time &lt; #{endTime} AND huibang_time &gt;=#{startTime} AND a.product_id=b.ID and" +
            " a.product_id=#{pdtId} AND  a.car_id in "
            + "<foreach item='item' index='index' collection='carNos' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>   GROUP BY a.product_id, b.p_type"
            + "</script>")
    List<WeightSheetStatisticsVO> statisticsByCarNOsAndDateRangeWithPdt(@Param("carNos") List<String> carNos,
                                                                        @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("pdtId") int pdtId);

    //
    @Select("<script>"
            + "SELECT a.product_id, b.p_type,SUM(jing_zhong) AS fhl,COUNT(a.id) AS ccl  FROM zkxy_weight_sheets a,zkxy_products b "
            + "WHERE huibang_time &lt; #{endTime} AND huibang_time &gt;=#{startTime} AND a.product_id=b.ID and jing_zhong!=0 and " +
            "  a.car_id in "
            + "<foreach item='item' index='index' collection='carNos' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>   GROUP BY a.product_id, b.p_type"
            + "</script>")
    List<WeightSheetStatisticsVO> statisticsByCarNOsAndDateRange(@Param("carNos") List<String> carNos,
                                                                 @Param("startTime") String startTime, @Param("endTime") String endTime);
//
//
//    @Select("<script>"
//            + "SELECT  a.*,b.p_type,SUM(jing_zhong) AS fhl,COUNT(a.id) AS ccl  FROM zkxy_weight_sheets a,zkxy_products b "
//            + "WHERE huibang_time &lt; #{endTime} AND huibang_time &gt;=#{startTime} AND a.product_id=b.ID AND  a.product_id =#{pds_id} AND  a.car_id in "
//            + "<foreach item='item' index='index' collection='carNos' open='(' separator=',' close=')'>"
//            + "#{item}"
//            + "</foreach>   GROUP BY a.product_id"
//            + "</script>")
//    List<WeightSheetStatisticsVO> statisticsByCarNOsAndDateRangeWithPds(@Param("carNos") List<String> carNos,
//                                                                        @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("pds_id") int pds_id);
}
