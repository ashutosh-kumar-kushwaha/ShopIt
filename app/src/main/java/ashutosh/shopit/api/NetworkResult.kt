package ashutosh.shopit.api

sealed class NetworkResult<T>(val responseCode: Int? = null, val data : T? = null, val message : String? = null){
    class Success<T>(responseCode: Int, data : T) : NetworkResult<T>(responseCode, data)
    class Error<T>(responseCode: Int, message: String?, data: T? = null) : NetworkResult<T>(responseCode, data, message)
    class Loading<T> : NetworkResult<T>()
}