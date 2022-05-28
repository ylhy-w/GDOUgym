package edu.gdou.gym_java.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.gdou.gym_java.entity.model.Role;
import edu.gdou.gym_java.entity.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liuyuanfeng
 * @since 2022-05-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from User")
    @Results({
            @Result(property = "id",column = "id")
    })
    List<User> getUserList ();

    @Select("select * from User where name=#{name}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "roleId",column = "role_id"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="edu.gdou.gym_java.mapper.RoleMapper.getById")),
    })
    User getUserByName(@Param("name")String name);

    @Select("select * from User where id=#{id}")
    @Results({
            @Result(property = "id",column = "id")
    })
    User getById(@Param("id")Long id);

    //根据用户名[可选]查询管理员用户
    List<User> queryManagerByName(String username);

    /**
     * 查询普通用户信息,不包含密码信息
     * @param page
     * @return
     */
    @Select("select id,name,role_id from User where role_id in (6,7)")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "roleId",column = "role_id"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="edu.gdou.gym_java.mapper.RoleMapper.getById")),
    })
    IPage<User> selectPageUsers(Page page);
}