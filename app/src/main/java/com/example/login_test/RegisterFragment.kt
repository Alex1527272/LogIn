package com.example.login_test


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.login_test.databinding.FragmentRegisterBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
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
            writeJSONFile(username, password)

            findNavController().navigate(R.id.action_RegisterFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun  writeJSONFile(username: String, password: String){
        var json : String? = null
        var alreadyExists : Boolean = false
        try{

            val inputStream: InputStream? = context?.assets?.open("accounts.json")
            json = inputStream?.bufferedReader().use{it?.readText()}


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
                val path = context?.filesDir
                val aux = 0
            }
            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}