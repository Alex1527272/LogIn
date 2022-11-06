package com.example.login_test


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.login_test.databinding.FragmentRegisterBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.nio.file.Paths


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.editTextUserName).text.toString()
            val password = view.findViewById<EditText>(R.id.editTextPassword).text.toString()
            val confirmPassword = view.findViewById<EditText>(R.id.editTextConfirmPassword).text.toString()
            if(password!=confirmPassword){
                val myToast = Toast.makeText(context, "Different passwords introduced", Toast.LENGTH_SHORT)

                myToast.show()

                view.findViewById<EditText>(R.id.editTextPassword).text.clear()
                view.findViewById<EditText>(R.id.editTextConfirmPassword).text.clear()
            }else {
                val alreadyExists = writeJSONFile(username, password)

                if(alreadyExists){
                    val myToast = Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT)

                    myToast.show()

                    view.findViewById<EditText>(R.id.editTextUserName).text.clear()
                    view.findViewById<EditText>(R.id.editTextPassword).text.clear()
                    view.findViewById<EditText>(R.id.editTextConfirmPassword).text.clear()
                }
                else{
                    val myToast = Toast.makeText(context, "Register successful", Toast.LENGTH_SHORT)

                    myToast.show()

                    findNavController().navigate(R.id.action_RegisterFragment_to_FirstFragment)
                }

            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun  writeJSONFile(username: String, password: String): Boolean{
        var json : String? = null
        var alreadyExists : Boolean = false
        try{

            val path = context?.filesDir
            val fileDirectory = File(path, "ACCOUNTS")
            val file = File(fileDirectory, "accountFiles.txt")

            json = FileInputStream(file).bufferedReader().use{it.readText()}

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
                var newText = jsonArr.toString().toByteArray()

                FileOutputStream(file).use { it.write(newText) }

            }
            println(FileInputStream(file).bufferedReader().use{it.readText()}.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return alreadyExists
    }

}