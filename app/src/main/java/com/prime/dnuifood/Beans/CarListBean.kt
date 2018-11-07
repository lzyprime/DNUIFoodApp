package com.prime.dnuifood.Beans

data class CarListBean(
    var item_id: String,
    var order_id: String,
    var food_id: String,
    var shop_id: String,
    var shopname: String,
    var foodname: String,
    var num: Int,
    var content: String,
    var comment_time: String,
    var pic: String,
    var price: Double,
    var ischecked : Boolean = false)