package com.example.eye.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.eye.R
import org.w3c.dom.Text

class PatientAdapter(var mList: List<PatientData>, val context: Context) :
    RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val meli: TextView = itemView.findViewById(R.id.tv_code_meli)
        val edit: ImageView = itemView.findViewById(R.id.im_edit)
        val history :TextView = itemView.findViewById(R.id.history)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view, parent, false)
        return PatientViewHolder(view)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(mList: List<PatientData>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PatientViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.name.text =
            ("نام و نام خانوادگی : " + mList[position].name + " " + mList[position].lastName)

        holder.meli.text = ("کدملی : " + mList[position].codeMeli)

        holder.edit.setOnClickListener {

            if (onClickListener != null) {
                onClickListener!!.onClick(position, 0)
            }

        }
        holder.itemView.setOnLongClickListener(object : OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {

                onClickListener!!.onClick(position , 2)
                return true
            }


        })
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, 1)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    interface OnClickListener {
        fun onClick(position: Int, item: Int)
    }

}