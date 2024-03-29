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



/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val register : JSONController = JSONController()

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
                val alreadyExists = register.writeJSONFile(context!!, username, password)

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
}