package zkxy.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zkxy.model.User;

 

public interface UserRepository {

	@Select("SELECT count(UserID) FROM zkxy_user") 
	int countItems();

    @Select("SELECT  *   FROM  zkxy_user ") 
    List<User> getAll();
    
    @Select("SELECT  *   FROM  zkxy_user  where UserId=#{userId}") 
    User getUserById(@Param("userId") int userId);
    
    @Select("SELECT  *  FROM zkxy_user  where UserName=#{userName} and LoginPsd=#{password}  " ) 
    User getUserByLogin(@Param("userName") String userName,@Param("password") String password);
    
    @Select("SELECT  *  FROM  zkxy_user  where UserPhone=#{phone} and LoginPsd=#{password}  " ) 
    User getUserByPhoneLogin(@Param("phone")  Number phone,@Param("password")  String password);
    
    @Update("Update zkxy_user set LoginPsd=#{password},FirstLogin=0 where UserName=#{userName}")
    void updatePassword(@Param("password")  String password,@Param("userName") String userName);
}
