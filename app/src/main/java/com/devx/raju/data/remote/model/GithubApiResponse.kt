package com.devx.raju.data.remote.model

import android.os.Parcelable
import com.devx.raju.data.local.entity.GithubEntity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class GithubApiResponse : Parcelable {


    @SerializedName("total_count")
    var totalCount: Long? = null
    var items: List<GithubEntity> ? =null

    override fun describeContents(): Int {
        return 0
    }




}