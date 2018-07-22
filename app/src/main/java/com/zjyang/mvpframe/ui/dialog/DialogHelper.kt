package com.zjyang.mvpframe.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.zjyang.mvpframe.R
import com.zjyang.mvpframe.ui.ShapeUtils
import com.zjyang.mvpframe.utils.DrawUtils
import com.zjyang.mvpframe.utils.PermissionUtils

/**
 * Created by 74215 on 2018/7/22.
 */

class DialogHelper{
    companion object {

        private val PERMISSION_DIALOG : String = "permission_dialog"

        fun showPermissionDialog(manager: FragmentManager, msg: String) {
            val dialogFragment = BaseDialogFragment.create(true, 0.7)
            dialogFragment.setDialogCallBack { context ->
                val builder = AlertDialog.Builder(context, R.style.Base_AlertDialog)
                builder.setView(getPermissionDialogView(context, msg, dialogFragment))
                builder.create()
            }
            dialogFragment.setCancelListener { }
            dialogFragment.show(manager, PERMISSION_DIALOG)
        }

        private fun getPermissionDialogView(context: Context, msg: String, dialogFragment: DialogFragment?): View {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_permission, null)
            val msgTv = view.findViewById<TextView>(R.id.dialog_detail)
            val cancelTv = view.findViewById<TextView>(R.id.cancel_tv)
            val agreeTv = view.findViewById<TextView>(R.id.agree_tv)
            val mBgView = view.findViewById<LinearLayout>(R.id.dialog_bg)
            msgTv.text = msg;
            mBgView.background = ShapeUtils.getRoundRectDrawable(DrawUtils.dp2px(4f), Color.parseColor("#ffffff"))
            cancelTv.setOnClickListener {
                dialogFragment!!.dismiss()
            }
            agreeTv.setOnClickListener {
                dialogFragment!!.dismiss()
                PermissionUtils.newInstance().jumpPermissionPage()
            }
            return view
        }
    }
}
