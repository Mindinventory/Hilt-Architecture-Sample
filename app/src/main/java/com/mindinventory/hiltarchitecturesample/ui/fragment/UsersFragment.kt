package com.mindinventory.hiltarchitecturesample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindinventory.hiltarchitecturesample.R
import com.mindinventory.hiltarchitecturesample.data.entity.ResponseUsers
import com.mindinventory.hiltarchitecturesample.ui.adapter.UsersAdapter
import com.mindinventory.hiltarchitecturesample.ui.common.Resource
import com.mindinventory.hiltarchitecturesample.ui.common.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_users.*

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private val viewModel : UserViewModel by viewModels { defaultViewModelProviderFactory }
    private var usersAdapter : UsersAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersAdapter = UsersAdapter()
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvUsers.layoutManager = layoutManager
        rvUsers.adapter = usersAdapter

        viewModel.getUsers()

        viewModel.usersEvent.observe(viewLifecycleOwner, this::handleUsers)

    }

    private fun handleUsers(response : Resource<ResponseUsers>)
    {
        when(response.status)
        {
            Status.LOADING -> {
                viewProgress.show()
            }
            Status.SUCCESS -> {
                viewProgress.hide()
                usersAdapter?.updateItems(response.data?.users)
            }
            Status.ERROR -> {
                viewProgress.hide()
            }
        }
    }

}