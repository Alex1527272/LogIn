package com.example.login_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.set
import androidx.navigation.fragment.findNavController
import com.example.login_test.databinding.FragmentLoginBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonSecond.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.editTextUserName).text.toString()
            val password = view.findViewById<EditText>(R.id.editTextPassword).text.toString()
            val context = context
            val login = JSONController()
            if(login.checkPassword(context!!, username, password)) {
                val myToast = Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT)

                myToast.show()
            }
            else{
                val myToast = Toast.makeText(context, "Incorrect user or password", Toast.LENGTH_SHORT)

                myToast.show()

                view.findViewById<EditText>(R.id.editTextUserName).text.clear()
                view.findViewById<EditText>(R.id.editTextPassword).text.clear()
            }
                //findNavController().navigate(R.id.action_LoginFragment_to_FirstFragment)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}