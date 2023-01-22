package ashutosh.shopit.interfaces

interface ChangeProductQuantity {
    fun increaseProductQuantity(productId: Int)
    fun decreaseProductQuantity(productId: Int)
}