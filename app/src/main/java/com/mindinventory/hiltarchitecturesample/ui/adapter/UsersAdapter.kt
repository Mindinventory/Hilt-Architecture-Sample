package com.mindinventory.hiltarchitecturesample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mindinventory.hiltarchitecturesample.R
import com.mindinventory.hiltarchitecturesample.data.entity.User
import kotlinx.android.synthetic.main.row_user.view.*

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val users = ArrayList<User>()

    fun updateItems(users : ArrayList<User>?, isClearAll:Boolean = true)
    {
        users?.let {

            if (isClearAll)
            {
                this.users.clear()
            }
            this.users.addAll(it)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        users[position].let {
            holder.itemView.tvUser.text = it.name?.getFullName()
            holder.itemView.tvEmail.text = it.email
            holder.itemView.ivUser.load(it.picture?.thumbnail) {
                crossfade(true)
                placeholder(R.drawable.ic_user)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}