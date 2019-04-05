package edu.uchicago.kjhawryluk.YelpReviewReceiver

case class YelpReview(
    businessid: String,
    user:String,
    stars:Int,
    review_count:Int=1
    )