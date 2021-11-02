package telegram.free.roomdatabases.Utils

import android.content.Context

class PrefrenceManager(context: Context) {
   var sharedPreferences= context.getSharedPreferences(context.applicationInfo.name,Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()
    fun SaveLogindata( string: String)
    {
        editor.putString(Constants.UserDao.toString(),string).commit()
    }
    fun getLoginData():String
    {
       return sharedPreferences.getString(Constants.UserDao.toString(),"")!!
    }
}