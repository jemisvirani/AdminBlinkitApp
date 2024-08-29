package com.eclatsol.adminblinkitclone.models

import com.google.firebase.database.IgnoreExtraProperties

data class Product(
    var adminUid: String? = null,
    var itemCount: Int? = null,
    var productCategory: String? = null,
    var productImageUris: ArrayList<String?>? = null,
    var productPrice: Int? = null,
    var productQuantity: Int? = null,
    var productRandomId: String? = null,
    var productStock: Int? = null,
    var productTitle: String? = null,
    var productUnit: String? = null,
    var productType: String? = null,
)



