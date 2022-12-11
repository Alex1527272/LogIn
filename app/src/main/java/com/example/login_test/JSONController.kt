package com.example.login_test

import android.content.Context
import io.michaelrocks.paranoid.Obfuscate
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

@Obfuscate
class JSONController {
    public fun  checkPassword(context: Context, username: String, password: String): Boolean {
        var correctUser : Boolean = false
        try{
            val path = context.filesDir
            val fileDirectory = File(path, "ACCOUNTS")
            val file = File(fileDirectory, "accountFiles.txt")

            val json = FileInputStream(file).bufferedReader().use{it.readText()}

            val jsonArr = JSONArray(json)

            for(i in 0 until jsonArr.length()){
                val jsonObj = jsonArr.getJSONObject(i)
                if(jsonObj.get("username") == username && jsonObj.get("password") == password){
                    correctUser = true
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return correctUser
    }

    public fun  writeJSONFile(context: Context, username: String, password: String): Boolean{
        var alreadyExists : Boolean = false
        try{

            val path = context.filesDir
            val fileDirectory = File(path, "ACCOUNTS")
            val file = File(fileDirectory, "accountFiles.txt")

            val json = FileInputStream(file).bufferedReader().use{it.readText()}

            val jsonArr = JSONArray(json)

            for(i in 0 until jsonArr.length()){
                val jsonObj = jsonArr.getJSONObject(i)
                if(jsonObj.get("username") == username){
                    alreadyExists = true
                }
            }
            if(!alreadyExists) {
                val newUser = JSONObject().put("username", username).put("password", password)
                jsonArr.put(newUser)
                val newText = jsonArr.toString().toByteArray()

                FileOutputStream(file).use { it.write(newText) }

            }
            println(FileInputStream(file).bufferedReader().use{it.readText()}.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return alreadyExists
    }

    public fun initFiles(context: Context){
        try {

            val inputStream: InputStream? = context.assets?.open("accounts.json")
            val accounts = inputStream?.bufferedReader().use{it?.readText()}

            val path = context.filesDir
            val fileDirectory = File(path, "ACCOUNTS")
            fileDirectory.mkdirs()

            val file = File(fileDirectory, "accountFiles.txt")

            if(FileInputStream(file).bufferedReader().use{it.readText()} == "") {
                FileOutputStream(file).use {
                    it.write(accounts?.toByteArray())
                }
                println("CREAT")
            }
            val test = FileInputStream(file).bufferedReader().use{it.readText()}
            println(test)
            println("______________________________________________________")
        }catch(e: Exception){
            e.printStackTrace()
        }
    }
}