package com.tongji.database.elecarbackend.Entity;

public class Review {
    private Integer FeedBackId;
    private Integer pileId;
    private Integer userId;
    private Integer rating;
    private String comment;
    private String tradername;
    public Integer getFeedBackId() {
        return FeedBackId;}
    public void setFeedBackId(Integer FeedBackId) {
        this.FeedBackId = FeedBackId;
    }
    public Integer getPileId() {
        return pileId;
    }
    public void setPileId(Integer pileId) {
        this.pileId = pileId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getTradername() {
        return tradername;
    }
    public void setTradername(String tradername) {
        this.tradername = tradername;

    }
    @Override
    public String toString() {
        return "Review [FeedBackId=" + FeedBackId + ", pileId=" + pileId + ", " +
                "userId=" + userId + ", rating=" + rating + ", comment=" + comment + "]"
                + ", tradername=" + tradername + "]";
    }

}
