package com.example.jetictors.welfare.view.views.home.girl

import android.support.v7.widget.GridLayoutManager
import com.example.jetictors.welfare.R
import com.example.jetictors.welfare.base.BaseFragment
import com.example.jetictors.welfare.modle.bean.JsonResult
import com.example.jetictors.welfare.presenter.BasePresenter
import com.example.jetictors.welfare.presenter.contract.IContract
import com.example.jetictors.welfare.view.UI.EmptyViewUI
import com.example.jetictors.welfare.view.UI.GirlUI
import com.example.jetictors.welfare.view.adapter.GirlAdapter
import com.example.jetictors.welfare.view.widgets.CommonItemDecoration
import kotlinx.android.synthetic.main.layout_common_rv.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.toast

/**
 * 描述    : 妹子fragment
 * author  : Jetictors
 * time    :  2017/10/30 14:38
 * version : v1.0.1
 */
class GirlFragment : BaseFragment<GirlUI,GirlFragment>(), IContract.IView{

    private lateinit var mData : MutableList<JsonResult>
    private lateinit var mAdapter : GirlAdapter
    private val mPresenter : BasePresenter by lazy { BasePresenter() }
    private var page = 1

    init {
        mPresenter.attachView(this)
    }

    override fun getAnkoUI(): GirlUI {
        return GirlUI()
    }

    override fun initView() {
        mData = mutableListOf()

        this.common_rv.layoutManager = GridLayoutManager(activity,2)
        mAdapter = GirlAdapter(ctx,mData)
        this.common_rv.adapter = mAdapter
        this.common_rv.addItemDecoration(CommonItemDecoration(ctx,dip(8),
                R.color.common_clr_white,false, CommonItemDecoration.HORIZONTAL))
        this.common_rv.addItemDecoration(CommonItemDecoration(ctx,dip(8),
                R.color.common_clr_white,false,CommonItemDecoration.VERTICAL))

        initListener()
    }

    override fun initData() {
        mPresenter.getData("福利",20,1)
        this.common_swipe_rfl.isRefreshing = true
    }

    private fun initListener() {
        this.common_swipe_rfl.setOnRefreshListener {
            mPresenter.getData("福利",20,1)
        }

        mAdapter.emptyView = EmptyViewUI(ctx).emptyView()
    }


    override fun getDataSuccess(mData: MutableList<JsonResult>) {
        this.common_swipe_rfl.isRefreshing = false
        if (!mData.isEmpty()){
            mAdapter.setNewData(mData)
        }
    }

    override fun getDataFailed(message: String) {
        this.common_swipe_rfl.isRefreshing = false
        toast(message)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun onDestroyView() {
        mPresenter.detachView()
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = GirlFragment()
    }

}