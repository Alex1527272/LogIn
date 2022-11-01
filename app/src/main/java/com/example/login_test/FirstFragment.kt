package com.example.login_test

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.login_test.databinding.FragmentFirstBinding
import java.io.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        try {
            val inputStream: InputStream? = context?.assets?.open("accounts.json")
            var accounts = inputStream?.bufferedReader().use{it?.readText()}

            val path = context?.filesDir
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

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_LoginFragment)
        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_RegisterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}