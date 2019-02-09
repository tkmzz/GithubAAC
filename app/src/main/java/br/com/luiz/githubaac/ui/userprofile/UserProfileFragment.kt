package br.com.luiz.githubaac.ui.userprofile


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.luiz.githubaac.R
import br.com.luiz.githubaac.util.viewmodel.FactoryViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_user_profile.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class UserProfileFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpDagger()
        setUpViewModel()
        setUpView()
    }

    fun setUpDagger(){
        AndroidSupportInjection.inject(this)
    }

    fun setUpView() {
        btSearch.setOnClickListener {
            viewModel.getUser(etUser.text.toString())
            viewModel.user.observe(this, Observer {
                tvUser.text = it?.name
                Picasso.get().load(it?.avatarURL).into(ivUser)
            })
        }
    }

    fun setUpViewModel() {

        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(UserProfileViewModel::class.java)

    }
}
