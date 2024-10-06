package com.devx.raju.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.devx.raju.data.local.converter.TimestampConverter
import com.google.gson.annotations.SerializedName

@Entity
class GithubEntity : Parcelable {
    @JvmField
    @PrimaryKey
    var id: Long = 0L

    @JvmField
    var page: Long? = null

    @JvmField
    var totalPages: Long? = null

    @JvmField
    var name: String? = null

    @JvmField
    @SerializedName("full_name")
    var fullName: String? = null

    @JvmField
    @Embedded
    var owner: Owner? = null

    @JvmField
    @SerializedName("html_url")
    var htmlUrl: String? = null

    @JvmField
    var description: String? = null

    @JvmField
    @SerializedName("contributors_url")
    var contributorsUrl: String? = null

    @JvmField
    @TypeConverters(TimestampConverter::class)
    @SerializedName("created_at")
    var createdAt: String? = null

    @JvmField
    @SerializedName("stargazers_count")
    var starsCount: Long? = null

    @JvmField
    var watchers: Long? = null
    @JvmField
    var forks: Long? = null
    @JvmField
    var language: String? = null

    val isLastPage: Boolean
        get() = page!! >= totalPages!!

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(this.id)
        dest.writeValue(this.page)
        dest.writeValue(this.totalPages)
        dest.writeString(this.name)
        dest.writeString(this.fullName)
        dest.writeParcelable(this.owner, flags)
        dest.writeString(this.htmlUrl)
        dest.writeString(this.description)
        dest.writeString(this.contributorsUrl)
        dest.writeString(this.createdAt)
        dest.writeValue(this.starsCount)
        dest.writeValue(this.watchers)
        dest.writeValue(this.forks)
        dest.writeString(this.language)
    }

    constructor()

    protected constructor(`in`: Parcel) {
        this.id = (`in`.readValue(Long::class.java.classLoader) as Long?)!!
        this.page = `in`.readValue(Long::class.java.classLoader) as Long?
        this.totalPages = `in`.readValue(Long::class.java.classLoader) as Long?
        this.name = `in`.readString()
        this.fullName = `in`.readString()
        this.owner = `in`.readParcelable(Owner::class.java.classLoader)
        this.htmlUrl = `in`.readString()
        this.description = `in`.readString()
        this.contributorsUrl = `in`.readString()
        this.createdAt = `in`.readString()
        this.starsCount = `in`.readValue(Long::class.java.classLoader) as Long?
        this.watchers = `in`.readValue(Long::class.java.classLoader) as Long?
        this.forks = `in`.readValue(Long::class.java.classLoader) as Long?
        this.language = `in`.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<GithubEntity?> = object : Parcelable.Creator<GithubEntity?> {
            override fun createFromParcel(source: Parcel): GithubEntity? {
                return GithubEntity(source)
            }

            override fun newArray(size: Int): Array<GithubEntity?> {
                return arrayOfNulls(size)
            }
        }
    }


}
