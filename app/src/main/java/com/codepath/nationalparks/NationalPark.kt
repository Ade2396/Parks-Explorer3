package com.codepath.nationalparks

import com.google.gson.annotations.SerializedName

/**
 * Model for a single park from the National Parks API
 */
class NationalPark {

    // Name
    @JvmField
    @SerializedName("fullName")
    var name: String? = null

    // Description
    @JvmField
    @SerializedName("description")
    var description: String? = null

    // State(s)
    @JvmField
    @SerializedName("states")
    var location: String? = null

    // Images array
    @SerializedName("images")
    var images: List<Image>? = null

    // Convenience: first image URL (if any)
    val imageUrl: String?
        get() = images?.firstOrNull()?.url

    class Image {
        @SerializedName("url")
        var url: String? = null
    }
}
