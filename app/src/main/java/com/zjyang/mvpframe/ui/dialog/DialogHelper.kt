package com.zjyang.mvpframe.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.zjyang.mvpframe.R
import com.zjyang.base.utils.ShapeUtils
import com.zjyang.base.utils.DrawUtils
import com.zjyang.base.utils.PermissionUtils
import com.zjyang.base.widget.dialog.BaseDialogFragment
import com.zjyang.mvpframe.event.FinishActivityEvent
import com.zjyang.mvpframe.module.UserDataManager
import com.zjyang.mvpframe.module.home.view.HomeActivity
import org.greenrobot.eventbus.EventBus

/**
 * Created by 74215 on 2018/7/22.
 */

class DialogHelper{
    companion object {

        private val PERMISSION_DIALOG : String = "permission_dialog"
        private val CONFIRM_DIALOG : String = "confirm_dialog"
        public val EXIT_DIALOG : String = "exit_dialog";

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

        fun showConfirmDialog(manager: FragmentManager, msg: String, entrance: String) {
            val dialogFragment = BaseDialogFragment.create(true, 0.7)
            dialogFragment.setDialogCallBack { context ->
                val builder = AlertDialog.Builder(context, R.style.Base_AlertDialog)
                builder.setView(getConfirmDialogView(context, msg, dialogFragment, entrance))
                builder.create()
            }
            dialogFragment.setCancelListener { }
            dialogFragment.show(manager, CONFIRM_DIALOG)
        }

        private fun getConfirmDialogView(context: Context, msg: String, dialogFragment: DialogFragment?, entrance: String): View {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
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
                if(entrance.equals(EXIT_DIALOG)){
                    //退出账号确认弹框
                    UserDataManager.getInstance().clearCurUserData()
                    EventBus.getDefault().post(FinishActivityEvent())
                    val i = Intent(context, HomeActivity::class.java)
                    context.startActivity(i)
                }
            }
            return view
        }
    }
}
