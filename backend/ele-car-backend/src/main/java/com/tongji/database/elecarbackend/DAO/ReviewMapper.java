package com.tongji.database.elecarbackend.DAO;

import com.tongji.database.elecarbackend.Entity.Review;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Insert("insert into chargingpilefeedback(pile_id, user_id,rating,comment,tradername) values(#{pileId}, #{userId}, #{rating}, #{comment},#{tradername})")
    int insertReview(Review review);

    @Select("SELECT * FROM chargingpilefeedback")
    @Results({
            @Result(property = "FeedBackId", column = "Feedback_id"),
            @Result(property = "pileId", column = "pile_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "comment", column = "comment"),
            @Result(property = "tradername", column = "tradername")
    })
    List<Review> getAllReviews();
}
