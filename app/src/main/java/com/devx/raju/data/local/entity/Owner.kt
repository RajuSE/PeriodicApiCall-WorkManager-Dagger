package com.devx.raju.data.local.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
class Owner : Parcelable {
    @JvmField
    var login: String? = null

    @JvmField
    @SerializedName("avatar_url")
    var avatarUrl: String? = null


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.login)
        dest.writeString(this.avatarUrl)
    }

    constructor()

    protected constructor(`in`: Parcel) {
        this.login = `in`.readString()
        this.avatarUrl = `in`.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Owner> = object : Parcelable.Creator<Owner> {
            override fun createFromParcel(source: Parcel): Owner {
                return Owner(source)
            }

            override fun newArray(size: Int): Array<Owner?> {
                return arrayOfNulls(size)
            }
        }
    }


}
