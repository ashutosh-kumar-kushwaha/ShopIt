package ashutosh.shopit.models

data class Product (
    val id : Int,
    val image : Int,
    val name : String,
    val rating : String,
    val sold : Int,
    val discountedPrice : String,
    val originalPrice : String
)