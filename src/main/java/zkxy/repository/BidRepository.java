package zkxy.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import zkxy.model.Bid;
import zkxy.model.BidDetail;
import zkxy.model.BidPds;

public interface BidRepository {

	@Insert("insert into zkxy_bid (bid_name ,bid_status,bid_time,remark) "
			+ "values (#{bid_name},#{bid_status},#{bid_time},#{remark})")
	@SelectKey(statement = "select @@identity", keyProperty = "ID", before = false, resultType = int.class)
	void AddBidInfo(Bid bid);

	@Update("update  zkxy_bid set bid_name=#{bid.bid_name} ,bid_status=#{bid.bid_status},"
			+ "bid_time=#{bid.bid_time},remark=#{bid.remark} where ID=#{bid.ID}")
	void UpdateBid(@Param("bid") Bid bid);

	@Update("update  zkxy_bid set deleted=1 where ID=#{bid_id}")
	void cancelBid(int bid_id);

	@Update("update  zkxy_bid_Pds set deleted=1 where bid_id=#{bid_id}")
	void cancelBidPds(int bid_id);

	@Update("update  zkxy_bid_detail set deleted=1 where ID=#{detail_Id}")
	void cancelBidDetail(int detail_Id);

	@Insert("insert into zkxy_bid_detail (UserID ,bid_id,pds_id,price,requirement,remark,bid_detail_status) "
			+ "values (#{UserID},#{bid_id},#{pds_id},#{price},#{requirement},#{remark},#{bid_status})")
	int AddBidDetailInfo(BidDetail bidDetail);

	@Insert("<script>" + "insert into zkxy_bid_pds (bid_id ,pds_id,yield,price,remark) " + "values "
			+ "<foreach collection =\"bidPdsinfos\" item=\"info\" index= \"index\" separator =\",\"> "
			+ "(#{info.bid_id},#{info.pds_id},#{info.yield},#{info.price},#{info.remark}) " + "</foreach > "
			+ "</script>")
	void AddBidPdsInfo(@Param("bidPdsinfos") List<BidPds> bidPdsinfos);

	@Insert("insert into zkxy_bid_pds (bid_id ,pds_id,yield,price,remark) "
			+ "values (#{bidPds.bid_id},#{bidPds.pds_id},#{bidPds.yield},#{bidPds.price},#{bidPds.remark})")
	void insertBidPdsInfo(@Param("bidPds") BidPds bidPds);

	@Select("select a.*,b.p_type from zkxy_bid_pds a,zkxy_products b where a.id=#{id} 	and a.deleted!=1 and a.pds_id=b.id")
	BidPds FindBidPdsById(@Param("id") int id);

	@Update("update  zkxy_bid_pds set yield=#{yield},price=#{price},remark#{remark},deleted=0 "
			+ "where ID=#{ID} and bid_id=#{bid_id} and pds_id=#{pds_id}")
	void UpdateBidPdsById(@Param("bidPds") BidPds bidPds);

	@Select("select a.*,b.p_type from zkxy_bid_pds a,zkxy_products b where bid_id=#{bidId}  "
			+ "	and a.deleted!=1 and a.pds_id=b.id")
	List<BidPds> FindBidPdsByBidId(@Param("bidId") int bidId);

	@Select("select a.*,b.p_type from zkxy_bid_pds a,zkxy_products b where bid_id=#{bidId}  "
			+ "	and a.deleted!=1 and a.pds_id={pds_id} and a.pds_id=b.id")
	List<BidPds> FindBidPdsByBidIdAndPds(@Param("bidId") int bidId, @Param("pds_id") int pds_id);

	@Select("select * from zkxy_bid where id=#{id} 	and deleted!=1 ")
	Bid FindBidById(@Param("id") int id);	

	@Delete("delete from zkxy_bid where id=#{id} ")
	void deleteById(@Param("id") int id);

	@Update("update  zkxy_bid set bid_name=#{bid_name}, bid_time=#{bid_time} where id=#{id} ")
	void UpdateBidTime(@Param("bid_name") String bid_name, @Param("id") int id, @Param("bid_time") String bid_time);

	@Select("select Top 100 * from zkxy_bid 	where deleted!=1 order By bid_time desc")
	List<Bid> FindAllBid();

	@Select("select a.*,b.p_type from zkxy_bid_Detail a,zkxy_products b where a.ID=#{id} 	and a.deleted!=1 and a.pds_id=b.id")
	BidDetail FindBidDetailById(@Param("id") int id);
	
	@Select("select a.*,b.p_type from zkxy_bid_Detail a,zkxy_products b where a.UserID=#{user_id} and a.bid_id=#{bid_id} "
			+ " and a.pds_id=#{pds_id}	and a.deleted!=1 and a.pds_id=b.id")
	List<BidDetail> FindBidDetailByPdsId(@Param("user_id") int user_id,@Param("bid_id") int bid_id,@Param("pds_id") int pds_id);

	// SELECT a.*,b.p_type,c.bid_name,e.c_name FROM zkxy_bid_Detail
	// a,zkxy_products b ,zkxy_bid c, zkxy_user d ,zkxy_companys e
	// WHERE a.UserId=d.UserID AND d.Companys=e.id AND a.pds_id=b.id AND
	// a.bid_id=c.ID
	@Select("select a.*,b.p_type,c.bid_name,e.c_name ,c.bid_status as bid_status from "
			+ " zkxy_bid_Detail a,zkxy_products b ,zkxy_bid c, zkxy_user d ,zkxy_companys e "
			+ "where a.UserId=d.UserID 	and a.deleted!=1 AND d.Companys=e.id AND   a.pds_id=b.id AND a.bid_id=c.ID and "
			+ "a.UserId=#{userId} and a.bid_id=#{bid_id} and a.pds_id=b.id order By a.create_time desc")
	List<BidDetail> FindBidDetailByBidAndUserId(@Param("userId") int userId, @Param("bid_id") int bid_id);

	// SELECT a.*,b.p_type,c.bid_name,e.c_name FROM zkxy_bid_Detail
	// a,zkxy_products b ,zkxy_bid c, zkxy_user d ,zkxy_companys e
	// WHERE a.UserId=d.UserID AND d.Companys=e.id AND a.pds_id=b.id AND
	// a.bid_id=c.ID
	@Select("select a.*,b.p_type,c.bid_name,e.c_name ,c.bid_status as bid_status from "
			+ " zkxy_bid_Detail a,zkxy_products b ,zkxy_bid c, zkxy_user d ,zkxy_companys e "
			+ " where a.UserId=d.UserID 	and a.deleted!=1 AND d.Companys=e.id AND   a.pds_id=b.id AND a.bid_id=c.ID and "
			+ " a.bid_id=#{bid_id} and a.pds_id=b.id order By a.requirement desc")
	List<BidDetail> FindBidDetailByBidId(@Param("bid_id") int bid_id);

	/**
	 * 等待开标
	 */
	@Update("update zkxy_bid set bid_status=41  where bid_time< getdate() and deleted=0 and bid_status<41 ")
	void UpdateBidStatusToWatingPingBiao();

	@Update("update zkxy_bid set bid_status=#{bid_status}  where ID=#{bid_id} ")
	void UpdateBidStatus(@Param("bid_id") int bid_id, @Param("bid_status") int bid_status);

}
