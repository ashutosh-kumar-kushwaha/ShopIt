package ashutosh.shopit.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ashutosh.shopit.models.LogInInfo
import kotlinx.coroutines.flow.map
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("dataStore")

class DataStoreManager(val context : Context) {

    companion object PreferenceKey{

        val accessToken = stringPreferencesKey("accessToken")
        val refreshToken = stringPreferencesKey("refreshToken")
        val logInState = booleanPreferencesKey("logInState")
        val firstName = stringPreferencesKey("firstName")
        val lastName = stringPreferencesKey("lastName")
        val role = stringPreferencesKey("role")
    }

    suspend fun storeLogInInfo(logInInfo: LogInInfo){
        context.dataStore.edit {
            it[accessToken] = logInInfo.accessToken!!
            it[refreshToken] = logInInfo.refreshToken!!
            it[logInState] = logInInfo.logInState
            it[firstName] = logInInfo.firstName!!
            it[lastName] = logInInfo.lastName!!
            it[role] = logInInfo.role!!
        }
    }

    suspend fun getLogInInfo() = context.dataStore.data.map {
        LogInInfo(
            accessToken = it[accessToken]?:"",
            refreshToken = it[refreshToken]?:"",
            logInState = it[logInState]?:false,
            firstName = it[firstName]?:"",
            lastName = it[lastName]?:"",
            role = it[role]?:""
        )
    }

    suspend fun deleteLogInInfo(){
        storeLogInInfo(LogInInfo("", "", false, "", "", ""))
    }
}